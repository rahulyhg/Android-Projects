package com.example.uj.mebarvendor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private EditText memail;
    private EditText mpass;
    private EditText mcpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        memail = findViewById(R.id.registeremail);
        mpass = findViewById(R.id.regpass);
        mcpass = findViewById(R.id.confirmpass);

    }

    public void register(View v){
        String email = memail.getText().toString();
        String pass = mpass.getText().toString();
        String cpass = mcpass.getText().toString();
        if(pass.equals(cpass) && pass.length()>5){
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                               startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        else if (pass.length()<6){
            Toast.makeText(getApplicationContext(), "Password must be of 6 Character", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Password Does not match!!", Toast.LENGTH_SHORT).show();

        }
    }
}
