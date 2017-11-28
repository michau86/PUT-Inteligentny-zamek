package inteligenty_zamek.app_ik.models

import android.content.Context
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.rest_class.User

/**
 * Created by Damian on 28.11.2017.
 */
class Managment_certyficationModel(context:Context) {
     var login:String=""
    var token:String=""
    var ipaddres:String=""
    init{
        login=sharedPreferenceApi.getString(context,5)
        token=sharedPreferenceApi.getString(context,3)
        ipaddres=sharedPreferenceApi.getString(context,1)
    }
}
