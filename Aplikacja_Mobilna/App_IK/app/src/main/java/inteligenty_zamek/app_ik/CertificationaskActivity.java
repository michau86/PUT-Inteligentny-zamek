package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
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

public class CertificationaskActivity extends BaseActivity {
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    int toastDelay = 4000;
    JSONArray arrJson;
    List<LinkedHashMap<String, String>> listItems = new ArrayList<>();
    List<LinkedHashMap<String, String>> listItems2 = new ArrayList<>();
    SimpleAdapter adapter;
    ListView resultsListView;
    LinkedHashMap<String, String> Keys;
    String[][] locks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certyficationask);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles, navMenuIcons);

        try {
            User user = ((GlobalClassContainer) getApplication()).getUser();
            new CertificationaskActivity.HTTPRequestLocks(user).execute();
        } catch (Exception except) {
        }
    }

    public class HTTPRequestLocks extends AsyncTask<Void, Void, String> {
        User user;
        boolean choise;

        public HTTPRequestLocks(User x) {
            user = x;
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();
            String adres = "http://" + ((GlobalClassContainer) getApplication()).getSerwerIP() + ":8080/api/download/all_locks/";
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
                    resultsListView = (ListView) CertificationaskActivity.this.findViewById(R.id.ListView_key_generate_certyfiaction);
                    //hasmap przechowujący elemety do wyświetlenia
                    Keys = new LinkedHashMap<>();
                    locks = new String[arrJson.length()][3];
                    for (int i = 0; i < arrJson.length(); i++) {
                        try {
                            locks[i][0] = arrJson.getJSONObject(i).getString("NAME");
                            locks[i][1] = arrJson.getJSONObject(i).getString("LOCALIZATION");
                            locks[i][2] = arrJson.getJSONObject(i).getString("ID_LOCK");
                            Keys.put(arrJson.getJSONObject(i).getString("NAME"), arrJson.getJSONObject(i).getString("LOCALIZATION"));
                        } catch (JSONException ignored) {
                        }
                    }

                    //stworzenie adaptera
                    SimpleAdapter adapter = new SimpleAdapter(CertificationaskActivity.this, listItems, R.layout.main_key_list,
                            new String[]{"First Line", "Second Line"},
                            new int[]{R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey});

                    //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
                    Iterator it = Keys.entrySet().iterator();
                    while (it.hasNext()) {
                        LinkedHashMap<String, String> resultsMap = new LinkedHashMap<>();
                        Map.Entry pair = (Map.Entry) it.next();
                        resultsMap.put("First Line", pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        listItems.add(resultsMap);
                    }
                    resultsListView.setAdapter(adapter);

                    resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //ustalenie id certyfikatu z listy certyfikatow
                            String key_id= locks[position][2];
                            User user = ((GlobalClassContainer) getApplication()).getUser();
                            new CertificationaskActivity.HTTPRequest(user, key_id).execute();
                        }
                    });

                    final Toast toast = Toast.makeText(CertificationaskActivity.this, "Pobrano listę zamków", Toast.LENGTH_LONG);
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
                    final Toast toast = Toast.makeText(CertificationaskActivity.this, "Lista pusta", Toast.LENGTH_LONG);
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

                final Toast toast = Toast.makeText(CertificationaskActivity.this, "Niemożna pobrać listy zamków", Toast.LENGTH_LONG);
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

    public class HTTPRequest extends AsyncTask<Void, Void, String> {
        User user;
        String id_lock;

        public HTTPRequest(User x, String id_lock) {
            user = x;
            this.id_lock = id_lock;
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();
            String adres = "http://" + ((GlobalClassContainer) getApplication()).getSerwerIP() + ":8080/api/request_new_certificate/";
            HttpPost httppost = new HttpPost(adres);
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
                nameValuePairs.add(new BasicNameValuePair("token", ((GlobalClassContainer) getApplication()).getSession()));
                nameValuePairs.add(new BasicNameValuePair("lock_id", id_lock));
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

                    final Toast toast = Toast.makeText(CertificationaskActivity.this, "Wysłano wniosek", Toast.LENGTH_LONG);
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
                    final Toast toast = Toast.makeText(CertificationaskActivity.this, "Wniosek odrzucono", Toast.LENGTH_LONG);
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

                final Toast toast = Toast.makeText(CertificationaskActivity.this, "Wniosek odrzucono", Toast.LENGTH_LONG);
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
