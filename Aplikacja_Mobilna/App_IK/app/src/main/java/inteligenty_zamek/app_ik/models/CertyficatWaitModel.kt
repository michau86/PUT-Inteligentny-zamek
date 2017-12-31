package inteligenty_zamek.app_ik.models

import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.Views.CertyficatWaitActivity
import java.util.LinkedHashMap
import java.util.Map

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
    var hashmapPositionID :LinkedHashMap<Int, String>?=null
    var resultsMap: LinkedHashMap<String, ArrayList<String>>?=null
    var indexOfKeysToRemove=-1
    var cs:CharSequence=""
    var sortedflag=true

fun updateHashmapPositionID()
{
    val it = resultsMap!!.entries.iterator()

    hashmapPositionID=LinkedHashMap()
    var i=0
    while (it.hasNext()) {
        val pair = it.next() as Map.Entry<*, *>
        hashmapPositionID!!.put(i,pair.key.toString())
        i++
    }
}




    init{
        login= sharedPreferenceApi.getString(view, EnumChoice.login)
        token= CyptographyApi.decrypt(sharedPreferenceApi.getString(view, EnumChoice.token))
        ipAddres= sharedPreferenceApi.getString(view, EnumChoice.ip)
    }
}