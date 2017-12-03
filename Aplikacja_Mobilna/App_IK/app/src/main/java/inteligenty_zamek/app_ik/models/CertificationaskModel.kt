package inteligenty_zamek.app_ik.models

import android.widget.SimpleAdapter
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.Views.CertificationaskActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * Created by Damian on 2017-11-29.
 */
class CertificationaskModel(val context: CertificationaskActivity)
{

    val login:String= sharedPreferenceApi.getString(context,EnumChoice.login)
    val token:String=sharedPreferenceApi.getString(context,EnumChoice.token)
    val ipAddres:String=sharedPreferenceApi.getString(context, EnumChoice.ip)
    private var listItems: MutableList<LinkedHashMap<String, String>> = ArrayList()
    var locks: Array<Array<String?>>?=null
    private var size: Int = 0
    private var resultsMap: LinkedHashMap<String, String>?=null
    private var adapter: SimpleAdapter?=null
    var arrJson:JSONArray?=null


    fun getjsonArrayOfLock(value: String)
    {
       val  jObj = JSONObject(value)
        if (jObj.getString("data") != "empty") {

            arrJson = jObj.getJSONArray("data")
            locks = Array(arrJson!!.length()) { arrayOfNulls<String>(4) }
            size = arrJson!!.length()
            for (i in 0 until arrJson!!.length()) {
                try {
                    locks!![i][0] = arrJson!!.getJSONObject(i).getString("NAME")
                    locks!![i][1] = arrJson!!.getJSONObject(i).getString("LOCALIZATION")
                    locks!![i][2] = arrJson!!.getJSONObject(i).getString("ID_LOCK")
                    locks!![i][3]=i.toString()
                } catch (ignored: JSONException) {
                }

            }
            context.showMessage("Pobrano listę zamków")
        }
    else {
            context.showMessage("Lista pusta")
        }

    }

    fun getKeyID(position:Int):String?
    {
        for (e in locks!!) {
            if (e[3]==position.toString())
        {
            return e[2]
        }
        }
        return null
    }

    fun returnAdapter(cs:CharSequence):SimpleAdapter
    {
        listItems= ArrayList()
        adapter = SimpleAdapter(context, listItems, R.layout.main_key_list,
                arrayOf("First Line", "Second Line"),
                intArrayOf(R.id.TextView_listPlaceKey, R.id.TextView_listNameKey))
        var i:Int=0

        for (e in locks!!) {

            if (e[0]!!.toLowerCase().contains(cs.toString().toLowerCase()) || e[1]!!.toLowerCase().contains(cs.toString().toLowerCase())) {
                resultsMap = LinkedHashMap()
                resultsMap!!.put("First Line", e[0]!!)
                resultsMap!!.put("Second Line", e[1]!!)
                listItems.add(resultsMap!!)
                e[3]=i.toString()
                i++

            } else if (cs.toString() === "") {
                resultsMap = LinkedHashMap()
                resultsMap!!.put("First Line", e[0]!!)
                resultsMap!!.put("Second Line",e[1]!!)
                listItems.add(resultsMap!!)
                e[3]=i.toString()
                i++
            }
            else
            {
                  e[3]=(-1).toString()
            }

    }
   return adapter!!
}

}