package inteligenty_zamek.app_ik.sampledata

import android.content.res.TypedArray
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast

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
import java.util.LinkedHashMap

import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer
import inteligenty_zamek.app_ik.rest_class.User
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import java.util.Map

class CertificationaskActivity : BaseActivity() {
    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null
    internal var toastDelay = 4000
    private var arrJson: JSONArray?=null
    private var listItems: MutableList<LinkedHashMap<String, String>> = ArrayList()
    private var listItems2: MutableList<LinkedHashMap<String, String>> = ArrayList()

    private var resultsListView: ListView?=null
    private var Keys: LinkedHashMap<String, String>?=null
    private var locks: Array<Array<String>>?=null
    private var size: Int = 0
    private var resultsMap: LinkedHashMap<String, String>?=null
    private var csk: CharSequence?=null
    private var adapter: SimpleAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  setContentView(R.layout.activity_certyficationask)
        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)

        try {
            val user = (application as GlobalClassContainer).user
            CertificationaskActivity.HTTPRequestLocks(user).execute()
        } catch (except: Exception) {
        }




        val inputSearch = findViewById(R.id.editText_Search) as EditText
        inputSearch.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                csk = cs
                Keys = LinkedHashMap()
                listItems2 = ArrayList()
                adapter = SimpleAdapter(this@CertificationaskActivity, listItems2, R.layout.main_key_list,
                        arrayOf("First Line", "Second Line"),
                        intArrayOf(R.id.TextView_listPlaceKey, R.id.TextView_listNameKey))



                try {
                    for (i in listItems.indices) {
                        Keys!!.put(listItems[i].values.toTypedArray()[0].toString(), listItems[i].values.toTypedArray()[1].toString())

                    }
                } catch (e: Exception) {
                }

                val it = Keys!!.entries.iterator()
                while (it.hasNext()) {
                    resultsMap = LinkedHashMap()
                    val pair = it.next() as Map.Entry<*, *>
                    if (pair.key.toString().toLowerCase().contains(cs.toString().toLowerCase()) || pair.value.toString().toLowerCase().contains(cs.toString().toLowerCase())) {

                        resultsMap!!.put("First Line", pair.key.toString())
                        resultsMap!!.put("Second Line", pair.value.toString())
                        listItems2.add(resultsMap!!)
                    } else if (cs.toString() === "") {


                        resultsMap!!.put("First Line", pair.key.toString())
                        resultsMap!!.put("Second Line", pair.value.toString())
                        listItems2.add(resultsMap!!)
                    }
                }
                resultsListView!!.adapter = adapter


                resultsListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    var poz = -1
                    for (i in 0 until size) {
                        if (listItems2[position].values.toTypedArray()[0].toString() == locks!![i][0] && listItems2[position].values.toTypedArray()[1].toString() == locks!![i][1]) {
                            poz = i
                            break
                        }
                    }
                    if (poz >= 0) {
                        val key_id = locks!![poz][2]
                        val user = (application as GlobalClassContainer).user
                        CertificationaskActivity.HTTPRequest(user, key_id).execute()
                    }
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

    inner class HTTPRequestLocks(internal var user: User) : AsyncTask<Void, Void, String>() {
        internal var choise: Boolean = false

        override fun doInBackground(vararg params: Void): String? {
            val httpclient = DefaultHttpClient()
            val adres = "http://" + (application as GlobalClassContainer).serwerIP + ":8080/api/download/all_locks/"
            val httppost = HttpPost(adres)
            try {
                // Add your data
                val nameValuePairs = ArrayList<NameValuePair>(2)
                nameValuePairs.add(BasicNameValuePair("login", user.login))
                nameValuePairs.add(BasicNameValuePair("token", (application as GlobalClassContainer).session))
                httppost.entity = UrlEncodedFormEntity(nameValuePairs)
                // Execute HTTP Post Request
                val response = httpclient.execute(httppost)
                val entity = response.entity
                return EntityUtils.toString(entity, "UTF-8")
            } catch (e: ClientProtocolException) {
                // TODO Auto-generated catch block
            } catch (e: IOException) {
                // TODO Auto-generated catch block
            }

            return null
        }

        //akcja po otrzyman iu odpowiedzi z serwera
        override fun onPostExecute(response: String) {
            super.onPostExecute(response)
            var jObj: JSONObject? = null
            try {
                jObj = JSONObject(response)
                if (jObj.getString("data") != "empty") {
                    arrJson = jObj.getJSONArray("data")
                    resultsListView = this@CertificationaskActivity.findViewById(R.id.ListView_key_generate_certyfiaction) as ListView
                    //hasmap przechowujący elemety do wyświetlenia
                    Keys = LinkedHashMap()
                    locks = Array(arrJson!!.length()) { arrayOfNulls(3) }
                    size = arrJson!!.length()
                    for (i in 0 until arrJson!!.length()) {
                        try {
                            locks!![i][0] = arrJson!!.getJSONObject(i).getString("NAME")
                            locks!![i][1] = arrJson!!.getJSONObject(i).getString("LOCALIZATION")
                            locks!![i][2] = arrJson!!.getJSONObject(i).getString("ID_LOCK")
                            Keys!!.put(arrJson!!.getJSONObject(i).getString("NAME"), arrJson!!.getJSONObject(i).getString("LOCALIZATION"))
                        } catch (ignored: JSONException) {
                        }

                    }

                    //stworzenie adaptera
                    adapter = SimpleAdapter(this@CertificationaskActivity, listItems, R.layout.main_key_list,
                            arrayOf("First Line", "Second Line"),
                            intArrayOf(R.id.TextView_listPlaceKey, R.id.TextView_listNameKey))

                    //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
                    val it = Keys!!.entries.iterator()
                    while (it.hasNext()) {
                        resultsMap = LinkedHashMap()
                        val pair = it.next() as Map.Entry<*, *>
                        Log.i("aaaaa", pair.key.toString())
                        resultsMap!!.put("First Line", pair.key.toString())
                        resultsMap!!.put("Second Line", pair.value.toString())
                        listItems.add(resultsMap!!)
                    }
                    resultsListView!!.adapter = adapter

                    resultsListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        //ustalenie id certyfikatu z listy certyfikatow
                        val key_id = locks!![position][2]
                        val user = (application as GlobalClassContainer).user
                        CertificationaskActivity.HTTPRequest(user, key_id).execute()
                    }

                    val toast = Toast.makeText(this@CertificationaskActivity, "Pobrano listę zamków", Toast.LENGTH_LONG)
                    toast.show()
                    object : CountDownTimer(toastDelay.toLong(), 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            toast.show()
                        }

                        override fun onFinish() {
                            toast.show()
                        }
                    }.start()
                } else {
                    val toast = Toast.makeText(this@CertificationaskActivity, "Lista pusta", Toast.LENGTH_LONG)
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
            } catch (e: JSONException) {

                val toast = Toast.makeText(this@CertificationaskActivity, "Niemożna pobrać listy zamków", Toast.LENGTH_LONG)
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
    }

    inner class HTTPRequest(internal var user: User, internal var id_lock: String) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void): String? {
            val httpclient = DefaultHttpClient()
            val adres = "http://" + (application as GlobalClassContainer).serwerIP + ":8080/api/request_new_certificate/"
            val httppost = HttpPost(adres)
            try {
                // Add your data
                val nameValuePairs = ArrayList<NameValuePair>(3)
                nameValuePairs.add(BasicNameValuePair("login", user.login))
                nameValuePairs.add(BasicNameValuePair("token", (application as GlobalClassContainer).session))
                nameValuePairs.add(BasicNameValuePair("lock_id", id_lock))
                httppost.entity = UrlEncodedFormEntity(nameValuePairs)
                // Execute HTTP Post Request
                val response = httpclient.execute(httppost)
                val entity = response.entity
                return EntityUtils.toString(entity, "UTF-8")
            } catch (e: ClientProtocolException) {
                // TODO Auto-generated catch block
            } catch (e: IOException) {
                // TODO Auto-generated catch block
            }

            return null
        }

        //akcja po otrzyman iu odpowiedzi z serwera
        override fun onPostExecute(response: String) {
            super.onPostExecute(response)
            var jObj: JSONObject? = null
            try {
                jObj = JSONObject(response)
                if (jObj.getString("status") == "ok") {

                    val toast = Toast.makeText(this@CertificationaskActivity, "Wysłano wniosek", Toast.LENGTH_LONG)
                    toast.show()
                    object : CountDownTimer(toastDelay.toLong(), 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            toast.show()
                        }

                        override fun onFinish() {
                            toast.show()
                        }
                    }.start()
                } else {
                    val toast = Toast.makeText(this@CertificationaskActivity, "Wniosek odrzucono", Toast.LENGTH_LONG)
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
            } catch (e: JSONException) {

                val toast = Toast.makeText(this@CertificationaskActivity, "Wniosek odrzucono", Toast.LENGTH_LONG)
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

        }*/
    }
}
