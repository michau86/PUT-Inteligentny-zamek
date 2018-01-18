package inteligenty_zamek.app_ik.presenters;


import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import inteligenty_zamek.app_ik.API.CyptographyApi;
import inteligenty_zamek.app_ik.API.EnumChoice;
import inteligenty_zamek.app_ik.API.sharedPreferenceApi;
import inteligenty_zamek.app_ik.API.HTTPRequestAPI;
import inteligenty_zamek.app_ik.Views.LoginActivity;
import inteligenty_zamek.app_ik.Views.MainActivity;
import inteligenty_zamek.app_ik.models.GlobalContainer;
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


    public boolean login(String login, String password, String ipserwer){


       if(model.setUser(login,password) && model.setIPserwer(ipserwer))
        {

            HashMap toSend = new HashMap();
            toSend.put("username", model.getUserLogin());
            toSend.put("password", model.getUserPasswordHash());

            HashMap <EnumChoice,String> value=new HashMap<>();
            value.put(EnumChoice.ip,ipserwer);
            value.put(EnumChoice.login,model.getUserLogin());
            try {
                value.put(EnumChoice.password, CyptographyApi.encrypt(model.getUserPassword()));
            }catch(Exception ex){}
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
                    value.put(EnumChoice.token,CyptographyApi.encrypt(jObj.getString("token")));
                    sharedPreferenceApi.INSTANCE.set(view,value);
                    sharedPreferenceApi.INSTANCE.set(view,true,EnumChoice.isLogin);
                    }catch(Exception e){}


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

                    GlobalContainer.user= model.getUserClass();
                    Intent intent = new Intent(view,MainActivity.class);
                    view.startActivity(intent);
                    view.finish();
                }
                else
                {

                    if(jObj.getString("status").equals("not activated"))
                    {
                        view.showMessage("konto musi zostac aktywowane przez administratora");
                    }
                    else
                        {
                       view.setWarning();
                            }
                    }
                }
                else{view.showMessage("wystapil problem podczas polaczenia z serwerem");}

        } catch (JSONException e) {

            view.showMessage("wystapil problem podczas polaczenia z serwerem");
        }
    }


    public void onTakeView(LoginActivity view) {
        this.view = view;
    }

}
