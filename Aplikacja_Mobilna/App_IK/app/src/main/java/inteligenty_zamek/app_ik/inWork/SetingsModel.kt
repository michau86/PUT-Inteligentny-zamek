package inteligenty_zamek.app_ik.inWork

import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.fileReadWriteApi
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.rest_class.GlobalContainer
import java.security.KeyPair

/**
 * Created by Damian on 09.12.2017.
 */
class SetingsModel(view: SetingsActivity)
{
    val login:String= sharedPreferenceApi.getString(view, EnumChoice.login)
    val token:String= CyptographyApi.decrypt(sharedPreferenceApi.getString(view, EnumChoice.token))
    val ipAddres:String=sharedPreferenceApi.getString(view,EnumChoice.ip)
    var pair: KeyPair? = null
    val password= CyptographyApi.decrypt(sharedPreferenceApi.getString(view,EnumChoice.password))
    val publicKey= GlobalContainer.getPublicKey(view).PUBLIC_KEY
}