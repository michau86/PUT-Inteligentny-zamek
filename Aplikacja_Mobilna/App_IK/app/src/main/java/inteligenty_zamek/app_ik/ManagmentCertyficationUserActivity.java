package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import inteligenty_zamek.app_ik.Navigation.BaseActivity;

public class ManagmentCertyficationUserActivity extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    int toastDelay = 4000;
    JSONArray arrJson;
    List<LinkedHashMap<String, String>> listItems = new ArrayList<>();
    List<LinkedHashMap<String, String>> listItems2 = new ArrayList<>();
    SimpleAdapter adapter;
    ListView resultsListView;
    LinkedHashMap<String, String> Keys;
    LinkedHashMap<String, String> resultsMap;
    EditText inputSearch;
    CharSequence csk = "";
    boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managment_certyfication_user);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles, navMenuIcons);
/*

        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.TextView_sortingIco);
        sampleText.setTypeface(fontFamily);

        try {
                User user = ((GlobalClassContainer) getApplication()).getUser();
                new ManagmentCertyficationUserActivity.HTTPRequest(user).execute();
        } catch (Exception except) {}
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
            String adres = "http://" + ((GlobalClassContainer) getApplication()).getSerwerIP() + ":8080/api/admin/download/all_certificate/";
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
                if (!jObj.getString("data").equals("empty")) {
                    arrJson = jObj.getJSONArray("data");
                    resultsListView = (ListView) ManagmentCertyficationUserActivity.this.findViewById(R.id.listView_userCertyfication);
                    //hasmap przechowujący elemety do wyświetlenia
                    Keys = new LinkedHashMap<>();
                    for (int i = 0; i < arrJson.length(); i++) {
                        try {
                            Keys.put(arrJson.getJSONObject(i).getString("USER_NAME") + " " + arrJson.getJSONObject(i).getString("USER_SURNAME") + " " + arrJson.getJSONObject(i).getString("LOCK_NAME"), "");
                        } catch (JSONException ignored) {
                        }
                    }

                    //stworzenie adaptera
                    listItems = new ArrayList<>();
                    adapter = new SimpleAdapter(ManagmentCertyficationUserActivity.this, listItems, R.layout.main_key_list,
                            new String[]{"First Line", "Second Line"},
                            new int[]{R.id.TextView_listPlaceKey, R.id.TextView_listNameKey});


                    //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
                    Iterator it = Keys.entrySet().iterator();
                    while (it.hasNext()) {
                        Log.i("aaaaaaa", "tutaj1");
                        resultsMap = new LinkedHashMap<>();
                        Map.Entry pair = (Map.Entry) it.next();
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


                            b.putString("name", listItems.get(position).values().toArray()[1].toString());
                            myIntent.putExtras(b);

                            startActivityForResult(myIntent, 0);

                        }
                    });

                    inputSearch = (EditText) findViewById(R.id.editText_Search);
                    inputSearch.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                            csk = cs;
                            Keys = new LinkedHashMap<>();
                            listItems = new ArrayList<>();
                            adapter = new SimpleAdapter(ManagmentCertyficationUserActivity.this, listItems, R.layout.main_key_list,
                                    new String[]{"First Line", "Second Line"},
                                    new int[]{R.id.TextView_listPlaceKey, R.id.TextView_listNameKey});
                            try {
                                for (int i = 0; i < arrJson.length(); i++) {
                                    try {
                                        Keys.put(arrJson.getJSONObject(i).getString("USER_NAME") + " " + arrJson.getJSONObject(i).getString("USER_SURNAME") + " " + arrJson.getJSONObject(i).getString("LOCK_NAME"), "");
                                    } catch (JSONException ignored) {
                                    }
                                }
                            } catch (Exception e) {
                            }
                            Iterator it = Keys.entrySet().iterator();
                            while (it.hasNext()) {
                                Log.i("aaaaaaa", "tutaj2");
                                resultsMap = new LinkedHashMap<>();
                                Map.Entry pair = (Map.Entry) it.next();
                                if (
                                        pair.getKey().toString().toLowerCase().contains(cs.toString().toLowerCase())
                                                || pair.getValue().toString().toLowerCase().contains(cs.toString().toLowerCase())
                                        ) {
                                    resultsMap.put("First Line", pair.getKey().toString());
                                    resultsMap.put("Second Line", pair.getValue().toString());
                                    listItems.add(resultsMap);
                                } else if (cs.toString() == "") {
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


                    //przycisk dopowiedzialny za sortowanie
                    TextView textView = (TextView) findViewById(R.id.TextView_sortingIco);
                    textView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View viewIn) {

                            if (flag == false) {
                                Arrays.sort(((GlobalClassContainer) getApplication()).getUser().getCertyficateList(), Collections.reverseOrder());
                                Log.i(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[0].getLockName(), "false");
                                flag = true;
                            } else {
                                Arrays.sort(((GlobalClassContainer) getApplication()).getUser().getCertyficateList());
                                Log.i(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[0].getLockName(), "true");
                                flag = false;
                            }

                            Keys = new LinkedHashMap<>();
                            listItems = new ArrayList<>();
                            adapter = new SimpleAdapter(ManagmentCertyficationUserActivity.this, listItems, R.layout.main_key_list,
                                    new String[]{"First Line", "Second Line"},
                                    new int[]{R.id.TextView_listPlaceKey, R.id.TextView_listNameKey});
                            try {
                                for (int i = 0; i < ((GlobalClassContainer) getApplication()).getUser().getCertyficateList().length; i++) {
                                    Keys.put(((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockLocalization(), ((GlobalClassContainer) getApplication()).getUser().getCertyficateList()[i].getLockName());
                                }
                            } catch (Exception e) {
                            }
                            Iterator it = Keys.entrySet().iterator();

                            while (it.hasNext()) {
                                Log.i("aaaaaaa", "tutaj3");
                                resultsMap = new LinkedHashMap<>();
                                Map.Entry pair = (Map.Entry) it.next();
                                if (
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

                            resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    Intent myIntent = new Intent(view.getContext(), certyficatActivity.class);
                                    startActivityForResult(myIntent, 0);

                                }
                            });
                        }
                    });

                    final Toast toast = Toast.makeText(ManagmentCertyficationUserActivity.this, "Pobrano listę certyfikatów", Toast.LENGTH_LONG);
                    toast.show();
                    new CountDownTimer(toastDelay, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }

                        public void onFinish() {
                            toast.show();
                        }
                    }.start();
                } else {
                    final Toast toast = Toast.makeText(ManagmentCertyficationUserActivity.this, "Lista pusta", Toast.LENGTH_LONG);
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
            } catch (JSONException e) {

                final Toast toast = Toast.makeText(ManagmentCertyficationUserActivity.this, "Niemożna pobrać listy certyfikatów", Toast.LENGTH_LONG);
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
        }*/
    }
}
