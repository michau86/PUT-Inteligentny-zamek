package inteligenty_zamek.app_ik.Views

import android.app.Activity
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import inteligenty_zamek.app_ik.*

import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.util.ArrayList
import java.util.Arrays

import inteligenty_zamek.app_ik.API.fileReadWriteApi
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer
import inteligenty_zamek.app_ik.rest_class.User
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.models.Managment_certyficationModel
import inteligenty_zamek.app_ik.presenters.Managment_certyficationPresenter
import inteligenty_zamek.app_ik.rest_class.GlobalContainer

class Managment_certyficationActivity : BaseActivity() {
    internal var toastDelay = 4000
    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null
    private var listView: ListView? =null
    private var textView: TextView?=null
    private var presenter:Managment_certyficationPresenter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managment_certyfication)
        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        presenter=Managment_certyficationPresenter(this)
        //ustawienie fontu dla ikon pobierania z serwera oraz pobrania z telefonu
        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        val ico_download_serwer = this.findViewById(R.id.TextView_download_serwer) as TextView
        ico_download_serwer.typeface = fontFamily
        val ico_download_file = this.findViewById(R.id.TextView_download_file) as TextView
        ico_download_file.typeface = fontFamily
        val resultsListView = this.findViewById(R.id.ListView_Managment_Certyfivation) as ListView
        //////////////////////model
        if(!presenter!!.isLogin()) {
            ico_download_serwer.setText("")
            ////////
            resultsListView.adapter = presenter!!.setAdapter()

            //wyszukanie listy oraz ustwaienie akcji dotyczacych klikniec na poszcegolne elementy
            listView = findViewById(R.id.ListView_Managment_Certyfivation) as ListView
            listView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                presenter!!.getAnotherActivity(position)
            }


            //akcja dotyczaca klikniecia na ikone sciagnij z serwera
            textView = findViewById(R.id.TextView_download_serwer) as TextView
            textView!!.setOnClickListener {
                presenter!!.getCertyficat()
            }

            //akcja dotyczaca kliknieca na ikonke sciagnij z pliku
            textView = findViewById(R.id.TextView_download_file) as TextView
            //na razie nie tykam
            textView!!.setOnClickListener {
                // TODO obsluga przycisku wgrywajacego certyfikat z pliku
                val intent = Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT)

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123)
            }
        }
    }
    //przeciazenie funkcji odpwoeidzialnrj za obrobke wskazanego pliku

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }




}




