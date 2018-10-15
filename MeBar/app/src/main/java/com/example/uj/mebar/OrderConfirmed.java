package com.example.uj.mebar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OrderConfirmed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,Home.class));
    }


    public void gotohome(View v){
        startActivity(new Intent(this, Home.class));
        finish();
    }
}
