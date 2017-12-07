package inteligenty_zamek.app_ik.sampledata;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inteligenty_zamek.app_ik.R;
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer;
import inteligenty_zamek.app_ik.rest_class.User;
import inteligenty_zamek.app_ik.Navigation.BaseActivity;


public class SetingsActivity extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

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
        setContentView(R.layout.activity_setings);
        navMenuTitles= getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons =getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);

        final Button change_passwd = (Button) findViewById(R.id.button_password_user);
        change_passwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User us=((GlobalClassContainer) getApplication()).getUser();
                EditText passwd=(EditText)findViewById(R.id.editText3);
                EditText new_passwd=(EditText)findViewById(R.id.editText6);
                if(!passwd.getText().toString().equals("") && !new_passwd.getText().toString().equals("")) {
                    if (isValidPassword(passwd.getText().toString()) && isValidPassword(new_passwd.getText().toString())) {
                        String passwd_hash = ((GlobalClassContainer) getApplication()).bin2hex(((GlobalClassContainer) getApplication()).getHash(passwd.getText().toString()));
                        String new_passwd_hash = ((GlobalClassContainer) getApplication()).bin2hex(((GlobalClassContainer) getApplication()).getHash(new_passwd.getText().toString()));
                        new HTTPRequest(us.getLogin(), ((GlobalClassContainer) getApplication()).getSession(), passwd_hash, new_passwd_hash).execute();
                    }
                    else{
                        if(!isValidPassword(passwd.getText().toString())) {
                            Tooltip tooltip = new Tooltip.Builder(passwd)
                                    .setText("hasło musi spełniać warunki:\n-długość minimum 8 znaków\n-posiadać duzy znak\n-posiadać znak specjalny (!@#$%^)\nposiadać cyfrę\n ")
                                    .setTextColor(Color.WHITE)
                                    .setBackgroundColor(getResources().getColor(R.color.color_background_list))
                                    .setCancelable(true)
                                    .setGravity(Gravity.TOP)
                                    .setCornerRadius(8f)

                                    .show();
                        }
                        else{
                            Tooltip tooltip = new Tooltip.Builder(new_passwd)
                                    .setText("hasło musi spełniać warunki:\n-długość minimum 8 znaków\n-posiadać duzy znak\n-posiadać znak specjalny (!@#$%^)\nposiadać cyfrę\n ")
                                    .setTextColor(Color.WHITE)
                                    .setBackgroundColor(getResources().getColor(R.color.color_background_list))
                                    .setCancelable(true)
                                    .setGravity(Gravity.TOP)
                                    .setCornerRadius(8f)

                                    .show();
                        }
                    }
                }
                else {
                    final Toast toast = Toast.makeText(SetingsActivity.this, "wypełnij wszystkie pola", Toast.LENGTH_LONG);
                    toast.show();
                    new CountDownTimer(4000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }

                        public void onFinish() {
                            toast.show();
                        }
                    }.start();
                }
            }
        });
    }

    public class HTTPRequest extends AsyncTask<Void, Void, String> {
        String login, token, passwd, new_passwd;
        int toastDelay = 4000;
        boolean choise;

        public HTTPRequest(String login, String token, String passwd, String new_passwd) {
            this.login = login;
            this.token = token;
            this.passwd = passwd;
            this.new_passwd = new_passwd;

        }
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();

            String adres = "http://" + ((GlobalClassContainer) getApplication()).getSerwerIP() + ":8080/api/change_password/";

            HttpPost httppost = new HttpPost(adres);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("login", login));
                nameValuePairs.add(new BasicNameValuePair("token", token));
                nameValuePairs.add(new BasicNameValuePair("passwd", passwd));
                nameValuePairs.add(new BasicNameValuePair("newpasswd", new_passwd));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");

                return responseString;
            } catch (ClientProtocolException e) {
                final Toast toast = Toast.makeText(SetingsActivity.this, "wystapił problem podczas połącenia z serwerem", Toast.LENGTH_LONG);
                toast.show();
                new CountDownTimer(toastDelay, 1000) {
                    public void onTick(long millisUntilFinished) {
                        toast.show();
                    }

                    public void onFinish() {
                        toast.show();
                    }
                }.start();
            } catch (IOException e) {
                final Toast toast = Toast.makeText(SetingsActivity.this, "wystapił problem podczas połącenia z serwerem", Toast.LENGTH_LONG);
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
            return null;
        }

        //akcja po otrzymaniu odpowiedzi z serwera
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            JSONObject jObj = null;

            try {
                jObj = new JSONObject(response);
                if(jObj.getString("status").equals("ok"))
                {
                    final Toast toast = Toast.makeText(SetingsActivity.this, "Zmieniono hasło", Toast.LENGTH_LONG);
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
                else
                {
                    final Toast toast = Toast.makeText(SetingsActivity.this, "Nie powiodła się zmiana hasła", Toast.LENGTH_LONG);
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
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
    }
}
