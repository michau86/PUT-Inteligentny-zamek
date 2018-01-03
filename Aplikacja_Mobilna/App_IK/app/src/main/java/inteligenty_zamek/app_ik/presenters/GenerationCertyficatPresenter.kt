package inteligenty_zamek.app_ik.presenters


import android.content.Intent
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.HTTPRequestAPI
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.Views.Admin_PanelActivity
import inteligenty_zamek.app_ik.Views.GenerationCertyficatActivity
import inteligenty_zamek.app_ik.models.GenerationCertyficatModel
import inteligenty_zamek.app_ik.rest_class.Certyficat
import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.HashMap

/**
 * Created by Damian on 06.12.2017.
 */
class GenerationCertyficatPresenter(val view: GenerationCertyficatActivity)
{
    val model: GenerationCertyficatModel = GenerationCertyficatModel(view)

    fun chekCertificate()
    {
        val bundle = view.intent.extras
        if (bundle != null)
        {
            if(bundle.getBoolean("haveCertificate"))
            {
                model.certificate=GlobalContainer.obj as Certyficat
            }

           view.setDefaultValueFromCertificate(model.certificate!!.userName,
                   model.certificate!!.userSurname,model.certificate!!.to)
            val value = HashMap<EnumChoice, String>()
            value.put(EnumChoice.choiceLock, model.certificate!!.lockName)
            value.put(EnumChoice.choiceLogin,getLoginUserFromID(model.certificate!!.id_user))
            sharedPreferenceApi.set(view,value)

        }
    }

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
            chekCertificate()
            view.setSpinnerUsersList(model.userlist)
            view.setSpinnerLockList(model.lockslist)
        } catch (e: JSONException) { }


    }


    fun getLoginUserFromID(idUser:String):String {
        val list = model.userlist
        for (i in list!!.indices) {
            try {
                if (idUser == list[i]!!.idUser) {
                   return list[i]!!.name.toString()
                }
            } catch (e: Exception) {
            }
        }
        return ""
    }
    fun generateCertyficat( fromDate:String, toDate:String, lockposition:Int, userposition:Int, namesUser:String, surnameUser:String)
    {

        val date1 =  SimpleDateFormat("yyyy-MM-dd").parse(fromDate)
        val date2 =  SimpleDateFormat("yyyy-MM-dd").parse(toDate)

       if(date1.compareTo(date2)>0)
       {
           view.setDateError()
           return;
       }

        val toSend: HashMap<String, String> = HashMap()
        toSend.put("login", model.login)
        toSend.put("token", model.token)

        toSend.put("user_id", model.userlist!![userposition]!!.getIdUser())
        toSend.put("lock_id", model.lockslist!![lockposition]!!.idKey)
        toSend.put("name", namesUser)
        toSend.put("surname",surnameUser)
        toSend.put("from_date", fromDate+" 00:00:00")
        toSend.put("to_date", toDate+" 00:00:00")


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

            HTTPRequestAPI(this, "http://" + model.ipAddres + ":8080/api/admin/generate_new_certificate/", "generateResult", toSend).execute()
        } catch (e: Exception) {
        }
    }


    fun generateResult(result:String)
    {
        val ob=JSONObject(result)
        if(ob.getString("status").equals("ok")) {
            GlobalContainer.mondayList = null
            GlobalContainer.tuesdayList = null
            GlobalContainer.wednesdayList = null
            GlobalContainer.thurstdayList = null
            GlobalContainer.fridyList = null
            GlobalContainer.saturdayList = null
            GlobalContainer.sundayList = null

            val myIntent = Intent(view, Admin_PanelActivity::class.java)
            view.startActivity(myIntent)
            view.finish()
        }
    }

}