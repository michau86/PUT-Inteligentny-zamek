package inteligenty_zamek.app_ik;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer;
import inteligenty_zamek.app_ik.rest_class.User;
import inteligenty_zamek.app_ik.Navigation.BaseActivity;

public class CertyficatWaitActivity extends BaseActivity {
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    int toastDelay = 4000;
    JSONArray arrJson;
    List<LinkedHashMap<String, String>> listItems = new ArrayList<>();
    List<LinkedHashMap<String, String>> listItems2 = new ArrayList<>();
    SimpleAdapter adapter;
    ListView resultsListView;
    CharSequence csk;
    LinkedHashMap<String, String> Keys;
    LinkedHashMap<String, String> resultsMap;
    LinkedHashMap<String, String> idcert;
    LinkedHashMap<String, String> login;
    LinkedHashMap<String, String> lockName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certyficat_wait);

        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);


        try {
            if (((GlobalClassContainer) getApplication()).getIsadmin() >= 0) {
                User user = ((GlobalClassContainer) getApplication()).getUser();
                new CertyficatWaitActivity.HTTPRequest(user).execute();
            }
        } catch (Exception except) {}



        EditText inputSearch = (EditText) findViewById(R.id.editText_Search);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                csk=cs;
                Keys = new LinkedHashMap<>();
                listItems2 = new ArrayList<>();
                adapter  = new SimpleAdapter(CertyficatWaitActivity.this, listItems2, R.layout.user_wait_key_list,
                        new String[]{"First Line", "Second Line"},
                        new int[]{R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey}){
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        View itemView = super.getView(position, convertView, parent);
                        //akceptuj
                        View myTaskButton = itemView.findViewById(R.id.button6);
                        myTaskButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //akcja do butonu akceptuj
                                try {
                                    if (((GlobalClassContainer) getApplication()).getIsadmin() >= 0) {
                                        User user = ((GlobalClassContainer) getApplication()).getUser();
                                        String s=listItems.get(position).values().toArray()[0].toString();
                                        new CertyficatWaitActivity.HTTPRequestDecision(user, listItems.get(position).values().toArray()[0].toString(), idcert.get(s)).execute();


                                            Log.i("aaaaaaaa","no wchodzii");
                                        Intent intent = new Intent(getBaseContext(), GenerationCertyficatActivity.class);
                                        intent.putExtra("lock", lockName.get(listItems.get(position).values().toArray()[0]));
                                        intent.putExtra("", login.get(listItems.get(position).values().toArray()[1]));
                                        listItems.remove(listItems2.get(position));
                                        //listItems2.remove(position);
                                        Log.i("aaaaaaaa","no wchodzii2");
                                        //z listitems 1 remowe
                                        resultsListView.setAdapter(adapter);
                                        startActivity(intent);
                                    }
                                } catch (Exception except) {
                                }
                            }
                        });
                        //odrzuc
                        View myTaskButton2 = itemView.findViewById(R.id.button5);
                        myTaskButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //akcja do butonu odrzuc
                                try {
                                    if (((GlobalClassContainer) getApplication()).getIsadmin() >= 0) {
                                        User user = ((GlobalClassContainer) getApplication()).getUser();
                                        new CertyficatWaitActivity.HTTPRequestDecision(user, listItems2.get(position).values().toArray()[0].toString(), "0").execute();
                                        listItems.remove(listItems2.get(position));
                                        listItems2.remove(position);
                                        //to samo
                                        resultsListView.setAdapter(adapter);
                                    }
                                } catch (Exception except) {
                                }
                            }
                        });
                        return itemView;
                    }
                };
                try {
                    for (int i = 0; i < listItems.size(); i++) {
                        Keys.put(listItems.get(i).values().toArray()[0].toString(), listItems.get(i).values().toArray()[1].toString());

                    }
                }catch (Exception e) {}
                Iterator it = Keys.entrySet().iterator();
                while (it.hasNext())
                {
                    resultsMap = new LinkedHashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    if(
                            pair.getKey().toString().toLowerCase().contains(cs.toString().toLowerCase())
                                    || pair.getValue().toString().toLowerCase().contains(cs.toString().toLowerCase())
                            ) {

                        resultsMap.put("First Line", pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        listItems2.add(resultsMap);
                    }
                    else if(cs.toString()=="")
                    {


                        resultsMap.put("First Line", pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
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
            String adres = "http://" + ((GlobalClassContainer) getApplication()).getSerwerIP() + ":8080/api/admin/cetificate_waiting/";
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
            Log.i("aaaaaaaaa",response);

            JSONObject jObj = null;
            try {
                jObj = new JSONObject(response);
                if (!jObj.getString("data").equals("empty")) {
                    arrJson = jObj.getJSONArray("data");
                    resultsListView  = (ListView) CertyficatWaitActivity.this.findViewById(R.id.listView_user_wait_Keys);
                    //hasmap przechowujący elemety do wyświetlenia
                    Keys = new LinkedHashMap<>();
                    idcert  = new LinkedHashMap<>();
                    login = new LinkedHashMap<>();
                    lockName = new LinkedHashMap<>();
                    for (int i = 0; i < arrJson.length(); i++) {
                        try {
                            Keys.put(arrJson.getJSONObject(i).getString("LOCK_NAME")+" "+arrJson.getJSONObject(i).getString("LOGIN"), "dla uzytkownika: "+arrJson.getJSONObject(i).getString("USER_NAME") + " " + arrJson.getJSONObject(i).getString("USER_SUERNAME") );

                            idcert.put(arrJson.getJSONObject(i).getString("LOCK_NAME")+" "+arrJson.getJSONObject(i).getString("LOGIN"),arrJson.getJSONObject(i).getString("ID_KEY"));
                            login.put(arrJson.getJSONObject(i).getString("LOCK_NAME")+" "+arrJson.getJSONObject(i).getString("LOGIN"),arrJson.getJSONObject(i).getString("LOGIN"));
                            lockName.put(arrJson.getJSONObject(i).getString("LOCK_NAME")+" "+arrJson.getJSONObject(i).getString("LOGIN"),arrJson.getJSONObject(i).getString("LOCK_NAME"));

                            Log.i("aaaaaaaaporow",arrJson.getJSONObject(i).getString("LOCK_NAME")+" "+arrJson.getJSONObject(i).getString("LOGIN"));

                        } catch (JSONException ignored) {
                        }
                    }
                    //stworzenie adaptera
                    adapter  = new SimpleAdapter(CertyficatWaitActivity.this, listItems, R.layout.user_wait_key_list,
                            new String[]{"First Line", "Second Line"},
                            new int[]{R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey}) {
                        public View getView(final int position, View convertView, ViewGroup parent) {
                            View itemView = super.getView(position, convertView, parent);
                            //akceptuj
                            View myTaskButton = itemView.findViewById(R.id.button6);
                            myTaskButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //akcja do butonu akceptuj
                                    try {
                                        if (((GlobalClassContainer) getApplication()).getIsadmin() >= 0) {
                                            User user = ((GlobalClassContainer) getApplication()).getUser();
                                            String s=listItems.get(position).values().toArray()[0].toString();

                                            new CertyficatWaitActivity.HTTPRequestDecision(user, listItems.get(position).values().toArray()[0].toString(), idcert.get(s)).execute();
                                            Intent intent = new Intent(CertyficatWaitActivity.this, GenerationCertyficatActivity.class);
                                            intent.putExtra("Lock_name", lockName.get(s));
                                            intent.putExtra("login", login.get(s));
                                            listItems2.remove(listItems.get(position));

                                            Log.i("aaaaaaa",login.get(s));
                                            //listItems.remove(position);
                                            resultsListView.setAdapter(adapter);
                                            startActivity(intent);
                                        }
                                    } catch (Exception except) {
                                    }
                                }
                            });
                            //odrzuc
                            View myTaskButton2 = itemView.findViewById(R.id.button5);
                            myTaskButton2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //akcja do butonu odrzuc
                                    try {
                                        if (((GlobalClassContainer) getApplication()).getIsadmin() >= 0) {
                                            User user = ((GlobalClassContainer) getApplication()).getUser();

                                            String s=listItems.get(position).values().toArray()[0].toString();

                                            new CertyficatWaitActivity.HTTPRequestDecision(user, listItems.get(position).values().toArray()[0].toString(), idcert.get(s)).execute();
                                            listItems2.remove(listItems.get(position));
                                            listItems.remove(position);
                                            resultsListView.setAdapter(adapter);
                                        }
                                    } catch (Exception except) {
                                    }
                                }
                            });
                            return itemView;
                        }
                    };

                    //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
                    Iterator it = Keys.entrySet().iterator();
                    while (it.hasNext()) {
                        resultsMap  = new LinkedHashMap<>();
                        Map.Entry pair = (Map.Entry) it.next();
                        resultsMap.put("First Line", pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        listItems.add(resultsMap);
                    }
                    listItems2=listItems;
                    resultsListView.setAdapter(adapter);

                    final Toast toast = Toast.makeText(CertyficatWaitActivity.this, "Pobrano listę oczekujących certyfikatów na akceptację", Toast.LENGTH_LONG);
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
                else{
                    final Toast toast = Toast.makeText(CertyficatWaitActivity.this, "Lista pusta", Toast.LENGTH_LONG);
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

                final Toast toast = Toast.makeText(CertyficatWaitActivity.this, "Niemożna pobrać listy oczekujących certyfikató na akceptację", Toast.LENGTH_LONG);
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

    public class HTTPRequestDecision extends AsyncTask<Void, Void, String> {
        User user;
        String id;


        public HTTPRequestDecision(User x, String login_user, String id) {
            user = x;

            this.id=id;
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();
            String adres = "http://" + ((GlobalClassContainer) getApplication()).getSerwerIP() + ":8080/api/admin/certificate_decision/";
            HttpPost httppost = new HttpPost(adres);
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
                nameValuePairs.add(new BasicNameValuePair("token", ((GlobalClassContainer) getApplication()).getSession()));
                nameValuePairs.add(new BasicNameValuePair("cetificate_id", id));
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
                if (jObj.getString("status").equals("ok")) {

                    final Toast toast = Toast.makeText(CertyficatWaitActivity.this, "Decyzja przyjęta", Toast.LENGTH_LONG);
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
                else{
                    final Toast toast = Toast.makeText(CertyficatWaitActivity.this, "Decyzja odrzucona", Toast.LENGTH_LONG);
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

                final Toast toast = Toast.makeText(CertyficatWaitActivity.this, "Decyzja odrzucona", Toast.LENGTH_LONG);
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
}
