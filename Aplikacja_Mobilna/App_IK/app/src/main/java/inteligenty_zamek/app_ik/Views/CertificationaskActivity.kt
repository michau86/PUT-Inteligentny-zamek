package inteligenty_zamek.app_ik.Views

import android.content.res.TypedArray
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.presenters.CertificationaskPresenter

class CertificationaskActivity : BaseActivity() {
    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null
    internal var toastDelay = 4000
    private var presenter: CertificationaskPresenter?=null
    var resultsListView: ListView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certyficationask)
        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        resultsListView = this@CertificationaskActivity.findViewById(R.id.ListView_key_generate_certyfiaction) as ListView

        presenter = CertificationaskPresenter(this)
        presenter!!.initActivity()

        val sort = findViewById(R.id.TextView_sortingIco) as TextView
        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        sort.typeface = fontFamily


        sort.setOnClickListener(object : View.OnClickListener {
            override
            fun onClick(viewIn: View) {
                presenter!!.sortArray()
                presenter!!.createValueToAdapter()
                val  adapter = SimpleAdapter(this@CertificationaskActivity, presenter!!.model.listItems, R.layout.main_key_list,
                        arrayOf("First Line", "Second Line"),
                        intArrayOf(R.id.TextView_listPlaceKey, R.id.TextView_listNameKey))
                resultsListView!!.adapter = adapter
                resultsListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    presenter!!.sendRequest(position)
                }

                if(sort.text.equals("\uf160"))
                {
                    sort.text="\uf161"
                }
                else
                {
                    sort.text= "\uf160"
                }

            }})



            val inputSearch = findViewById(R.id.editText_Search) as EditText
            inputSearch.addTextChangedListener(object : TextWatcher {

                override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                    resultsListView = this@CertificationaskActivity.findViewById(R.id.ListView_key_generate_certyfiaction) as ListView
                    presenter!!.createValueToAdapter(cs)
                    val  adapter = SimpleAdapter(this@CertificationaskActivity, presenter!!.model.listItems, R.layout.main_key_list,
                            arrayOf("First Line", "Second Line"),
                            intArrayOf(R.id.TextView_listPlaceKey, R.id.TextView_listNameKey))
                    resultsListView!!.adapter = adapter
                    resultsListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        presenter!!.sendRequest(position)
                    }
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

    fun showMessage(message:String)
    {
        val toast = Toast.makeText(this@CertificationaskActivity, message, Toast.LENGTH_LONG)
        toast.show()
        object : CountDownTimer(toastDelay.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                toast.show()
            }

            override fun onFinish() {
                toast.show()
            }
        }.start()}
    fun setCertyficats()
    {
        presenter!!.createValueToAdapter("")
        val  adapter = SimpleAdapter(this@CertificationaskActivity, presenter!!.model.listItems, R.layout.main_key_list,
                arrayOf("First Line", "Second Line"),
                intArrayOf(R.id.TextView_listPlaceKey, R.id.TextView_listNameKey))
        resultsListView!!.adapter=adapter
        resultsListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            //ustalenie id certyfikatu z listy certyfikatow
            presenter!!.sendRequest(position)
        }
    }
}









