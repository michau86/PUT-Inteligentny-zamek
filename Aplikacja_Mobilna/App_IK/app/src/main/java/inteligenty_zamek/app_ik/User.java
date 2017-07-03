package inteligenty_zamek.app_ik;

import org.json.JSONArray;
import org.json.JSONException;



public class User {


    public String idUser;
    public Lock[] lockslist;
    public  Certyficat[] certyficateList;
    private String login;
    private String name;
    private String surname;
    private String password;
    private  boolean isAdmin;

    public String getLogin() {
        return login;
    }
    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public Certyficat[] getCertyficateList() {
        return certyficateList;
    }
    public Lock[] getLockslist() {
        return lockslist;
    }
    public void setLockslist(Lock[] lockslist) {
        this.lockslist = lockslist;
    }
    public void setCertyficateList(Certyficat[] certyficateList) {
        this.certyficateList = certyficateList;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void addCertyficatList(JSONArray arrJson)
    {
        certyficateList=new Certyficat[arrJson.length()];
        for(int i = 0; i < arrJson.length(); i++) {
            try {
                certyficateList[i] = new Certyficat(
                        arrJson.getJSONObject(i).getString("ISACTUAL"),
                        arrJson.getJSONObject(i).getString("IS_PERNAMENT"),
                        arrJson.getJSONObject(i).getString("MONDAY"),
                        arrJson.getJSONObject(i).getString("TUESDAY"),
                        arrJson.getJSONObject(i).getString("WEDNESDAY"),
                        arrJson.getJSONObject(i).getString("THURSDAY"),
                        arrJson.getJSONObject(i).getString("FRIDAY"),
                        arrJson.getJSONObject(i).getString("SUNDAY"),
                        arrJson.getJSONObject(i).getString("SATURDAY"),
                        arrJson.getJSONObject(i).getString("FROM_DATE"),
                        arrJson.getJSONObject(i).getString("TO_DATE"),
                        arrJson.getJSONObject(i).getString("NAME"),
                        arrJson.getJSONObject(i).getString("SURNAME"),
                        arrJson.getJSONObject(i).getString("ID_KEY"),
                        arrJson.getJSONObject(i).getString("LOCK_NAME"),
                        arrJson.getJSONObject(i).getString("LOCALIZATION"),
                        arrJson.getJSONObject(i).getString("ID_LOCK"),
                        arrJson.getJSONObject(i).getString("LOCK_KEY"),
                        arrJson.getJSONObject(i).getString("ID_USER"),
                        arrJson.getJSONObject(i).getString("MAC_ADDRESS")
                );
            }catch(JSONException e)
            {}
        }
    }
    public void addLockList(JSONArray arrJson)
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
}
