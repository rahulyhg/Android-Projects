package utkarshjain.instagram;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPass;
    private Button mRegister;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (EditText) findViewById(R.id.rEmail);
        mPassword = (EditText) findViewById(R.id.rPass);
        mConfirmPass = (EditText) findViewById(R.id.rConPass);
        mRegister = (Button) findViewById(R.id.rRegister);
        mAuth = FirebaseAuth.getInstance();
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {

        String email = mEmail.getText().toString();
        String pass = mPassword.getText().toString();
        String conpass = mConfirmPass.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(conpass)) {

            Toast.makeText(Register.this, "Fields Are Empty", Toast.LENGTH_LONG).show();

        } else if (!pass.equals(conpass)) {

            Toast.makeText(Register.this, "Password and confirm pass does not matches", Toast.LENGTH_LONG).show();

        } else {

            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Register.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Register.this, "User sucessfully created", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

        }

    }
}