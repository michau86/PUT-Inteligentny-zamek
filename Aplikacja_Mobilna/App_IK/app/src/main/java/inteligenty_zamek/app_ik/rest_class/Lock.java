package inteligenty_zamek.app_ik.rest_class;

/**
 * Created by damian on 10.05.2017.
 */

public class Lock implements Comparable<Certyficat> {
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String surname) {
        this.localization = surname;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    private String key;
    private String from;
    private String to;
    private String name;
    private String localization;

    public String getMac_Adres() {
        return Mac_Adres;
    }

    public void setMac_Adres(String mac_Adres) {
        Mac_Adres = mac_Adres;
    }

    private String Mac_Adres;
   private String idKey;

   @Override
    public int compareTo(Certyficat o) {
        return name.compareTo(o.name);
    }

}
