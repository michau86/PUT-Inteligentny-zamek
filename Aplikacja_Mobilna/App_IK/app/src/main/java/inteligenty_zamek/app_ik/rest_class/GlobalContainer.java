package inteligenty_zamek.app_ik.rest_class;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;

import inteligenty_zamek.app_ik.API.CyptographyApi;
import inteligenty_zamek.app_ik.API.EnumChoice;
import inteligenty_zamek.app_ik.API.fileReadWriteApi;
import inteligenty_zamek.app_ik.API.sharedPreferenceApi;
import inteligenty_zamek.app_ik.R;

/**
 * Created by Damian on 21.11.2017.
 */

public final class GlobalContainer {

    public static int menuSelectedNumber=0;
    private static User user=null;
    private static PrivateKey privatekye=null;
    public static Lock[] lockslist=null;
    public static User[] userlist=null;

    //listy uzyskane z aktywnośći GenerateCertyficatRange i wykorzystywane w GenerateCertyficat
    public static ArrayList<String> mondayList;
    public static ArrayList<String> tuesdayList;
    public static ArrayList<String> wednesdayList;
    public static ArrayList<String> thurstdayList;
    public static ArrayList<String> fridyList;
    public static ArrayList<String>  saturdayList;
    public static ArrayList<String> sundayList;




    public static void setdefault()
    {
        user=null;
        privatekye=null;
        menuSelectedNumber=0;
    }


    public static void addLockList(JSONArray arrJson)
    {
        lockslist=new Lock[arrJson.length()];
        for(int i = 0; i < arrJson.length(); i++) {
            try {
                lockslist[i] = new Lock();
                lockslist[i].setName( arrJson.getJSONObject(i).getString("NAME"));
                lockslist[i].setIdKey(arrJson.getJSONObject(i).getString("ID_LOCK"));
            }catch(JSONException e)
            {}
        }

    }



    public static void addUserList(JSONArray arrJson)
    {
        userlist=new User[arrJson.length()];
        for(int i = 0; i < arrJson.length(); i++) {
            try {
                userlist[i] = new User();
                userlist[i].setLogin( arrJson.getJSONObject(i).getString("LOGIN"));
                userlist[i].setIdUser(arrJson.getJSONObject(i).getString("ID_USER"));
            }catch(JSONException e)
            {}
        }

    }


    /// czy my potrzebujemy?
    public static User getUser(Context context)
    {
        if(user!=null){return user;}
        else
        {
            loadDataFromSharedPreferences(context);
        }
        return user;
    }
/*
    public static void addLockLIst(JSONArray obj)
    {
        if(user==null)
        {
            user=new User();
        }
        user.addLockList(obj);
    }
*/
    public static PrivateKey getPrivateKey(Context context)
    {
        if  (privatekye!=null){return privatekye;}
        else
        {

            String keyString="";
            try {
                keyString  = CyptographyApi.decrypt(fileReadWriteApi.readFromFile("*"+getUser(context).getLogin(), context), getUser(context).getPassword());
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

    // czy my potrzebujemy?
    public static void loadDataFromSharedPreferences(Context context)
    {
        user=new User();
        user.setLogin(sharedPreferenceApi.INSTANCE.getString(context, EnumChoice.login));
        user.setPassword(sharedPreferenceApi.INSTANCE.getString(context,EnumChoice.password));

    }

}

