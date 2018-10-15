package com.example.uj.lastloc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private Button btnreg;
    private EditText username;
    private EditText pass;
    private EditText cpass;
    String uname;
    String password;
    String cPass ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username= findViewById(R.id.username);
        pass= findViewById(R.id.pass);
        cpass= findViewById(R.id.confirmpass);
        btnreg= findViewById(R.id.btnreg);
        mAuth = FirebaseAuth.getInstance();

    }



    public void register(View view) {
        getdetail();
        mAuth.createUserWithEmailAndPassword(uname, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "Some Error occur try Again.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    private void getdetail() {

        uname = username.getText().toString();
        password = pass.getText().toString();
        cPass = cpass.getText().toString();

    }

}
