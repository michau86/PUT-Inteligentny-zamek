package inteligenty_zamek.app_ik.inWork

import android.content.Context
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
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
    var Keys: LinkedHashMap<String, String>?=null
    var listItems: MutableList<LinkedHashMap<String, String>> = ArrayList()
    var cs:CharSequence=""
    var flag=true

}