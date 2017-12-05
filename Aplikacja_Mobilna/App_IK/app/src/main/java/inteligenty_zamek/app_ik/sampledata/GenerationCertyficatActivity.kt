package inteligenty_zamek.app_ik.sampledata

import android.content.Intent
import android.content.res.TypedArray
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.ViewFlipper
import inteligenty_zamek.app_ik.GenerateCertyficatRangeActivity

import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.util.ArrayList

import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer
import inteligenty_zamek.app_ik.rest_class.User
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.SpinnerAdapter

class GenerationCertyficatActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null

    internal var items = arrayOf("Certyfikat dla użytkownika", "Certyfikat dla gościa")
    internal var viewFlipper: ViewFlipper?=null
    internal var spin: Spinner?=null
    internal var spin2: Spinner?=null
    internal var name2: TextView?=null
    internal var surname: TextView?=null
    internal var nam: String?=null
    internal var surnam: String?=null
    internal var pozlock = -1
    internal var pozus = -1
    internal var lock = ""
    internal var login = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generation_certyficat)
        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
/*
        viewFlipper = findViewById(R.id.myViewFlipper) as ViewFlipper
        name2 = this.findViewById(R.id.editText7) as TextView
        surname = this.findViewById(R.id.editText8) as TextView
        viewFlipper.displayedChild = 0
        spin = findViewById(R.id.spinnerChangeAdminGenerationCertyficat) as Spinner
        spin2 = findViewById(R.id.spinnerChangeAdminGenerationCertyficat2) as Spinner
        spin.onItemSelectedListener = this
        spin2.onItemSelectedListener = this

        try {
            lock = intent.getStringExtra("lock")
            login = intent.getStringExtra("name")
        } catch (e: Exception) {
        }

        val aa = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                items)

        val aa2 = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                items)

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = aa

        aa2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        spin2.adapter = aa2
        val user = (application as GlobalClassContainer).user
        HTTPRequestDownloadKey(user).execute()
        HTTPRequestDownloadUser(user).execute()
        val register = findViewById(R.id.buttonRangeGenerateCertyficat) as Button
        register.setOnClickListener {
            val intent = Intent(this@GenerationCertyficatActivity, GenerateCertyficatRangeActivity::class.java)
            startActivity(intent)
        }


        val generate = findViewById(R.id.generujAdmin) as Button
        generate.setOnClickListener {
            val from = findViewById(R.id.inputfromAdmingenerate) as EditText
            val to = findViewById(R.id.inputtoadminGenerateKey) as EditText
            val spiner1 = findViewById(R.id.spinnerLoks) as Spinner
            val spiner2 = findViewById(R.id.spinnerUser) as Spinner
            val name = findViewById(R.id.inputName) as EditText
            val fromstring = from.text.toString()
            val tostring = to.text.toString()
            val lokposition = spiner1.selectedItemPosition
            val userposition = spiner2.selectedItemPosition
            val namestring = name.text.toString()
            nam = name2.text.toString()
            surnam = surname.text.toString()
            HTTPRequest((application as GlobalClassContainer).user, fromstring, tostring, lokposition, userposition, namestring).execute()
        }
*/

    }


    override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int,
                                id: Long) {
        if (position == 1) {
          // spin2.setSelection(1)
        } else {
        //    spin.setSelection(0)
        }
       // viewFlipper.displayedChild = position

    }

    override fun onNothingSelected(parent: AdapterView<*>) {


    }


    inner class HTTPRequestDownloadKey(internal var user: User) : AsyncTask<Void, Void, String>() {
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

                val arrJson = jObj.getJSONArray("data")
                (application as GlobalClassContainer).user.addLockList(arrJson)

                runOnUiThread {
                    val spiner1 = findViewById(R.id.spinnerLoks) as Spinner
                    val listkey = ArrayList<String>()
                    val spinerListKeyAdapter = SpinnerAdapter(this@GenerationCertyficatActivity, R.layout.spinner_row, listkey, listkey)
                    spiner1.adapter = spinerListKeyAdapter
                    val usrLog = (application as GlobalClassContainer).user.login
                    val list = (application as GlobalClassContainer).user.getLockslist(usrLog)
                    for (i in list.indices) {
                        try {
                            if (lock == list[i].name.toString()) {
                                spiner1.setSelection(i)
                            }
                        } catch (e: Exception) {
                        }

                        spinerListKeyAdapter.add(list[i].name)
                        spinerListKeyAdapter.notifyDataSetChanged()
                    }
                }

            } catch (e: JSONException) {

            }

        }
    }


    inner class HTTPRequest(internal var user: User, internal var from: String, internal var to: String, internal var idloks: Int, internal var iduser: Int, internal var nameCert: String) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void): String? {
            val httpclient = DefaultHttpClient()


            val adres = "http://" + (application as GlobalClassContainer).serwerIP + ":8080/api/admin/generate_new_certificate/"

            val httppost = HttpPost(adres)

            try {
                // Add your data
                val nameValuePairs = ArrayList<NameValuePair>(2)
                nameValuePairs.add(BasicNameValuePair("login", user.login))
                nameValuePairs.add(BasicNameValuePair("token", (application as GlobalClassContainer).session))
                nameValuePairs.add(BasicNameValuePair("user_id", (application as GlobalClassContainer).userlist[iduser].getIdUser()))
                val login = (application as GlobalClassContainer).user.login
                nameValuePairs.add(BasicNameValuePair("lock_id", (application as GlobalClassContainer).user.getLockslist(login)[idloks].idKey))

                nameValuePairs.add(BasicNameValuePair("name", nam))
                nameValuePairs.add(BasicNameValuePair("surname", surnam))



                nameValuePairs.add(BasicNameValuePair("from_date", from))
                nameValuePairs.add(BasicNameValuePair("to_date", to))


                if ((application as GlobalClassContainer).mondayList != null) {
                    val list = (application as GlobalClassContainer).mondayList
                    var result = ""
                    for (s in list) {
                        result += s + ";"
                    }
                    nameValuePairs.add(BasicNameValuePair("monday", result))
                } else {
                    nameValuePairs.add(BasicNameValuePair("monday", " "))
                }

                if ((application as GlobalClassContainer).tuesdayList != null) {
                    val list = (application as GlobalClassContainer).tuesdayList
                    var result = ""
                    for (s in list) {
                        result += s + ";"
                    }
                    nameValuePairs.add(BasicNameValuePair("tuesday", result))
                } else {
                    nameValuePairs.add(BasicNameValuePair("tuesday", " "))
                }


                if ((application as GlobalClassContainer).wednesdayList != null) {
                    val list = (application as GlobalClassContainer).wednesdayList
                    var result = ""
                    for (s in list) {
                        result += s + ";"
                    }
                    nameValuePairs.add(BasicNameValuePair("wednesday", result))
                } else {
                    nameValuePairs.add(BasicNameValuePair("wednesday", " "))
                }


                if ((application as GlobalClassContainer).thurstdayList != null) {
                    val list = (application as GlobalClassContainer).thurstdayList
                    var result = ""
                    for (s in list) {
                        result += s + ";"
                    }
                    nameValuePairs.add(BasicNameValuePair("thursday", result))
                } else {
                    nameValuePairs.add(BasicNameValuePair("thursday", " "))
                }


                if ((application as GlobalClassContainer).fridyList != null) {
                    val list = (application as GlobalClassContainer).fridyList
                    var result = ""
                    for (s in list) {
                        result += s + ";"
                    }
                    nameValuePairs.add(BasicNameValuePair("friday", result))
                } else {
                    nameValuePairs.add(BasicNameValuePair("friday", " "))
                }

                if ((application as GlobalClassContainer).saturdayList != null) {
                    val list = (application as GlobalClassContainer).saturdayList
                    var result = ""
                    for (s in list) {
                        result += s + ";"
                    }
                    nameValuePairs.add(BasicNameValuePair("saturday", result))
                } else {
                    nameValuePairs.add(BasicNameValuePair("saturday", " "))
                }

                if ((application as GlobalClassContainer).sundayList != null) {
                    val list = (application as GlobalClassContainer).sundayList
                    var result = ""
                    for (s in list) {
                        result += s + ";"
                    }
                    nameValuePairs.add(BasicNameValuePair("sunday", result))
                } else {
                    nameValuePairs.add(BasicNameValuePair("sunday", " "))
                }

                nameValuePairs.add(BasicNameValuePair("is_pernament", "0"))




                httppost.entity = UrlEncodedFormEntity(nameValuePairs)
                //Log.i("tutaj"," 5");

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

            } catch (e: JSONException) {

            }


        }
    }


    inner class HTTPRequestDownloadUser(internal var user: User) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void): String? {
            val httpclient = DefaultHttpClient()

            val adres = "http://" + (application as GlobalClassContainer).serwerIP + ":8080/api/download/all_user/"

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

                val arrJson = jObj.getJSONArray("data")
                (application as GlobalClassContainer).addUserList(arrJson)

                runOnUiThread {
                    val spiner1 = findViewById(R.id.spinnerUser) as Spinner
                    val listkey = ArrayList<String>()
                    val spinerListKeyAdapter = SpinnerAdapter(this@GenerationCertyficatActivity, R.layout.spinner_row, listkey, listkey)
                    spiner1.adapter = spinerListKeyAdapter
                    val list = (application as GlobalClassContainer).userlist
                    for (i in list.indices) {
                        try {
                            if (login == list[i].name.toString()) {
                                spiner1.setSelection(i)
                            }
                        } catch (e: Exception) {
                        }

                        spinerListKeyAdapter.add(list[i].login)
                        spinerListKeyAdapter.notifyDataSetChanged()
                    }
                }

            } catch (e: JSONException) {

            }

        }
    }


}