package inteligenty_zamek.app_ik.models

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ArrayAdapter
import android.widget.TextView
import inteligenty_zamek.app_ik.API.sharedPreferenceApi
import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.rest_class.User
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
    var islogin:Boolean=true;
    var selectedfile: Uri? =null
    var arrJson: JSONArray? = null


    init{
        login=sharedPreferenceApi.getString(context, sharedPreferenceApi.choise.login)
        token=sharedPreferenceApi.getString(context,sharedPreferenceApi.choise.token)
        ipaddres=sharedPreferenceApi.getString(context,sharedPreferenceApi.choise.ip)

        if (!sharedPreferenceApi.getBoolean(context,sharedPreferenceApi.choise.isLogin)) {
            list.addAll(Arrays.asList(context.getString(R.string.activity_managmentCertyfication2)))
            islogin=false
        } else {
            val list_item = arrayOf(context.getString(R.string.activity_managmentCertyfication2), context.getString(R.string.activity_managmentCertyfication3), context.getString(R.string.activity_managmentCertyfication4))
            list.addAll(Arrays.asList(*list_item))
        }
        adapter = ArrayAdapter(context, R.layout.admin_panel_key_list, list)
    }

}
