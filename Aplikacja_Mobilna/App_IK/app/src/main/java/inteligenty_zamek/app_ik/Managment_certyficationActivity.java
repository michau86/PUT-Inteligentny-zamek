package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Managment_certyficationActivity extends BaseActivity
       {

           private String[] navMenuTitles;
           private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managment_certyfication);
        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);

        ListView resultsListView = (ListView) this.findViewById(R.id.ListView_Managment_Certyfivation);

        String list_item[] = {getString(R.string.activity_managmentCertyfication1),getString(R.string.activity_managmentCertyfication2),getString(R.string.activity_managmentCertyfication3),getString(R.string.activity_managmentCertyfication4) };

        ArrayList<String> list = new ArrayList<String>();
        list.addAll( Arrays.asList(list_item) );
        ArrayAdapter<String> adapter ;


        adapter = new ArrayAdapter<String>(this, R.layout.admin_panel_key_list, list);

        resultsListView.setAdapter(adapter);

    }



}
