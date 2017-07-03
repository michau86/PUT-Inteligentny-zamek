package inteligenty_zamek.app_ik;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//opis dostania się do obiektu z listy certyfikatow

//((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLok_key()
// gdzie i jest elementem na tej liscie (wedlug tej kolejnosci sa wyswietlane w main)
//po ostatniej wartosci jest get/set do konkretnej wartosci otrzymanej z jsona


public class MainActivity extends BaseActivity {
    Typeface fontFamily,tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles, navMenuIcons);
        TextView sampleText = (TextView) this.findViewById(R.id.TextView_sortingIco);
        fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        sampleText.setTypeface(fontFamily);
        final ListView resultsListView = (ListView) this.findViewById(R.id.listView_Keys);




        //hasmap przechowujący elemety do wyświetlenia
        HashMap<String, String> Keys = new HashMap<>();
        try {
            for (int i = 0; i < ((GlobalClassContainer) getApplication()).getUser().getCertyficateList().length; i++) {
                Keys.put(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockName(), ((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockLocalization());
            }
        }catch (Exception e) {

        }


        List<HashMap<String, String>> listItems = new ArrayList<>();
        //stworzenie adaptera
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.main_key_list,
                new String[]{"First Line", "Second Line", "ico"},
                new int[]{R.id.TextView_listNameKey, R.id.TextView_listPlaceKey ,R.id.ico_lock }) {
            //przeciązenie get view w celu sutawienia noweog fontu do elementu ico_lock



            @Override
            public View getView (int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                ((TextView)view.findViewById(R.id.ico_lock)).setTypeface(Typeface.createFromAsset(parent.getContext()
                        .getAssets(), "fonts/fontawesome.ttf"));

                ((TextView)view.findViewById(R.id.ico_lock)).setText("&#xf160;");

                return view;
            }
        };

        //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
        for (Object o : Keys.entrySet()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) o;
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }
        resultsListView.setAdapter(adapter);

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                }
                User us=((GlobalClassContainer) getApplication()).getUser();

                String signature="";
                try {
                     signature = sign(us.getCertyficateList()[position].getLok_key(), ((GlobalClassContainer) getApplication()).getPrivatekye());
                }catch(Exception e){}
                String tosend=us.getCertyficateList()[position].getIdKey()+";"+us.getLogin()+";"+signature;
                new Connect_and_send_message(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[position].getMac_addres(), tosend).execute();          //B8:27:EB:FC:73:A2  64:B3:10:B4:81:DD
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}