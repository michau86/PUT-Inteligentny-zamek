package inteligenty_zamek.app_ik.sampledata

import android.content.Intent
import android.content.res.TypedArray
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
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

import inteligenty_zamek.app_ik.GenerationCertyficatActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer
import inteligenty_zamek.app_ik.rest_class.User
import inteligenty_zamek.app_ik.Navigation.BaseActivity

class CertyficatWaitActivity : BaseActivity() {
    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null
    internal var toastDelay = 4000
    internal var arrJson: JSONArray?=null
    internal var listItems: MutableList<LinkedHashMap<String, String>> = ArrayList()
    internal var listItems2: MutableList<LinkedHashMap<String, String>> = ArrayList()
    internal var adapter: SimpleAdapter?=null
    internal var resultsListView: ListView?=null
    internal var csk: CharSequence?=null
    internal var Keys: LinkedHashMap<String, String>?=null
    internal var resultsMap: LinkedHashMap<String, String>?=null
    internal var idcert: LinkedHashMap<String, String>?=null
    internal var login: LinkedHashMap<String, String>?=null
    internal var lockName: LinkedHashMap<String, String>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certyficat_wait)

        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)


        try {
            if ((application as GlobalClassContainer).isadmin >= 0) {
                val user = (application as GlobalClassContainer).user
                CertyficatWaitActivity.HTTPRequest(user).execute()
            }
        } catch (except: Exception) {
        }


        val inputSearch = findViewById(R.id.editText_Search) as EditText
        inputSearch.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                csk = cs
                Keys = LinkedHashMap()
                listItems2 = ArrayList()
                adapter = object : SimpleAdapter(this@CertyficatWaitActivity, listItems2, R.layout.user_wait_key_list,
                        arrayOf("First Line", "Second Line"),
                        intArrayOf(R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey)) {
                    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
                        val itemView = super.getView(position, convertView, parent)
                        //akceptuj
                        val myTaskButton = itemView.findViewById(R.id.button6)
                        myTaskButton.setOnClickListener {
                            //akcja do butonu akceptuj
                            try {
                                if ((application as GlobalClassContainer).isadmin >= 0) {
                                    val user = (application as GlobalClassContainer).user
                                    val s = listItems[position].values.toTypedArray()[0].toString()
                                    CertyficatWaitActivity.HTTPRequestDecision(user, listItems[position].values.toTypedArray()[0].toString(), idcert[s]).execute()


                                    Log.i("aaaaaaaa", "no wchodzii")
                                    val intent = Intent(baseContext, GenerationCertyficatActivity::class.java)
                                    intent.putExtra("lock", lockName[listItems[position].values.toTypedArray()[0]])
                                    intent.putExtra("", login[listItems[position].values.toTypedArray()[1]])
                                    listItems.remove(listItems2[position])
                                    //listItems2.remove(position);
                                    Log.i("aaaaaaaa", "no wchodzii2")
                                    //z listitems 1 remowe
                                    resultsListView.adapter = adapter
                                    startActivity(intent)
                                }
                            } catch (except: Exception) {
                            }
                        }
                        //odrzuc
                        val myTaskButton2 = itemView.findViewById(R.id.button5)
                        myTaskButton2.setOnClickListener {
                            //akcja do butonu odrzuc
                            try {
                                if ((application as GlobalClassContainer).isadmin >= 0) {
                                    val user = (application as GlobalClassContainer).user
                                    CertyficatWaitActivity.HTTPRequestDecision(user, listItems2[position].values.toTypedArray()[0].toString(), "0").execute()
                                    listItems.remove(listItems2[position])
                                    listItems2.removeAt(position)
                                    //to samo
                                    resultsListView.adapter = adapter
                                }
                            } catch (except: Exception) {
                            }
                        }
                        return itemView
                    }
                }
                try {
                    for (i in listItems.indices) {
                        Keys.put(listItems[i].values.toTypedArray()[0].toString(), listItems[i].values.toTypedArray()[1].toString())

                    }
                } catch (e: Exception) {
                }

                val it = Keys.entries.iterator()
                while (it.hasNext()) {
                    resultsMap = LinkedHashMap()
                    val pair = it.next() as Entry<*, *>
                    if (pair.key.toString().toLowerCase().contains(cs.toString().toLowerCase()) || pair.value.toString().toLowerCase().contains(cs.toString().toLowerCase())) {

                        resultsMap.put("First Line", pair.key.toString())
                        resultsMap.put("Second Line", pair.value.toString())
                        listItems2.add(resultsMap)
                    } else if (cs.toString() === "") {


                        resultsMap.put("First Line", pair.key.toString())
                        resultsMap.put("Second Line", pair.value.toString())
                        listItems2.add(resultsMap)
                    }
                }
                resultsListView.adapter = adapter
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


    inner class HTTPRequest(internal var user: User) : AsyncTask<Void, Void, String>() {
        internal var choise: Boolean = false

        override fun doInBackground(vararg params: Void): String? {
            val httpclient = DefaultHttpClient()
            val adres = "http://" + (application as GlobalClassContainer).serwerIP + ":8080/api/admin/cetificate_waiting/"
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
            Log.i("aaaaaaaaa", response)

            var jObj: JSONObject? = null
            try {
                jObj = JSONObject(response)
                if (jObj.getString("data") != "empty") {
                    arrJson = jObj.getJSONArray("data")
                    resultsListView = this@CertyficatWaitActivity.findViewById(R.id.listView_user_wait_Keys) as ListView
                    //hasmap przechowujący elemety do wyświetlenia
                    Keys = LinkedHashMap()
                    idcert = LinkedHashMap()
                    login = LinkedHashMap()
                    lockName = LinkedHashMap()
                    for (i in 0 until arrJson.length()) {
                        try {
                            Keys.put(arrJson.getJSONObject(i).getString("LOCK_NAME") + " " + arrJson.getJSONObject(i).getString("LOGIN"), "dla uzytkownika: " + arrJson.getJSONObject(i).getString("USER_NAME") + " " + arrJson.getJSONObject(i).getString("USER_SUERNAME"))

                            idcert.put(arrJson.getJSONObject(i).getString("LOCK_NAME") + " " + arrJson.getJSONObject(i).getString("LOGIN"), arrJson.getJSONObject(i).getString("ID_KEY"))
                            login.put(arrJson.getJSONObject(i).getString("LOCK_NAME") + " " + arrJson.getJSONObject(i).getString("LOGIN"), arrJson.getJSONObject(i).getString("LOGIN"))
                            lockName.put(arrJson.getJSONObject(i).getString("LOCK_NAME") + " " + arrJson.getJSONObject(i).getString("LOGIN"), arrJson.getJSONObject(i).getString("LOCK_NAME"))

                            Log.i("aaaaaaaaporow", arrJson.getJSONObject(i).getString("LOCK_NAME") + " " + arrJson.getJSONObject(i).getString("LOGIN"))

                        } catch (ignored: JSONException) {
                        }

                    }
                    //stworzenie adaptera
                    adapter = object : SimpleAdapter(this@CertyficatWaitActivity, listItems, R.layout.user_wait_key_list,
                            arrayOf("First Line", "Second Line"),
                            intArrayOf(R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey)) {
                        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
                            val itemView = super.getView(position, convertView, parent)
                            //akceptuj
                            val myTaskButton = itemView.findViewById(R.id.button6)
                            myTaskButton.setOnClickListener {
                                //akcja do butonu akceptuj
                                try {
                                    if ((application as GlobalClassContainer).isadmin >= 0) {
                                        val user = (application as GlobalClassContainer).user
                                        val s = listItems[position].values.toTypedArray()[0].toString()

                                        CertyficatWaitActivity.HTTPRequestDecision(user, listItems[position].values.toTypedArray()[0].toString(), idcert[s]).execute()
                                        val intent = Intent(this@CertyficatWaitActivity, GenerationCertyficatActivity::class.java)
                                        intent.putExtra("Lock_name", lockName[s])
                                        intent.putExtra("login", login[s])
                                        listItems2.remove(listItems[position])

                                        Log.i("aaaaaaa", login[s])
                                        //listItems.remove(position);
                                        resultsListView.adapter = adapter
                                        startActivity(intent)
                                    }
                                } catch (except: Exception) {
                                }
                            }
                            //odrzuc
                            val myTaskButton2 = itemView.findViewById(R.id.button5)
                            myTaskButton2.setOnClickListener {
                                //akcja do butonu odrzuc
                                try {
                                    if ((application as GlobalClassContainer).isadmin >= 0) {
                                        val user = (application as GlobalClassContainer).user

                                        val s = listItems[position].values.toTypedArray()[0].toString()

                                        CertyficatWaitActivity.HTTPRequestDecision(user, listItems[position].values.toTypedArray()[0].toString(), idcert[s]).execute()
                                        listItems2.remove(listItems[position])
                                        listItems.removeAt(position)
                                        resultsListView.adapter = adapter
                                    }
                                } catch (except: Exception) {
                                }
                            }
                            return itemView
                        }
                    }

                    //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
                    val it = Keys.entries.iterator()
                    while (it.hasNext()) {
                        resultsMap = LinkedHashMap()
                        val pair = it.next() as Entry<*, *>
                        resultsMap.put("First Line", pair.key.toString())
                        resultsMap.put("Second Line", pair.value.toString())
                        listItems.add(resultsMap)
                    }
                    listItems2 = listItems
                    resultsListView.adapter = adapter

                    val toast = Toast.makeText(this@CertyficatWaitActivity, "Pobrano listę oczekujących certyfikatów na akceptację", Toast.LENGTH_LONG)
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
                    val toast = Toast.makeText(this@CertyficatWaitActivity, "Lista pusta", Toast.LENGTH_LONG)
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

                val toast = Toast.makeText(this@CertyficatWaitActivity, "Niemożna pobrać listy oczekujących certyfikató na akceptację", Toast.LENGTH_LONG)
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

    inner class HTTPRequestDecision(internal var user: User, login_user: String, internal var id: String) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void): String? {
            val httpclient = DefaultHttpClient()
            val adres = "http://" + (application as GlobalClassContainer).serwerIP + ":8080/api/admin/certificate_decision/"
            val httppost = HttpPost(adres)
            try {
                // Add your data
                val nameValuePairs = ArrayList<NameValuePair>(4)
                nameValuePairs.add(BasicNameValuePair("login", user.login))
                nameValuePairs.add(BasicNameValuePair("token", (application as GlobalClassContainer).session))
                nameValuePairs.add(BasicNameValuePair("cetificate_id", id))
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

                    val toast = Toast.makeText(this@CertyficatWaitActivity, "Decyzja przyjęta", Toast.LENGTH_LONG)
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
                    val toast = Toast.makeText(this@CertyficatWaitActivity, "Decyzja odrzucona", Toast.LENGTH_LONG)
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

                val toast = Toast.makeText(this@CertyficatWaitActivity, "Decyzja odrzucona", Toast.LENGTH_LONG)
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
}
