package com.example.uj.mebar;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Button mlogout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mlogout = findViewById(R.id.logout);


        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                gotoAuthentication();

            }
        });

    }

    private void gotoAuthentication() {
        startActivity(new Intent(this,Authentication.class));
        finish();
    }

    public void wine(View v){
        Toast.makeText(this, "Only Beer is Available today", Toast.LENGTH_SHORT).show();
    }


    public void snacks(View v){
        Toast.makeText(this, "Only Beer is Available today", Toast.LENGTH_SHORT).show();

    }


    public void spirits(View v){
        Toast.makeText(this, "Only Beer is Available today", Toast.LENGTH_SHORT).show();

    }

    public void beer(View v){
        startActivity(new Intent(this, Beer.class));
    }

    public void frag(View v){
        startActivity(new Intent(this,ItemFragment.class));
    }
}
