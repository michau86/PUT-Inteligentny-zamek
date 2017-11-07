package inteligenty_zamek.app_ik;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.CryptoPrimitive;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;



public class LoginPresenter {
    private LoginActivity view;
    private LoginModel model;
    int toastDelay=4000;
    public LoginPresenter(LoginActivity context)
    {
        view=context;
        model=new LoginModel(view);
    }



    public void isLogin()
    {
        if(model.session!=null && model.session!="")
        {
            Intent intent = new Intent(view,MainActivity.class);
            view.startActivity(intent);
        }

    }


    public boolean login(User user, String ipserwer){

       if(model.setUser(user) && model.setIPserwer(ipserwer))
        {
            HashMap toSend = new HashMap();
            toSend.put("username", user.getLogin());
            toSend.put("password", user.getPasswordHash());
            try {
                new HTTPRequestAPI(this, "http://" + ipserwer + ":8080/api/login/", 1, toSend).execute();
            }catch (Exception e)
            {
                return false;
            }
            return true;
        }
        return false;
    }

    public void loginResult(String result)
    {

        JSONObject jObj = null;

        try {
            if(result!=null) {
                jObj = new JSONObject(result);
                if (jObj.getString("status").equals("ok") || jObj.getString("status").equals("root")) {


                    SharedPreferences sharedPref = view.getSharedPreferences(view.getString(R.string.SPName), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();



                   // String sessionencrypt= DontWork.decryption(jObj.getString("token"));
                   // fileReadWriteApi.writeToFile(sessionencrypt,view,"session");
                    try{
                    editor.putString("token", CyptographyApi.encrypt(jObj.getString("token")));
                    editor.commit();
                    }catch(Exception e){}
                    //container.setPrivatekye(priv);
                    //zaszyfrować

                    //JSONObject  json= null;
                    model.setUserCertyficat();


                    if(jObj.getString("status").equals("ok"))
                    {
                        editor.putBoolean("isadmin", false);
                        editor.commit();
                        model.getUserClass().setAdmin(false);
                    }
                    else
                    {
                        editor.putBoolean("isadmin", true);
                        editor.commit();
                        model.getUserClass().setAdmin(true);
                    }

                    ((GlobalClassContainer) view.getApplication()).setUser(model.getUserClass());
                    Intent intent = new Intent(view,MainActivity.class);
                    view.startActivity(intent);
                    view.finish();
                }
                else
                {
                    if(jObj.getString("status").equals("not activated"))
                    {

                        view.runOnUiThread(new Runnable() {
                            public void run() {
                                final Toast toast =Toast.makeText(view, "konto musi zostac aktywowane przez administratora", Toast.LENGTH_LONG);
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

                        TextView textView = (TextView) view.findViewById(R.id.warning_icologin);
                        textView.setVisibility(View.VISIBLE);
                        TextView textView2 = (TextView) view.findViewById(R.id.errorlogin);
                        textView2.setVisibility(View.VISIBLE);
                            }

                    }
                }

        } catch (JSONException e) {
            view.runOnUiThread(new Runnable() {
                public void run() {
                    final Toast toast =Toast.makeText(view, "wystapił problem podczas połącenia z serwerem", Toast.LENGTH_LONG);
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


    public void onTakeView(LoginActivity view) {
        this.view = view;
    }

}
