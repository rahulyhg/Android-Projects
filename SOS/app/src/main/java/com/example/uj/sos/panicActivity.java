package com.example.uj.sos;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class panicActivity extends AppCompatActivity {

    String[] emergency;
    private MediaPlayer mp;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic);
        tv = findViewById(R.id.textView4);
        emergency = getContactList();
        sendMessage();
        if (emergency != null) {
            mp = MediaPlayer.create(this, R.raw.police);
            mp.start();
            mp.setLooping(true);

        }
    }


    public void stoppanic(View v){
        mp.stop();
        tv.setText("Service Stopped!!");
    }


    public String[] getContactList(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String contactno = sharedPreferences.getString("Contact",null);
        if (contactno == null){
            return null;
        }
        return convertStringToArray(contactno);
    }


    private static String[] convertStringToArray(String str){
        String[] arr = str.split(",");
        return arr;
    }


    public void sendMessage(){
        SmsManager manager = SmsManager.getDefault();
        if (emergency != null) {
            for (int i = 0; i < emergency.length; i++) {
                manager.sendTextMessage(emergency[i], null, "Emergency, Please Help!!!", null, null);
            }
        }else{
            Toast.makeText(this, "Add Emergency Contact Number To Proceed.", Toast.LENGTH_SHORT).show();
        }
    }
}