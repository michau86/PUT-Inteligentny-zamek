package inteligenty_zamek.app_ik;

import java.security.PrivateKey;

/**
 * Created by Damian on 26.10.2017.
 */

public class GlobalClassContainerSingletone {


    private static GlobalClassContainerSingletone mInstance= null;
    protected GlobalClassContainerSingletone(){}

    public static synchronized GlobalClassContainerSingletone getInstance(){
        if(null == mInstance){
            mInstance = new GlobalClassContainerSingletone();
        }
        return mInstance;
    }




    public String session;
    public User user;

    public PrivateKey getPrivatekye() {
        return privatekye;
    }

    public void setPrivatekye(PrivateKey privatekye) {
        this.privatekye = privatekye;
    }

    private PrivateKey privatekye;
    //getter and setter
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
