package inteligenty_zamek.app_ik.presenters

import android.content.Intent
import android.widget.ArrayAdapter
import inteligenty_zamek.app_ik.*
import inteligenty_zamek.app_ik.Views.Admin_PanelActivity
import inteligenty_zamek.app_ik.models.AdminPanelModel
import inteligenty_zamek.app_ik.Views.CertyficatWaitActivity
import inteligenty_zamek.app_ik.Views.UserWaitActivity
import inteligenty_zamek.app_ik.Views.GenerationCertyficatActivity
import inteligenty_zamek.app_ik.inWork.History_using_keyActivity
import inteligenty_zamek.app_ik.Views.ManagmentCertyficationUserActivity
import inteligenty_zamek.app_ik.beforeChange.EmergencyPasswordActivity

/**
 * Created by Damian on 24.11.2017.
 */
class AdminPanelPresenter ( val view: Admin_PanelActivity) {

    val model:AdminPanelModel= AdminPanelModel(this)
    val adapter: ArrayAdapter<String>
    init {

       adapter  = ArrayAdapter(view, R.layout.admin_panel_key_list, model.returnList())
    }
    fun returnAdapter():ArrayAdapter<String>
    {
        return adapter
    }

    fun onItemClikAction(position:Int)
    {
        var intent:Intent?
        when(position){
            0-> {intent = Intent(view, History_using_keyActivity::class.java)
                view.startActivity(intent)}
            1->{intent = Intent(view, GenerationCertyficatActivity::class.java)
                view.startActivity(intent)}
            2-> {intent = Intent(view, ManagmentCertyficationUserActivity::class.java)
                view.startActivity(intent)}
            3->{intent = Intent(view, UserWaitActivity::class.java)
                view.startActivity(intent)}
            4-> {intent = Intent(view, CertyficatWaitActivity::class.java)
                view.startActivity(intent)}
            5->{intent = Intent(view, EmergencyPasswordActivity::class.java)
                view.startActivity(intent)}
        }




    }

}