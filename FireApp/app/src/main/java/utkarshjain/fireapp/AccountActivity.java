package utkarshjain.fireapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class AccountActivity extends AppCompatActivity {

    private Button mlogout;
    private Button mRecord;
    private TextView mStatusText;
    private MediaRecorder mRecorder;
    private static String mFileName = null;
    private static final String LOG_TAG = "Recording Tag";
    private StorageReference mReference;
    private ProgressDialog mdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mdialog = new ProgressDialog(this);
        mReference = FirebaseStorage.getInstance().getReference();
        mlogout = (Button) findViewById(R.id.logout);
        mRecord = (Button) findViewById(R.id.recordbtn);
        mStatusText = (TextView) findViewById(R.id.statustext);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();

        mFileName += "/Recording_audio.3gp";


        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
            }
        });

        mRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                   startRecording();
                    mStatusText.setText("Recording");
                }

                else if(event.getAction() == MotionEvent.ACTION_UP){
                    stopRecording();
                    mStatusText.setText("Recording Stopped");
                }

                return false;
            }
        });
    }


    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        uploadrecording();
        mdialog.setMessage("Uploading....");
        mdialog.show();
    }

    private void uploadrecording() {

        StorageReference filepath = mReference.child("Audio").child(mFileName);
        Uri uri = Uri.fromFile(new File(mFileName));
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mdialog.dismiss();
                Toast.makeText(AccountActivity.this, "Audio uploaded", Toast.LENGTH_LONG).show();

            }
        });

    }
}
