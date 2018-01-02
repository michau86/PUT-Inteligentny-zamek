package inteligenty_zamek.app_ik.Views
import android.content.res.TypedArray
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView

import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.presenters.ManagmentCertyficationUserPresenter

class ManagmentCertyficationUserActivity : BaseActivity() {

    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null
    internal var toastDelay = 4000
    var adapter: SimpleAdapter?=null
    var resultsListView: ListView?=null
    internal var inputSearch: EditText?=null

    var presenter: ManagmentCertyficationUserPresenter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managment_certyfication_user)

        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        resultsListView = this@ManagmentCertyficationUserActivity.findViewById(R.id.listView_userCertyfication) as ListView

        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        val sampleText = this.findViewById(R.id.TextView_sortingIco) as TextView
        sampleText.typeface = fontFamily

        presenter= ManagmentCertyficationUserPresenter(this)
        presenter!!.initActivity()

        inputSearch = findViewById(R.id.userWaitEditText) as EditText
        inputSearch!!.addTextChangedListener(object : TextWatcher {

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

        //przycisk dopowiedzialny za sortowanie
        val textView = findViewById(R.id.TextView_sortingIco) as TextView
        textView.setOnClickListener {

            if(textView.text.equals("\uf160"))
            {
                textView.text="\uf161"
            }
            else
            {
                textView.text= "\uf160"
            }
        presenter!!.sortList()
        }
    }

    fun setAdapter()
    {
        adapter = SimpleAdapter(this@ManagmentCertyficationUserActivity, presenter!!.model.listItems, R.layout.main_key_list,
                arrayOf("First Line", "Second Line"),
                intArrayOf(R.id.TextView_listNameKey,R.id.TextView_listPlaceKey ))


        resultsListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

           presenter!!.goToCertificate(position)
        }
        resultsListView!!.adapter=adapter!!
    }

}
