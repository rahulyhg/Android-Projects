package com.example.uj.mebar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Authentication extends AppCompatActivity {

    private EditText phone;
    private EditText otp;
    private Button verify;
    private Button send;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private ProgressBar pbar;
    private ProgressBar pbar2;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        phone = findViewById(R.id.phone);
        otp = findViewById(R.id.otp);
        pbar = findViewById(R.id.progressBar);
        pbar2 = findViewById(R.id.progressBar2);
        verify = findViewById(R.id.button4);
        send = findViewById(R.id.button3);
        mAuth = FirebaseAuth.getInstance();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbar.setVisibility(View.VISIBLE);
                String phon = phone.getText().toString();
                String phone1 = "+91"+phon;
                otp.setVisibility(View.VISIBLE);
                verify.setVisibility(View.VISIBLE);
                phone.setEnabled(false);
                send.setEnabled(false);
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phone1,60, TimeUnit.SECONDS,Authentication.this, mCallbacks);
                }

        });



        mCallbacks =  new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                otp.setText("AUTO VERIFIED");
                signInWithPhoneAuthCredential(phoneAuthCredential);
                Toast.makeText(Authentication.this, "Code Auto Verified", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Authentication.this, "Error is"+e, Toast.LENGTH_SHORT).show();
                Log.i("Error",""+e);
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                mVerificationId = verificationId;
                mResendToken = token;
                pbar.setVisibility(View.INVISIBLE);

            }
        };

    }

    public void verifycode(View v) {
        String otp1 = otp.getText().toString();
        verifyPhoneNumberWithCode(mVerificationId,otp1);
        pbar2.setVisibility(View.VISIBLE);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(Authentication.this, Home.class));
                            finish();
                            FirebaseUser user = task.getResult().getUser();
                            pbar2.setVisibility(View.VISIBLE);
                            // ...
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Authentication.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }
}
