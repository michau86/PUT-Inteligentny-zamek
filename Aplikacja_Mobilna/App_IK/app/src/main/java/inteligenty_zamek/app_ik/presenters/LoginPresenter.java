package inteligenty_zamek.app_ik.presenters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import inteligenty_zamek.app_ik.API.CyptographyApi;
import inteligenty_zamek.app_ik.API.EnumChoice;
import inteligenty_zamek.app_ik.API.sharedPreferenceApi;
import inteligenty_zamek.app_ik.rest_class.GlobalClassContainer;
import inteligenty_zamek.app_ik.API.HTTPRequestAPI;
import inteligenty_zamek.app_ik.Views.LoginActivity;
import inteligenty_zamek.app_ik.Views.MainActivity;
import inteligenty_zamek.app_ik.R;
import inteligenty_zamek.app_ik.rest_class.GlobalContainer;
import inteligenty_zamek.app_ik.rest_class.User;
import inteligenty_zamek.app_ik.models.LoginModel;



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


        if(model.getIsLogin()==true)
        {
            Intent intent = new Intent(view,MainActivity.class);
            view.startActivity(intent);
            view.finish();
        }

    }


    public boolean login(User user, String ipserwer){

       if(model.setUser(user) && model.setIPserwer(ipserwer))
        {
            HashMap toSend = new HashMap();
            toSend.put("username", user.getLogin());
            toSend.put("password", user.getPasswordHash());

            HashMap <EnumChoice,String> value=new HashMap<>();
            value.put(EnumChoice.ip,ipserwer);
            value.put(EnumChoice.login,user.getLogin());
            value.put(EnumChoice.password,user.getPassword());
            sharedPreferenceApi.INSTANCE.set(view,value);

            try {
                new HTTPRequestAPI(this, "http://" + ipserwer + ":8080/api/login/", "loginResult", toSend).execute();
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

                    try{
                    HashMap<EnumChoice,String> value=new HashMap<>();
                    value.put(EnumChoice.token,jObj.getString("token"));
                    sharedPreferenceApi.INSTANCE.set(view,value);
                    sharedPreferenceApi.INSTANCE.set(view,true,EnumChoice.isLogin);


                    }catch(Exception e){}

                    model.setUserCertyficat();

                    if(jObj.getString("status").equals("ok"))
                    {
                        sharedPreferenceApi.INSTANCE.set(view,false, EnumChoice.isAdmin);
                        model.getUserClass().setAdmin(false);
                    }
                    else
                    {
                        sharedPreferenceApi.INSTANCE.set(view,true,EnumChoice.isAdmin);
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
                }else{
                view.runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast =Toast.makeText(view, "wystapil problem podczas polaczenia z serwerem", Toast.LENGTH_LONG);
                        toast.show();
                        new CountDownTimer(toastDelay, 1000)
                        {
                            public void onTick(long millisUntilFinished) {toast.show();}
                            public void onFinish() {toast.show();}
                        }.start();
                    }
                });

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
