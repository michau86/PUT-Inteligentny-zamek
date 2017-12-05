package inteligenty_zamek.app_ik.Views

import android.content.res.TypedArray
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import java.util.LinkedHashMap
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.presenters.UserWaitPresenter

class UserWaitActivity : BaseActivity() {


    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null
    private var toastDelay = 4000
    var adapter: SimpleAdapter? = null
    var resultsListView: ListView? = null
    var presenter: UserWaitPresenter? = null
    var view: UserWaitActivity?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_wait)
        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        resultsListView = this@UserWaitActivity.findViewById(R.id.listView_user_wait_Keys) as ListView
        presenter = UserWaitPresenter(this)
        presenter!!.initAvtivity()
        view=this
        val inputSearch = findViewById(R.id.editText_Search) as EditText
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


    fun setAdapter(listitem: MutableList<LinkedHashMap<String, String>>) {
        adapter = object : SimpleAdapter(this, listitem, R.layout.user_wait_key_list,
                arrayOf("First Line", "Second Line"),
                intArrayOf(R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey)) {

                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

                val itemView = super.getView(position, convertView, parent)

                val myTaskButton = itemView.findViewById(R.id.button6)
                myTaskButton.setOnClickListener {

                    presenter!!.acceptButton(position)

                }
                val myTaskButton2 = itemView.findViewById(R.id.button5)
                myTaskButton2.setOnClickListener {
                    //////to do presenteraaa
                    presenter!!.removeButton(position)
                }

                return itemView
            }

        }

        resultsListView!!.adapter = adapter

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
