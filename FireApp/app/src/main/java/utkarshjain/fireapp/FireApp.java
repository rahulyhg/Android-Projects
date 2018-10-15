package utkarshjain.fireapp;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Utkarsh Jain on 28-Nov-17.
 */

public class FireApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
