package com.example.uj.mebar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView age;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        age = findViewById(R.id.age);
        FirebaseUser cuFirebaseUser = mAuth.getCurrentUser();
        if(cuFirebaseUser!=null){
            startActivity(new Intent(this, Home.class));
            finish();
        }

    }

    public boolean login(){
        FirebaseUser cuFirebaseUser = mAuth.getCurrentUser();
        if(cuFirebaseUser==null){
            Intent intent = new Intent(this, Authentication.class);
            startActivity(intent);
            finish();
            return false;
        }
        return true;
        }

    public void checkAge(View v) {
        String c = age.getText().toString();
        int a = Integer.parseInt(age.getText().toString());
        if (a > 20) {
            boolean b = login();
            if(b)
            startActivity(new Intent(this, Home.class));
            finish();

        } else if( c.equals(null)) {
            Toast.makeText(this, "Enter age to enter!!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not permitted to enter!!!", Toast.LENGTH_SHORT).show();

        }
    }
}
