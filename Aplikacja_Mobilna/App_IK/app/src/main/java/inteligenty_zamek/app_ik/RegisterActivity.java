package inteligenty_zamek.app_ik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tooltip.Tooltip;

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
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;

public class RegisterActivity extends Activity {

    private KeyPair pair = null;
    private int toastDelay=4000;

    //funkcja zwracajaca czy adres ip jest poprawny
    public static boolean isValidIP(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }

    //funkcjja zwracajaca czy jest poprawne haslo
    //-przynajmniej jeden numer
    //-przynajmniej jeden znak specjalny
    //-przynajmniej jeden duży znak
    public static boolean isValidPassword(final String password) {

        if(password.length()<8){return false;}
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        Log.i("", password);
        return matcher.matches();

    }


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
        sampleText2 = (TextView) this.findViewById(R.id.warning_ico3);
        sampleText2.setTypeface(fontFamily);

        TextView ipserwerregister = (TextView) this.findViewById(R.id.ipserwertextview);

        ipserwerregister.setText( ((GlobalClassContainer) getApplication()).getSerwerIP());
        final Button register = (Button) findViewById(R.id.button_Register);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                TextView textView = (TextView) findViewById(R.id.loginerrortextview);
                textView.setVisibility(View.INVISIBLE);
                TextView textView2 = (TextView) findViewById(R.id.warning_ico1);
                textView2.setVisibility(View.INVISIBLE);

                textView = (TextView) findViewById(R.id.iperrortextview);
                textView.setVisibility(View.INVISIBLE);
                textView2 = (TextView) findViewById(R.id.warning_ico2);
                textView2.setVisibility(View.INVISIBLE);

                textView = (TextView) findViewById(R.id.passworderrorTextView);
                textView.setVisibility(View.INVISIBLE);
                 textView2 = (TextView) findViewById(R.id.warning_ico3);
                textView2.setVisibility(View.INVISIBLE);

                EditText login=(EditText)findViewById(R.id.editText_Login);
                EditText password=(EditText)findViewById(R.id.editText_Password2);
                EditText password2=(EditText)findViewById(R.id.editText_Password2);
                EditText name=(EditText)findViewById(R.id.editTextName);
                EditText surname=(EditText)findViewById(R.id.editTextSurname);
                EditText ipserwer=  (EditText) findViewById(R.id.ipserwertextview);

                User user=new User();
                user.setLogin(login.getText().toString());
                user.setName(name.getText().toString());
                user.setPassword(((GlobalClassContainer) getApplication()).bin2hex(((GlobalClassContainer) getApplication()).getHash(password.getText().toString())));
                user.setSurname(surname.getText().toString());

                //warunek sprawdzajacy czy wszystkie pola sa wypelnione
                if(!user.getLogin().equals("") && !user.getName().equals("") && !user.getPassword().equals("") && !user.getSurname().equals("")) {
                    //warunek sprawdzajacy poprawność ip
                    if(isValidIP(ipserwer.getText().toString())) {
                        //warunek sprawdzajacy poprawnosc hasla
                        if (isValidPassword(password.getText().toString())) {
                            if(password.getText().toString().equals(password2.getText().toString())) {
                                new HTTPRequest(user).execute();
                            }
                            else{ Tooltip tooltip=new Tooltip.Builder(password2)
                                    .setText("hasła sie nie zgadzają")
                                    .setTextColor(Color.WHITE)
                                    .setBackgroundColor(getResources().getColor(R.color.color_background_list))
                                    .setCancelable(true)
                                    .setGravity(Gravity.TOP)
                                    .setCornerRadius(8f)
                                    .show();}
                        } else
                        {

                            Tooltip tooltip=new Tooltip.Builder(password)
                                    .setText("hasło musi spełniać warunki:\n-długość minimum 8 znaków\n-posiadać duzy znak\n-posiadać znak specjalny (!@#$%^)\nposiadać cyfrę\n ")
                                    .setTextColor(Color.WHITE)
                                    .setBackgroundColor(getResources().getColor(R.color.color_background_list))
                                    .setCancelable(true)
                                    .setGravity(Gravity.TOP)
                                    .setCornerRadius(8f)

                                    .show();

                            textView = (TextView) findViewById(R.id.passworderrorTextView);
                            textView.setVisibility(View.VISIBLE);
                            textView2 = (TextView) findViewById(R.id.warning_ico2);
                            textView2.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        if (isValidPassword(password.getText().toString())) {
                             textView = (TextView) findViewById(R.id.iperrortextview);
                            textView.setVisibility(View.VISIBLE);
                             textView2 = (TextView) findViewById(R.id.warning_ico3);
                            textView2.setVisibility(View.VISIBLE);
                        } else
                        {
                            textView = (TextView) findViewById(R.id.passworderrorTextView);
                            textView.setVisibility(View.VISIBLE);
                            textView2 = (TextView) findViewById(R.id.warning_ico2);
                            textView2.setVisibility(View.VISIBLE);
                            TextView textView3 = (TextView) findViewById(R.id.iperrortextview);
                            textView3.setVisibility(View.VISIBLE);
                            TextView textView4 = (TextView) findViewById(R.id.warning_ico3);
                            textView4.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else
                {
                    final Toast toast =Toast.makeText(RegisterActivity.this, "wypełnij wszystkie pola", Toast.LENGTH_LONG);
                    toast.show();
                    new CountDownTimer(toastDelay, 1000)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.show();}
                    }.start();
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
                String adres="http://"+ ((GlobalClassContainer) getApplication()).getSerwerIP()+":8080/api/register/";

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
                       //TODO szyforwanie klucza


                      //  byte[] bytes = text.getBytes("UTF-8");
                      //  String text = new String(bytes, "UTF-8");
                        try{

                        ((GlobalClassContainer) getApplication()).writeToFile(
                                new String(  ((GlobalClassContainer) getApplication()).encryptMsg(stringKey, ((GlobalClassContainer) getApplication()).generateKey()), "UTF-8"  )
                        , RegisterActivity.this,"*"+user.getLogin());
                        }catch (Exception e){}
                        ((GlobalClassContainer) getApplication()).setPrivatekye(pair.getPrivate());
                        final Toast toast =Toast.makeText(RegisterActivity.this, "nastapiła poprawna rejestracja", Toast.LENGTH_LONG);
                        toast.show();
                        new CountDownTimer(toastDelay, 1000)
                        {
                            public void onTick(long millisUntilFinished) {toast.show();}
                            public void onFinish() {toast.show();}
                        }.start();
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

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        return pair;
    }
}






