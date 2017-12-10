package inteligenty_zamek.app_ik.beforeTest

import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.Map
import kotlin.collections.LinkedHashMap

/**
 * Created by Damian on 09.12.2017.
 */
class ManagmentCertyficationUserPresenter(val view: ManagmentCertyficationUserActivity)
{

    val model= ManagmentCertyficationUserModel(view)
    fun initActivity() {

        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        try {
            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/download/all_certificate/", "initResult", toSend).execute()
        } catch (e: Exception) { }
    }

    fun initResult(response:String) {
        try {

            if (JSONObject(response).getString("data") != "empty") {
                val arrJson = JSONObject(response).getJSONArray("data")
                //hasmap przechowujący elemety do wyświetlenia
                model.Keys = LinkedHashMap()
                for (i in 0 until arrJson.length()) {
                    try {
                        model.Keys!!.put(arrJson.getJSONObject(i).getString("USER_NAME") + " " + arrJson.getJSONObject(i).getString("USER_SURNAME") + " " + arrJson.getJSONObject(i).getString("LOCK_NAME"), "")
                    } catch (ignored: JSONException) {
                    }

                }
                updateList("")
            }
        } catch (exc: Exception) {
        }

    }


    fun sortList()
    {
        if (!!model.flag) {


           // Arrays.sort(model.Keys, Collections.reverseOrder())
           // model.Keys!!.toSortedMap().
            //Arrays.sort(model!!.Keys, Collections.reverseOrder())
            model.flag = true
        } else {

            //  Arrays.sort((application as GlobalClassContainer).getUser().getCertyficateList())
            model.flag = false
        }

        updateList(model.cs)

    }

    fun updateList(cs:CharSequence)
    {
        model.cs=cs
        model.listItems = ArrayList()
        val it = model.Keys!!.entries.iterator()
        while (it.hasNext()) {
            val resultsMap: LinkedHashMap<String, String> = LinkedHashMap()
            val pair = it.next() as Map.Entry<*, *>
            if (pair.key.toString().toLowerCase().contains(cs.toString().toLowerCase()) || pair.value.toString().toLowerCase().contains(cs.toString().toLowerCase())) {
                resultsMap.put("First Line", pair.key.toString())
                resultsMap.put("Second Line", pair.value.toString())
                model.listItems.add(resultsMap)
            } else if (cs.toString() === "") {
                resultsMap.put("First Line", pair.key.toString())
                resultsMap.put("Second Line", pair.value.toString())
                model.listItems.add(resultsMap)
            }
        }
       view.setAdapter()
    }


}
