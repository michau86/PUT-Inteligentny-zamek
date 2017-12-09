package inteligenty_zamek.app_ik.beforeTest

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R


class userCertyfikationListActivity : BaseActivity() {


   //
    //var inputSearch: EditText?=null
    var adapter: SimpleAdapter?=null
    var resultsListView: ListView?=null
    var presenter: userCertyfikationListPresenter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_certyfikation_list)



        val navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        val navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)

        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        val sampleText = this.findViewById(R.id.TextView_sortingIco) as TextView
        sampleText.setTypeface(fontFamily)
        resultsListView = this.findViewById(R.id.listView_userCertyfication) as ListView
        presenter = userCertyfikationListPresenter(this)
        //initKey

        //stworzenie adaptera
        adapter = SimpleAdapter(this, presenter!!.model.listItems, R.layout.main_key_list,
                arrayOf("First Line", "Second Line"),
                intArrayOf(R.id.TextView_listPlaceKey, R.id.TextView_listNameKey))


        //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)

        presenter!!.setValueToAdapter()
        resultsListView!!.adapter = adapter
        resultsListView!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override
            fun onItemClick(parent: AdapterView<*>, view: View,
                            position: Int, id: Long) {
                presenter!!.goToCertyficat(position)
                resultsListView!!.adapter = adapter
            }
        })

        val inputSearch = findViewById(R.id.editText_Search) as EditText
        inputSearch.addTextChangedListener(object : TextWatcher {
            override
            fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                presenter!!.setValueSearch(cs)
                presenter!!.setValueToAdapter()
                resultsListView!!.adapter = adapter

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


        //przycisk dopowiedzialny za sortowanie
        val textView = findViewById(R.id.TextView_sortingIco) as TextView
        textView.setOnClickListener(object : View.OnClickListener {
            override
            fun onClick(viewIn: View) {
                presenter!!.sortList()
                resultsListView!!.adapter = adapter
            }


        })

    }
}
