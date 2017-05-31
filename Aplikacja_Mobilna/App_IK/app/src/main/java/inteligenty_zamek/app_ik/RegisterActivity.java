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
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RegisterActivity extends Activity {

    private KeyPair pair = null;
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
        TextView ipserwerregister = (TextView) this.findViewById(R.id.ipserwertextview);

        ipserwerregister.setText( ((SessionContainer) getApplication()).getSerwerIP());
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
            String stringKey="";
            try {
                pair = generateKeyPair();
            }catch(Exception e){}
            if (pair.getPublic()!= null) {stringKey = Base64.encodeToString(pair.getPublic().getEncoded(), Base64.DEFAULT);

            }
                String adres="http://"+ ((SessionContainer) getApplication()).getSerwerIP()+":8080/api/register/";

                HttpPost httppost = new HttpPost(adres);
            try{

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
                nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
                nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
                nameValuePairs.add(new BasicNameValuePair("surname", user.getSurname()));
                nameValuePairs.add(new BasicNameValuePair("publickkey", stringKey));
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
                        String stringKey="";
                        stringKey = Base64.encodeToString(pair.getPrivate().getEncoded(), Base64.DEFAULT);
                        ((SessionContainer) getApplication()).writeToFile(stringKey,RegisterActivity.this,"X"+user.getLogin());
                        ((SessionContainer) getApplication()).setPrivatekye(pair.getPrivate());

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

    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.encodeToString(signature,Base64.DEFAULT);
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.decode(signature,Base64.DEFAULT);

        return publicSignature.verify(signatureBytes);
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        return pair;
    }

}






