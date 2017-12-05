package inteligenty_zamek.app_ik.presenters

import android.content.Intent
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.sampledata.GenerationCertyficatActivity
import inteligenty_zamek.app_ik.Views.CertyficatWaitActivity
import inteligenty_zamek.app_ik.models.CertyficatWaitModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.Map
import kotlin.collections.LinkedHashMap

/**
 * Created by Damian on 05.12.2017.
 */
class CertyficatWaitPresenter(val view: CertyficatWaitActivity)
{
    val model: CertyficatWaitModel
    init {
        model= CertyficatWaitModel(view)
        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        try {
            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/cetificate_waiting/", "initresult", toSend).execute()
        } catch (e: Exception) { }
    }

    fun initresult(response:String)
    {
        try {
            if (JSONObject(response).getString("data") != "empty") {
                var arrJson: JSONArray  = JSONObject(response).getJSONArray("data")
                model.Keys = LinkedHashMap()
                model.hashmapPositionID= LinkedHashMap()
                for (i in 0 until arrJson.length()) {
                    try {

                        model!!.Keys!!.put(arrJson.getJSONObject(i).getString("ID_KEY"),
                                ArrayList<String>(
                                Arrays.asList(
                                arrJson.getJSONObject(i).getString("LOCK_NAME"),
                                arrJson.getJSONObject(i).getString("LOGIN"),
                                arrJson.getJSONObject(i).getString("USER_NAME"),
                                arrJson.getJSONObject(i).getString("USER_SUERNAME")
                                )
                                ))
                        model!!.hashmapPositionID!!.put(i,arrJson.getJSONObject(i).getString("ID_KEY"))

                    } catch (ignored: JSONException) {
                    }
                }
                updateList("")
                view.showMessage("Lista została pobrana")
            } else {
                view.showMessage("Brak elementów do wyświetlenia")
            }
        } catch (e: JSONException) {
           view.showMessage("Wystąpił problem podczas pobierania listy")
        }
    }

    fun updateList(cs:CharSequence)
    {
        model.hashmapPositionID= LinkedHashMap()
        model.listItems = ArrayList()
        val it = model.Keys!!.entries.iterator()
        var i:Int=0
        while (it.hasNext()) {

            val  resultsMap = LinkedHashMap<String, String>()
            val pair = it.next() as Map.Entry<*, *>
            val Array:ArrayList<String> =pair.value  as ArrayList<String>
            if (Array[0].toLowerCase().contains(cs.toString().toLowerCase()) || Array[1].toString().toLowerCase().contains(cs.toString().toLowerCase())
                 ||   Array[2].toString().toLowerCase().contains(cs.toString().toLowerCase()) ||Array[3].toString().toLowerCase().contains(cs.toString().toLowerCase())
                    ) {

                resultsMap.put("First Line", Array[0] )
                resultsMap.put("Second Line", "dla użytkownika: Login:"+ Array[1]+
                        " Imie i nazwisko: "+Array[2]+" "+Array[3])
                model.listItems.add(resultsMap)
                model!!.hashmapPositionID!!.put(i,pair.key.toString())
                i++
            } else if (cs.toString() === "") {

                resultsMap.put("First Line", Array[0] )
                resultsMap.put("Second Line", "dla użytkownika: Login:"+ Array[1]+
                        " Imie i nazwisko: "+Array[2]+" "+Array[3])
                model.listItems.add(resultsMap)
                model!!.hashmapPositionID!!.put(i,pair.key.toString())
                i++
            }
        }
       view.setAdapter(model.listItems)
    }

    fun acceptButton(position:Int)
    {
        model.position=position
        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        toSend.put("cetificate_id",model.hashmapPositionID!!.get(position)!!)
        try {
            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/certificate_decision/", "AcceptDecision", toSend).execute()
        } catch (e: Exception) { }
    }

    fun AcceptDecision(result:String)
    {
        if (JSONObject(result).getString("status") == "ok")
        {
            val intent = Intent(view, GenerationCertyficatActivity::class.java)
            intent.putExtra("Lock_name",   model.Keys!!.get(model.hashmapPositionID!!.get(model.position))!![0])
            intent.putExtra("login", model.Keys!!.get(model.hashmapPositionID!!.get(model.position))!![1])
            model!!.Keys!!.remove(model.hashmapPositionID!!.get(model.position))
            model.listItems.removeAt(model.position)
            view.setAdapter(model.listItems)
            view.startActivity(intent)
        }
        else
        {
            view.showMessage("Wystąpił problem podczas akeptowania ")
        }
    }

    fun removeButton(position:Int)
    {
    model.position=position
    model.Keys!!.get(model.hashmapPositionID!!.get(position))
    val toSend: HashMap<String, String> = HashMap()
    toSend.put("login", model.login)
    toSend.put("token", model.token)
    toSend.put("cetificate_id",model.hashmapPositionID!!.get(position)!!)
    try {
        HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/certificate_decision/", "removeDecision", toSend).execute()
    } catch (e: Exception) { }
}

    fun removeDecision(result:String)
    {
        if (JSONObject(result).getString("status") == "ok")
        {
            model!!.Keys!!.remove(model.hashmapPositionID!!.get(model.position))
            model.listItems.removeAt(model.position)
            view.setAdapter(model.listItems)
        }
        else
        {
            view.showMessage("Wystąpił problem podczas usuwania z listy")
        }
        }


}