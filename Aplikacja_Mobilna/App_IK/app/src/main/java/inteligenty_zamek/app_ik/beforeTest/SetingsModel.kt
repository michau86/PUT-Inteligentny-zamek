package inteligenty_zamek.app_ik.beforeTest

import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.beforeTest.SetingsActivity

/**
 * Created by Damian on 09.12.2017.
 */
class SetingsModel(view: SetingsActivity)
{
    val login:String= sharedPreferenceApi.getString(view, EnumChoice.login)
    val token:String= sharedPreferenceApi.getString(view, EnumChoice.token)
    val ipAddres:String=sharedPreferenceApi.getString(view,EnumChoice.ip)

}