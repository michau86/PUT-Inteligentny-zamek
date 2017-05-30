package inteligenty_zamek.app_ik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity{


    public class HTTPRequest extends AsyncTask<Void, Void, String> {
        User user;
        boolean choise;
        public HTTPRequest(User x){
            user=x;
        }
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();

            String adres="http://"+ ((SessionContainer) getApplication()).getSerwerIP()+":8080/api/login/";

            HttpPost httppost = new HttpPost(adres);

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", user.getLogin()));

                nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
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
                    ((SessionContainer) getApplication()).setSession(jObj.getString("token"));
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                }
                else
                {

                }

            } catch (JSONException e) {

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      /*akcja do przycisku zaloguj  */
        final Button login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText login=(EditText)findViewById(R.id.EditText_login);
                EditText password=(EditText)findViewById(R.id.EditText_password);
                EditText ipserwer=  (EditText) findViewById(R.id.iptextview);

                if (ipserwer.getText().toString()!="") {
                    ((SessionContainer) getApplication()).setSerwerIP(ipserwer.getText().toString());
                }

                User user=new User();
                user.setLogin(login.getText().toString());
                user.setPassword(((SessionContainer) getApplication()).bin2hex(((SessionContainer) getApplication()).getHash(password.getText().toString())));
                ((SessionContainer) getApplication()).setUser(user);
                Log.i("aaaaaaa",user.getLogin());
                //wywolanie posta
                new HTTPRequest(user).execute();

            }
        });

        /* akcja do przycisku zarejestruj  */

        final Button register = (Button) findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


}
