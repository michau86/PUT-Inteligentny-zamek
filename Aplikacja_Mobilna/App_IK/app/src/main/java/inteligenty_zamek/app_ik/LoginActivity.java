package inteligenty_zamek.app_ik;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.LoginFilter;
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
import static java.nio.charset.StandardCharsets.UTF_8;

public class LoginActivity extends Activity{

    public class HTTPRequest extends AsyncTask<Void, Void, String> {
        User user;
        int toastDelay=4000;
        boolean choise;
        public HTTPRequest(User x){
            user=x;
        }
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();

            String adres="http://"+ ((GlobalClassContainer) getApplication()).getSerwerIP()+":8080/api/login/";

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
                Log.i("heloo","there1");
                LoginActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast =Toast.makeText(LoginActivity.this, "wystapił problem podczas połącenia z serwerem", Toast.LENGTH_LONG);
                        toast.show();
                        new CountDownTimer(toastDelay, 1000)
                        {
                            public void onTick(long millisUntilFinished) {toast.show();}
                            public void onFinish() {toast.show();}
                        }.start();
                    }
                });

            } catch (IOException e) {
                Log.i("heloo","there");
                LoginActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast =Toast.makeText(LoginActivity.this, "wystapił problem podczas połącenia z serwerem", Toast.LENGTH_LONG);
                        toast.show();
                        new CountDownTimer(toastDelay, 1000)
                        {
                            public void onTick(long millisUntilFinished) {toast.show();}
                            public void onFinish() {toast.show();}
                        }.start();
                    }
                });
            }
            return null;
        }

        //akcja po otrzymaniu odpowiedzi z serwera
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            JSONObject jObj = null;

            try {
                if(response!=null) {
                    jObj = new JSONObject(response);

                if (jObj.getString("status").equals("ok") || jObj.getString("status").equals("root")) {

                    ((GlobalClassContainer) getApplication()).setSession(jObj.getString("token"));
                    String sessionencrypt=((GlobalClassContainer) getApplication()).decryption(jObj.getString("token"));
                    ((GlobalClassContainer) getApplication()).writeToFile(sessionencrypt,LoginActivity.this,"session");
                    String privatekeystring= ((GlobalClassContainer) getApplication()).readFromFile(LoginActivity.this,"*"+((GlobalClassContainer) getApplication()).getUser().getLogin());
                    String privatekeystringDecrytp="x";
                    Log.i("aaaaaaaa","weszlo1");
                    try {

                        AESHelper as=new AESHelper();
                        // String encryptedString = as.encrypt("kluuucz",stringKey);
                        // String decryptedString = as.decrypt("kluuucz","Input Encrypted String");
                      privatekeystringDecrytp = as.decrypt(privatekeystring,"kluuucz");
                    }catch(Exception e){}

                    Log.i("aaaaaaaaaaencry",privatekeystringDecrytp);
                    Log.i("aaaaaaaadecryp",privatekeystring);
                    byte[] encodedKey     = Base64.decode(privatekeystringDecrytp , Base64.DEFAULT);
                    PrivateKey priv=null;
                    try {
                        KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
                         priv = kf.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
                    }catch(Exception e){}

                    ((GlobalClassContainer) getApplication()).setPrivatekye(priv);
                        JSONObject  json= null;
                        try {
                            String readfromcertyficat=((GlobalClassContainer) getApplication()).readFromFile(LoginActivity.this, user.getLogin());

                            JSONArray arrJson = new JSONArray(readfromcertyficat);
                            ((GlobalClassContainer) getApplication()).getUser().addCertyficatList(arrJson);
                        } catch (Exception e) {}

                    if(jObj.getString("status").equals("ok"))
                    {
                        ((GlobalClassContainer) getApplication()).setIsadmin(0);
                    }
                    else
                    {
                        ((GlobalClassContainer) getApplication()).setIsadmin(1);
                    }
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    if(jObj.getString("status").equals("not activated"))
                    {

                        LoginActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                final Toast toast =Toast.makeText(LoginActivity.this, "konto musi zostac aktywowane przez administratora", Toast.LENGTH_LONG);
                                toast.show();
                                new CountDownTimer(toastDelay, 1000)
                                {
                                    public void onTick(long millisUntilFinished) {toast.show();}
                                    public void onFinish() {toast.show();}
                                }.start();
                            }
                        });
                    }
                    else {
                        TextView textView = (TextView) findViewById(R.id.warning_icologin);
                        textView.setVisibility(View.VISIBLE);
                        TextView textView2 = (TextView) findViewById(R.id.errorlogin);
                        textView2.setVisibility(View.VISIBLE);
                    }
                }
                }
            } catch (JSONException e) {
                Log.i("heloo","there3");
                LoginActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast =Toast.makeText(LoginActivity.this, "wystapił problem podczas połącenia z serwerem", Toast.LENGTH_LONG);
                        toast.show();
                        new CountDownTimer(toastDelay, 1000)
                        {
                            public void onTick(long millisUntilFinished) {toast.show();}
                            public void onFinish() {toast.show();}
                        }.start();
                    }
                });
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

        String session=((GlobalClassContainer) getApplication()).readFromFile(this,"session");
        //TODO troche bez sensu to cos jest nie tak (sesja)
        if(session!="NULL")
        {
            String sessiondecrypt=((GlobalClassContainer) getApplication()).decryption("Input Encrypted String");
            ((GlobalClassContainer) getApplication()).setSession(sessiondecrypt);
        }

        String setings=((GlobalClassContainer) getApplication()).readFromFile(this,"setings");
        if(setings!="NULL")
        {
            EditText ipserwer=  (EditText) findViewById(R.id.iptextview);
            ipserwer.setText(setings);
            ((GlobalClassContainer) getApplication()).setSerwerIP(setings);
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
                    ((GlobalClassContainer) getApplication()).setSerwerIP(setingswrite);
                    ((GlobalClassContainer) getApplication()).writeToFile(setingswrite,LoginActivity.this,"setings");

                }

                User user=new User();
                user.setLogin(login.getText().toString());
                user.setPassword(((GlobalClassContainer) getApplication()).bin2hex(((GlobalClassContainer) getApplication()).getHash(password.getText().toString())));
                ((GlobalClassContainer) getApplication()).setUser(user);
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

        /* akcja do przycisku gosc */
        final Button guest = (Button) findViewById(R.id.button_guest);
        guest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User user=new User();
                user.setLogin("guest");
                ((GlobalClassContainer) getApplication()).setUser(user);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    /*
    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));
        byte[] signature = privateSignature.sign();

        return Base64.encodeToString(signature,Base64.DEFAULT);
    }
    */
}
