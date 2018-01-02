package inteligenty_zamek.app_ik.inWork

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.os.AsyncTask
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
import android.widget.*

import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.LinkedHashMap

import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.adapters.HistoryListAdapter
import inteligenty_zamek.app_ik.adapters.TwoButtonAdapter
import inteligenty_zamek.app_ik.adapters.userWatModel
import inteligenty_zamek.app_ik.models.HistoryListElement
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer
import inteligenty_zamek.app_ik.rest_class.User
import java.security.AccessController.getContext
import java.util.Map


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
    var presenter:History_using_keyPresenter?=null
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
        val sampleText = this.findViewById(R.id.TextView_sortingIco) as TextView
        sampleText.typeface = fontFamily
        context=this
        presenter=History_using_keyPresenter(this)

        presenter!!.getList()
        (findViewById(R.id.historyUsingKeyTextViewShowHideIco) as TextView).typeface=fontFamily

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


//

/*
val inputSearch = findViewById(R.id.HistoryEditText) as EditText
inputSearch.addTextChangedListener(object : TextWatcher {

   override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
       csk = cs
       Keys = LinkedHashMap()
       listItems2 = ArrayList()
       adapter = SimpleAdapter(this@History_using_keyActivity, listItems2, R.layout.history_using_key_list,
               arrayOf("First Line", "Second Line", "Third Line"),
               intArrayOf(R.id.history_icon, R.id.title, R.id.describe))
       try {
           for (i in listItems!!.indices) {
              presenter!!.model. Keys!!.put(listItems!![i].values.toTypedArray()[1].toString(), listItems!![i].values.toTypedArray()[2].toString())
               Log.i(listItems!![i].values.toTypedArray()[1].toString(), listItems!![i].values.toTypedArray()[2].toString())

           }
       } catch (e: Exception) {
       }

       val it = Keys!!.entries.iterator()
       while (it.hasNext()) {
           resultsMap = LinkedHashMap()
           val pair = it.next() as Map.Entry<*, *>
           Log.i("aaaaaaa", pair.value.toString().toLowerCase())
           Log.i("aaaaaaaa", cs.toString().toLowerCase())
           if (pair.key.toString().toLowerCase().contains(cs.toString().toLowerCase()) || pair.value.toString().toLowerCase().contains(cs.toString().toLowerCase())) {


               resultsMap!!.put("First Line", "")
               resultsMap!!.put("Second Line", pair.key.toString())
               resultsMap!!.put("Third Line", pair.value.toString())
               listItems2!!.add(resultsMap!!)
           } else if (cs.toString() === "") {


               resultsMap!!.put("First Line", "")
               resultsMap!!.put("Second Line", pair.key.toString())
               resultsMap!!.put("Third Line", pair.value.toString())
               listItems2!!.add(resultsMap!!)
           }
       }
       resultsListView!!.adapter = adapter
   }

   override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int,
                                  arg3: Int) {
       // TODO Auto-generated method stub

   }

   override fun afterTextChanged(arg0: Editable) {
       // TODO Auto-generated method stub
   }
})
*/

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

// adapter = SimpleAdapter(this@History_using_keyActivity,
//       presenter!!.model!!.listItems, R.layout.history_using_key_list,
//         arrayOf("First Line", "Second Line", "Third Line"),
//         intArrayOf(R.id.history_icon, R.id.title, R.id.describe))
// resultsListView!!.setAdapter(adapter)
}

}




