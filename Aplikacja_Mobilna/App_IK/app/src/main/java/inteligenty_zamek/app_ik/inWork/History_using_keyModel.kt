package inteligenty_zamek.app_ik.inWork

import android.content.Context
import inteligenty_zamek.app_ik.API.Connect_and_send_message
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.adapters.HistoryListAdapter
import inteligenty_zamek.app_ik.models.HistoryListElement

/**
 * Created by Damian on 02.01.2018.
 */
class History_using_keyModel(view:Context)
{
    val login:String= sharedPreferenceApi.getString(view, EnumChoice.login)
    val token:String= CyptographyApi.decrypt(sharedPreferenceApi.getString(view, EnumChoice.token))
    val ipAddres:String= sharedPreferenceApi.getString(view, EnumChoice.ip)
    var Keys:LinkedHashMap<Int, HistoryListElement>?=null
    var listItems :ArrayList<HistoryListElement>?=null
    var keyList:ArrayList<String>?=null
    var userList:ArrayList<String>?=null
    var userLinkedHasmap:LinkedHashMap<String,String>?=null

}