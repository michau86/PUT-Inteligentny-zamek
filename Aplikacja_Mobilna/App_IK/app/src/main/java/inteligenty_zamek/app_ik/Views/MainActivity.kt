package inteligenty_zamek.app_ik.Views

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.adapters.*
import inteligenty_zamek.app_ik.presenters.MainPresenter
import inteligenty_zamek.app_ik.rest_class.Certyficat
import inteligenty_zamek.app_ik.rest_class.GlobalContainer

import java.util.Arrays
import java.util.Collections
import java.util.LinkedHashMap


class MainActivity : BaseActivity() {





    internal var flag = true

    private var presenter : MainPresenter?= null
    private var  preferences: SharedPreferences?=null
    private  var csq:CharSequence=""
    private var itemList:ArrayList<MainListModel>?= ArrayList()
    private var recyclerView: RecyclerView? = null
    private var adapter: MainAdapter? = null
    private var firstUse=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        val navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        val sampleText = this.findViewById(R.id.mainTextView_sortingIco) as TextView
        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        sampleText.typeface = fontFamily
        preferences = this.getSharedPreferences(this.getString(R.string.SPName), Context.MODE_PRIVATE)
        presenter= MainPresenter(this)

        GlobalContainer.loadDataFromSharedPreferences(this)
        sampleText.setOnClickListener {
            if (flag == false) {
                sampleText.setText("\uF161")
                Arrays.sort(GlobalContainer.getUser(this).getCertyficateList(this), Collections.reverseOrder<Any>())
                flag = true
            } else {
                sampleText.setText("\uF160")
                Arrays.sort(GlobalContainer.getUser(this).getCertyficateList(this))
                flag = false
            }

        }


        val inputSearch = findViewById(R.id.mainEditText) as EditText
        inputSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
             presenter!!.updateList(cs)
                csq=cs
            }
            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int,
                                           arg3: Int) {
            }
            override fun afterTextChanged(arg0: Editable) {
            }
        })

        presenter!!.updateList("")

    }

    fun listClick(position:Int,icoColor:Byte)
    {
        presenter!!.sendCertyficate(position,csq)
       changeIco(position,icoColor)
    }


    fun changeIco(position:Int,IcoColor:Byte)
    {
        itemList!![position].IcoColor=IcoColor
        val x:Byte=1
        if(IcoColor==x) {
            itemList!![position].icoKey ="\uf13e"
        }
        else
        {
            itemList!![position].icoKey ="\uf023"
        }
        adapter!!.notifyDataSetChanged()
    }

    fun setAdapter(listitem: LinkedHashMap<Int, Certyficat>) {

        itemList =ArrayList()
        recyclerView = (findViewById(R.id.mainRecyclerView) as RecyclerView)


        val it = listitem.entries.iterator()
        while (it.hasNext()) {
            val pair = it.next() as java.util.Map.Entry<*, *>
            val cert=pair.value as Certyficat
            val x:Byte=2
            if(cert.status==x)
            {
                val item = MainListModel(cert.lockName,cert.lockLocalization,"\uf13e",cert.status)
                itemList!!.add(item)
            }
            else
            {
                val item = MainListModel(cert.lockName,cert.lockLocalization,"\uf023",cert.status)
                itemList!!.add(item)
            }

        }

        adapter = MainAdapter(itemList, this)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.setLayoutManager(mLayoutManager)
        recyclerView!!.setItemAnimator(DefaultItemAnimator())
        recyclerView!!.setAdapter(adapter)
        if(!firstUse)
        {firstUse=true
            recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))}
        adapter!!.notifyDataSetChanged()

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