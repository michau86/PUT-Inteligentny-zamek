package inteligenty_zamek.app_ik;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Managment_certyficationActivity extends BaseActivity
       {
           int toastDelay=4000;
           private String[] navMenuTitles;
           private TypedArray navMenuIcons;
           ListView listView;
           TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managment_certyfication);
        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);
        //ustawienie fontu dla ikon pobierania z serwera oraz pobrania z telefonu
        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView ico_download_serwer = (TextView) this.findViewById(R.id.TextView_download_serwer);
        ico_download_serwer.setTypeface(fontFamily);
        TextView ico_download_file = (TextView) this.findViewById(R.id.TextView_download_file);
        ico_download_file.setTypeface(fontFamily);

        //lista
        ListView resultsListView = (ListView) this.findViewById(R.id.ListView_Managment_Certyfivation);
        //tablica tringow dla uzytkownika zalogowanego
         ArrayList<String> list = new ArrayList<String>();
        //ustawienie listview dla zuytkownika niezalogowanego
        if(((GlobalClassContainer) getApplication()).getIsadmin()<0)
        {
            ico_download_serwer.setText("");
            list.addAll( Arrays.asList(getString(R.string.activity_managmentCertyfication2)) );
        }
        //ustawienie listview dla uzytkownika zalogowanego
        else {
            String list_item[] = {getString(R.string.activity_managmentCertyfication2),getString(R.string.activity_managmentCertyfication3),getString(R.string.activity_managmentCertyfication4) };
            list.addAll(Arrays.asList(list_item));
        }
        ArrayAdapter<String> adapter ;
        adapter = new ArrayAdapter<String>(this, R.layout.admin_panel_key_list, list);
        resultsListView.setAdapter(adapter);
        //wyszukanie listy oraz ustwaienie akcji dotyczacych klikniec na poszcegolne elementy
        listView = (ListView) findViewById(R.id.ListView_Managment_Certyfivation);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent myIntent = new Intent(view.getContext(), userCertyfikationListActivity.class);
                    startActivityForResult(myIntent, 0);
                }
                if (((GlobalClassContainer) getApplication()).getIsadmin() >= 0)
                {
                    if (position == 1) {
                        Intent myIntent = new Intent(view.getContext(), CertificationaskActivity.class);
                        startActivityForResult(myIntent, 0);
                    }
                    if (position == 2) {
                    Intent myIntent = new Intent(view.getContext(), GenerationCertyfikatForGuestActivity.class);
                    startActivityForResult(myIntent, 0);
                    }
                }
            }

            });

        //akcja dotyczaca klikniecia na ikone sciagnij z serwera
        textView= (TextView) findViewById(R.id.TextView_download_serwer);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                try {
                    if (((GlobalClassContainer) getApplication()).getIsadmin() >= 0){
                        User user = ((GlobalClassContainer) getApplication()).getUser();
                    new Managment_certyficationActivity.HTTPRequest(user).execute();
                }
                } catch (Exception except) {
                }
            }
        });


        //akcja dotyczaca kliknieca na ikonke sciagnij z pliku
        textView= (TextView) findViewById(R.id.TextView_download_file);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                // TODO obsluga przycisku wgrywajacego certyfikat z pliku
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);



            }
        });




    }
        //przeciazenie funkcji odpwoeidzialnrj za obrobke wskazanego pliku

           @Override
           protected void onActivityResult(int requestCode, int resultCode, Intent data ) {
               super.onActivityResult(requestCode, resultCode, data);
               if(requestCode==123 && resultCode==RESULT_OK) {
                   Uri selectedfile = data.getData();
                   JSONArray arrJson=null;
                    try {
                        JSONObject jObj = new JSONObject(((GlobalClassContainer) getApplication()).readFromFile(this, selectedfile.toString()));
                        String readcertyficat=((GlobalClassContainer) getApplication()).readFromFile(this,((GlobalClassContainer) getApplication()).getUser().getLogin());
                        JSONObject jObj2 = new JSONObject(readcertyficat);

                        JSONObject merged = new JSONObject();
                        JSONObject[] objs = new JSONObject[] { jObj, jObj2 };
                        for (JSONObject obj : objs) {
                            Iterator it = obj.keys();
                            while (it.hasNext()) {
                                String key = (String)it.next();
                                merged.put(key, obj.get(key));
                            }
                        }

                       arrJson = merged.getJSONArray("data");
                    }catch(Exception e) {}
                //TODO zrobienie zapisywania do pliku, przetestowanie
                 //  ((GlobalClassContainer) getApplication()).writeToFile(response,Managment_certyficationActivity.this,((GlobalClassContainer) getApplication()).getUser().getLogin());
                   ((GlobalClassContainer) getApplication()).getUser().addCertyficatList(arrJson);

                   //zapisanie pliku
                   ((GlobalClassContainer) getApplication()).writeToFile("tresc",Managment_certyficationActivity.this,"guest");
               }
           }


           @Override
           public void onBackPressed() {
           }

           public class HTTPRequest extends AsyncTask<Void, Void, String> {
               User user;
               boolean choise;
               public HTTPRequest(User x){
                   user=x;
               }
               @Override
               protected String doInBackground(Void... params) {
                   HttpClient httpclient = new DefaultHttpClient();
                   String adres="http://"+ ((GlobalClassContainer) getApplication()).getSerwerIP()+":8080/api/download/all_certifacate/";
                   HttpPost httppost = new HttpPost(adres);
                   try {
                       // Add your data
                       List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                       nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
                       nameValuePairs.add(new BasicNameValuePair("token",  ((GlobalClassContainer) getApplication()).getSession()));
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
                       JSONArray arrJson = jObj.getJSONArray("data");
                       ((GlobalClassContainer) getApplication()).writeToFile(response,Managment_certyficationActivity.this,((GlobalClassContainer) getApplication()).getUser().getLogin());
                       ((GlobalClassContainer) getApplication()).getUser().addCertyficatList(arrJson);

                       final Toast toast =Toast.makeText(Managment_certyficationActivity.this, "dodano certyfikaty", Toast.LENGTH_LONG);
                       toast.show();
                       new CountDownTimer(toastDelay, 1000)
                       {
                           public void onTick(long millisUntilFinished) {toast.show();}
                           public void onFinish() {toast.show();}
                       }.start();

                   } catch (JSONException e) {

                       final Toast toast =Toast.makeText(Managment_certyficationActivity.this, "brak certyfikatow", Toast.LENGTH_LONG);
                       toast.show();
                       new CountDownTimer(toastDelay, 1000)
                       {
                           public void onTick(long millisUntilFinished) {toast.show();}
                           public void onFinish() {toast.show();}
                       }.start();
                   }
               }
           }

       }




