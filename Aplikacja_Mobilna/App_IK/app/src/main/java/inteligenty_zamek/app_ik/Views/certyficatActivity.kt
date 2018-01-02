package inteligenty_zamek.app_ik.Views

import android.content.res.TypedArray
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.presenters.certyficatPresenter

class certyficatActivity : BaseActivity() {

    private var toastDelay = 4000
    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certyficat)

        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        val presenter= certyficatPresenter(this)

        if (presenter.isCertyficat()) {
            val tx1 = this.findViewById(R.id.certificateTextViewExpiryData) as TextView
            tx1.text = presenter.getCertyficatExpiryDateText()
            val tx2 = this.findViewById(R.id.certificateTextViewInfo) as TextView
            tx2.text =presenter.getCertyficatInfoText()
            val tx3 = this.findViewById(R.id.certificateTextViewDate) as TextView
            tx3.text = presenter.getCertyficatText()

        } else {
            val button = findViewById(R.id.certificateButtonExtend) as Button
            button.visibility = View.INVISIBLE
        }

        //akcja po nacisniecu usun certyfikat
        val delete = findViewById(R.id.certificateButtonDelete) as Button
        delete.setOnClickListener {
            presenter.deleteCertyficat()
        }

        //akcja po nacisnieciu przedluż certyfikat
        val extend = findViewById(R.id.certificateButtonExtend) as Button
        extend.setOnClickListener {
            presenter.extendCertyficat()
        }

    }

    fun showMessage(message: String) {
        val toast = Toast.makeText(this@certyficatActivity, message, Toast.LENGTH_LONG)
        toast.show()
        object : CountDownTimer(toastDelay.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                toast.show()
            }
            override fun onFinish() {
                toast.show()
            }
        }.start()
    }



}









