package inteligenty_zamek.app_ik.Views

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*

import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.presenters.userCertyfikationListPresenter


class userCertyfikationListActivity : BaseActivity() {

    var adapter: SimpleAdapter?=null
    var resultsListView: ListView?=null
    var presenter: userCertyfikationListPresenter?=null
        var context:Context?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_certyfikation_list)

        context=this

        val navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        val navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)

        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        val sampleText = this.findViewById(R.id.userCertyficationListTextViewSortingIco) as TextView
        sampleText.typeface = fontFamily
        resultsListView = this.findViewById(R.id.userCertyficationListListViewUserCertyfication) as ListView
        presenter = userCertyfikationListPresenter(this)
        //initKey

        presenter!!.setValueToAdapter()
        //stworzenie adaptera
        adapter = SimpleAdapter(this, presenter!!.model.listItems, R.layout.main_key_list,
                arrayOf("First Line", "Second Line"),
                intArrayOf(R.id.TextView_listPlaceKey, R.id.TextView_listNameKey))

        resultsListView!!.adapter = adapter
        resultsListView!!.onItemClickListener = object : AdapterView.OnItemClickListener {
            override
            fun onItemClick(parent: AdapterView<*>, view: View,
                            position: Int, id: Long) {
                presenter!!.goToCertyficat(position)
                resultsListView!!.adapter = adapter
            }
        }

        val inputSearch = findViewById(R.id.userCertyficationListEditTextSearch) as EditText
        inputSearch.addTextChangedListener(object : TextWatcher {
            override
            fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                presenter!!.setValueSearch(cs)
                presenter!!.setValueToAdapter()

                adapter = SimpleAdapter(context, presenter!!.model.listItems, R.layout.main_key_list,
                        arrayOf("First Line", "Second Line"),
                        intArrayOf(R.id.TextView_listPlaceKey, R.id.TextView_listNameKey))

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
        val textView = findViewById(R.id.userCertyficationListTextViewSortingIco) as TextView
        textView.setOnClickListener(object : View.OnClickListener {
            override
            fun onClick(viewIn: View) {
                presenter!!.sortList()

                adapter = SimpleAdapter(context, presenter!!.model.listItems, R.layout.main_key_list,
                        arrayOf("First Line", "Second Line"),
                        intArrayOf(R.id.TextView_listPlaceKey, R.id.TextView_listNameKey))
                resultsListView!!.adapter = adapter

                if(textView.text.equals("\uf160"))
                {
                    textView.text="\uf161"
                }
                else
                {
                    textView.text= "\uf160"
                }
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this, Managment_certyficationActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}
