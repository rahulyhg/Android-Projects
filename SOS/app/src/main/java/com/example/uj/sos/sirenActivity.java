package com.example.uj.sos;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class sirenActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private ImageButton ib;
    private TextView tv;
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siren);
        ib = findViewById(R.id.imageButton);
        mp = MediaPlayer.create(this, R.raw.police);
        tv = findViewById(R.id.textView);
    }

    public void playsiren(View v){
        if (flag == false) {
            mp.start();
            mp.setLooping(true);
            ib.setBackgroundColor(111111);
            flag = true;
            tv.setText("Click again to stop Whistle");

        }
        else{
            mp.stop();
            ib.setBackgroundColor(000000);
            flag = false;
            tv.setText("Click on the Above button to activate Whistle");

        }
    }



}
