package inteligenty_zamek.app_ik;

import android.app.Application;



public class SessionContainer extends Application {

    public String session;
    public User user;

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
