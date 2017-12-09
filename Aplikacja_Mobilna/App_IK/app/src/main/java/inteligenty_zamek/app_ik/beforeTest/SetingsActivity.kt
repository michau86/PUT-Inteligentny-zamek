package inteligenty_zamek.app_ik.beforeTest

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.tooltip.Tooltip
import inteligenty_zamek.app_ik.API.Valdiation

import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer
import inteligenty_zamek.app_ik.Navigation.BaseActivity


class SetingsActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setings)
        val navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        val navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)

        val presenter= SetingsPresenter(this)
        val change_passwd = findViewById(R.id.button_password_user) as Button
        change_passwd.setOnClickListener {
            val us = (application as GlobalClassContainer).user
            val passwd = findViewById(R.id.editText3) as EditText
            val new_passwd = findViewById(R.id.editText6) as EditText
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
                    } else {
                        val tooltip = Tooltip.Builder(new_passwd)
                                .setText("hasło musi spełniać warunki:\n-długość minimum 8 znaków\n-posiadać duzy znak\n-posiadać znak specjalny (!@#$%^)\nposiadać cyfrę\n ")
                                .setTextColor(Color.WHITE)
                                .setBackgroundColor(resources.getColor(R.color.color_background_list))
                                .setCancelable(true)
                                .setGravity(Gravity.TOP)
                                .setCornerRadius(8f)
                                .show()
                    }
                }
            } else {
               showMessage("wypełnij wszystkie pola")
            }
        }
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
    override fun onBackPressed() {}


}
