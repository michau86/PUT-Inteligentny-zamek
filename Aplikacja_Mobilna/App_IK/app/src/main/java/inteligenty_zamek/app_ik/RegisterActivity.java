package inteligenty_zamek.app_ik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Context registercontext=this;
        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.warning_ico1);
        sampleText.setTypeface(fontFamily);
        TextView sampleText2 = (TextView) this.findViewById(R.id.warning_ico2);
        sampleText2.setTypeface(fontFamily);
        TextView sampleText3 = (TextView) this.findViewById(R.id.eye_ico);
        sampleText3.setTypeface(fontFamily);


        final Button register = (Button) findViewById(R.id.button_Register);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText login=(EditText)findViewById(R.id.editText_Login);
                EditText password=(EditText)findViewById(R.id.editText_Password);
                EditText name=(EditText)findViewById(R.id.editTextName);
                EditText surname=(EditText)findViewById(R.id.editTextSurname);
                EditText ipserwer=  (EditText) findViewById(R.id.ipserwertextview);


                //  tutaj warunek sprawdzajacy poprawnosc adresu ip
               if (ipserwer.getText().toString()!="") {
                    ((SessionContainer) getApplication()).setSerwerIP(ipserwer.getText().toString());
                }
                User user=new User();
                user.setLogin(login.getText().toString());
                user.setName(name.getText().toString());
                user.setPassword(((SessionContainer) getApplication()).bin2hex(((SessionContainer) getApplication()).getHash(password.getText().toString())));
                user.setSurname(surname.getText().toString());
                if(user.getLogin()!=null && user.getName()!=null && user.getPassword()!=null && user.getSurname()!=null) {
                    new HTTPRequest(user).execute();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "wypelnij wszystkie pola", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public class HTTPRequest extends AsyncTask<Void, Void, String> {
        User user;
        boolean choise;
        Context registerContex;
        public HTTPRequest(User x){
            user=x;

        }
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();

            String adres="http://"+ ((SessionContainer) getApplication()).getSerwerIP()+":8080/api/register/";

            HttpPost httppost = new HttpPost(adres);
            try{
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
                nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
                nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
                nameValuePairs.add(new BasicNameValuePair("surname", user.getSurname()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");

                return responseString;
            } catch (ClientProtocolException e) {

                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(RegisterActivity.this, "problem z polaczneniem z serwerem", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {

                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(RegisterActivity.this, "problem z polaczneniem z serwerem", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return null;
        }

        //akcja po otrzyman iu odpowiedzi z serwera
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            JSONObject jObj = null;

            try {
                if(response!=null) {
                    jObj = new JSONObject(response);

                    if (jObj.getString("status").equals("REGISTER OK")) {
                        //przechodzimy do okna logowania
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);

                    } else {
                        TextView textView = (TextView) findViewById(R.id.loginerrortextview);
                        textView.setVisibility(View.VISIBLE);
                        TextView textView2 = (TextView) findViewById(R.id.warning_ico1);
                        textView2.setVisibility(View.VISIBLE);

                    }
                }
            } catch (JSONException e) {

            }
        }
    }



}






