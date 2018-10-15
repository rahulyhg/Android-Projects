package com.example.uj.sos;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void sirenactivity(View v){
        startActivity(new Intent(this, sirenActivity.class));

    }
    public void whistleactivity(View v){
        startActivity(new Intent(this, whistleActivity.class));

    }

    public void addcontact(View v){
        startActivity(new Intent(this, addcontactActivity.class));

    }

    public void panic(View v){
        startActivity(new Intent(this, panicActivity.class));
    }
}
