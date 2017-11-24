package inteligenty_zamek.app_ik.Views
import android.content.res.TypedArray
import android.graphics.Typeface
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import inteligenty_zamek.app_ik.*
import inteligenty_zamek.app_ik.Navigation.BaseActivity
import inteligenty_zamek.app_ik.presenters.AdminPanelPresenter

class Admin_PanelActivity : BaseActivity() {

    private var navMenuTitles: Array<String>? = null
    private var navMenuIcons: TypedArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)
        navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        set(navMenuTitles, navMenuIcons)
        val fontFamily = Typeface.createFromAsset(assets, "fonts/fontawesome.ttf")
        val resultsListView = this.findViewById(R.id.ListView_Admin_Panel) as ListView
        val presenter: AdminPanelPresenter=  AdminPanelPresenter(this)


        resultsListView.adapter = presenter.returnAdapter()


        resultsListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            presenter.onItemClikAction(position)
        }

    }

    override fun onBackPressed() {}


}
