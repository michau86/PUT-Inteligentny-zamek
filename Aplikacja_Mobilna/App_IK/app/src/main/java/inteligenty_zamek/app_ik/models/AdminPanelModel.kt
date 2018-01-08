package inteligenty_zamek.app_ik.models

import inteligenty_zamek.app_ik.R
import inteligenty_zamek.app_ik.presenters.AdminPanelPresenter
import java.util.*

/**
 * Created by Damian on 24.11.2017.
 */
class AdminPanelModel (presenter:AdminPanelPresenter)
{
    val list:ArrayList<String>
    init {
        val list_item = arrayOf(presenter.view.getString(R.string.activity_admin_panel1), presenter.view.getString(R.string.activity_admin_panel2), presenter.view.getString(R.string.activity_admin_panel3), presenter.view.getString(R.string.activity_admin_panel4), presenter.view.getString(R.string.activity_admin_panel5),"Zarządzaj kontami użytkowników")
        list = ArrayList<String>()
        list.addAll(Arrays.asList(*list_item))
    }

    fun returnList():ArrayList<String>
    {
       return list
    }
}