package com.example.uj.notificationapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        int notificationId = getIntent().getIntExtra("MessageAaya", 0);
//        NotificationManager manager = ( NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        manager.cancel(notificationId);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setSmallIcon(R.drawable.ic_launcher_background);
//        builder.setContentTitle("CounterService Stopped");
//        builder.setContentText("Counter stopped incrementing");
//        Intent intent = new Intent(this, ResultActivity.class);
//        int count = 0;
//        intent.putExtra("count",count);
//        PendingIntent pendingIntent = PendingIntent.getActivities(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);



    }

    public void firenotification(View view){

        NewMessageNotification newMessageNotification = new NewMessageNotification();
        newMessageNotification.notify(getApplicationContext(), "This is notification...... ",5);


    }
}
