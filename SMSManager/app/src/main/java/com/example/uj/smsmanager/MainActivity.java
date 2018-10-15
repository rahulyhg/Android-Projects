package com.example.uj.smsmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 }

 public void sendMessage(View view){

     SmsManager manager = SmsManager.getDefault();
     manager.sendTextMessage("+919053101727",null,"Pagal",null,null);

 }
}
