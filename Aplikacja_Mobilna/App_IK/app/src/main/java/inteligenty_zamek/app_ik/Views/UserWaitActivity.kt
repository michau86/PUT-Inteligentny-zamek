package inteligenty_zamek.app_ik.Views

import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import java.util.LinkedHashMap
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.presenters.UserWaitPresenter
import android.support.v7.widget.RecyclerView
import inteligenty_zamek.app_ik.adapters.TwoButtonAdapter
import android.support.v7.widget.DefaultItemAnimator
import inteligenty_zamek.app_ik.adapters.userWatModel
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import android.widget.*


class UserWaitActivity : BaseActivity() {



    private var toastDelay = 4000

    private var itemList:ArrayList<userWatModel>?= ArrayList()
    private var recyclerView: RecyclerView? = null
    private var adapter: TwoButtonAdapter? = null
    private var firstUse=false
    var presenter: UserWaitPresenter? = null
    var view: UserWaitActivity?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_wait)
        val navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        val  navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)

        presenter = UserWaitPresenter(this)
        presenter!!.initAvtivity()
        view=this

        val sort = findViewById(R.id.userWaitTextViewSortingIco) as TextView
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


        val inputSearch = findViewById(R.id.userWaitEditText) as EditText
        inputSearch.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                presenter!!.updateListInSearch(cs)

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

fun acceptButton(position:Int)
{
    presenter!!.acceptButton(position)
}

    fun deleteButton(position:Int)
    {
        presenter!!.removeButton(position)
    }

    fun setAdapter(listitem: LinkedHashMap<String, String>) {

        itemList =ArrayList()
        recyclerView = (findViewById(R.id.userWaitRecyclerView) as RecyclerView)


        val it = listitem.entries.iterator()
        while (it.hasNext()) {
            val pair = it.next() as java.util.Map.Entry<*, *>
            val item = userWatModel(pair.key.toString(),pair.value.toString())
            itemList!!.add(item)
        }

        adapter = TwoButtonAdapter(itemList, presenter)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.setLayoutManager(mLayoutManager)
        recyclerView!!.setItemAnimator(DefaultItemAnimator())
        recyclerView!!.setAdapter(adapter)
       if(!firstUse)
       {firstUse=true
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))}
        adapter!!.notifyDataSetChanged()




    }

    fun deleteFromRecycler(position:Int)
    {
    adapter!!.removeAt(position)
    }

    fun showMessage(message: String) {
        val toast = Toast.makeText(this@UserWaitActivity, message, Toast.LENGTH_LONG)
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
