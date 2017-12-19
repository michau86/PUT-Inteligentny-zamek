package inteligenty_zamek.app_ik.models

import android.util.Log
import android.widget.SimpleAdapter
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.Views.CertificationaskActivity
import inteligenty_zamek.app_ik.Views.CertyficatWaitActivity
import inteligenty_zamek.app_ik.rest_class.Certyficat
import inteligenty_zamek.app_ik.rest_class.Lock
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
    val token:String=CyptographyApi.decrypt(sharedPreferenceApi.getString(context,EnumChoice.token))
    val ipAddres:String=sharedPreferenceApi.getString(context, EnumChoice.ip)
    var listItems: MutableList<LinkedHashMap<String, String>> = ArrayList()
    var lockList: ArrayList<Lock>?=null
    private var positionCertyficat:LinkedHashMap<Int,Int>?=null
    private var resultsMap: LinkedHashMap<String, String>?=null
    var cs:CharSequence=""

    fun getjsonArrayOfLock(value: String)
    {
       val  jObj = JSONObject(value)
        if (jObj.getString("data") != "empty") {

           val arrJson = jObj.getJSONArray("data")
            lockList = ArrayList<Lock>()
            for (i in 0 until arrJson!!.length()) {
                try {
                   val lock=Lock()
                    lock.name  =  arrJson.getJSONObject(i).getString("NAME")
                    lock.localization=arrJson.getJSONObject(i).getString("LOCALIZATION")
                    lock.idKey=arrJson.getJSONObject(i).getString("ID_LOCK")
                    lock.mac_Adres=arrJson.getJSONObject(i).getString("MAC_ADDRESS")
                    lockList!!.add(lock)

                } catch (e: JSONException) { }
            }
            context.showMessage("Pobrano listę zamków")
        }
    else {
            context.showMessage("Lista pusta")
        }

    }

    fun getKeyID(position:Int):String?
    {
       return lockList!![positionCertyficat!![position]!!].idKey!!
    }

    fun createValueToAdapter(cs:CharSequence)
    {
        this.cs=cs
        listItems= ArrayList()
        positionCertyficat= LinkedHashMap()
        var indexInCertificatArray=0
        var indexInListItem=0
        for (e in lockList!!) {

            if (e.name!!.toLowerCase().contains(cs.toString().toLowerCase()) || e.localization!!.toLowerCase().contains(cs.toString().toLowerCase())) {
                resultsMap = LinkedHashMap()
                resultsMap!!.put("First Line",e.localization )
                resultsMap!!.put("Second Line", e.name)
                listItems.add(resultsMap!!)
                positionCertyficat!!.put(indexInListItem,indexInCertificatArray)
                indexInListItem++

            } else if (cs.toString() === "") {
                resultsMap = LinkedHashMap()
                resultsMap!!.put("First Line", e.name!!)
                resultsMap!!.put("Second Line",e.localization!!)
                listItems.add(resultsMap!!)
                positionCertyficat!!.put(indexInListItem,indexInCertificatArray)
                indexInListItem++
            }
            indexInCertificatArray++
    }
   return
}

}