package com.example.uj.smsmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle = intent.getExtras();
        Object[] p = (Object[]) bundle.get("p");
        SmsMessage message = SmsMessage.createFromPdu((byte[])p[0]);
        Toast.makeText(context, "Sms", Toast.LENGTH_SHORT).show();
        Log.i("com",message.getOriginatingAddress());
    }
}
