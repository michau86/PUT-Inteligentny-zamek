package inteligenty_zamek.app_ik.presenters

import android.util.Log
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.Views.UserWaitActivity
import inteligenty_zamek.app_ik.adapters.ITwoButtonList
import inteligenty_zamek.app_ik.models.UserWaitModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.Map
import kotlin.collections.LinkedHashMap

/**
 * Created by Damian on 04.12.2017.
 */
class UserWaitPresenter(val view: UserWaitActivity) :ITwoButtonList {

    var model: UserWaitModel = UserWaitModel(view)
    fun initAvtivity() {

        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        try {
            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/register_waiting/", "initresult", toSend).execute()
        } catch (e: Exception) { }
    }

    fun initresult(result: String) {
        try {
            if (JSONObject(result).getString("data") != "empty") {
                var arrJson: JSONArray = JSONObject(result).getJSONArray("data")
                model.Keys = LinkedHashMap()
                for (i in 0 until arrJson.length()) {
                    try {
                        model.Keys!!.put(arrJson.getJSONObject(i).getString("LOGIN"), arrJson.getJSONObject(i).getString("NAME") + " " + arrJson.getJSONObject(i).getString("SURNAME"))
                    } catch (ignored: JSONException) { }
                }
                updateListInSearch("")
                view.showMessage("Pobrano listę oczekujących użytkowników na rejestrację")

            } else {
                view.showMessage("Brak użytkowników oczekujących na rejestrację")
            }
        } catch (e: JSONException) {
            view.showMessage("Wystąpił problem podczas pobierania isty")
        }
    }

    fun sortArray()
    {
        if(model.sortedflag) {
           model.Keys= LinkedHashMap(model.Keys!!.toList().sortedBy { (key, value) -> key }.toMap())
        }
        else {
            model.Keys= LinkedHashMap(model.Keys!!.toList().sortedByDescending { (key, value) -> key }.toMap())
        }
        model.sortedflag=!model.sortedflag
        updateListInSearch(model.cs)

    }
    fun updateListInSearch( cs:CharSequence)
    {
        model.cs=cs
        val it = model.Keys!!.entries.iterator()
        model.resultsMap = LinkedHashMap()
        model.loginMap=LinkedHashMap()
        var i:Int=0
        while (it.hasNext()) {

            val pair = it.next() as Map.Entry<*, *>
            if (pair.key.toString().toLowerCase().contains(cs.toString().toLowerCase()) || pair.value.toString().toLowerCase().contains(cs.toString().toLowerCase()))
            {
                if(pair.key.toString()!=null) {
                    model.resultsMap!!.put(pair.key.toString(), pair.value.toString())
                    model.loginMap!!.put(i, pair.key.toString())
                    i++
                }
            } else if (cs.toString() === "") {
                if(pair.key.toString()!=null) {
                    model.resultsMap!!.put(pair.key.toString(), pair.value.toString())
                    model.loginMap!!.put(i, pair.key.toString())
                    i++
                }
            }
        }
       view.setAdapter(model.resultsMap!!)
    }

    fun removefromKeys()
    {
        model.Keys!!.remove(model.getLoginFromListItem(model.indexOfKeysToRemove))
        model.resultsMap!!.remove(model.getLoginFromListItem(model.indexOfKeysToRemove))
        model.updateLoginMap()
    }

    override
    fun acceptButton(position:Int)
   {
       val toSend: HashMap<String, String> = HashMap()



       toSend.put("login", model.login)
       toSend.put("token", model.token)
       toSend.put("decision","1")
       toSend.put("user_login",model.getLoginFromListItem(position))
       model.indexOfKeysToRemove=position
       try {
           HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/register_decision/", "acceptButtonResult", toSend).execute()
       } catch (e: Exception) { }

   }

    fun acceptButtonResult(result:String) {
        try {
            if (JSONObject(result).getString("status") == "ok"){
                removefromKeys()
                view.deleteFromRecycler(model.indexOfKeysToRemove)
            }
            else
            {
                view.showMessage("Wystąpił problem podczas wykonywanej operacji")
            }
        }catch (ex:Exception)
        {
            view.showMessage("Wystąpił problem podczas wykonywanej operacji")
        }


    }
    override
    fun removeButton(position:Int)
    {
        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        toSend.put("decision","0")
        toSend.put("user_login",model.getLoginFromListItem(position))
        model.indexOfKeysToRemove=position
        try {
            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/register_decision/", "removeButtonResult", toSend).execute()
        } catch (e: Exception) {
        }
    }
    fun removeButtonResult(result:String) {
        var jObj: JSONObject? = null
        try {
            jObj = JSONObject(result)
            if (jObj.getString("status") == "ok"){

                removefromKeys()
                view.deleteFromRecycler(model.indexOfKeysToRemove)
                model.indexOfKeysToRemove=-1;
            }
            else
            {
                view.showMessage("Wystąpił problem podczas wykonywanej operacji")
            }
        }catch (ex:Exception)
        {
            view.showMessage("Wystąpił problem podczas wykonywanej operacji")
        }


    }

}