package inteligenty_zamek.app_ik.Views

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.*
import org.json.JSONArray
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.adapters.HistoryListAdapter
import inteligenty_zamek.app_ik.adapters.SpinnerWithoutDeleteAdapter
import inteligenty_zamek.app_ik.presenters.History_using_keyPresenter
import inteligenty_zamek.app_ik.models.HistoryListElement
import java.text.SimpleDateFormat
import java.util.*


class History_using_keyActivity : BaseActivity() {

    var navMenuTitles: Array<String>? = null
    var navMenuIcons: TypedArray? = null
    var toastDelay = 4000
    var arrJson: JSONArray? = null
    var Keys: LinkedHashMap<String, String>?=null
    var resultsListView: RecyclerView?=null
    var resultsMap: LinkedHashMap<String, String>?=null
    var listItems: MutableList<LinkedHashMap<String, String>>? = null
    var listItems2: MutableList<LinkedHashMap<String, String>>?=null
    var flag = true
    var csk: CharSequence=""
    var adapter: HistoryListAdapter?=null
    var presenter: History_using_keyPresenter?=null
    var firstUse=false
    var context:Context?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_of_using_key)
        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)

        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        resultsListView = this@History_using_keyActivity.findViewById(R.id.historyUsingKeyRecyclerView) as RecyclerView
        val fontFamily = Typeface.createFromAsset(this.assets, "fonts/fontawesome.ttf")
        context=this
        presenter= History_using_keyPresenter(this)

        presenter!!.getList()
        (findViewById(R.id.historyUsingKeyTextViewShowHideIco) as TextView).typeface=fontFamily
        (findViewById(R.id.historyUsingKeytextViewSortingIco) as TextView).typeface=fontFamily

    var textView = findViewById(R.id.historyUsingKeyLinearLayoutSorting) as LinearLayout
    textView.setOnClickListener( View.OnClickListener {

        val scale = this.getResources().getDisplayMetrics().density
        val size = (30 * scale + 0.5f)

        if(textView.getLayoutParams().height==size.toInt()) {
            val params = textView.getLayoutParams()
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            textView.setLayoutParams(params)
            (findViewById(R.id.historyUsingKeyTextViewShowHideIco) as TextView).setText("\uf106")
            (findViewById(R.id.historyUsingKeyTextViewShowHideText) as TextView).setText("zwiń wyszukiwanie")
        }
        else
        {
            val params = textView.getLayoutParams()
            params.height = size.toInt()
            textView.setLayoutParams(params)
            (findViewById(R.id.historyUsingKeyTextViewShowHideIco) as TextView).setText("\uf107")
            (findViewById(R.id.historyUsingKeyTextViewShowHideText) as TextView).setText("rozwiń wyszukiwanie")
        }
    })


        val myCalendar = Calendar.getInstance()

        val from = findViewById(R.id.historyUsingKeyEditTextFrom) as EditText
        val dateFrom = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "yyyy-MM-dd"; //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.GERMAN);
            from.setText(sdf.format(myCalendar.getTime()));

        }



        from.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                // TODO Auto-generated method stub
                val data=    DatePickerDialog(this@History_using_keyActivity, dateFrom, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))

                data.show()
                from.background.setColorFilter(ResourcesCompat.getColor(resources,
                        R.color.inpuLneColor, context!!.theme), PorterDuff.Mode.SRC_ATOP)
            }

        })



        val to = findViewById(R.id.historyUsingKeyEditTextTo) as EditText
        val dateTo = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "yyyy-MM-dd"; //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.GERMAN);
            to.setText(sdf.format(myCalendar.getTime()));

        }


        to.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                // TODO Auto-generated method stub
                val data=    DatePickerDialog(this@History_using_keyActivity, dateTo, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))

                data.show()
                to.background.setColorFilter(ResourcesCompat.getColor(resources,
                        R.color.inpuLneColor, context!!.theme), PorterDuff.Mode.SRC_ATOP)
            }
        })

        val sort = findViewById(R.id.historyUsingKeytextViewSortingIco) as TextView
        sort.setOnClickListener(View.OnClickListener {
            if(sort.text.equals("\uf160"))
            {
                sort.text="\uf161"
            }
            else
            {
                sort.text= "\uf160"
            }
        })

        val filtr = findViewById(R.id.historyUsingKeyButtonFiltr) as Button
        filtr.setOnClickListener(View.OnClickListener {
            presenter!!.setFiltr(from.text.toString(),to.text.toString(),
                    (findViewById(R.id.historyUsingKeySpinnerLock) as Spinner).getSelectedItem().toString(),
                    (findViewById(R.id.historyUsingKeySpinnerUser) as Spinner).getSelectedItem().toString(),
                    (findViewById(R.id.historyUsingKeyChekBox) as CheckBox).isChecked(),
                    (findViewById(R.id.historyUsingKeytextViewSortingIco) as TextView).text.toString()
            )})
//


//przycisk dopowiedzialny za sortowanie
/*val textView = findViewById(R.id.TextView_sortingIco) as TextView
textView.setOnClickListener(object : View.OnClickListener {
   // }catch (Exception e) {}
   internal var it: Iterator<*> = Keys!!.entries.iterator()

   override fun onClick(viewIn: View) {


       if (flag == false) {
           Arrays.sort(listItems2!!.toTypedArray())
           flag = true
       } else {
           //Comparator <LinkedHashMap<String, String>> cmp =
           Collections.sort(listItems2) { one, two -> one.values.toString().compareTo(two.values.toString()) }



           Arrays.sort(listItems2!!.toTypedArray())
           flag = false
       }

       Keys = LinkedHashMap()
       adapter = SimpleAdapter(this@History_using_keyActivity, listItems2, R.layout.history_using_key_list,
               arrayOf("First Line", "Second Line", "Third Line"),
               intArrayOf(R.id.history_icon, R.id.title, R.id.describe))
       // try {
       //   for (int i = 0; i < ((GlobalClassContainer) getApplication()).getUser().getCertyficateList(this).length; i++) {
       //      Keys.put(listItems2.get(i).values().toArray()[0].toString(), listItems2.get(i).values().toArray()[1].toString() );

   }

   init {
       resultsMap = LinkedHashMap()
       val pair = it.next() as Map.Entry<*, *>
       if (pair.key.toString().toLowerCase().contains(csk.toString().toLowerCase()) || pair.value.toString().toLowerCase().contains(csk.toString().toLowerCase())) {
           Log.i("bbbb", pair.key.toString())
           resultsMap!!.put("First Line", "")
           resultsMap!!.put("Second Line", pair.key.toString())
           resultsMap!!.put("Third Line", pair.value.toString())
           listItems!!.add(resultsMap!!)
       }
   }
})*/

}
    fun setSpinnerAdapter()
    {
        val spinerLock = findViewById(R.id.historyUsingKeySpinnerLock) as Spinner
        val spinerLockAdapter = SpinnerWithoutDeleteAdapter(this, R.layout.spinner_row,
                presenter!!.getLockList(), presenter!!.getLockList())

        spinerLock.adapter = spinerLockAdapter
    }

    fun setSpinnerUserAdapter()
    {
        val spinerLock = findViewById(R.id.historyUsingKeySpinnerUser) as Spinner
        val spinerUserAdapter = SpinnerWithoutDeleteAdapter(this, R.layout.spinner_row,
                presenter!!.getUserList(), presenter!!.getUserList())

        spinerLock.adapter = spinerUserAdapter
    }

    fun setAdapter(listitem:ArrayList<HistoryListElement>)
    {
        Log.i("HHHH","bylem w setAdapter")
        val recyclerView = (findViewById(R.id.historyUsingKeyRecyclerView) as RecyclerView)
        adapter = HistoryListAdapter(listitem,context)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.setLayoutManager(mLayoutManager)
        recyclerView!!.setItemAnimator(DefaultItemAnimator())
        recyclerView!!.setAdapter(adapter)
        if(!firstUse)
        {firstUse=true
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))}
        adapter!!.notifyDataSetChanged()

}

}




