package inteligenty_zamek.app_ik.models

import android.content.Context
import android.util.Log
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
    val isAdmin=sharedPreferenceApi.getBoolean(view,EnumChoice.isAdmin)
    val login= sharedPreferenceApi.getString(view,EnumChoice.login)
    val token=CyptographyApi.decrypt(sharedPreferenceApi.getString(view,EnumChoice.token))
    val ipaddres=sharedPreferenceApi.getString(view,EnumChoice.ip)
    val fromAdminPanel:Boolean
    init
    {
        if(position>=0)
        {
            certyficat=GlobalContainer.getUser(view).getCertyficateList(view)[position]
        fromAdminPanel=false
        }
        else
        {
            certyficat=GlobalContainer.obj as Certyficat
            fromAdminPanel=true
            GlobalContainer.obj=null;
        }
    }
   fun getCertyficatText():String
   {
       var textValue = ""
       if (certyficat!!.monday != "null") {
           textValue = textValue + "poniedziałek: " + certyficat!!.monday+"\n"
       }
       if (certyficat!!.tuesday != "null") {
           textValue = textValue + "wtorek: " + certyficat!!.tuesday+"\n"
       }
       if (certyficat!!.wednesday != "null") {
           textValue = textValue + "sroda: " + certyficat!!.wednesday+"\n"
       }
       if (certyficat!!.thurstday != "null") {
           textValue = textValue + "czwartek: " + certyficat!!.thurstday+"\n"
       }
       if (certyficat!!.friday != "null") {
           textValue = textValue + "piatek: " + certyficat!!.friday+"\n"
       }
       if (certyficat!!.saturday != "null") {
           textValue = textValue + "sobota: " + certyficat!!.saturday+"\n"
       }
       if (certyficat!!.sunday != "null") {
           textValue = textValue + "niedziela: " + certyficat!!.sunday+"\n"
       }
       return textValue
   }

}