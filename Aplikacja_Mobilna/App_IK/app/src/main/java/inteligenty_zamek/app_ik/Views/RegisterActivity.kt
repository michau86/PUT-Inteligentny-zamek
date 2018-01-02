package inteligenty_zamek.app_ik.Views
import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.tooltip.Tooltip
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.presenters.RegisterPresenter
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import java.util.HashMap
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod

class RegisterActivity : Activity() {
    private var presenter : RegisterPresenter?= null
    private var originalDrawable:Drawable ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter= RegisterPresenter(this)

        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        val login = findViewById(R.id.registerEditTextLogin) as EditText
        val name = findViewById(R.id.registerEditTextName) as EditText
        val surname = findViewById(R.id.registerEditTextSurname) as EditText
        val ipserwer = findViewById(R.id.registerEditTextIPAdres) as EditText
        val password = findViewById(R.id.registerEditTextPassword) as EditText

        var sampleText = this.findViewById(R.id.registerTextViewErrorLoginIco) as TextView
        sampleText.typeface = fontFamily
        sampleText = this.findViewById(R.id.registerTextViewSeeIco) as TextView
        sampleText.typeface = fontFamily
        sampleText.setOnClickListener {
        if(sampleText.text.toString().equals("\uf06e"))
        {
            sampleText.text="\uf070"
            sampleText.typeface = fontFamily
            val et=findViewById(R.id.registerEditTextPassword) as EditText
            et.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        else
        {
            sampleText.text="\uf06e"
            sampleText.typeface = fontFamily

            val et=findViewById(R.id.registerEditTextPassword) as EditText
            et.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        }

/////////listeners for input ////////////////////////////////////////////////////

        /////pasword
            password.setOnClickListener(View.OnClickListener {
                setTooltip(password)
            })

        password.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                setTooltip(password)
            }
        }
////////////////////////////////////////////////////////////////////////////////
/////////// name

        name.setOnClickListener(View.OnClickListener {
            name.background.setColorFilter(ResourcesCompat.getColor(resources,
                    R.color.inpuLneColor, this.theme), PorterDuff.Mode.SRC_ATOP)
        })

        name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                name.background.setColorFilter(ResourcesCompat.getColor(resources,
                        R.color.inpuLneColor, this.theme), PorterDuff.Mode.SRC_ATOP)
            }
        }

//////////////////////////////////////////////////////////////////////////////
/////surname
        surname.setOnClickListener(View.OnClickListener {
            surname.background.setColorFilter(ResourcesCompat.getColor(resources,
                    R.color.inpuLneColor, this.theme), PorterDuff.Mode.SRC_ATOP)
        })

        surname.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                surname.background.setColorFilter(ResourcesCompat.getColor(resources,
                        R.color.inpuLneColor, this.theme), PorterDuff.Mode.SRC_ATOP)
            }
        }
/////////////////////////////////////////////////////////////////////////////
//////ip
        ipserwer.setOnClickListener(View.OnClickListener {
            ipserwer.background.setColorFilter(ResourcesCompat.getColor(resources,
                    R.color.inpuLneColor, this.theme), PorterDuff.Mode.SRC_ATOP)
        })

        ipserwer.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                ipserwer.background.setColorFilter(ResourcesCompat.getColor(resources,
                        R.color.inpuLneColor, this.theme), PorterDuff.Mode.SRC_ATOP)
            }
        }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

            val register = findViewById(R.id.registerButtonRegister) as Button
            register.setOnClickListener {

                presenter!!.sendData(login.text.toString(), password.text.toString(),
                        name.text.toString(), surname.text.toString(), ipserwer.text.toString())
            }

        originalDrawable = password.background
    }

fun setTooltip(password:EditText)
{
    originalDrawable!!.setColorFilter(ResourcesCompat.getColor(resources, R.color.inpuLneColor, this.theme), PorterDuff.Mode.SRC_ATOP) // change the drawable color

    Tooltip.Builder(password)
            .setText("hasło musi spełniać warunki:\n-długość minimum 8 znaków" +
                    "\n-posiadać duzy znak\n-posiadać znak specjalny (!@#$%^)\nposiadać cyfrę\n ")
            .setTextColor(Color.WHITE)
            .setBackgroundColor(ContextCompat.getColor(this, R.color.color_background_list))
            .setCancelable(true)
            .setGravity(Gravity.BOTTOM)
            .setCornerRadius(8f)
            .setPadding(20f)
            .show()
}

    fun tooltip(i:Int)
    {
        var textView = findViewById(R.id.registerTextViewLoginError) as TextView
        textView.visibility = View.VISIBLE
        val params = textView.layoutParams
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        textView.layoutParams = params
        textView = findViewById(R.id.registerTextViewErrorLoginIco) as TextView
        textView.visibility = View.VISIBLE
        textView.layoutParams = params

        when(i) {

            2-> {
                val et=findViewById(R.id.registerEditTextPassword)
                val drawable:Drawable = et.background // get current EditText drawable
                drawable.setColorFilter(ResourcesCompat.getColor(resources, R.color.errorColor, this.theme), PorterDuff.Mode.SRC_ATOP) // change the drawable color
                et.background = drawable // set the new drawable to EditTex
            }

            3-> {
                val et=findViewById(R.id.registerEditTextIPAdres)
                val drawable:Drawable = et.background
                drawable.setColorFilter(ResourcesCompat.getColor(resources, R.color.errorColor, this.theme), PorterDuff.Mode.SRC_ATOP) // change the drawable color
                et.background = drawable // set the new drawable to EditTex
            }

            4->
            {
                val et=findViewById(R.id.registerEditTextName)
                val drawable:Drawable = et.background
                drawable.setColorFilter(ResourcesCompat.getColor(resources, R.color.errorColor, this.theme), PorterDuff.Mode.SRC_ATOP) // change the drawable color
                et.background = drawable // set the new drawable to EditTex
            }

            5->
            {
                val et=findViewById(R.id.registerEditTextSurname)
                val drawable:Drawable = et.background
                drawable.setColorFilter(ResourcesCompat.getColor(resources, R.color.errorColor, this.theme), PorterDuff.Mode.SRC_ATOP) // change the drawable color
                et.background = drawable // set the new drawable to EditTex
            }
        }
    }

    fun setDefaultValue(ip:String,log:String,nameuser:String,surnam:String)
    {
        val login = findViewById(R.id.registerEditTextLogin) as EditText
        val name = findViewById(R.id.registerEditTextName) as EditText
        val surname = findViewById(R.id.registerEditTextSurname) as EditText
        val ipserwer = findViewById(R.id.registerEditTextIPAdres) as EditText
        ipserwer.setText(ip)
        login.setText(log)
        name.setText(nameuser)
        surname.setText(surnam)
    }

    fun showMessage(message:String)
    {
        val toast = Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG)
        toast.show()
        object : CountDownTimer(presenter!!.model.toastDelay.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                toast.show()
            }
            override fun onFinish() {
                toast.show()
            }
        }.start()
    }

    override fun onPause() {
        super.onPause()
        val value = HashMap<EnumChoice, String>()
        val login = findViewById(R.id.registerEditTextLogin) as EditText
        val name = findViewById(R.id.registerEditTextName) as EditText
        val surname = findViewById(R.id.registerEditTextSurname) as EditText
        val ipserwer = findViewById(R.id.registerEditTextIPAdres) as EditText
        value.put(EnumChoice.ip, ipserwer.text.toString())
        value.put(EnumChoice.login, login.text.toString())
        value.put(EnumChoice.nameuser, name.text.toString())
        value.put(EnumChoice.surname, surname.text.toString())
        sharedPreferenceApi.set(this, value)
    }

    override fun onResume() {
        super.onResume()
        val login = findViewById(R.id.registerEditTextLogin) as EditText
        val name = findViewById(R.id.registerEditTextName) as EditText
        val surname = findViewById(R.id.registerEditTextSurname) as EditText
        val ipserwer = findViewById(R.id.registerEditTextIPAdres) as EditText
        ipserwer.setText(sharedPreferenceApi.getString(this, EnumChoice.ip))
        login.setText(sharedPreferenceApi.getString(this, EnumChoice.login))
        name.setText(sharedPreferenceApi.getString(this, EnumChoice.nameuser))
        surname.setText(sharedPreferenceApi.getString(this, EnumChoice.surname))
    }

}






