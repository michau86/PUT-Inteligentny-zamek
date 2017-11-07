package inteligenty_zamek.app_ik;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;

/**
 * Created by Damian on 26.10.2017.
 */

public class LoginModel {
    public String session;
    public String setings;
    private String ipserwer;
    private Context context;
    private  SharedPreferences sharedPref;// = context.getSharedPreferences("InteligentKeySharedPreferences",Context.MODE_PRIVATE);
    public boolean setIPserwer(String ipserwer)
    {
        if(Valdiation.isCorrectIP(ipserwer)) {
            this.ipserwer = ipserwer;
            return true;
        }
        return false;
    }

    private User user;

    public boolean setUser(User user)
    {
        if(Valdiation.isCorrectLogin(user.getLogin()))
        Log.i("HHHH","poprawny login");

        if(Valdiation.isCorrectPassword(user.getPassword()))
            Log.i("HHHH","poprawne haslo");

        if(Valdiation.isCorrectLogin(user.getLogin()) && Valdiation.isCorrectPassword(user.getPassword())) {
            this.user = user;

            return true;
        }
        return false;
    }

    public void setUserCertyficat()
    {
            user.getLockslist(user.getLogin());
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

    public LoginModel(Context context)
    {
        this.context=context;
        sharedPref = context.getSharedPreferences("InteligentKeySharedPreferences",Context.MODE_PRIVATE);

        session=sharedPref.getString("token", "");
        Log.i("HHH",session);
        setings=sharedPref.getString("ipserwer", "");
    }


}
