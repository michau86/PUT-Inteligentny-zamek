package inteligenty_zamek.app_ik.presenters

import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.models.UserActivity
import inteligenty_zamek.app_ik.models.UserModel
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Damian on 07.01.2018.
 */
class UserPresenter(val view: UserActivity)
{
    val model= UserModel(view)

    fun chekDate()
    {
        val myCalendar = Calendar.getInstance()

        val myFormat = "yyyy-MM-dd hh:mm:ss"; //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.GERMAN);


        try {
            val date1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sdf.format(myCalendar.getTime()))
            val date2 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(model.user.validitiy_period.replace("T", " "))

            if (date1.compareTo(date2) > 0) {
                view.blockButton()
            }
        }catch (ex:Exception){view.blockButton()}
    }

    fun blockKey()
    {
        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        toSend.put("user_id", model.user.idUser)

        try {

            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/deactivation_user_certificate/", "deactivationKeyResult", toSend).execute()
        } catch (e: Exception) { }
    }

    fun blockUser()
    {
        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        toSend.put("user_id", model.user.idUser)
        try {

            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/deactivation_user_accout/", "deactivationUserResult", toSend).execute()
        } catch (e: Exception) {}
    }

    fun deactivationUserResult(result:String)
    {
        val ob= JSONObject(result)
        if(ob.getString("status").equals("ok")) {
            view.blockButtonAll()
        }

    }

    fun deactivationKeyResult(result:String)
    {
        val ob= JSONObject(result)
        if(ob.getString("status").equals("ok")) {
            view.blockButton()
        }
    }
}