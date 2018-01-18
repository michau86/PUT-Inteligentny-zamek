package inteligenty_zamek.app_ik.models

import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.Views.SetingsActivity
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
    val publicKey:String
    init
    {
        if(GlobalContainer.getPublicKey(view)!=null)
        {
            publicKey= GlobalContainer.getPublicKey(view).PUBLIC_KEY
        }
        else
        {
            publicKey = ""
        }
    }
}