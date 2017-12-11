package inteligenty_zamek.app_ik.Views;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import inteligenty_zamek.app_ik.API.CyptographyApi;
import inteligenty_zamek.app_ik.API.EnumChoice;
import inteligenty_zamek.app_ik.API.sharedPreferenceApi;
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer;
import inteligenty_zamek.app_ik.R;
import inteligenty_zamek.app_ik.rest_class.User;
import inteligenty_zamek.app_ik.presenters.LoginPresenter;

public class LoginActivity extends Activity{

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter=new LoginPresenter(this);

        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.warning_icologin);
        sampleText.setTypeface(fontFamily);
        presenter.onTakeView(this);

        EditText ipserwer=  (EditText) findViewById(R.id.iptextview);
        String ipserwerstring= sharedPreferenceApi.INSTANCE.getString(this, EnumChoice.ip);
        if(ipserwerstring==""){ipserwerstring=getString(R.string.defaultIPSerwer);}
        ipserwer.setText(ipserwerstring);

      /*akcja do przycisku zaloguj  */
        final Button login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("hhhh","login button");

                EditText login=(EditText)findViewById(R.id.EditText_login);
                EditText password=(EditText)findViewById(R.id.EditText_password);
                EditText ipserwer=  (EditText) findViewById(R.id.iptextview);
                Log.i("hhhh","wczytanie wartosci");

                    User user = new User();
                    user.setLogin(login.getText().toString());
                    user.setPassword(password.getText().toString());
                Log.i("hhhh","po utworzeniu usera");

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
                finish();

            }
        });
        presenter.isLogin();
    }


    @Override
    protected void onPause() {
        super.onPause();
        HashMap<EnumChoice, String> value=new HashMap<>();
        EditText ipserwer=  (EditText) findViewById(R.id.iptextview);
       // EditText password=  (EditText) findViewById(R.id.EditText_password);
        EditText login=  (EditText) findViewById(R.id.EditText_login);

        value.put(EnumChoice.ip,ipserwer.getText().toString());
       // try {

        //    value.put(2, password.getText().toString());
       // }catch (Exception e){}
        value.put(EnumChoice.login, login.getText().toString());
        sharedPreferenceApi.INSTANCE.set(this,value);
    }


    @Override
    protected void onResume() {
        super.onResume();

        EditText ipserwer = (EditText) findViewById(R.id.iptextview);
        EditText login = (EditText) findViewById(R.id.EditText_login);
        //EditText password=  (EditText) findViewById(R.id.EditText_password);

        ipserwer.setText(sharedPreferenceApi.INSTANCE.getString(this, EnumChoice.ip));
        login.setText(sharedPreferenceApi.INSTANCE.getString(this, EnumChoice.login));
        // try {
        //     password.setText(sharedPreferenceApi.INSTANCE.getString(this,2));
        // }catch (Exception e){}
        // }

    }
}
