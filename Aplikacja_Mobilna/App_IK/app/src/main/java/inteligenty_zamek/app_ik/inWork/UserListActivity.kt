package inteligenty_zamek.app_ik.inWork

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.Views.Admin_PanelActivity
import inteligenty_zamek.app_ik.Views.Managment_certyficationActivity
import inteligenty_zamek.app_ik.adapters.MainAdapter
import inteligenty_zamek.app_ik.adapters.UserListAdapter
import inteligenty_zamek.app_ik.presenters.userCertyfikationListPresenter
import inteligenty_zamek.app_ik.rest_class.User
import android.widget.CompoundButton



class UserListActivity : BaseActivity() {

    private var adapter: UserListAdapter? = null
    var resultsListView: ListView?=null
    var presenter: UserListPresenter?=null
    var context: Context?=null
    private var recyclerView: RecyclerView? = null
    private var firstUse=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        context=this

        val navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        val navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)

        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        val sampleText = this.findViewById(R.id.userListTextViewSortingIco) as TextView
        sampleText.typeface = fontFamily
       // resultsListView = this.findViewById(R.id.userListListViewUserCertyfication) as ListView
        presenter = UserListPresenter(this)
        //initKey
        presenter!!.downloadUsers()

        //stworzenie adaptera




        val inputSearch = findViewById(R.id.userListEditTextSearch) as EditText
        inputSearch.addTextChangedListener(object : TextWatcher {
            override
            fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
              presenter!!.search(cs)

            }

            override
            fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int,
                                  arg3: Int) {
                // TODO Auto-generated method stub

            }

            override
            fun afterTextChanged(arg0: Editable) {
                // TODO Auto-generated method stub
            }
        })


        val checkBox = findViewById(R.id.userListcheckBox) as CheckBox
        checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                presenter!!.setIsActive(isChecked)

            }
        })
        //przycisk dopowiedzialny za sortowanie
        val textView = findViewById(R.id.userListTextViewSortingIco) as TextView
        textView.setOnClickListener(object : View.OnClickListener {
            override
            fun onClick(viewIn: View) {



                if(textView.text.equals("\uf160"))
                {
                    textView.text="\uf161"
                    presenter!!.sort(true)
                }
                else
                {
                    textView.text= "\uf160"
                    presenter!!.sort(false)

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        presenter!!.downloadUsers()
    }

    override fun onBackPressed() {
        val intent = Intent(this, Admin_PanelActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    fun setAdapter()
    {

        recyclerView = (findViewById(R.id.userListRecyclerViewUserCertyfication) as RecyclerView)

        adapter = UserListAdapter(presenter!!.model.userlist2, this)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.setLayoutManager(mLayoutManager)
        recyclerView!!.setItemAnimator(DefaultItemAnimator())
        recyclerView!!.setAdapter(adapter)
        if(!firstUse)
        {firstUse=true
            recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))}
        adapter!!.notifyDataSetChanged()
    }

    fun listClick(position:Int)
    {
        presenter!!.goToUserView(position)
    }
}
