package inteligenty_zamek.app_ik.models

import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.Views.GenerationCertyficatActivity
import org.json.JSONArray
import org.json.JSONException

/**
 * Created by Damian on 06.12.2017.
 */
class GenerationCertyficatModel(view: GenerationCertyficatActivity)
{

   var lockslist: Array<Lock?>? = null
   var userlist: Array<User?>? = null
   val login:String= sharedPreferenceApi.getString(view, EnumChoice.login)
   val token:String=CyptographyApi.decrypt(
           sharedPreferenceApi.getString(view,EnumChoice.token))
   val ipAddres:String=sharedPreferenceApi.getString(view,EnumChoice.ip)
   var certificate: Certyficat?=null


   fun addLockList(arrJson: JSONArray) {
      lockslist = arrayOfNulls(arrJson.length())
      for (i in 0 until arrJson.length()) {
         try {
            lockslist!![i] = Lock()
            lockslist!![i]!!.name = arrJson.getJSONObject(i).getString("NAME")
            lockslist!![i]!!.idKey = arrJson.getJSONObject(i).getString("ID_LOCK")
         } catch (e: JSONException) {
         }

      }

   }


   fun addUserList(arrJson: JSONArray) {
      userlist = arrayOfNulls(arrJson.length())
      for (i in 0 until arrJson.length()) {
         try {
            userlist!![i] = User()
            userlist!![i]!!.login = arrJson.getJSONObject(i).getString("LOGIN")
            userlist!![i]!!.setIdUser(arrJson.getJSONObject(i).getString("ID_USER"))
         } catch (e: JSONException) {
         }

      }

   }

}