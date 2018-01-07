package inteligenty_zamek.app_ik.inWork

import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import inteligenty_zamek.app_ik.rest_class.User

/**
 * Created by Damian on 07.01.2018.
 */
class UserModel(val view:UserActivity)
{
    val user:User=GlobalContainer.obj as User
    val login:String= sharedPreferenceApi.getString(view, EnumChoice.login)
    val token:String= CyptographyApi.decrypt(
            sharedPreferenceApi.getString(view, EnumChoice.token))
    val ipAddres:String= sharedPreferenceApi.getString(view, EnumChoice.ip)

}