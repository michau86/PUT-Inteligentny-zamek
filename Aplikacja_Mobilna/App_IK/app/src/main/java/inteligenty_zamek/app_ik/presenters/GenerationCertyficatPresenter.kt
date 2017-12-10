package inteligenty_zamek.app_ik.presenters


import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.Views.GenerationCertyficatActivity
import inteligenty_zamek.app_ik.models.GenerationCertyficatModel
import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

/**
 * Created by Damian on 06.12.2017.
 */
class GenerationCertyficatPresenter(val view: GenerationCertyficatActivity)
{
    val model: GenerationCertyficatModel = GenerationCertyficatModel(view)


    fun getLockName(position:Int): String
    {
        return model.lockslist!![position]!!.name
    }

    fun getUserLogin(position:Int): String
    {
        return model.userlist!![position]!!.login
    }

    fun downloadKey()
    {
        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        try {

            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/download/all_locks/", "downloadKeyResult", toSend).execute()
        } catch (e: Exception) {
        }
    }
    fun downloadKeyResult(result:String)
    {
        try {
            val arrJson = JSONObject(result).getJSONArray("data")
            model.addLockList(arrJson)
            view.setSpinnerLockList(model.lockslist)
        } catch (e: JSONException) {
        }
    }

    fun downloadUsers()
    {
        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)
        try {

            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/download/all_user/", "downloadUsersResult", toSend).execute()
        } catch (e: Exception) {
        }
    }

    fun downloadUsersResult(result:String)
    {

        try {
            val arrJson = JSONObject(result).getJSONArray("data")
            model.addUserList(arrJson)
            view.setSpinnerUsersList(model.userlist)
        } catch (e: JSONException) {

        }
    }


    fun generateCertyficat( fromDate:String, toDate:String, lockposition:Int, userposition:Int, namesUser:String, surnameUser:String)
    {

        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)

        toSend.put("user_id", model.userlist!![userposition]!!.getIdUser())
        toSend.put("lock_id", model.lockslist!![lockposition]!!.getIdKey())
        toSend.put("name", namesUser)
        toSend.put("surname",surnameUser)
        toSend.put("from_date", fromDate)
        toSend.put("to_date", toDate)


        if (GlobalContainer.mondayList != null) {
            val list = GlobalContainer.mondayList
            var result = ""
            for (s in list) {
                result += s + ";"
            }
          toSend.put("monday", result)

        } else {
            toSend.put("monday", " ")
        }

        if (GlobalContainer.tuesdayList != null) {
            val list = GlobalContainer.tuesdayList
            var result = ""
            for (s in list) {
                result += s + ";"
            }
            toSend.put("tuesday", result)
        } else {
            toSend.put("tuesday", " ")
        }


        if (GlobalContainer.wednesdayList != null) {
            val list = GlobalContainer.wednesdayList
            var result = ""
            for (s in list) {
                result += s + ";"
            }
            toSend.put("wednesday", result)
        } else {
            toSend.put("wednesday", " ")
        }


        if (GlobalContainer.thurstdayList != null) {
            val list = GlobalContainer.thurstdayList
            var result = ""
            for (s in list) {
                result += s + ";"
            }
            toSend.put("thursday", result)
        } else {
            toSend.put("thursday", " ")
        }


        if (GlobalContainer.fridyList != null) {
            val list = GlobalContainer.fridyList
            var result = ""
            for (s in list) {
                result += s + ";"
            }
            toSend.put("friday", result)
        } else {
            toSend.put("friday", " ")
        }

        if (GlobalContainer.saturdayList != null) {
            val list = GlobalContainer.saturdayList
            var result = ""
            for (s in list) {
                result += s + ";"
            }
            toSend.put("saturday", result)
        } else {
            toSend.put("saturday", " ")
        }

        if (GlobalContainer.sundayList != null) {
            val list = GlobalContainer.sundayList
            var result = ""
            for (s in list) {
                result += s + ";"
            }
            toSend.put("sunday", result)
        } else {
            toSend.put("sunday", " ")
        }

        toSend.put("is_pernament", "0")

        try {

            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/generate_new_certificate/", "downloadUsersResult", toSend).execute()
        } catch (e: Exception) {
        }
    }


}