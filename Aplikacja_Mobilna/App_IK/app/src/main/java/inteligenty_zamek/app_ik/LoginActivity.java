package inteligenty_zamek.app_ik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import static android.content.ContentValues.TAG;
import static java.nio.charset.StandardCharsets.UTF_8;

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

                    String sessionencrypt=((SessionContainer) getApplication()).decryption(jObj.getString("token"));
                    ((SessionContainer) getApplication()).writeToFile(sessionencrypt,LoginActivity.this,"session");


                    String privatekeystring=((SessionContainer) getApplication()).readFromFile(LoginActivity.this,"X"+((SessionContainer) getApplication()).getUser().getLogin());



                    byte[] encodedKey     = Base64.decode(privatekeystring, Base64.DEFAULT);
                    PrivateKey priv=null;
                    try {
                        KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
                         priv = kf.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
                    }catch(Exception e){}

                    ((SessionContainer) getApplication()).setPrivatekye(priv);


                        JSONObject  json= null;
                        try {

                            String readfromcertyficat=((SessionContainer) getApplication()).readFromFile(LoginActivity.this, user.getLogin());

                            json = new JSONObject(readfromcertyficat);

                            JSONArray arrJson = json.getJSONArray("data");
                            ((SessionContainer) getApplication()).getUser().addCertyficatList(arrJson);

                        } catch (Exception e) {}





                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                }
                else
                {
                    TextView textView = (TextView) findViewById(R.id.warning_icologin);
                    textView.setVisibility(View.VISIBLE);
                    TextView textView2 = (TextView) findViewById(R.id.errorlogin);
                    textView2.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.warning_icologin);
        sampleText.setTypeface(fontFamily);

        String session=((SessionContainer) getApplication()).readFromFile(this,"session");
        if(session!="NULL")
        {
            String sessiondecrypt=((SessionContainer) getApplication()).decryption("Input Encrypted String");
            ((SessionContainer) getApplication()).setSession(sessiondecrypt);
        }

        String setings=((SessionContainer) getApplication()).readFromFile(this,"setings");
        if(setings!="NULL")
        {
            EditText ipserwer=  (EditText) findViewById(R.id.iptextview);
            ipserwer.setText(setings);
            ((SessionContainer) getApplication()).setSerwerIP(setings);
        }
      /*akcja do przycisku zaloguj  */
        final Button login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText login=(EditText)findViewById(R.id.EditText_login);
                EditText password=(EditText)findViewById(R.id.EditText_password);
                EditText ipserwer=  (EditText) findViewById(R.id.iptextview);

                if (ipserwer.getText().toString()!="") {
                    String setingswrite=ipserwer.getText().toString();
                    ((SessionContainer) getApplication()).setSerwerIP(setingswrite);
                    ((SessionContainer) getApplication()).writeToFile(setingswrite,LoginActivity.this,"setings");

                }

                User user=new User();
                user.setLogin(login.getText().toString());
                user.setPassword(((SessionContainer) getApplication()).bin2hex(((SessionContainer) getApplication()).getHash(password.getText().toString())));
                ((SessionContainer) getApplication()).setUser(user);
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
    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.encodeToString(signature,Base64.DEFAULT);
    }

}
