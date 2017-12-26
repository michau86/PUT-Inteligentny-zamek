package inteligenty_zamek.app_ik.presenters

import android.util.Log
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.Views.CertificationaskActivity
import inteligenty_zamek.app_ik.models.CertificationaskModel
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class CertificationaskPresenter(val view: CertificationaskActivity) {
    val model: CertificationaskModel = CertificationaskModel(view)

    fun initActivity() {
        try {
            val toSend: HashMap<String, String> = HashMap()
            toSend.put("login", model.login)
            toSend.put("token", model.token)
            try {
                HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/download/all_locks/", "initresult", toSend).execute()
            } catch (e: Exception) {
            }
        } catch (except: Exception) {
        }
    }



    fun createValueToAdapter(cs:CharSequence)
    {
       model.createValueToAdapter(cs)
    }

    fun createValueToAdapter()
    {
        model.createValueToAdapter(model.cs)
    }


fun sortArray()
{
    Collections.reverse(model.lockList!!)
}
    fun sendRequest(position:Int)
    {
        val key_id = model.getKeyID(position)
        if(key_id!=null) {
            val toSend: HashMap<String, String> = HashMap()
            toSend.put("login", model.login)
            toSend.put("token", model.token)
            toSend.put("lock_id", key_id)
            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/request_new_certificate/", "resultRequestCertyficat", toSend).execute()
        }
    }


    // funkcje odpowiedzialne za obrobke danych otrzymanych  z HTTPrequestow
    fun initresult(result: String) {
        model.getjsonArrayOfLock(result)
        model.lockList!!.sortedWith(compareBy( { it.name }))
        view.setCertyficats()
    }

    fun resultRequestCertyficat(result:String)
{


    try {

        if (JSONObject(result).getString("status") == "ok") {
        view.showMessage("Wniosek został wysłany")
        } else {
            view.showMessage("Wystąpił problem podczas wysyłania certyfikatu")
        }
    } catch (e: JSONException) {
        view.showMessage("Wystąpił problem podczas wysyłania certyfikatu")
    }

}

}