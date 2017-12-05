package inteligenty_zamek.app_ik.presenters

import android.util.Log
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.Views.UserWaitActivity
import inteligenty_zamek.app_ik.models.UserWaitModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.Map

/**
 * Created by Damian on 04.12.2017.
 */
class UserWaitPresenter(val view: UserWaitActivity) {

    var model: UserWaitModel = UserWaitModel(view)
    fun initAvtivity() {

        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        Log.i("HHHH", "0,1")

        try {


            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/register_waiting/", "initresult", toSend).execute()
        } catch (e: Exception) {
        }


    }

    fun initresult(result: String) {
        try {
            Log.i("HHHH", "result")

            if (JSONObject(result).getString("data") != "empty") {

                var arrJson: JSONArray = JSONObject(result).getJSONArray("data")
                model.Keys = LinkedHashMap()
                for (i in 0 until arrJson.length()) {
                    try {
                        model!!.Keys!!.put(arrJson.getJSONObject(i).getString("LOGIN"), arrJson.getJSONObject(i).getString("NAME") + " " + arrJson.getJSONObject(i).getString("SURNAME"))
                    } catch (ignored: JSONException) {
                    }

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

    fun updateListInSearch( cs:CharSequence)
    {
        model.listItems = ArrayList()
        model.cs=cs
        val it = model.Keys!!.entries.iterator()

        while (it.hasNext()) {
            model.resultsMap = LinkedHashMap()
            val pair = it.next() as Map.Entry<*, *>
            if (pair.key.toString().toLowerCase().contains(cs.toString().toLowerCase()) || pair.value.toString().toLowerCase().contains(cs.toString().toLowerCase()))
            {
                model!!.resultsMap!!.put("First Line", pair.key.toString())
                model!!.resultsMap!!.put("Second Line", pair.value.toString())
                model!!.listItems.add(model!!.resultsMap!!)
            } else if (cs.toString() === "") {
                model!!.resultsMap!!.put("First Line", pair.key.toString())
                model!!.resultsMap!!.put("Second Line", pair.value.toString())
                model!!.listItems.add(model!!.resultsMap!!)
            }
        }
       view.setAdapter(model!!.listItems)
    }

    fun removefromKeys()
    {
        model!!.Keys!!.remove(model.getLoginFromListItem(model.indexOfKeysToRemove))
        updateListInSearch(model.cs)
    }

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
              view.showMessage("Zaakceptowano rejestrację użytkownika")
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
                view.showMessage("Odmówiono rejestracji użytkownika")
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