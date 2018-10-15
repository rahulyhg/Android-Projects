package utkarshjain.fireapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private Button mSignin;
    private Button mImg;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorage;
    private static final int mintent = 2;
    private ProgressDialog mProgressDialog;
    private ProgressDialog mdownload;
    private ImageView mImage;
    private Button mCam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        mSignin = (Button)findViewById(R.id.signin);
        mImg = (Button) findViewById(R.id.img);
        mImage= (ImageView) findViewById(R.id.imageView);
        mProgressDialog = new ProgressDialog(this);
        mdownload = new ProgressDialog(this);
        mCam = (Button) findViewById(R.id.cam);


        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, mintent);
            }
        });

        mCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,mintent) ;
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, AccountActivity.class));
                }
            }
        };

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startsignin();
            }
        });

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == mintent && resultCode == RESULT_OK){
            Uri uri = data.getData();
            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
            mProgressDialog.setMessage("Uploading File....");
            mProgressDialog.show();
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.dismiss();
                    mdownload.setMessage("Downloading Image...");
                    mdownload.show();
                    Toast.makeText(MainActivity.this, "Uploading done", Toast.LENGTH_LONG).show();
                    @SuppressWarnings("VisibleForTests") Uri downloaduri = taskSnapshot.getDownloadUrl();
                    Picasso.with(MainActivity.this).load(downloaduri).fit().centerCrop().into(mImage);
                    mdownload.dismiss();
                    Toast.makeText(MainActivity.this, "Downloading done", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }



    private void startsignin(){

        String email = mEmail.getText().toString();
        String pass= mPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {

            Toast.makeText(MainActivity.this, "Fields Are Empty", Toast.LENGTH_LONG).show();

        }
            else{

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }
}