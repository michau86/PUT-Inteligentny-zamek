package inteligenty_zamek.app_ik.Views;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import inteligenty_zamek.app_ik.API.EnumChoice;
import inteligenty_zamek.app_ik.API.sharedPreferenceApi;
import inteligenty_zamek.app_ik.R;
import inteligenty_zamek.app_ik.models.GlobalContainer;
import inteligenty_zamek.app_ik.presenters.LoginPresenter;

public class  LoginActivity extends Activity{

    private LoginPresenter presenter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter=new LoginPresenter(this);
        context=this;
        Typeface fontFamily = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.ttf");
        TextView sampleText = (TextView) this.findViewById(R.id.loginTextViewWarningIco);
        sampleText.setTypeface(fontFamily);
        presenter.onTakeView(this);

        EditText ipserwer=  (EditText) findViewById(R.id.loginEditTextIpSerwer);
        String ipserwerstring= sharedPreferenceApi.INSTANCE.getString(this, EnumChoice.ip);
        if(ipserwerstring==""){ipserwerstring=getString(R.string.defaultIPSerwer);}
        ipserwer.setText(ipserwerstring);

      /*akcja do przycisku zaloguj  */
        final Button login = (Button) findViewById(R.id.loginButtonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                disableWarning();
                EditText login=(EditText)findViewById(R.id.loginEditTextLogin);
                EditText password=(EditText)findViewById(R.id.loginEditTextPassword);
                EditText ipserwer=  (EditText) findViewById(R.id.loginEditTextIpSerwer);
                if(!presenter.login(login.getText().toString(),password.getText().toString(),ipserwer.getText().toString()))
                    {
                        setWarning();
                    }

            }
        });

        /* akcja do przycisku zarejestruj  */

        final Button register = (Button) findViewById(R.id.loginButtonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        presenter.isLogin();
    }

    public void setWarning()
    {
        TextView value=(TextView)findViewById(R.id.loginTextViewErrorText);
        value.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams params = value.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        value.setLayoutParams(params);


        TypedArray themeArray = context.getTheme().obtainStyledAttributes(new int[] {android.R.attr.editTextColor});
        try {
            int index = 0;
            int defaultColourValue = 0;
            int editTextColour = themeArray.getColor(index, defaultColourValue);
        }
        finally
        {
            themeArray.recycle();
        }



        value=(TextView)findViewById(R.id.loginTextViewWarningIco);
        value.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams params2 = value.getLayoutParams();
        params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        value.setLayoutParams(params);
    }


    public void disableWarning()
    {
        TextView value=(TextView)findViewById(R.id.loginTextViewErrorText);
        value.setVisibility(View.INVISIBLE);
        value=(TextView)findViewById(R.id.loginTextViewWarningIco);
        value.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HashMap<EnumChoice, String> value=new HashMap<>();
        EditText ipserwer=  (EditText) findViewById(R.id.loginEditTextIpSerwer);
        EditText login=  (EditText) findViewById(R.id.loginEditTextLogin);
        value.put(EnumChoice.ip,ipserwer.getText().toString());
        value.put(EnumChoice.login, login.getText().toString());
        sharedPreferenceApi.INSTANCE.set(this,value);
    }


    @Override
    protected void onResume() {
        super.onResume();

        EditText ipserwer = (EditText) findViewById(R.id.loginEditTextIpSerwer);
        EditText login = (EditText) findViewById(R.id.loginEditTextLogin);
        ipserwer.setText(sharedPreferenceApi.INSTANCE.getString(this, EnumChoice.ip));
        login.setText(sharedPreferenceApi.INSTANCE.getString(this, EnumChoice.login));
    }


    public void showMessage(String message)
    {
        runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast =Toast.makeText(context, "wystapil problem podczas polaczenia z serwerem", Toast.LENGTH_LONG);
                toast.show();
                new CountDownTimer(GlobalContainer.toastDelay, 1000)
                {
                    public void onTick(long millisUntilFinished) {toast.show();}
                    public void onFinish() {toast.show();}
                }.start();
    }

    });
    }

}