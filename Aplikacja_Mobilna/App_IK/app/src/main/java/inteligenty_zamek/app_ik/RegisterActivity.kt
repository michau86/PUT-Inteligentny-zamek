package inteligenty_zamek.app_ik

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.CountDownTimer
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.tooltip.Tooltip

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.util.ArrayList
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterActivity : Activity() {


    private var presenter : RegisterPresenter?= null
    private var  preferences: SharedPreferences?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter=RegisterPresenter(this)

        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        val sampleText = this.findViewById(R.id.warning_ico1) as TextView
        sampleText.typeface = fontFamily
        var sampleText2 = this.findViewById(R.id.warning_ico2) as TextView
        sampleText2.typeface = fontFamily
        sampleText2 = this.findViewById(R.id.warning_ico3) as TextView
        sampleText2.typeface = fontFamily


        ////////////////////////////////////////////////////////
        ////ustawienie domyślnych wartośći//////////////////////
        val ipserwerregister = this.findViewById(R.id.ipserwertextview) as TextView
        ipserwerregister.text = (application as GlobalClassContainer).serwerIP



        val register = findViewById(R.id.button_Register) as Button
        register.setOnClickListener {
            var textView = findViewById(R.id.loginerrortextview) as TextView
            textView.visibility = View.INVISIBLE
            var textView2 = findViewById(R.id.warning_ico1) as TextView
            textView2.visibility = View.INVISIBLE

            textView = findViewById(R.id.iperrortextview) as TextView
            textView.visibility = View.INVISIBLE
            textView2 = findViewById(R.id.warning_ico2) as TextView
            textView2.visibility = View.INVISIBLE

            textView = findViewById(R.id.passworderrorTextView) as TextView
            textView.visibility = View.INVISIBLE
            textView2 = findViewById(R.id.warning_ico3) as TextView
            textView2.visibility = View.INVISIBLE

            val login = findViewById(R.id.editText_Login) as EditText
            val password = findViewById(R.id.editText_Password2) as EditText
            val password2 = findViewById(R.id.editText_Password2) as EditText
            val name = findViewById(R.id.editTextName) as EditText
            val surname = findViewById(R.id.editTextSurname) as EditText
            val ipserwer = findViewById(R.id.ipserwertextview) as EditText



            //warunek sprawdzajacy czy wszystkie pola sa wypelnione
            if (login.text.toString() != "" &&  name.text.toString() != "" &&  password.text.toString() != "" && surname.text.toString() != "") {
                //warunek sprawdzajacy poprawność ip
                if (Valdiation.isCorrectIP(ipserwer.text.toString())) {
                    //warunek sprawdzajacy poprawnosc hasla
                    if (Valdiation.isCorrectPassword(password.text.toString())) {
                        if (password.text.toString() == password2.text.toString()) {



                            presenter!!.sendData( login.text.toString(), password.text.toString(),
                                    name.text.toString(),surname.text.toString(),ipserwer.text.toString())



                        } else {
                            val tooltip = Tooltip.Builder(password2)
                                    .setText("hasła sie nie zgadzają")
                                    .setTextColor(Color.WHITE)
                                    .setBackgroundColor(resources.getColor(R.color.color_background_list))
                                    .setCancelable(true)
                                    .setGravity(Gravity.TOP)
                                    .setCornerRadius(8f)
                                    .show()
                        }
                    } else {

                        val tooltip = Tooltip.Builder(password)
                                .setText("hasło musi spełniać warunki:\n-długość minimum 8 znaków\n-posiadać duzy znak\n-posiadać znak specjalny (!@#$%^)\nposiadać cyfrę\n ")
                                .setTextColor(Color.WHITE)
                                .setBackgroundColor(resources.getColor(R.color.color_background_list))
                                .setCancelable(true)
                                .setGravity(Gravity.TOP)
                                .setCornerRadius(8f)
                                .show()

                        textView = findViewById(R.id.passworderrorTextView) as TextView
                        textView.visibility = View.VISIBLE
                        textView2 = findViewById(R.id.warning_ico2) as TextView
                        textView2.visibility = View.VISIBLE
                    }
                } else {
                    if (Valdiation.isCorrectPassword(password.text.toString())) {
                        textView = findViewById(R.id.iperrortextview) as TextView
                        textView.visibility = View.VISIBLE
                        textView2 = findViewById(R.id.warning_ico3) as TextView
                        textView2.visibility = View.VISIBLE
                    } else {
                        textView = findViewById(R.id.passworderrorTextView) as TextView
                        textView.visibility = View.VISIBLE
                        textView2 = findViewById(R.id.warning_ico2) as TextView
                        textView2.visibility = View.VISIBLE
                        val textView3 = findViewById(R.id.iperrortextview) as TextView
                        textView3.visibility = View.VISIBLE
                        val textView4 = findViewById(R.id.warning_ico3) as TextView
                        textView4.visibility = View.VISIBLE
                    }
                }
            } else {
                val toast = Toast.makeText(this@RegisterActivity, "wypełnij wszystkie pola", Toast.LENGTH_LONG)
                toast.show()
                object : CountDownTimer(presenter!!.model!!.toastDelay.toLong(), 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        toast.show()
                    }

                    override fun onFinish() {
                        toast.show()
                    }
                }.start()
            }
        }
    }












}






