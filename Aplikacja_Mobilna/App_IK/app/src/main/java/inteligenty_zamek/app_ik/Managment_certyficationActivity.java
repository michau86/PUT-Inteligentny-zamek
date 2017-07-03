package inteligenty_zamek.app_ik;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;

public class Managment_certyficationActivity extends BaseActivity
       {

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


        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.TextView_download_serwer);
        sampleText.setTypeface(fontFamily);
        TextView sampleText2 = (TextView) this.findViewById(R.id.TextView_download_file);
        sampleText2.setTypeface(fontFamily);

        ListView resultsListView = (ListView) this.findViewById(R.id.ListView_Managment_Certyfivation);

        String list_item[] = {getString(R.string.activity_managmentCertyfication2),getString(R.string.activity_managmentCertyfication3),getString(R.string.activity_managmentCertyfication4) };

        ArrayList<String> list = new ArrayList<String>();
        list.addAll( Arrays.asList(list_item) );
        ArrayAdapter<String> adapter ;


        adapter = new ArrayAdapter<String>(this, R.layout.admin_panel_key_list, list);

        resultsListView.setAdapter(adapter);

        listView = (ListView) findViewById(R.id.ListView_Managment_Certyfivation);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent myIntent = new Intent(view.getContext(), userCertyfikationListActivity.class);
                    startActivityForResult(myIntent, 0);
                }

                if (position == 1) {
                    Intent myIntent = new Intent(view.getContext(), CertificationaskActivity.class);
                    startActivityForResult(myIntent, 0);
                }
                if (position == 2) {
                    Intent myIntent = new Intent(view.getContext(), GenerationCertyfikatForGuestActivity.class);
                    startActivityForResult(myIntent, 0);
                }


            }

            });

        textView= (TextView) findViewById(R.id.TextView_download_serwer);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                try {

                    User user=((GlobalClassContainer) getApplication()).getUser();
                    new Managment_certyficationActivity.HTTPRequest(user).execute();
                } catch (Exception except) {

                }
            }
        });

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



                   } catch (JSONException e) {

                   }
               }
           }





       }




