package inteligenty_zamek.app_ik;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
                getString(R.string.activity_admin_panel5),
                "Hasło awaryjne"};

        ArrayList<String> list = new ArrayList<String>();
        list.addAll( Arrays.asList(list_item) );
        ArrayAdapter<String> adapter ;

        adapter = new ArrayAdapter<String>(this, R.layout.admin_panel_key_list, list);
        resultsListView.setAdapter(adapter);


        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {
                    Intent myIntent = new Intent(view.getContext(), History_using_keyActivity.class);
                    startActivityForResult(myIntent, 0);
                }

                if (position == 1) {
                    Intent myIntent = new Intent(view.getContext(), GenerationCertyficatActivity.class);
                    startActivityForResult(myIntent, 0);
                }
                if (position == 2) {
                    Intent myIntent = new Intent(view.getContext(), ManagmentCertyficationUserActivity.class);
                    startActivityForResult(myIntent, 0);
                }

                if (position == 3) {
                    Intent myIntent = new Intent(view.getContext(), UserWaitActivity.class);
                    startActivityForResult(myIntent, 0);
                }

                if (position == 4) {
                    Intent myIntent = new Intent(view.getContext(), CertyficatWaitActivity.class);
                    startActivityForResult(myIntent, 0);
                }

                if (position == 5) {
                    Intent myIntent = new Intent(view.getContext(), EmergencyPasswordActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }

        });

    }
            @Override
            public void onBackPressed() {
            }



}
