package inteligenty_zamek.app_ik.rest_class;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;

import inteligenty_zamek.app_ik.API.CyptographyApi;
import inteligenty_zamek.app_ik.R;


public class GlobalClassContainer extends Application {

    private SharedPreferences sharedPref;

    private String session;
    private User user;
    private Certyficat[] certyficatlist;
    private String serwerIP="192.168.8.100";
    private PrivateKey privatekye;
    private String certyficatadminlist="";
    private User[] userlist;
    public ArrayList<String> mondayList;
    private ArrayList<String> tuesdayList;
    private ArrayList<String> wednesdayList;
    private ArrayList<String> thurstdayList;
    private ArrayList<String> fridyList;
    private ArrayList<String>  saturdayList;
    private ArrayList<String> sundayList;
    private int buffor1,buffor2;
    private final static String HEX = "0123456789ABCDEF";
    private int isadmin=-1;
    public Context context;
    //ustawia domsylne wartosci (uzywane przy wylogowaniu)
    public void setDefaultValue()
    {
        session="";
        user=null;
        certyficatlist=null;
        privatekye=null;
        certyficatadminlist="";
        userlist=null;
        mondayList=null;
        tuesdayList=null;
        wednesdayList=null;
        thurstdayList=null;
        fridyList=null;
        saturdayList=null;
        sundayList=null;
        buffor1=0;
        buffor2=0;
        isadmin=-1;
    }


    //////////////////// do usuniecia
    public byte[] getHash(String password) {
        MessageDigest digest=null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }
    public static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
    }
    //metody odpowiedzialne za zapisywanie dfo pliku
    public void writeToFile(String data,Context context,String filepath) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filepath, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Log.i("HHHH","po zapisie do pliku");
            Log.i("HHHH",filepath);
            Log.i("HHHH","powyzej nazwa pliku");
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
   // funkcja odpowiedzialna za odczytywanie z pliku
    public String readFromFile(Context context, String filepath) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filepath);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return "NULL";
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    public void loadDataFromSharedPreferences()
    {
        user=new User();
        sharedPref = this.getSharedPreferences(this.getString(R.string.SPName),Context.MODE_PRIVATE);
        user.setLogin(sharedPref.getString("login", ""));
        try {
          user.setPassword(CyptographyApi.decrypt(sharedPref.getString("password", "")));
        }catch(Exception e){}
        serwerIP=sharedPref.getString("ipserwer", "");
        try {
          session = CyptographyApi.decrypt(sharedPref.getString("token", ""));
      }catch (Exception e){}
    }
    public void addUserList(JSONArray arrJson)
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


    public int getIsadmin() {

        if (isadmin<0)
        {
            loadDataFromSharedPreferences();
        }
        return isadmin;
    }

    public ArrayList<String> getMondayList() {
        return mondayList;
    }
    public User[] getUserlist() {
        return userlist;
    }
    public void setMondayList(ArrayList<String> mondayList) {
        this.mondayList = mondayList;
    }
    public ArrayList<String> getTuesdayList() {
        return tuesdayList;
    }
    public void setTuesdayList(ArrayList<String> tuesdayList) {
        this.tuesdayList = tuesdayList;
    }
    public ArrayList<String> getWednesdayList() {
        return wednesdayList;
    }
    public void setWednesdayList(ArrayList<String> wednesdayList) {
        this.wednesdayList = wednesdayList;
    }
    public ArrayList<String> getThurstdayList() {
        return thurstdayList;
    }
    public void setThurstdayList(ArrayList<String> thurstdayList) {
        this.thurstdayList = thurstdayList;
    }
    public ArrayList<String> getFridyList() {
        return fridyList;
    }
    public void setFridyList(ArrayList<String> fridyList) {
        this.fridyList = fridyList;
    }
    public ArrayList<String> getSaturdayList() {
        return saturdayList;
    }
    public void setSaturdayList(ArrayList<String> saturdayList) {
        this.saturdayList = saturdayList;
    }
    public ArrayList<String> getSundayList() {
        return sundayList;
    }
    public void setSundayList(ArrayList<String> sundayList) {
        this.sundayList = sundayList;
    }
    public PrivateKey getPrivatekye() {
        return privatekye;
    }
    public void setPrivatekye(PrivateKey privatekye) {
        this.privatekye = privatekye;
    }
    public String getSerwerIP() {
        return serwerIP;
    }
    public String getSession() {
        return session;
    }
    public void setSession(String session) {
        this.session = session;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


}
