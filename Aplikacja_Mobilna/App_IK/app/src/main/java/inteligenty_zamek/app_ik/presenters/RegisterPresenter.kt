package inteligenty_zamek.app_ik.presenters

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.Base64
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.util.HashMap
import android.app.Activity
import inteligenty_zamek.app_ik.*
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.API.Valdiation
import inteligenty_zamek.app_ik.API.fileReadWriteApi
import inteligenty_zamek.app_ik.Views.LoginActivity
import inteligenty_zamek.app_ik.models.RegisterModel
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer


/**
 * Created by Damian on 07.11.2017.
 */
public class RegisterPresenter (val view:Context) {

    val model: RegisterModel = RegisterModel()


    fun registerResult(response: String) {
        var jObj: JSONObject? = null
        try {
            if (response != null) {
                jObj = JSONObject(response)
                if (jObj.getString("status") == "REGISTER OK") {
                    var stringKey = ""
                    stringKey = Base64.encodeToString(model!!.pair!!.private.encoded, Base64.DEFAULT)
                    //TODO szyforwanie klucza


                    try {
                        fileReadWriteApi.writeToFile(
                                CyptographyApi.encrypt(stringKey), view, "*" + model!!.user!!.login)
                    } catch (e: Exception) {
                    }

                    (view.applicationContext as GlobalClassContainer).privatekye = model!!.pair!!.private
                    val toast = Toast.makeText(view, "nastapiła poprawna rejestracja", Toast.LENGTH_LONG)
                    toast.show()
                    object : CountDownTimer(model!!.toastDelay.toLong(), 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            toast.show()
                        }

                        override fun onFinish() {
                            toast.show()
                        }
                    }.start()
                    val intent = Intent(view, LoginActivity::class.java)
                    view.startActivity(intent)

                } else {
                    (view as Activity).runOnUiThread {
                        val textView = view.findViewById(R.id.loginerrortextview) as TextView
                        textView.visibility = View.VISIBLE
                        val textView2 = view.findViewById(R.id.warning_ico1) as TextView
                        textView2.visibility = View.VISIBLE
                    }

                }
            }
        } catch (e: JSONException) {
        }

    }
    public fun sendData(login: String, password: String, name: String, surname: String, ip: String): Boolean {

        var stringKey = ""
        try {
           model!!.pair = this.generateKeyPair()
        } catch (e: Exception) {
        }

        if (model!!.pair!!.public != null) {
            stringKey = Base64.encodeToString(model!!.pair!!.public.encoded, Base64.DEFAULT)

        }

        //warunek sprawdzajacy poprawnosc loginu ip i hasla
        if (Valdiation.isCorrectIP(ip) && Valdiation.isCorrectLogin(login) && Valdiation.isCorrectPassword(password)) {
            val toSend: HashMap<String, String> = HashMap()
            toSend.put("login", login)
            toSend.put("password", password)
            toSend.put("name", name)
            toSend.put("surname", surname)
            toSend.put("publickkey", stringKey)

            model!!.setRegisterValue(login,password,name,surname,ip)

            val sharedPref = view.getSharedPreferences(view.getString(R.string.SPName), Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("login",login)
            editor.putString("ipserwer",ip)
            editor.putString("password", CyptographyApi.encrypt(password))
            editor.putString("name",name)
            editor.putString("surname",surname)
            editor.commit()

            try {
                HTTPRequestAPI(this, "http://" + ip + ":8080/api/register/", 2, toSend).execute()
            } catch (e: Exception) {

                /////////////tu lepiej true i wyswietlić error z view
                return false
            }

            return true
        }
        return false

    }
    fun generateKeyPair(): KeyPair {
        val generator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(1024, SecureRandom())
        return generator.generateKeyPair()
    }

}

