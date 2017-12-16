package inteligenty_zamek.app_ik.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import inteligenty_zamek.app_ik.API.EnumChoice;
import inteligenty_zamek.app_ik.API.sharedPreferenceApi;
import inteligenty_zamek.app_ik.rest_class.User;
import inteligenty_zamek.app_ik.API.Valdiation;

/**
 * Created by Damian on 26.10.2017.
 */

public class LoginModel {
    public String session;
    public String setings;
    private String ipserwer;
    private Context context;
    public boolean setIPserwer(String ipserwer)
    {
        if(Valdiation.isCorrectIP(ipserwer)) {
            this.ipserwer = ipserwer;
            return true;
        }
        return false;
    }

    public boolean getstatus()
    {
       return sharedPreferenceApi.INSTANCE.getBoolean(context,EnumChoice.isAdmin);
    }

    public boolean getIsLogin()
    {
        return sharedPreferenceApi.INSTANCE.getBoolean(context,EnumChoice.isLogin);
    }
    private User user;



    public boolean setUser(String login, String password)
    {
        if(Valdiation.isCorrectLogin(login) && Valdiation.isCorrectPassword(password)) {
            User user=new User();
            user.setLogin(login);
            user.setPassword(password);
            this.user = user;

            return true;
        }
        return false;
    }



    public String getUserLogin()
    {
        return user.getLogin();
    }

    public User getUserClass()
    {
        return user;
    }

    public String getUserPassword()
    {
        return user.getPassword();
    }

    public String getUserPasswordHash()
    {
        return user.getPasswordHash();
    }
    public LoginModel(Context context)
    {
        this.context=context;
        session=sharedPreferenceApi.INSTANCE.getString(context, EnumChoice.token);
        setings=sharedPreferenceApi.INSTANCE.getString(context,EnumChoice.ip);
    }


}
