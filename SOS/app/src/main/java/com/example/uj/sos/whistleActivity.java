package com.example.uj.sos;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class whistleActivity extends AppCompatActivity {
    private MediaPlayer mp;
    private ImageButton ib;
    private static boolean flag = true;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whistle);
        ib = findViewById(R.id.whistlebtn);
        mp = MediaPlayer.create(this, R.raw.whistle);
        tv = findViewById(R.id.textView);
    }

    public void playsiren(View v){
        if (flag == true) {
            mp.start();
            mp.setLooping(true);
            ib.setBackgroundColor(111111);
            flag = false;
            tv.setText("Click again to stop Whistle");
        }
        else if(flag == false){
            mp.stop();
            ib.setBackgroundColor(000000);
            flag = true;
            tv.setText("Click on the Above button to activate Whistle");

        }
    }
}