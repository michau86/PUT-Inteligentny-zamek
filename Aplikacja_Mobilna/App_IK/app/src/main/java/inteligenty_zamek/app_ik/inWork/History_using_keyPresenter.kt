package inteligenty_zamek.app_ik.inWork

import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.API.Valdiation
import inteligenty_zamek.app_ik.models.HistoryListElement
import org.json.JSONException
import org.json.JSONObject
import java.util.LinkedHashMap
import java.util.Map

/**
 * Created by Damian on 02.01.2018.
 */
class History_using_keyPresenter(val view:History_using_keyActivity)
{
    val model=History_using_keyModel(view)
   fun getList()
   {
       val toSend: HashMap<String, String> = HashMap()
       toSend.put("login", model.login)
       toSend.put("token", model.token)
       try {
           HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/history/", "setList", toSend).execute()
       } catch (e: Exception) { }

       try {
           HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/download/all_locks/", "setLockList", toSend).execute()
       } catch (e: Exception) { }

       try {
           HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/download/all_user/", "setUserList", toSend).execute()
       } catch (e: Exception) { }
   }

    fun setUserList(response:String)
    {
        val arrJson = JSONObject(response).getJSONArray("data")
        model.userList= ArrayList()
        model.userList!!.add("")
        model.userLinkedHasmap= LinkedHashMap()
        for (i in 0 until arrJson.length()) {
            model.userList!!.add(arrJson.getJSONObject(i).getString("LOGIN"))
            model.userLinkedHasmap!!.put(arrJson.getJSONObject(i).getString("LOGIN"),
                    arrJson.getJSONObject(i).getString("Name"))
        }
        view.setSpinnerUserAdapter()
    }

    fun setLockList(response:String)
    {
        val arrJson = JSONObject(response).getJSONArray("data")
        model.keyList= ArrayList()
        model.keyList!!.add("")
        for (i in 0 until arrJson.length()) {
        model.keyList!!.add(arrJson.getJSONObject(i).getString("NAME"))
        }
        view.setSpinnerAdapter()

    }
    fun setList(response:String)
    {
        try {
            val arrJson = JSONObject(response).getJSONArray("data")
            //hasmap przechowujący elemety do wyświetlenia
            model.Keys = LinkedHashMap()
            for (i in 0 until arrJson.length()) {
                try {
                    val date = arrJson.getJSONObject(i).getString("DATE").split("T")
                    if (arrJson.getJSONObject(i).getInt("ACCESS") === 1) {
                    val item= HistoryListElement()
                    item.ico="\uf00c"
                    item.Title="Otwarcie"
                        item.key= arrJson.getJSONObject(i).getString("ZAMEK")
                        item.date=date[0]
                        item.dateHour=date[1]
                        item.text="otwarte przez"
                        item.name=arrJson.getJSONObject(i).getString("NAME")
                        item.surname=arrJson.getJSONObject(i).getString("SURNAME")
                         model.Keys!!.put(i,item)
                    } else {
                        val item= HistoryListElement()
                        item.ico="\uf071"
                        item.Title="Nieautoryzowane próba otwarcia"
                        item.key= arrJson.getJSONObject(i).getString("ZAMEK")
                        item.date=date[0]
                        item.dateHour=date[1]
                        item.text="nieutoryzowana próba otwarcia zamka przez"
                        item.name=arrJson.getJSONObject(i).getString("NAME")
                        item.surname=arrJson.getJSONObject(i).getString("SURNAME")

                                model.Keys!!.put(i,item)
                    }
                } catch (ignored: JSONException) {
                }
            }

         updateList("")

        }catch (ex : Exception){}
    }

    fun setFiltr(from:String,to:String,lock:String,user:String,onlyError:Boolean,sort:String) {

        model.listItems = ArrayList()
        for (i in 0 until model.Keys!!.size) {
            if (onlyError) {
                if (model.Keys!!.get(i)!!.Title.equals("Nieautoryzowane próba otwarcia")) {

                    model.listItems!!.add(model.Keys!!.get(i)!!)
                    Log.i("hhhh",model.Keys!!.get(i)!!.Title)
                }
            } else {
                model.listItems!!.add(model.Keys!!.get(i)!!)
            }
        }
          if (lock != "") {
             var i=0
             var to=model.listItems!!.size
             while (i<to)
              {
                 if (model.listItems!![i].key != lock) {
                     model.listItems!!.removeAt(i)
                     to--
                     i--
                 }
                  i++
             }
         }
        if (user != "") {

            var i=0
            var to=model.listItems!!.size
            while (i<to) {
                if (model.listItems!![i].name !=model.userLinkedHasmap!!.get(user))
                {
                    model.listItems!!.removeAt(i)
                    to--
                    i--
                }
                i++
            }
        }

       if(from!="" && to!="" )
        {
            var i=0
            var toWhile=model.listItems!!.size
            while (i<toWhile){
                if (Valdiation.biggerThanTime( model.listItems!![i].date,from)  &&
                        !Valdiation.biggerThanTime( model.listItems!![i].date,to))
                {
                    model.listItems!!.removeAt(i)
                    i--
                    toWhile--
                }
                i++
            }
        }
    if(sort=="\uF160") {
        model.listItems!!.sort()
    }
        else
    {
        model.listItems!!.reversed()
    }
        view.setAdapter(model.listItems!!)
    }






    fun getLockList():ArrayList<String>
    {
    return model.keyList!!
    }
   fun getUserList():ArrayList<String>
   {
       return model.userList!!
   }

    fun updateList(cs:CharSequence)
    {
        model.listItems = ArrayList<HistoryListElement>()
        for (i in 0 until model.Keys!!.size) {
            model.listItems!!.add(model.Keys!!.get(i)!!)
        }
        view.setAdapter(model.listItems!!)
    }

}