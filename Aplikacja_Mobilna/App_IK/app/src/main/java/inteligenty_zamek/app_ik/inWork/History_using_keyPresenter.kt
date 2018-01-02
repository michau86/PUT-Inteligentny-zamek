package inteligenty_zamek.app_ik.inWork

import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
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
                    item.Title="Otwarcie "
                    item.firstElement="zamek " + arrJson.getJSONObject(i).getString("ZAMEK")+"\ndata zdarzenia: " +  date[0] + " godzina: " + date[1]
                    item.secondElement="został otwarty przez\n" + arrJson.getJSONObject(i).getString("NAME") + " " + arrJson.getJSONObject(i).getString("SURNAME")
                        model.Keys!!.put(i,item)
                    } else {
                        val item= HistoryListElement()
                        item.ico="\uf071"
                        item.Title="Nieautoryzowane otwarcie "
                        item.firstElement="zamek " + arrJson.getJSONObject(i).getString("ZAMEK")+"\ndata zdarzenia: " +  date[0] + " godzina: " + date[1]
                        item.secondElement="zarejestrował nieutoryzowaną próbę dostępu przez\n" + arrJson.getJSONObject(i).getString("NAME") + " " + arrJson.getJSONObject(i).getString("SURNAME")

                                model.Keys!!.put(i,item)
                    }
                } catch (ignored: JSONException) {
                }
            }

         updateList("")

        }catch (ex : Exception){}
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