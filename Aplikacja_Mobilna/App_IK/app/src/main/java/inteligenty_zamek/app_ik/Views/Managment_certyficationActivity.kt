package inteligenty_zamek.app_ik.Views

import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import inteligenty_zamek.app_ik.*

import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.presenters.Managment_certyficationPresenter
import inteligenty_zamek.app_ik.rest_class.GlobalContainer

class Managment_certyficationActivity : BaseActivity() {
    internal var toastDelay = 4000
    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null
    private var LinearLayout: LinearLayout?=null
    private var presenter:Managment_certyficationPresenter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managment_certyfication)
        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        presenter=Managment_certyficationPresenter(this)
        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        val ico_download_serwer = this.findViewById(R.id.TextView_download_serwer) as TextView
        ico_download_serwer.typeface = fontFamily
        val ico_download_file = this.findViewById(R.id.TextView_download_file) as TextView
        ico_download_file.typeface = fontFamily

        if(presenter!!.isLogin()) {

            val CertificationList = this.findViewById(R.id.managmentCertificationTextViewList) as TextView
            val CertificationAsk = this.findViewById(R.id.managmentCertificationTextViewExtend) as TextView

            CertificationList.setOnClickListener {
                presenter!!.goToCertifiationList()
            }

            CertificationAsk.setOnClickListener {
                presenter!!.goToCertificationAsk()
            }
            //akcja dotyczaca klikniecia na ikone sciagnij z serwera

            LinearLayout = findViewById(R.id.managmentCertificationLinearLayoutDownloadSerwer) as LinearLayout
            LinearLayout!!.setOnClickListener {
                presenter!!.getCertyficat()
            }

            //akcja dotyczaca kliknieca na ikonke sciagnij z pliku
            LinearLayout = findViewById(R.id.managmentCertificationLinearLayoutDownloadFile) as LinearLayout
            //na razie nie tykam
            LinearLayout!!.setOnClickListener {
                // TODO obsluga przycisku wgrywajacego certyfikat z pliku
                val intent = Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT)

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123)
            }




        }
        else
        {
            ico_download_serwer.text = ""
        }
    }

    fun showMessage(message:String)
    {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
        object : CountDownTimer(GlobalContainer.toastDelay.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                toast.show()
            }
            override fun onFinish() {
                toast.show()
            }
        }.start()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }




}




