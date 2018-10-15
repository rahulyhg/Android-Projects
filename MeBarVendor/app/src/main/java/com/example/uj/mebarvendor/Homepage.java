package com.example.uj.mebarvendor;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Homepage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button uploadImage;
    private ImageView imageView;
    private EditText mtitle;
    private EditText mfirm;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private EditText mDescription;
    private EditText mprice;
    private TextView UploadImage;

    private FirebaseAuth mAuth;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Button mMycart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        uploadImage =findViewById(R.id.uploadBtn);
        imageView =findViewById(R.id.imageprivew2);
        mtitle =findViewById(R.id.title);
        mprice = findViewById(R.id.price);
        mfirm = findViewById(R.id.firm);
        mDescription = findViewById(R.id.description);
        mProgressBar =findViewById(R.id.progressbar2);
        mAuth = FirebaseAuth.getInstance();
        mMycart = findViewById(R.id.mycart);
        UploadImage = findViewById(R.id.textView4);
        storageReference = FirebaseStorage.getInstance().getReference("images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Beer");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    openFileChooser();
                }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageUri == null) {
                    Toast.makeText(Homepage.this, "Choose Image", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImg();
                    uploadImage.setEnabled(false);
                }
            }
        });

        mMycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Beer.class));
            }
        });

    }

    public void logout(View v){
        mAuth.signOut();
        startActivity(new Intent(this,MainActivity.class));
    }

    private void uploadImg() {
        final FirebaseUser cuFirebaseUser = mAuth.getCurrentUser();
        if (mImageUri != null){
            StorageReference filestorageReference = storageReference.child(mtitle.getText().toString().trim()+"."+getFileExtension(mImageUri));
            filestorageReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    },500);
                    Toast.makeText(Homepage.this, "Upload Sucessfull", Toast.LENGTH_SHORT).show();
                    Upload upload = new Upload(mtitle.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString(),mDescription.getText().toString()
                            .trim(),Integer.parseInt(mprice.getText().toString().trim()),mfirm.getText().toString()
                    ,cuFirebaseUser.getEmail());
                    databaseReference.child(mtitle.getText().toString().trim()).setValue(upload);
                    uploadImage.setEnabled(true);

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Homepage.this, "Error in Uploading File", Toast.LENGTH_SHORT).show();
                    uploadImage.setText("Try Again");
                    uploadImage.setEnabled(true);
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    long progress = (100*taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int)progress);
                }
            });
        }else{
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }


    }

    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void showUploads(View v){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imageView);
            UploadImage.setVisibility(View.INVISIBLE);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
