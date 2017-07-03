package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class History_using_keyActivity extends BaseActivity
        {

            private String[] navMenuTitles;
            private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_of_using_key);
        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);

        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.TextView_sortingIco);
        sampleText.setTypeface(fontFamily);

        ListView resultsListView = (ListView) this.findViewById(R.id.ListView_History);
        //hasmap przechowujący elemety do wyświetlenia
        HashMap<String, String> Keys = new HashMap<>();
        Keys.put("Otwarcie", "zamek XYZ zotał otwart przez XYZ, data zdarzenia:2017.02.27 o godzinei 13:15");
        Keys.put("Nieautoryzowana próba otwarcia", "zamek XYZ zarejestrował neiautoryzowaną próbę dostępu  przez XYZ, data zdarzenia:2017.02.27 o godzinei 13:15");

        //stworzenie adaptera
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.history_using_key_list,
                new String[]{"First Line", "Second Line", "Third Line"},
                new int[]{R.id.history_icon,R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey});


        //TODO zmiana koloru czcionki nieautoryzowany dostęp
        String buffor;
        //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
        Iterator it = Keys.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            buffor=pair.getKey().toString();
            if(buffor=="Nieautoryzowana próba otwarcia")
            {
                resultsMap.put("First Line", "");
            }
            else
            {
                resultsMap.put("First Line", "");
            }
            resultsMap.put("Second Line", buffor);
            resultsMap.put("Third Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        resultsListView.setAdapter(adapter);
    }

}

