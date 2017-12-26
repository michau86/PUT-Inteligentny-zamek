package inteligenty_zamek.app_ik.beforeChange;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;

import java.util.LinkedHashMap;
import java.util.List;

import inteligenty_zamek.app_ik.Navigation.BaseActivity;
import inteligenty_zamek.app_ik.R;


public class History_using_keyActivity extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    int toastDelay = 4000;
    JSONArray arrJson;
    LinkedHashMap<String, String> Keys;
    ListView resultsListView;
    LinkedHashMap<String, String> resultsMap;
    List<LinkedHashMap<String, String>> listItems;
    List<LinkedHashMap<String, String>> listItems2;
    boolean flag=true;
    CharSequence csk;
    SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_of_using_key);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
/*
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles, navMenuIcons);
        resultsListView = (ListView) History_using_keyActivity.this.findViewById(R.id.ListView_History);
        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.TextView_sortingIco);
        sampleText.setTypeface(fontFamily);

        try {
            if (((GlobalClassContainer) getApplication()).getIsadmin() >= 0) {
                User user = ((GlobalClassContainer) getApplication()).getUser();
                new History_using_keyActivity.HTTPRequest(user).execute();
            }
        } catch (Exception except) {}

       EditText inputSearch = (EditText) findViewById(R.id.editText_Search);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                csk=cs;
                Keys = new LinkedHashMap<>();
                listItems2 = new ArrayList<>();
                adapter = new SimpleAdapter(History_using_keyActivity.this, listItems2, R.layout.history_using_key_list,
                        new String[]{"First Line", "Second Line", "Third Line"},
                        new int[]{R.id.history_icon, R.id.title, R.id.describe});
                try {
                    for (int i = 0; i < listItems.size(); i++) {
                        Keys.put(listItems.get(i).values().toArray()[1].toString(), listItems.get(i).values().toArray()[2].toString());
                        Log.i(listItems.get(i).values().toArray()[1].toString(),listItems.get(i).values().toArray()[2].toString());

                    }
                }catch (Exception e) {}
                Iterator it = Keys.entrySet().iterator();
                while (it.hasNext())
                {
                    resultsMap = new LinkedHashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    Log.i("aaaaaaa",pair.getValue().toString().toLowerCase());
                    Log.i("aaaaaaaa",cs.toString().toLowerCase());
                    if(
                            pair.getKey().toString().toLowerCase().contains(cs.toString().toLowerCase())
                                    || pair.getValue().toString().toLowerCase().contains(cs.toString().toLowerCase())
                            ) {


                        resultsMap.put("First Line", "");
                        resultsMap.put("Second Line", pair.getKey().toString());
                        resultsMap.put("Third Line", pair.getValue().toString());
                        listItems2.add(resultsMap);
                    }
                    else if(cs.toString()=="")
                    {


                        resultsMap.put("First Line", "");
                        resultsMap.put("Second Line", pair.getKey().toString());
                        resultsMap.put("Third Line", pair.getValue().toString());
                        listItems2.add(resultsMap);
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


        //przycisk dopowiedzialny za sortowanie
        TextView  textView= (TextView) findViewById(R.id.TextView_sortingIco);
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View viewIn) {




                if(flag==false) {
                    Arrays.sort(listItems2.toArray());
                    flag=true;
                }
                else
                {
                    //Comparator <LinkedHashMap<String, String>> cmp =
                            Collections.sort(listItems2, new Comparator<LinkedHashMap<String, String>>(){
                        public int compare(LinkedHashMap<String, String> one, LinkedHashMap<String, String> two) {
                            return one.values().toString().compareTo(two.values().toString());
                            }});



                 Arrays.sort(listItems2.toArray());
                    flag=false;
                }

                Keys =new LinkedHashMap<>();
                 adapter = new SimpleAdapter(History_using_keyActivity.this, listItems2, R.layout.history_using_key_list,
                        new String[]{"First Line", "Second Line", "Third Line"},
                        new int[]{R.id.history_icon, R.id.title, R.id.describe});
               // try {
                 //   for (int i = 0; i < ((GlobalClassContainer) getApplication()).getUser().getCertyficateList(this).length; i++) {
                  //      Keys.put(listItems2.get(i).values().toArray()[0].toString(), listItems2.get(i).values().toArray()[1].toString() );
                    }
               // }catch (Exception e) {}
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
                        resultsMap.put("First Line", "");
                        resultsMap.put("Second Line", pair.getKey().toString());
                        resultsMap.put("Third Line", pair.getValue().toString());
                        listItems.add(resultsMap);
                    }
                }
                resultsListView.setAdapter(adapter);
            }
        });















    }


    public class HTTPRequest extends AsyncTask<Void, Void, String> {
        User user;
        boolean choise;

        public HTTPRequest(User x) {
            user = x;
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();
            String adres = "http://" + ((GlobalClassContainer) getApplication()).getSerwerIP() + ":8080/api/admin/history/";
            HttpPost httppost = new HttpPost(adres);
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
                nameValuePairs.add(new BasicNameValuePair("token", ((GlobalClassContainer) getApplication()).getSession()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                return responseString;
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return null;
        }

        //akcja po otrzyman iu odpowiedzi z serwera
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            JSONObject jObj = null;
            try {
                jObj = new JSONObject(response);
                arrJson = jObj.getJSONArray("data");
                 //hasmap przechowujący elemety do wyświetlenia
                 Keys = new LinkedHashMap<>();
                for (int i = 0; i < arrJson.length(); i++) {
                    try {
                        String[] date = arrJson.getJSONObject(i).getString("DATE").split("T");
                        if (arrJson.getJSONObject(i).getInt("ACCESS") == 1) {
                            Keys.put("Otwarcie " + Integer.toString(i), "zamek " + arrJson.getJSONObject(i).getString("ZAMEK") + " został otwarty przez " + arrJson.getJSONObject(i).getString("NAME") + " " + arrJson.getJSONObject(i).getString("SURNAME") + ", data zdarzenia: " + date[0] +  " godzina: " + date[1]);
                        } else {
                            Keys.put("Nieautoryzowana próba otwarcia " + Integer.toString(i), "zamek " + arrJson.getJSONObject(i).getString("ZAMEK") + " zarejestrował nieutoryzowaną próbę dostępu przez " + arrJson.getJSONObject(i).getString("NAME") + " " + arrJson.getJSONObject(i).getString("SURNAME") + ", data zdarzenia: " + date[0] +  " godzina: " + date[1]);
                        }
                    } catch (JSONException ignored) {}
                }
                //stworzenie adaptera
                listItems = new ArrayList<>();
                adapter = new SimpleAdapter(History_using_keyActivity.this, listItems, R.layout.history_using_key_list,
                        new String[]{"First Line", "Second Line", "Third Line"},
                        new int[]{R.id.history_icon, R.id.title, R.id.describe});


                //TODO zmiana koloru czcionki nieautoryzowany dostęp
                String buffor;
                //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
                Iterator it = Keys.entrySet().iterator();
                while (it.hasNext()) {
                    resultsMap = new LinkedHashMap<>();
                    Map.Entry pair = (Map.Entry) it.next();
                    buffor = pair.getKey().toString();
                    if (buffor == "Nieautoryzowana próba otwarcia") {
                        resultsMap.put("First Line", "");
                    } else {
                        resultsMap.put("First Line", "");
                    }
                    resultsMap.put("Second Line", buffor);
                    resultsMap.put("Third Line", pair.getValue().toString());
                    listItems.add(resultsMap);
                }
                listItems2=listItems;
                resultsListView.setAdapter(adapter);
                final Toast toast = Toast.makeText(History_using_keyActivity.this, "Pobrano historie zamków", Toast.LENGTH_LONG);
                toast.show();
                new CountDownTimer(toastDelay, 1000) {
                    public void onTick(long millisUntilFinished) {
                        toast.show();
                    }

                    public void onFinish() {
                        toast.show();
                    }
                }.start();

            } catch (JSONException e) {

                final Toast toast = Toast.makeText(History_using_keyActivity.this, "Niemożna pobrać historii zamków", Toast.LENGTH_LONG);
                toast.show();
                new CountDownTimer(toastDelay, 1000) {
                    public void onTick(long millisUntilFinished) {
                        toast.show();
                    }

                    public void onFinish() {
                        toast.show();
                    }
                }.start();
            }
        }
    }
}*/
    }
    }


