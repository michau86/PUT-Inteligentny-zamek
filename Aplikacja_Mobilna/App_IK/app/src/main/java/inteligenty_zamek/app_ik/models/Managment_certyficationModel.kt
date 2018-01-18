package inteligenty_zamek.app_ik.models

import android.content.Context
import android.net.Uri
import android.widget.ArrayAdapter
import inteligenty_zamek.app_ik.API.CyptographyApi
import inteligenty_zamek.app_ik.API.EnumChoice
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.R
import org.json.JSONArray
import java.util.*

/**
 * Created by Damian on 28.11.2017.
 */
class Managment_certyficationModel(context:Context) {

    var login:String=""
    var token:String=""
    var ipaddres:String=""
    val list = ArrayList<String>()
    val adapter: ArrayAdapter<String>
    var islogin:Boolean=true
    var selectedfile: Uri? =null
    var arrJson: JSONArray? = null


    init{
        login=sharedPreferenceApi.getString(context, EnumChoice.login)
        token= CyptographyApi.decrypt( sharedPreferenceApi.getString(context,EnumChoice.token))
        ipaddres=sharedPreferenceApi.getString(context,EnumChoice.ip)
            val list_item = arrayOf(context.getString(R.string.activity_managmentCertyfication2), context.getString(R.string.activity_managmentCertyfication3))
            list.addAll(Arrays.asList(*list_item))
        adapter = ArrayAdapter(context, R.layout.admin_panel_key_list, list)
    }

}
