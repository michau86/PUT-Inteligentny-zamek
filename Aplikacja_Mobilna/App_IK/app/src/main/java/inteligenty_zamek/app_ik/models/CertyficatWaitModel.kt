package inteligenty_zamek.app_ik.models

import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.Views.CertyficatWaitActivity
import java.util.LinkedHashMap

/**
 * Created by Damian on 05.12.2017.
 */
class CertyficatWaitModel(view: CertyficatWaitActivity)
{
    val login:String
    val token:String
    val ipAddres:String
    var position=-1
    var Keys: LinkedHashMap<String, ArrayList<String>>?=null
    var listItems: MutableList<LinkedHashMap<String, String>> = ArrayList()
    var hashmapPositionID :LinkedHashMap<Int, String>?=null
    init{
        login= sharedPreferenceApi.getString(view, EnumChoice.login)
        token= CyptographyApi.decrypt(sharedPreferenceApi.getString(view, EnumChoice.token))
        ipAddres= sharedPreferenceApi.getString(view, EnumChoice.ip)
    }
}