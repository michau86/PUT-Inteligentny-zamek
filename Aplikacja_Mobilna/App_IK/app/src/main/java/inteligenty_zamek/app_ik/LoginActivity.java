package inteligenty_zamek.app_ik;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

    private SharedPreferences sharedPref;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPref = this.getSharedPreferences(this.getString(R.string.SPName),Context.MODE_PRIVATE);
        presenter=new LoginPresenter(this);
        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.warning_icologin);
        sampleText.setTypeface(fontFamily);
        presenter.onTakeView(this);

        EditText ipserwer=  (EditText) findViewById(R.id.iptextview);
        String ipserwerstring=sharedPref.getString("ipserwer", "");
        if(ipserwerstring==""){ipserwerstring=getString(R.string.defaultIPSerwer);}
        ipserwer.setText(ipserwerstring);

      /*akcja do przycisku zaloguj  */
        final Button login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText login=(EditText)findViewById(R.id.EditText_login);
                EditText password=(EditText)findViewById(R.id.EditText_password);
                EditText ipserwer=  (EditText) findViewById(R.id.iptextview);

                    User user = new User();
                    user.setLogin(login.getText().toString());
                    user.setPassword(password.getText().toString());
                    if(!presenter.login(user,ipserwer.getText().toString()))
                    {
                    TextView value=(TextView)findViewById(R.id.errorlogin);
                    value.setVisibility(View.VISIBLE);
                    value=(TextView)findViewById(R.id.warning_icologin);
                    value.setVisibility(View.VISIBLE);
                    }

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
        presenter.isLogin();
    }


    @Override
    protected void onPause() {
        super.onPause();
        sharedPref = this.getSharedPreferences(this.getString(R.string.SPName),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        EditText ipserwer=  (EditText) findViewById(R.id.iptextview);
        editor.putString("ipserwer", ipserwer.getText().toString());
        EditText login=  (EditText) findViewById(R.id.EditText_login);
        editor.putString("login", login.getText().toString());
        EditText password=  (EditText) findViewById(R.id.EditText_password);
        try {
            editor.putString("password", CyptographyApi.encrypt(password.getText().toString()));
        }catch (Exception e){}
        editor.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();

        sharedPref = this.getSharedPreferences(this.getString(R.string.SPName),Context.MODE_PRIVATE);
        EditText ipserwer=  (EditText) findViewById(R.id.iptextview);
        EditText login=  (EditText) findViewById(R.id.EditText_login);
        EditText password=  (EditText) findViewById(R.id.EditText_password);
        ipserwer.setText(sharedPref.getString("ipserwer", ""));
        login.setText(sharedPref.getString("login", ""));
        try {
            password.setText(CyptographyApi.decrypt(sharedPref.getString("password", "")));
        }catch (Exception e){}
        }


}
