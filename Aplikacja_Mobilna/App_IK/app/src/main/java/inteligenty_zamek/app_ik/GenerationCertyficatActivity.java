package inteligenty_zamek.app_ik;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

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
import java.util.List;

public class GenerationCertyficatActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    String[] items = { "Certyfikat dla użytkownika", "Certyfikat dla gościa"};
    ViewFlipper viewFlipper;
    Spinner spin,spin2;
    TextView name;
    TextView surname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generation_certyficat);
        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);

        viewFlipper = (ViewFlipper) findViewById(R.id.myViewFlipper);
        name = (TextView) this.findViewById(R.id.warning_icologin);
        surname = (TextView) this.findViewById(R.id.warning_icologin);
        viewFlipper.setDisplayedChild(0);
        spin = (Spinner) findViewById(R.id.spinnerChangeAdminGenerationCertyficat);
        spin2 = (Spinner) findViewById(R.id.spinnerChangeAdminGenerationCertyficat2);
        spin.setOnItemSelectedListener(this);
        spin2.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                items);

        ArrayAdapter aa2 = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                items);

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        aa2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(aa2);
        User user=((GlobalClassContainer) getApplication()).getUser();
        new HTTPRequestDownloadKey(user).execute();
        new HTTPRequestDownloadUser(user).execute();
        final Button register = (Button) findViewById(R.id.buttonRangeGenerateCertyficat);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GenerationCertyficatActivity.this,GenerateCertyficatRangeActivity.class);
                startActivity(intent);
            }
        });


        final Button generate = (Button) findViewById(R.id.generujAdmin);
        generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText from=(EditText) findViewById(R.id.inputfromAdmingenerate);
                EditText to=(EditText) findViewById(R.id.inputtoadminGenerateKey);
                final Spinner spiner1 = (Spinner) findViewById(R.id.spinnerLoks);
                final Spinner spiner2 = (Spinner) findViewById(R.id.spinnerUser);
                EditText name=(EditText) findViewById(R.id.inputName);
                String fromstring=from.getText().toString();
                String tostring=to.getText().toString();
                int lokposition=spiner1.getSelectedItemPosition();
                int userposition=spiner2.getSelectedItemPosition();
                String namestring=name.getText().toString();
                new HTTPRequest(((GlobalClassContainer) getApplication()).getUser() ,fromstring ,tostring,lokposition,userposition,namestring ).execute();


            }
        });


    }



    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        if(position==1){spin2.setSelection(1);} else {spin.setSelection(0);}
        viewFlipper.setDisplayedChild(position);

    }

    public void onNothingSelected(AdapterView<?> parent) {




    }



    public class HTTPRequestDownloadKey extends AsyncTask<Void, Void, String> {
        User user;

        public HTTPRequestDownloadKey(User x){
            user=x;
        }
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();

            String adres="http://"+ ((GlobalClassContainer) getApplication()).getSerwerIP()+":8080/api/download/all_locks/";

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
                ((GlobalClassContainer) getApplication()).getUser().addLockList(arrJson);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Spinner spiner1 = (Spinner) findViewById(R.id.spinnerLoks);
                        final ArrayList<String> listkey = new ArrayList<String>();
                        final SpinnerAdapter spinerListKeyAdapter=new SpinnerAdapter(GenerationCertyficatActivity.this, R.layout.spinner_row,  listkey,  listkey);
                        spiner1.setAdapter(spinerListKeyAdapter);
                        Lock[] list=((GlobalClassContainer) getApplication()).getUser().getLockslist();
                        for(int i=0; i<list.length; i++)
                        {
                            spinerListKeyAdapter.add(list[i].getName());
                            spinerListKeyAdapter.notifyDataSetChanged();
                        }
                    }
                });

            } catch (JSONException e) {

            }
        }
    }



    public class HTTPRequest extends AsyncTask<Void, Void, String> {
        User user;
        String from; String to; int idloks; int iduser; String nameCert;
        public HTTPRequest(User x,String from, String to, int idloks, int iduser, String nameCert ){
            user=x;
            this.from=from;
            this.to=to;
            this.idloks=idloks;
            this.iduser=iduser;
            this.nameCert=nameCert;
        }
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();


            String adres="http://"+ ((GlobalClassContainer) getApplication()).getSerwerIP()+":8080/api/admin/generate_new_certificate/";

            HttpPost httppost = new HttpPost(adres);

            try {
                // Add your data
              final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
                nameValuePairs.add(new BasicNameValuePair("token",  ((GlobalClassContainer) getApplication()).getSession()));
                nameValuePairs.add(new BasicNameValuePair("user_id",  ((GlobalClassContainer) getApplication()).getUserlist()[iduser].getIdUser()));
                nameValuePairs.add(new BasicNameValuePair("lock_id",  ((GlobalClassContainer) getApplication()).getUser().getLockslist()[idloks].getIdKey()));
                GenerationCertyficatActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        nameValuePairs.add(new BasicNameValuePair("name", name.getText().toString() ));
                        nameValuePairs.add(new BasicNameValuePair("surname", surname.getText().toString() ));
                    }
                });


                nameValuePairs.add(new BasicNameValuePair("from_date",from ));
                nameValuePairs.add(new BasicNameValuePair("to_date",to ));


                if( ((GlobalClassContainer) getApplication()).getMondayList()!=null) {
                    ArrayList<String> list=((GlobalClassContainer) getApplication()).getMondayList();
                    String result="";
                    for (String s : list)
                    {
                        result += s + ";";
                    }
                    nameValuePairs.add(new BasicNameValuePair("monday", result));
                }
                else {nameValuePairs.add(new BasicNameValuePair("monday", " "));}

                if( ((GlobalClassContainer) getApplication()).getTuesdayList()!=null) {
                    ArrayList<String> list=((GlobalClassContainer) getApplication()).getTuesdayList();
                    String result="";
                    for (String s : list)
                    {
                        result += s + ";";
                    }
                    nameValuePairs.add(new BasicNameValuePair("tuesday", result));
                }
                else {nameValuePairs.add(new BasicNameValuePair("tuesday", " "));}


                if( ((GlobalClassContainer) getApplication()).getWednesdayList()!=null) {
                    ArrayList<String> list=((GlobalClassContainer) getApplication()).getWednesdayList();
                    String result="";
                    for (String s : list)
                    {
                        result += s + ";";
                    }
                    nameValuePairs.add(new BasicNameValuePair("wednesday", result));
                }
                else {nameValuePairs.add(new BasicNameValuePair("wednesday", " "));}


                if( ((GlobalClassContainer) getApplication()).getThurstdayList()!=null) {
                    ArrayList<String> list=((GlobalClassContainer) getApplication()).getThurstdayList();
                    String result="";
                    for (String s : list)
                    {
                        result += s + ";";
                    }
                    nameValuePairs.add(new BasicNameValuePair("thursday", result));
                }
                else {nameValuePairs.add(new BasicNameValuePair("thursday", " "));}


                if( ((GlobalClassContainer) getApplication()).getFridyList()!=null) {
                    ArrayList<String> list=((GlobalClassContainer) getApplication()).getFridyList();
                    String result="";
                    for (String s : list)
                    {
                        result += s + ";";
                    }
                    nameValuePairs.add(new BasicNameValuePair("friday", result));
                }
                else {nameValuePairs.add(new BasicNameValuePair("friday", " "));}

                if( ((GlobalClassContainer) getApplication()).getSaturdayList()!=null) {
                    ArrayList<String> list=((GlobalClassContainer) getApplication()).getSaturdayList();
                    String result="";
                    for (String s : list)
                    {
                        result += s + ";";
                    }
                    nameValuePairs.add(new BasicNameValuePair("saturday", result));
                }
                else {nameValuePairs.add(new BasicNameValuePair("saturday", " "));}

                if( ((GlobalClassContainer) getApplication()).getSundayList()!=null) {
                    ArrayList<String> list=((GlobalClassContainer) getApplication()).getSundayList();
                    String result="";
                    for (String s : list)
                    {
                        result += s + ";";
                    }
                    nameValuePairs.add(new BasicNameValuePair("sunday", result));
                }
                else {nameValuePairs.add(new BasicNameValuePair("sunday", " "));}

                nameValuePairs.add(new BasicNameValuePair("is_pernament","0" ));




                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //Log.i("tutaj"," 5");

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

            } catch (JSONException e) {

            }


        }
    }





    public class HTTPRequestDownloadUser extends AsyncTask<Void, Void, String> {
        User user;

        public HTTPRequestDownloadUser(User x){
            user=x;
        }
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();

            String adres="http://"+ ((GlobalClassContainer) getApplication()).getSerwerIP()+":8080/api/download/all_user/";

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
                ((GlobalClassContainer) getApplication()).addUserList(arrJson);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Spinner spiner1 = (Spinner) findViewById(R.id.spinnerUser);
                        final ArrayList<String> listkey = new ArrayList<String>();
                        final SpinnerAdapter spinerListKeyAdapter=new SpinnerAdapter(GenerationCertyficatActivity.this, R.layout.spinner_row,  listkey,  listkey);
                        spiner1.setAdapter(spinerListKeyAdapter);
                        User[] list=((GlobalClassContainer) getApplication()).getUserlist();
                        for(int i=0; i<list.length; i++)
                        {
                            spinerListKeyAdapter.add(list[i].getLogin());
                            spinerListKeyAdapter.notifyDataSetChanged();
                        }
                    }
                });

            } catch (JSONException e) {

            }
        }
    }






}