package utkarshjain.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView mUsername;
    private TextView mPassword;
    private Button mLoginBtn;
    private SignInButton mgoogleloginbtn;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleA;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button mSignup;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private LoginButton fbloginButton;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = (TextView) findViewById(R.id.username);
        mPassword = (TextView) findViewById(R.id.password);
        mLoginBtn = (Button) findViewById(R.id.loginbtn);
        mgoogleloginbtn = (SignInButton) findViewById(R.id.googleloginbtn);
        mSignup = (Button) findViewById(R.id.signup);
        fbloginButton = (LoginButton) findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);

// *************--- Get the keyHash ---**********************
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "utkarshjain.instagram",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
// *************--- Get the keyHash ends ---**********************

//      ************-------Facebook login-----------***************

        fbloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackManager = CallbackManager.Factory.create();

                fbloginButton.setReadPermissions("email");
                // If using in a fragment
//        loginButton.setFragment(this);
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;

                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
                // Callback registration
                fbloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

                callbackManager = CallbackManager.Factory.create();

                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {

                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                            }
                        });

            }
        });

//      ************-------Facebook login Ends-----------***************
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, Account.class));
                }
            }
        };

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signin();
            }
        });


        //      ************-------Google login-----------***************
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        
        mGoogleA = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                     Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();   
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        mgoogleloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleA);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                startActivity(new Intent(MainActivity.this, Account.class));
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        } else {
                callbackManager.onActivityResult(requestCode, resultCode, data);
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

//    Login with Google

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, ""+user, Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        }

//  Signin with Email and Password
    private void signin() {

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        mProgressDialog.setMessage("Logging in");
        mProgressDialog.show();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            mProgressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();

        }else {
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
//                    mProgressDialog.dismiss();
                    if (task.isSuccessful()) {
                        startActivity(new Intent(MainActivity.this, Account.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

}
