package inteligenty_zamek.app_ik.inWork

import android.content.Context
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.rest_class.Certyficat
import inteligenty_zamek.app_ik.rest_class.GlobalContainer

/**
 * Created by Damian on 09.12.2017.
 */
class certyficatModel(val view:Context, val position:Int)
{
    var certyficat:Certyficat?=null
    val isLogin=sharedPreferenceApi.getBoolean(view,EnumChoice.isLogin)
    val login= sharedPreferenceApi.getString(view,EnumChoice.login)
    val token=CyptographyApi.decrypt(sharedPreferenceApi.getString(view,EnumChoice.token))
    val ipaddres=sharedPreferenceApi.getString(view,EnumChoice.ip)
    init
    {
        if(position>=0)
        {certyficat=GlobalContainer.getUser(view).getCertyficateList(view)[position]}
        else
        {

        }
    }
   fun getCertyficatText():String
   {
       var textValue = ""
       if (certyficat!!.monday != "null") {
           textValue = textValue + "poniedziałek: " + certyficat!!.monday
       }
       if (certyficat!!.tuesday != "null") {
           textValue = textValue + "\nwtorek: " + certyficat!!.tuesday
       }
       if (certyficat!!.wednesday != "null") {
           textValue = textValue + "\nsroda: " + certyficat!!.wednesday
       }
       if (certyficat!!.thurstday != "null") {
           textValue = textValue + "\nczwartek: " + certyficat!!.thurstday
       }
       if (certyficat!!.friday != "null") {
           textValue = textValue + "\npiatek: " + certyficat!!.friday
       }
       if (certyficat!!.saturday != "null") {
           textValue = textValue + "\nsobota: " + certyficat!!.saturday
       }
       if (certyficat!!.sunday != "null") {
           textValue = textValue + "\nniedziela: " + certyficat!!.sunday
       }
       return textValue
   }

}