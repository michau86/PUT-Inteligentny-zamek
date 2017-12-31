package inteligenty_zamek.app_ik.models

import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.Views.UserWaitActivity
import java.util.ArrayList
import java.util.LinkedHashMap
import java.util.Map

/**
 * Created by Damian on 04.12.2017.
 */
class UserWaitModel(val view: UserWaitActivity)
{
    var Keys: LinkedHashMap<String, String>?=null
    var resultsMap: LinkedHashMap<String, String>?=null
    var loginMap:LinkedHashMap<Int,String>?=null
    val login:String
    val token:String
    val ipAddres:String
    var indexOfKeysToRemove=-1
    var cs:CharSequence=""
    var sortedflag=true
    fun getLoginFromListItem(position:Int):String {

        return loginMap!!.get(position)!!
    }

    init{
        login=sharedPreferenceApi.getString(view,EnumChoice.login)
        token=CyptographyApi.decrypt(sharedPreferenceApi.getString(view,EnumChoice.token))
        ipAddres=sharedPreferenceApi.getString(view,EnumChoice.ip)
    }


    fun updateLoginMap()
    {

     val it = resultsMap!!.entries.iterator()

        loginMap=LinkedHashMap()
        var i=0
        while (it.hasNext()) {
            val pair = it.next() as Map.Entry<*, *>
            loginMap!!.put(i,pair.key.toString())
            i++
        }
    }


}