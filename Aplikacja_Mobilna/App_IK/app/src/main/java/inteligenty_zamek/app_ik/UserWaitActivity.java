package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserWaitActivity extends BaseActivity {


    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wait);
        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);
        ListView resultsListView = (ListView) this.findViewById(R.id.listView_user_wait_Keys);
        //hasmap przechowujący elemety do wyświetlenia
        LinkedHashMap<String, String> Keys = new LinkedHashMap<>();
        Keys.put("Użytkownik 1", "Jan Kowlaski");
        Keys.put("Użytkownik 2", "Tola Nowak");

        //stworzenie adaptera
        List<LinkedHashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.user_wait_key_list,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey});

        //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
        Iterator it = Keys.entrySet().iterator();
        while (it.hasNext())
        {
            LinkedHashMap<String, String> resultsMap = new LinkedHashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }
        resultsListView.setAdapter(adapter);
    }
}
