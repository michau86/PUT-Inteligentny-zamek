package inteligenty_zamek.app_ik.rest_class;

import android.content.Context;
import android.util.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;

import inteligenty_zamek.app_ik.API.CyptographyApi;
import inteligenty_zamek.app_ik.API.fileReadWriteApi;
import inteligenty_zamek.app_ik.API.sharedPreferenceApi;
import inteligenty_zamek.app_ik.R;

/**
 * Created by Damian on 21.11.2017.
 */

public final class GlobalContainer {

    public static int menuSelectedNumber=0;
  //  public static boolean isAdmin=false;
  //  public static boolean isLogin=false;
    private static User user=null;
    private static PrivateKey privatekye=null;

    public static User getUser(Context context)
    {
        if(user!=null){return user;}
        else
        {
            loadDataFromSharedPreferences(context);
        }
        return user;
    }


    public static PrivateKey getPrivateKey(Context context)
    {
        if  (privatekye!=null){return privatekye;}
        else
        {
            String keyString="";
            try {
               keyString  = CyptographyApi.decrypt(fileReadWriteApi.readFromFile(user.getLogin(), context), user.getPassword());
            }catch (Exception ex){return null;}

            byte [] pkcs8EncodedBytes = Base64.decode(keyString, Base64.DEFAULT);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
            try {
                KeyFactory kf = KeyFactory.getInstance("RSA");
                privatekye = kf.generatePrivate(keySpec);
            } catch(Exception ex){return null;}

            return privatekye;
        }

    }
    public static void loadDataFromSharedPreferences(Context context)
    {
        user=new User();
        user.setLogin(sharedPreferenceApi.INSTANCE.getString(context,sharedPreferenceApi.choise.login));
        user.setPassword(sharedPreferenceApi.INSTANCE.getString(context,sharedPreferenceApi.choise.password));



    }

}

