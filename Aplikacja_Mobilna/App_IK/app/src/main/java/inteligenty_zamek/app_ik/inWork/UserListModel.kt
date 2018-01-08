package inteligenty_zamek.app_ik.inWork

import android.content.Context
import android.util.Log
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.rest_class.User
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

/**
 * Created by Damian on 06.01.2018.
 */
class UserListModel(view: UserListActivity)
{
    var userlist: ArrayList<User>? = null
    var userlist2: ArrayList<User>? = null
    var isActive=false;
    val login:String= sharedPreferenceApi.getString(view, EnumChoice.login)
    val token:String= CyptographyApi.decrypt(
            sharedPreferenceApi.getString(view, EnumChoice.token))
    val ipAddres:String= sharedPreferenceApi.getString(view, EnumChoice.ip)

    var cs:CharSequence="";
    fun addUserList(arrJson: JSONArray) {
        userlist = ArrayList()
        userlist2 = ArrayList()


        for (i in 0 until arrJson.length()) {
            try {
                Log.i("hhhh",arrJson.getJSONObject(i).getInt("Status").toString())
                Log.i("hhhh",arrJson.getJSONObject(i).getString("Status"))

                userlist!!.add(
                         User(
                         arrJson.getJSONObject(i).getString("LOGIN"),
                         arrJson.getJSONObject(i).getString("Name"),
                         arrJson.getJSONObject(i).getString("ID_USER"),
                                 if( arrJson.getJSONObject(i).getInt("Status")==0){true}else{false},
                                 arrJson.getJSONObject(i).getString("Validitiy_period")
                 )
                )
                userlist2!!.add(

                        User(
                                arrJson.getJSONObject(i).getString("LOGIN"),
                               arrJson.getJSONObject(i).getString("Name"),
                                arrJson.getJSONObject(i).getString("ID_USER"),
                                if( arrJson.getJSONObject(i).getInt("Status")==0){true}else{false},
                                arrJson.getJSONObject(i).getString("Validitiy_period")
                        )
                )

            } catch (e: Exception) {
            }

        }

    }

}