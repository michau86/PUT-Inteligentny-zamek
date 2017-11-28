package inteligenty_zamek.app_ik.API;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Damian on 05.11.2017.
 */

public final class Valdiation {

    public static boolean isCorrectIP(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }

    //funkcjja zwracajaca czy jest poprawne haslo
    //-przynajmniej jeden numer
    //-przynajmniej jeden znak specjalny
    //-przynajmniej jeden duży znak
    public static boolean isCorrectPassword(final String password) {
        if(password.length()<8){return false;}
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        Log.i("HHHH", password);
        return matcher.matches();
    }

    public static boolean isCorrectLogin(final String login)
    {
        return login!="";
    }

}
