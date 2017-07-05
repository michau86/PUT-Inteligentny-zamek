package inteligenty_zamek.app_ik;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class History_using_keyActivity extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    int toastDelay = 4000;
    JSONArray arrJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_of_using_key);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles, navMenuIcons);

        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.TextView_sortingIco);
        sampleText.setTypeface(fontFamily);

        try {
            if (((GlobalClassContainer) getApplication()).getIsadmin() >= 0) {
                User user = ((GlobalClassContainer) getApplication()).getUser();
                new History_using_keyActivity.HTTPRequest(user).execute();
            }
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
                ListView resultsListView = (ListView) History_using_keyActivity.this.findViewById(R.id.ListView_History);
                //hasmap przechowujący elemety do wyświetlenia
                LinkedHashMap<String, String> Keys = new LinkedHashMap<>();
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
                List<LinkedHashMap<String, String>> listItems = new ArrayList<>();
                SimpleAdapter adapter = new SimpleAdapter(History_using_keyActivity.this, listItems, R.layout.history_using_key_list,
                        new String[]{"First Line", "Second Line", "Third Line"},
                        new int[]{R.id.history_icon, R.id.TextView_liistNameKey, R.id.TextView_listPlaceKey});


                //TODO zmiana koloru czcionki nieautoryzowany dostęp
                String buffor;
                //iterator elementow (przepisanie z hashmap do adaptera[listitems] elementow)
                Iterator it = Keys.entrySet().iterator();
                while (it.hasNext()) {
                    LinkedHashMap<String, String> resultsMap = new LinkedHashMap<>();
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
}

