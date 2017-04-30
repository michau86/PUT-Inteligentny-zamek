package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Admin_PanelActivity extends BaseActivity
        {

            private String[] navMenuTitles;
            private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");

        ListView resultsListView = (ListView) this.findViewById(R.id.ListView_Admin_Panel);

        String list_item[] = {getString(R.string.activity_admin_panel1),
                getString(R.string.activity_admin_panel2),
                getString(R.string.activity_admin_panel3),
                getString(R.string.activity_admin_panel4),
                getString(R.string.activity_admin_panel5)};

        ArrayList<String> list = new ArrayList<String>();
        list.addAll( Arrays.asList(list_item) );
        ArrayAdapter<String> adapter ;

        adapter = new ArrayAdapter<String>(this, R.layout.admin_panel_key_list, list);
        resultsListView.setAdapter(adapter);


    }







}
