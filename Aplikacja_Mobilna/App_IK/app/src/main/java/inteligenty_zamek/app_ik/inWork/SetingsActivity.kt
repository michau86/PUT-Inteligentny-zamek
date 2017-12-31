package inteligenty_zamek.app_ik.inWork

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.tooltip.Tooltip
import inteligenty_zamek.app_ik.API.Valdiation
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.Views.MainActivity


class SetingsActivity : BaseActivity() {

    private var originalDrawable:Drawable ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setings)
        val navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        val navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        val presenter= SetingsPresenter(this)

        val change_passwd = findViewById(R.id.settingsButtonChangePassword) as Button
        change_passwd.setOnClickListener {
            val passwd = findViewById(R.id.settingsEditTextPassword) as EditText
            val new_passwd = findViewById(R.id.settingsEditTextNewPassword) as EditText
            if (passwd.text.toString() != "" && new_passwd.text.toString() != "") {
                if (Valdiation.isCorrectPassword(passwd.text.toString()) && Valdiation.isCorrectPassword(new_passwd.text.toString()))
                {
                  presenter.changePassword(new_passwd.text.toString())
                }
                else
                {
                    if (!Valdiation.isCorrectPassword(passwd.text.toString())) {
                        val tooltip = Tooltip.Builder(passwd)
                                .setText("hasło musi spełniać warunki:\n-długość minimum 8 znaków\n-posiadać duzy znak\n-posiadać znak specjalny (!@#$%^)\nposiadać cyfrę\n ")
                                .setTextColor(Color.WHITE)
                                .setBackgroundColor(resources.getColor(R.color.color_background_list))
                                .setCancelable(true)
                                .setGravity(Gravity.TOP)
                                .setCornerRadius(8f)
                                .show()
                        val editText = findViewById(R.id.settingsEditTextPassword) as EditText
                        val drawable: Drawable = editText.background // get current EditText drawable
                        drawable.setColorFilter(ResourcesCompat.getColor(resources, R.color.errorColor, this.theme), PorterDuff.Mode.SRC_ATOP) // change the drawable color
                        editText.background = drawable


                    } else {
                        val tooltip = Tooltip.Builder(new_passwd)
                                .setText("hasło musi spełniać warunki:\n-długość minimum 8 znaków\n-posiadać duzy znak\n-posiadać znak specjalny (!@#$%^)\nposiadać cyfrę\n ")
                                .setTextColor(Color.WHITE)
                                .setBackgroundColor(resources.getColor(R.color.color_background_list))
                                .setCancelable(true)
                                .setGravity(Gravity.TOP)
                                .setCornerRadius(8f)
                                .show()

                        val editText = findViewById(R.id.settingsEditTextNewPassword) as EditText
                        val drawable: Drawable = editText.background // get current EditText drawable
                        drawable.setColorFilter(ResourcesCompat.getColor(resources, R.color.errorColor, this.theme), PorterDuff.Mode.SRC_ATOP) // change the drawable color
                        editText.background = drawable
                    }
                }
            } else {
               showMessage("wypełnij wszystkie pola")
            }
        }

        val change_Key = findViewById(R.id.settingsButtonChangeKey) as Button
        change_Key.setOnClickListener {
        presenter.changeKey()
        }

        val ipET=findViewById(R.id.settingsEditTextIP) as EditText
        ipET.setText(presenter.model.ipAddres)

        val ipbuton = findViewById(R.id.settingsButtonChangeIP) as Button
        ipbuton.setOnClickListener{
            presenter.changeIP(ipET.text.toString())
        }

        val password=findViewById(R.id.settingsEditTextNewPassword) as EditText
        originalDrawable = password.background
        password.setOnClickListener(View.OnClickListener {
            setTooltip(password)
        })
        password.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                setTooltip(password)
            }
        }

        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")



        val eyeOldPassword = this.findViewById(R.id.settingsTextViewSeeIcoOldPassword) as TextView
        eyeOldPassword.typeface = fontFamily
        eyeOldPassword.setOnClickListener {
            if(eyeOldPassword.text.toString().equals("\uf06e"))
            {
                eyeOldPassword.text="\uf070"
                eyeOldPassword.typeface = fontFamily
                val et=findViewById(R.id.settingsEditTextPassword) as EditText
                et.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else
            {
                eyeOldPassword.text="\uf06e"
                eyeOldPassword.typeface = fontFamily

                val et=findViewById(R.id.settingsEditTextPassword) as EditText
                et.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        val eyeNewPassword = this.findViewById(R.id.settingsTextViewSeeIcoNewPassword) as TextView
        eyeNewPassword.typeface = fontFamily
        eyeNewPassword.setOnClickListener {
            if(eyeNewPassword.text.toString().equals("\uf06e"))
            {
                eyeNewPassword.text="\uf070"
                eyeNewPassword.typeface = fontFamily
                val et=findViewById(R.id.settingsEditTextNewPassword) as EditText
                et.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else
            {
                eyeNewPassword.text="\uf06e"
                eyeNewPassword.typeface = fontFamily

                val et=findViewById(R.id.settingsEditTextNewPassword) as EditText
                et.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }



        ipET.setOnClickListener(View.OnClickListener {
            ipET.background.setColorFilter(ResourcesCompat.getColor(resources,
                    R.color.inpuLneColor, this.theme), PorterDuff.Mode.SRC_ATOP)
        })
        ipET.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                ipET.background.setColorFilter(ResourcesCompat.getColor(resources,
                        R.color.inpuLneColor, this.theme), PorterDuff.Mode.SRC_ATOP)
            }
        }


    }

    fun showErrorIp()
    {
        val editText = findViewById(R.id.settingsEditTextIP) as EditText
        val drawable: Drawable = editText.background
        drawable.setColorFilter(ResourcesCompat.getColor(resources, R.color.errorColor, this.theme), PorterDuff.Mode.SRC_ATOP) // change the drawable color
        editText.background = drawable
    }

    fun showErrorNewPassword()
    {

    }

    fun updateCertyficat(row1:String,row2:String,row3:String,row4:String,row5:String,row6:String,row7:String,row8:String)
    {
        var value =findViewById(R.id.settingsTableRow1) as TextView
        value.text = row1

        value =findViewById(R.id.settingsTableRow2) as TextView
        value.text = row2


        value =findViewById(R.id.settingsTableRow3) as TextView
        value.text = row3

        value =findViewById(R.id.settingsTableRow4) as TextView
        value.text = row4

        value =findViewById(R.id.settingsTableRow5) as TextView
        value.text = row5

        value =findViewById(R.id.settingsTableRow6) as TextView
        value.text = row6

        value =findViewById(R.id.settingsTableRow7) as TextView
        value.text = row7

        value =findViewById(R.id.settingsTableRow8) as TextView
        value.text = row8
    }

    fun showMessage(message:String)
    {
        val toast = Toast.makeText(this@SetingsActivity, message, Toast.LENGTH_LONG)
        toast.show()
        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                toast.show()
            }

            override fun onFinish() {
                toast.show()
            }
        }.start()
    }

    override fun onBackPressed()
    {
        val intent = Intent(this, MainActivity::class.java)
       this.startActivity(intent)
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

}
