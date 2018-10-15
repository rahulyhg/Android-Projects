package com.example.uj.lastloc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private EditText edEmail;
    private EditText edPass;
    private TextView mRegister;
    private Button mLogin;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");

    private String mEmail;
    private String mPass;
    String currentUser,uid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edEmail = findViewById(R.id.email);
        edPass = findViewById(R.id.pass);
        mRegister = findViewById(R.id.register);
        mLogin = findViewById(R.id.Loginbtn);


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    uid = user.getUid();
                    currentUser = firebaseAuth.getCurrentUser().getEmail();
                    Intent myintent = new Intent(MainActivity.this, PlayGame.class);
                    myintent.putExtra("currentUser",currentUser);
                    myintent.putExtra("uid",uid);
//                    Toast.makeText(MainActivity.this, "Already Logged in  "+currentUser, Toast.LENGTH_LONG).show();
                    myRef.child("Users").child(beforeAt(currentUser)).child("Request").setValue(uid);
                    startActivity(myintent);


                }
            }
        };


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    String beforeAt(String Email) {
        String[] nSplit = Email.split("@");
        return nSplit[0];
    }



    public void btlogin(View view) {
        mEmail = edEmail.getText().toString();
        mPass = edPass.getText().toString();
        mAuth.signInWithEmailAndPassword(mEmail, mPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent myintent = new Intent(getApplicationContext(),PlayGame.class);
                            myintent.putExtra("currentUser",currentUser);
                            startActivity(myintent);
//                            Toast.makeText(getApplicationContext(), "Login  "+currentUser, Toast.LENGTH_LONG).show();

//                            myRef.child(beforeAt(mEmail)).child("Request").setValue(currentUser);
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
