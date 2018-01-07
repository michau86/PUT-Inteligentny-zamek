package inteligenty_zamek.app_ik.inWork

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.R

class UserActivity :  BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val presenter=UserPresenter(this)

        val navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        val navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)

        val tit=findViewById(R.id.UserTextViewName) as TextView
        tit.setText(presenter.model.user.login)

        val dat=findViewById(R.id.userTextViewDate) as TextView
        dat.setText(presenter.model.user.validitiy_period)
    }
}
