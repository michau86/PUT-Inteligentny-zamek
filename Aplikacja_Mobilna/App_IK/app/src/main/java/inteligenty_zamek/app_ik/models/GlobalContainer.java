package inteligenty_zamek.app_ik.models;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;

import inteligenty_zamek.app_ik.API.CyptographyApi;
import inteligenty_zamek.app_ik.API.EnumChoice;
import inteligenty_zamek.app_ik.API.fileReadWriteApi;
import inteligenty_zamek.app_ik.API.sharedPreferenceApi;

/**
 * Created by Damian on 21.11.2017.
 */

public final class GlobalContainer {

    public static int toastDelay=2000;
    public static int menuSelectedNumber=0;
    public static User user=null;
    private static PrivateKey privatekye=null;
    private static Lock[] lockslist=null;
    private static User[] userlist=null;
    private static PublicKey publicKey=null;

    public static Object obj;

    public static Certyficat getCertitificate() {
        return certitificate;
    }

    public static void setCertitificate(Certyficat certitificate) {
        GlobalContainer.certitificate = certitificate;
    }

    private static Certyficat certitificate=null;
    //listy uzyskane z aktywnośći GenerateCertyficatRange i wykorzystywane w GenerateCertyficat

    public static ArrayList<String> mondayList;
    public static ArrayList<String> tuesdayList;
    public static ArrayList<String> wednesdayList;
    public static ArrayList<String> thurstdayList;
    public static ArrayList<String> fridyList;
    public static ArrayList<String>  saturdayList;
    public static ArrayList<String> sundayList;


    public static PublicKey getPublicKey(Context context)
    {
        if(publicKey!=null){return publicKey;}
        String publickkey;

            try {
                publickkey = fileReadWriteApi.readFromFile("**" + sharedPreferenceApi.INSTANCE.getString(context, EnumChoice.login), context);
            }catch (Exception ex){return null;}


        JsonParser parser = new JsonParser();
        JsonElement mJson =  parser.parse(publickkey);
        Gson gson = new Gson();
        publicKey=gson.fromJson(mJson, PublicKey.class);
        return publicKey;
    }

    public static void publicKeyReset()
    {
publicKey=null;
    }

    public static void setDay(int day,ArrayList<String> value)
    {
        switch(day)
        {
            case 0:
            {
                mondayList=value;
                break;
            }
            case 1:
            {
                tuesdayList=value;
                break;
            }
            case 2:
            {
                wednesdayList=value;
                break;
            }
            case 3:
            {
                thurstdayList=value;
                break;
            }
            case 4:
            {
                fridyList=value;
                break;
            }
            case 5:
            {
                saturdayList=value;
                break;
            }
            case  6:
            {
                sundayList=value;
                break;
            }
        }

    }
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
    public static void setPrivateKey(PrivateKey priv)
    {
        privatekye=priv;
    }
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

    public static String getPrivateKeyCertyficat(Context context)
    {
        return   fileReadWriteApi.readFromFile("**"+getUser(context).getLogin(), context);
    }

    public static void loadDataFromSharedPreferences(Context context)
    {
        user=new User();
        user.setLogin(sharedPreferenceApi.INSTANCE.getString(context, EnumChoice.login));
        try {
            user.setPassword(CyptographyApi.decrypt(sharedPreferenceApi.INSTANCE.getString(context, EnumChoice.password)));
        }catch (Exception ex){}
        user.setAdmin(sharedPreferenceApi.INSTANCE.getBoolean(context,EnumChoice.isAdmin));
        user.setisLogin(sharedPreferenceApi.INSTANCE.getBoolean(context,EnumChoice.isAdmin));
    }

}

