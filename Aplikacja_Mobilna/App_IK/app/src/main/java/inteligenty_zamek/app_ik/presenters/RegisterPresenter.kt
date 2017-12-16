package inteligenty_zamek.app_ik.presenters

import android.content.Intent
import android.util.Base64
import android.view.View
import android.widget.TextView
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap
import android.app.Activity
import inteligenty_zamek.app_ik.*
import inteligenty_zamek.app_ik.API.*
import inteligenty_zamek.app_ik.Views.LoginActivity
import inteligenty_zamek.app_ik.Views.RegisterActivity
import inteligenty_zamek.app_ik.models.RegisterModel


/**
 * Created by Damian on 07.11.2017.
 */
class RegisterPresenter (val view:RegisterActivity) {

    init{
        setvaluefromsharedPreference()

    }

    val model: RegisterModel = RegisterModel()

    fun setvaluefromsharedPreference()
    {
        view.setDefaultValue(
                sharedPreferenceApi.getString(view, EnumChoice.ip),
                sharedPreferenceApi.getString(view, EnumChoice.login),
                sharedPreferenceApi.getString(view, EnumChoice.nameuser),
                sharedPreferenceApi.getString(view, EnumChoice.surname)
        )
    }

    fun registerResult(response: String) {

        try {
                if (JSONObject(response).getString("status") == "ok") {
                   val stringKey = Base64.encodeToString(model.pair!!.private.encoded, Base64.DEFAULT)
                    try {
                        fileReadWriteApi.writeToFile(
                                CyptographyApi.encrypt(stringKey,model.user!!.password), view, "*" + model.user!!.login)

                    } catch (e: Exception) { }

                    if (JSONObject(response).getString("data") != "empty") {
                        val arrJson = JSONObject(response).getJSONArray("data")
                        try {
                            fileReadWriteApi.writeToFile(
                                    arrJson.getJSONObject(0).toString(), view, "**" + model.user!!.login)
                            val value = HashMap<EnumChoice, String>()
                            value.put(EnumChoice.publicKey,arrJson.toString(1))
                            sharedPreferenceApi.set(view,value)
                        } catch (e: Exception) { }
                    }

                    view.showMessage("nastapiła poprawna rejestracja")
                    val intent = Intent(view, LoginActivity::class.java)
                    view.startActivity(intent)

                } else {
                    view.showMessage("wystąpił błą podczas rejestracji")

                }

        } catch (e: JSONException) {}

    }

    fun sendData(login: String, password: String, name: String, surname: String,
                 ip: String) {

       var validation=false
        if(!Valdiation.isCorrectPassword(password))
        {
            view.tooltip(2);
            validation=true
        }
        if(name.equals(""))
        {
            view.tooltip(4);
            validation=true

        }
        if(surname.equals(""))
        {
            view.tooltip(5);
            validation=true

        }
        if(!Valdiation.isCorrectIP(ip)) {
            view.tooltip(3);
            validation=true
        }

        if(validation==true)
        {
            return
        }


        var stringKey = ""
        try {
            model.pair = CyptographyApi.KeyPairGenerator()
        } catch (e: Exception) {
        }

        if (model.pair!!.public != null) {
            stringKey = Base64.encodeToString(model.pair!!.public.encoded, Base64.DEFAULT)

        }

        //warunek sprawdzajacy poprawnosc loginu ip i hasla
        if (Valdiation.isCorrectIP(ip) && Valdiation.isCorrectLogin(login) && Valdiation.isCorrectPassword(password)) {

            model.setRegisterValue(login, password, name, surname, ip)
            val toSend: HashMap<String, String> = HashMap()
            toSend.put("login", login)
            toSend.put("password", model!!.user!!.passwordHash)
            toSend.put("name", name)
            toSend.put("surname", surname)
            toSend.put("publickkey", stringKey)

            val value = HashMap<EnumChoice, String>()
            value.put(EnumChoice.ip, ip)
            value.put(EnumChoice.login, login)
            value.put(EnumChoice.nameuser, name)
            value.put(EnumChoice.surname, surname)
            value.put(EnumChoice.password,password)
            sharedPreferenceApi.set(view, value)

            try {
                HTTPRequestAPI(this, "http://" + ip + ":8080/api/register/","registerResult" , toSend).execute()
            } catch (e: Exception) {
                    return
            }


        }
        return
    }


}

