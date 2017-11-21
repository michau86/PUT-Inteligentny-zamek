package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import inteligenty_zamek.app_ik.Navigation.BaseActivity;


public class userCertyfikationListActivity extends BaseActivity {




    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    EditText inputSearch;
    SimpleAdapter adapter;
    List<HashMap<String, String>> listItems;
    ListView resultsListView;
    LinkedHashMap<String, String> Keys;
    CharSequence csk="";
    boolean flag=true;
    LinkedHashMap<String, String> resultsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_certyfikation_list);

        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);
/*
        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.TextView_sortingIco);
        sampleText.setTypeface(fontFamily);
        resultsListView= (ListView) this.findViewById(R.id.listView_userCertyfication);
        Keys= new LinkedHashMap<>();
        try {
            for (int i = 0; i < ((GlobalClassContainer) getApplication()).getUser().getCertyficateList().length; i++) {
                Keys.put( ((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockLocalization(),((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockName());
            }
        }catch (Exception e) {

        }


        //stworzenie adaptera
        listItems = new ArrayList<>();
        adapter = new SimpleAdapter(this, listItems, R.layout.main_key_list,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.TextView_listPlaceKey, R.id.TextView_listNameKey});



        //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
        Iterator it = Keys.entrySet().iterator();
        while (it.hasNext())
        {
            Log.i("aaaaaaa","tutaj1");
            resultsMap = new LinkedHashMap<>();
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

                Bundle b = new Bundle();


                b.putString("lock", listItems.get(position).values().toArray()[0].toString());
                b.putString("name", listItems.get(position).values().toArray()[1].toString());
                myIntent.putExtras(b);

                startActivityForResult(myIntent, 0);

            }
            });

        inputSearch = (EditText) findViewById(R.id.editText_Search);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                csk=cs;
                Keys = new LinkedHashMap<>();
                listItems = new ArrayList<>();
                adapter = new SimpleAdapter(userCertyfikationListActivity.this, listItems, R.layout.main_key_list,
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
                    Log.i("aaaaaaa","tutaj2");
                     resultsMap = new LinkedHashMap<>();
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
                resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent myIntent = new Intent(view.getContext(), certyficatActivity.class);

                        Bundle b = new Bundle();


                        b.putString("lock", listItems.get(position).values().toArray()[0].toString());
                        b.putString("name", listItems.get(position).values().toArray()[1].toString());
                        myIntent.putExtras(b);

                        startActivityForResult(myIntent, 0);

                    }
                });
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


        //przycisk dopowiedzialny za sortowanie
        TextView  textView= (TextView) findViewById(R.id.TextView_sortingIco);
        textView.setOnClickListener(new View.OnClickListener() {

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

                 Keys =new LinkedHashMap<>();
                listItems = new ArrayList<>();
                adapter = new SimpleAdapter(userCertyfikationListActivity.this, listItems, R.layout.main_key_list,
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
                    Log.i("aaaaaaa","tutaj3");
                    resultsMap = new LinkedHashMap<>();
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
        });*/
    }



}
