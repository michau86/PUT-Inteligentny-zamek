package inteligenty_zamek.app_ik.Views

import android.content.res.TypedArray
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*

import java.util.LinkedHashMap

import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.adapters.TwoButtonAdapter
import inteligenty_zamek.app_ik.adapters.userWatModel
import inteligenty_zamek.app_ik.presenters.CertyficatWaitPresenter

class CertyficatWaitActivity : BaseActivity() {
    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null
    private var toastDelay = 4000

    var presenter: CertyficatWaitPresenter?=null
    private var itemList:ArrayList<userWatModel>?= ArrayList()
    private var recyclerView: RecyclerView? = null
    private var adapter: TwoButtonAdapter? = null
    private var firstUse=false



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certyficat_wait)
        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        presenter= CertyficatWaitPresenter(this)

        val sort = findViewById(R.id.certificatWaitTextViewSortingIco) as TextView
        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        sort.typeface = fontFamily

        sort.setOnClickListener(object : View.OnClickListener {
            override
            fun onClick(viewIn: View) {
                presenter!!.sortArray()

                if(sort.text.equals("\uf160"))
                {
                    sort.text="\uf161"
                }
                else
                {
                    sort.text= "\uf160"
                }

            }})



        val inputSearch = findViewById(R.id.certificatWaitEditText) as EditText
        inputSearch.addTextChangedListener(object : TextWatcher
        {

            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
             presenter!!.updateList(cs)
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int,
                                           arg3: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(arg0: Editable) {
                // TODO Auto-generated method stub
            }
        })
    }

    fun upd()
    {
        adapter!!.notifyDataSetChanged()
    }


    fun setAdapter(listitem: LinkedHashMap<String, ArrayList<String>>)
    {
        itemList =ArrayList()
        recyclerView = (findViewById(R.id.certificatWaitRecyclerView) as RecyclerView)


        adapter = TwoButtonAdapter(itemList, presenter)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.setLayoutManager(mLayoutManager)
        recyclerView!!.setItemAnimator(DefaultItemAnimator())
        recyclerView!!.setAdapter(adapter)
        if(!firstUse)
        {firstUse=true
            recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))}
        adapter!!.notifyDataSetChanged()


        val it = listitem.entries.iterator()
        while (it.hasNext()) {
            val pair = it.next() as java.util.Map.Entry<*, *>
            val Array:ArrayList<String> =pair.value  as ArrayList<String>
            val item = userWatModel(Array[0], "dla użytkownika:\nLogin "+ Array[1]+
                    "\nImie i nazwisko  "+Array[2]+" "+Array[3])
            itemList!!.add(item)
        }
        val item = userWatModel("test ", "teest")
        itemList!!.add(item)
    }

    fun showMessage(message:String)
    {
    val toast = Toast.makeText(this@CertyficatWaitActivity, message, Toast.LENGTH_LONG)
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
