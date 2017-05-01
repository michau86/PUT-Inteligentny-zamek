package inteligenty_zamek.app_ik;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Managment_certyficationActivity extends BaseActivity
       {

           private String[] navMenuTitles;
           private TypedArray navMenuIcons;
           ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managment_certyfication);
        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);


        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.TextView_download_serwer);
        sampleText.setTypeface(fontFamily);
        TextView sampleText2 = (TextView) this.findViewById(R.id.TextView_download_file);
        sampleText2.setTypeface(fontFamily);

        ListView resultsListView = (ListView) this.findViewById(R.id.ListView_Managment_Certyfivation);

        String list_item[] = {getString(R.string.activity_managmentCertyfication2),getString(R.string.activity_managmentCertyfication3),getString(R.string.activity_managmentCertyfication4) };

        ArrayList<String> list = new ArrayList<String>();
        list.addAll( Arrays.asList(list_item) );
        ArrayAdapter<String> adapter ;


        adapter = new ArrayAdapter<String>(this, R.layout.admin_panel_key_list, list);

        resultsListView.setAdapter(adapter);

        listView = (ListView) findViewById(R.id.ListView_Managment_Certyfivation);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {
                    Intent myIntent = new Intent(view.getContext(), userCertyfikationListActivity.class);
                    startActivityForResult(myIntent, 0);
                }

                if (position == 1) {
                    Intent myIntent = new Intent(view.getContext(), CertificationaskActivity.class);
                    startActivityForResult(myIntent, 0);
                }
                if (position == 2) {
                    Intent myIntent = new Intent(view.getContext(), GenerationCertyfikatForGuestActivity.class);
                    startActivityForResult(myIntent, 0);
                }


            }

            });
    }
       }




