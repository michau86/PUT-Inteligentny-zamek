package inteligenty_zamek.app_ik.Views

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.CountDownTimer
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.tooltip.Tooltip
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.presenters.RegisterPresenter
import inteligenty_zamek.app_ik.API.Valdiation
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import java.util.HashMap

class RegisterActivity : Activity() {


    private var presenter : RegisterPresenter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter= RegisterPresenter(this)

        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        val sampleText = this.findViewById(R.id.warning_ico1) as TextView
        sampleText.typeface = fontFamily
        var sampleText2 = this.findViewById(R.id.warning_ico2) as TextView
        sampleText2.typeface = fontFamily
        sampleText2 = this.findViewById(R.id.warning_ico3) as TextView
        sampleText2.typeface = fontFamily


        ////////////////////////////////////////////////////////
        ////ustawienie domyślnych wartośći//////////////////////
        val login = findViewById(R.id.editText_Login) as EditText
        val name = findViewById(R.id.editTextName) as EditText
        val surname = findViewById(R.id.editTextSurname) as EditText
        val ipserwer = findViewById(R.id.ipserwertextview) as EditText

        ipserwer.setText(sharedPreferenceApi.getString(this, EnumChoice.ip))
        login.setText(sharedPreferenceApi.getString(this, EnumChoice.login))
        name.setText(sharedPreferenceApi.getString(this, EnumChoice.nameuser))
        surname.setText(sharedPreferenceApi.getString(this, EnumChoice.surname))



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



                            if(!presenter!!.sendData( login.text.toString(), password.text.toString(),
                                    name.text.toString(),surname.text.toString(),ipserwer.text.toString()))
                            {
                                val toast = Toast.makeText(this@RegisterActivity, "wystąpił problem z połaczeniem", Toast.LENGTH_LONG)
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

    override fun onPause() {
        super.onPause()
        val value = HashMap<EnumChoice, String>()
        val login = findViewById(R.id.editText_Login) as EditText
        val name = findViewById(R.id.editTextName) as EditText
        val surname = findViewById(R.id.editTextSurname) as EditText
        val ipserwer = findViewById(R.id.ipserwertextview) as EditText

        value.put(EnumChoice.ip, ipserwer.text.toString())
        value.put(EnumChoice.login, login.text.toString())
        value.put(EnumChoice.nameuser, name.text.toString())
        value.put(EnumChoice.surname, surname.text.toString())
        sharedPreferenceApi.set(this, value)
    }

    override fun onResume() {
        super.onResume()

        val login = findViewById(R.id.editText_Login) as EditText
        val name = findViewById(R.id.editTextName) as EditText
        val surname = findViewById(R.id.editTextSurname) as EditText
        val ipserwer = findViewById(R.id.ipserwertextview) as EditText

        ipserwer.setText(sharedPreferenceApi.getString(this, EnumChoice.ip))
        login.setText(sharedPreferenceApi.getString(this, EnumChoice.login))
        name.setText(sharedPreferenceApi.getString(this, EnumChoice.nameuser))
        surname.setText(sharedPreferenceApi.getString(this, EnumChoice.surname))
    }


}






