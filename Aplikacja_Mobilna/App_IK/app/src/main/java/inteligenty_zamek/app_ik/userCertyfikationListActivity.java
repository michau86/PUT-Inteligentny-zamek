package inteligenty_zamek.app_ik;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class userCertyfikationListActivity extends BaseActivity {


    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_certyfikation_list);

        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);

        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.TextView_sortingIco);
        sampleText.setTypeface(fontFamily);

        ListView resultsListView = (ListView) this.findViewById(R.id.listView_userCertyfication);
        //hasmap przechowujący elemety do wyświetlenia
        HashMap<String, String> Keys = new HashMap<>();
        Keys.put("Zamek 1", "piwnica");
        Keys.put("Zamek 2", "parter");

        //stworzenie adaptera
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.main_key_list,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey});

        //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
        Iterator it = Keys.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }
        resultsListView.setAdapter(adapter);
        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent myIntent = new Intent(view.getContext(), certyficatActivity.class);
                startActivityForResult(myIntent, 0);

            }
            });

    }
}
