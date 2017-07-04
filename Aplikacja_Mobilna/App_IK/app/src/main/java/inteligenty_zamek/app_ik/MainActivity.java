package inteligenty_zamek.app_ik;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


//opis dostania się do obiektu z listy certyfikatow

//((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLok_key()
// gdzie i jest elementem na tej liscie (wedlug tej kolejnosci sa wyswietlane w main)
//po ostatniej wartosci jest get/set do konkretnej wartosci otrzymanej z jsona


public class MainActivity extends BaseActivity {
    Typeface fontFamily;
    CharSequence csk;
    List<HashMap<String, String>> listItems;
    SimpleAdapter adapter;
    ListView resultsListView;
    LinkedHashMap<String, String> Keys;
    boolean flag=true;
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
        resultsListView  = (ListView) this.findViewById(R.id.listView_Keys);



        sampleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {

                if(flag==false) {
                    Arrays.sort(((GlobalClassContainer) getApplication()).getUser().getCertyficateList(), Collections.reverseOrder());
                    Log.i(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[0].getLockName(), "false");
                    flag=true;
                }
                else
                {
                    Arrays.sort(((GlobalClassContainer) getApplication()).getUser().getCertyficateList());
                    Log.i(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[0].getLockName(), "true");
                    flag=false;
                }

                LinkedHashMap<String, String> Keys =new LinkedHashMap<>();
                listItems = new ArrayList<>();
                adapter = new SimpleAdapter(MainActivity.this, listItems, R.layout.main_key_list,
                        new String[]{"First Line", "Second Line"},
                        new int[]{R.id.TextView_listPlaceKey, R.id.TextView_listNameKey});
                try {
                    for (int i = 0; i < ((GlobalClassContainer) getApplication()).getUser().getCertyficateList().length; i++) {
                        Keys.put(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockLocalization(),((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockName() );
                    }
                }catch (Exception e) {}
                Iterator it = Keys.entrySet().iterator();

                while (it.hasNext())
                {
                    HashMap<String, String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    if(
                            pair.getKey().toString().toLowerCase().contains(csk.toString().toLowerCase())
                                    || pair.getValue().toString().toLowerCase().contains(csk.toString().toLowerCase())
                            ) {
                        Log.i("bbbb", pair.getKey().toString());
                        resultsMap.put("First Line", pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        listItems.add(resultsMap);
                    }
                }
                resultsListView.setAdapter(adapter);
            }
        });



        EditText inputSearch = (EditText) findViewById(R.id.editText_Search);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                csk=cs;
                Keys = new LinkedHashMap<String, String>();
                listItems = new ArrayList<>();
                adapter = new SimpleAdapter(MainActivity.this, listItems, R.layout.main_key_list,
                        new String[]{"First Line", "Second Line"},
                        new int[]{R.id.TextView_listPlaceKey, R.id.TextView_listNameKey});
                try {
                    for (int i = 0; i < ((GlobalClassContainer) getApplication()).getUser().getCertyficateList().length; i++) {
                        Keys.put(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockLocalization(),((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockName() );
                    }
                }catch (Exception e) {}
                Iterator it = Keys.entrySet().iterator();
                while (it.hasNext())
                {
                    HashMap<String, String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    if(
                            pair.getKey().toString().toLowerCase().contains(cs.toString().toLowerCase())
                                    || pair.getValue().toString().toLowerCase().contains(cs.toString().toLowerCase())
                            ) {
                        resultsMap.put("First Line", pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        listItems.add(resultsMap);
                    }
                    else if(cs.toString()=="")
                    {
                        resultsMap.put("First Line", pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        listItems.add(resultsMap);
                    }
                }
                resultsListView.setAdapter(adapter);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


        //hasmap przechowujący elemety do wyświetlenia
       Keys = new LinkedHashMap<>();
        try {
            for (int i = 0; i < ((GlobalClassContainer) getApplication()).getUser().getCertyficateList().length; i++) {
                Keys.put(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockName(), ((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockLocalization());
            }
        }catch (Exception e) {

        }

         listItems   = new ArrayList<>();
        //stworzenie adaptera
        adapter = new SimpleAdapter(this, listItems, R.layout.main_key_list,
                new String[]{"First Line", "Second Line", "ico"},
                new int[]{R.id.TextView_listNameKey, R.id.TextView_listPlaceKey  }) {
            //przeciązenie get view w celu sutawienia noweog fontu do elementu ico_lock
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
        // akcja po nacisnieciu zamka na liśćie
        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //ustalenie id certyfikatu z listy certyfikatow
                String keyname= Keys.values().toArray()[position].toString();
                int index=((GlobalClassContainer) getApplication()).searchcertyficat(keyname);


                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                }
                User us=((GlobalClassContainer) getApplication()).getUser();

                String signature="";
                try {
                     signature = sign(us.getCertyficateList()[index].getLok_key(), ((GlobalClassContainer) getApplication()).getPrivatekye());
                }catch(Exception e){}
                String tosend=us.getCertyficateList()[index].getIdKey()+";"+us.getLogin()+";"+signature;
                new Connect_and_send_message(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[index].getMac_addres(), tosend).execute();          //B8:27:EB:FC:73:A2  64:B3:10:B4:81:DD
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