package inteligenty_zamek.app_ik;

import android.app.Application;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SessionContainer extends Application {

    public String session;
    public User user;

    public String getSerwerIP() {
        return serwerIP;
    }

    public void setSerwerIP(String serwerIP) {
        this.serwerIP = serwerIP;
    }

    public String serwerIP="192.168.8.100";
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
    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
    }

}
