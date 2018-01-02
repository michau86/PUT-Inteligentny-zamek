package inteligenty_zamek.app_ik.models

import android.content.Context
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.rest_class.Certyficat
import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * Created by Damian on 09.12.2017.
 */
class ManagmentCertyficationUserModel(view:Context)
{
    val login:String= sharedPreferenceApi.getString(view, EnumChoice.login)
    val token:String= CyptographyApi.decrypt(sharedPreferenceApi.getString(view, EnumChoice.token))
    val ipAddres:String= sharedPreferenceApi.getString(view, EnumChoice.ip)
    var Keys: LinkedHashMap<Int, Certyficat>?=null
    var keysPosition:ArrayList<Int>?=null
    var listItems: MutableList<LinkedHashMap<String, String>> = ArrayList()
    var cs:CharSequence=""
    var flag=true

}