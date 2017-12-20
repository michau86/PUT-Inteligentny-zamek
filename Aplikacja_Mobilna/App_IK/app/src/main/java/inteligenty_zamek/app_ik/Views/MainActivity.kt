package inteligenty_zamek.app_ik.Views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.presenters.MainPresenter
import inteligenty_zamek.app_ik.rest_class.GlobalContainer

import java.util.Arrays
import java.util.Collections


class MainActivity : BaseActivity() {




    internal var resultsListView: ListView?=null

    internal var flag = true

    private var presenter : MainPresenter?= null
    private var  preferences: SharedPreferences?=null
    private var context :Context=this
    private  var csq:CharSequence=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        val navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        val sampleText = this.findViewById(R.id.TextView_sortingIco) as TextView
        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        sampleText.typeface = fontFamily
        resultsListView = this.findViewById(R.id.listView_Keys) as ListView
        preferences = this.getSharedPreferences(this.getString(R.string.SPName), Context.MODE_PRIVATE)
        presenter= MainPresenter(this)
        resultsListView!!.adapter=presenter!!.updateList("")

        GlobalContainer.loadDataFromSharedPreferences(this)
        sampleText.setOnClickListener {
            if (flag == false) {
                Arrays.sort(GlobalContainer.getUser(this).getCertyficateList(this), Collections.reverseOrder<Any>())
                flag = true
            } else {
                Arrays.sort(GlobalContainer.getUser(this).getCertyficateList(this))
                flag = false
            }
            resultsListView!!.adapter = presenter!!.updateList("")
        }


        val inputSearch = findViewById(R.id.editText_Search) as EditText
        inputSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                resultsListView!!.adapter = presenter!!.updateList(cs)
                csq=cs
            }
            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int,
                                           arg3: Int) {
            }
            override fun afterTextChanged(arg0: Editable) {
            }
        })

        resultsListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            presenter!!.sendCertyficate(position,csq)
        }

    }


    override fun onBackPressed() {
        this.finish()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}