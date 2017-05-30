package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CertyficatWaitActivity extends BaseActivity{
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certyficat_wait);

        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);
        Certyficat[] certyficatlist=new Certyficat[1];
        ListView resultsListView = (ListView) this.findViewById(R.id.listView_user_wait_Keys);
        //hasmap przechowujący elemety do wyświetlenia
        HashMap<String, String> Keys = new HashMap<>();
        Keys.put("Użytkownik 1", "Jan Kowlaski");
       /* certyficatlist[0]=new Certyficat();
       // certyficatlist[0].setName("Jan Kowalski");

        //stworzenie adaptera
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.user_wait_key_list,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey});

        //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)


        Iterator it = Keys.entrySet().iterator();
        for (Certyficat X : certyficatlist)
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            resultsMap.put("First Line", certyficatlist[0].getName());
            resultsMap.put("Second Line", "Piwnica");
            listItems.add(resultsMap);
        }
        resultsListView.setAdapter(adapter);
        */
    }
}
