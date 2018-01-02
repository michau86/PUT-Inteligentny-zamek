package inteligenty_zamek.app_ik.rest_class;

import android.util.Log;

/**
 * Created by damian on 10.05.2017.
 */

public class Certyficat implements Comparable<Certyficat>{
    public String mac_addres;
    String isActual,isPermanent;
    String monday,tuesday,wednesday,thurstday,friday,sunday,saturday,from,to,name,surname;
    String idKey;
    String lockName;
    String lockLocalization;
    String id_lock;
    String lok_key;
    String id_user;
    public String userName;
    public String userSurname;
    public byte status;
    //konstyruktor
    public Certyficat(String isActual, String isPermanent, String monday, String tuesday, String wednesday, String thurstday, String friday, String sunday, String saturday, String from, String to, String name, String surname, String idKey, String lockName, String lockLocalization, String id_lock, String lok_key, String id_user, String mac_addres) {
        this.isActual = isActual;
        this.isPermanent = isPermanent;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thurstday = thurstday;
        this.friday = friday;
        this.sunday = sunday;
        this.saturday = saturday;
        this.from = from;
        this.to = to;
        this.name = name;
        this.surname = surname;
        this.idKey = idKey;
        this.lockName = lockName;
        this.lockLocalization = lockLocalization;
        this.id_lock = id_lock;
        this.lok_key = lok_key;
        this.id_user = id_user;
        this.mac_addres=mac_addres;
        this.status=0;

    }

    //gettery i settery
    public String getMac_addres() {
        return mac_addres;
    }
    public void setMac_addres(String mac_addres) {
        this.mac_addres = mac_addres;
    }
    public String isActual() {
        return isActual;
    }
    public void setActual(String actual) {
        isActual = actual;
    }
    public String isPermanent() {
        return isPermanent;
    }
    public void setPermanent(String permanent) {
        isPermanent = permanent;
    }
    public String getMonday() {
        return monday;
    }
    public void setMonday(String monday) {
        this.monday = monday;
    }
    public String getTuesday() {
        return tuesday;
    }
    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }
    public String getWednesday() {
        return wednesday;
    }
    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }
    public String getThurstday() {
        return thurstday;
    }
    public void setThurstday(String thurstday) {
        this.thurstday = thurstday;
    }
    public String getFriday() {
        return friday;
    }
    public void setFriday(String friday) {
        this.friday = friday;
    }
    public String getSunday() {
        return sunday;
    }
    public void setSunday(String sunday) {
        this.sunday = sunday;
    }
    public String getSaturday() {
        return saturday;
    }
    public void setSaturday(String saturday) {
        this.saturday = saturday;
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
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getIdKey() {
        return idKey;
    }
    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }
    public String getLockName() {
        return lockName;
    }
    public void setLockName(String lockName) {
        this.lockName = lockName;
    }
    public String getLockLocalization() {
        return lockLocalization;
    }
    public void setLockLocalization(String lockLocalization) {
        this.lockLocalization = lockLocalization;
    }
    public String getId_lock() {
        return id_lock;
    }
    public void setId_lock(String id_lock) {
        this.id_lock = id_lock;
    }
    public String getLok_key() {
        return lok_key;
    }
    public void setLok_key(String lok_key) {
        this.lok_key = lok_key;
    }
    public String getId_user() {
        return id_user;
    }
    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    @Override
    public int compareTo(Certyficat o) {
        return lockName.compareTo(o.getLockName());
    }

}
