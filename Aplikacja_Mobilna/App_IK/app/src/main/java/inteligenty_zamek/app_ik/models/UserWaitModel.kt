package inteligenty_zamek.app_ik.models

import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.Views.UserWaitActivity
import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * Created by Damian on 04.12.2017.
 */
class UserWaitModel(val view: UserWaitActivity)
{
    var Keys: LinkedHashMap<String, String>?=null
    var resultsMap: LinkedHashMap<String, String>?=null
    var listItems: MutableList<LinkedHashMap<String, String>> = ArrayList()
    val login:String
    val token:String
    val ipAddres:String
    var indexOfKeysToRemove=-1
    var cs:CharSequence=""
    fun getLoginFromListItem(position:Int):String
    {
    return listItems[position].values.toTypedArray()[0].toString()
    }
    init{
        login=sharedPreferenceApi.getString(view,EnumChoice.login)
        token=CyptographyApi.decrypt(sharedPreferenceApi.getString(view,EnumChoice.token))
        ipAddres=sharedPreferenceApi.getString(view,EnumChoice.ip)
    }



}