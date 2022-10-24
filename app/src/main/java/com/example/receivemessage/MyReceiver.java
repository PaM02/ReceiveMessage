package com.example.receivemessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class  MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG="SmsBroadcastReceiver";
    String msg,adresse;

    public void onReceive(Context context, Intent intent) {

        Log.i(TAG,"Intent Received:"+intent.getAction());

        if (intent.getAction()==SMS_RECEIVED){

            Bundle bundle = intent.getExtras();
            if (bundle!=null) {
                Object[] mypdu = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[mypdu.length];
                //long time = 0;
                for (int i = 0; i < mypdu.length; i++) {
                    //for building version>= api23
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        String format = bundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i], format);

                    } else {

                        messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                    }

                    msg = messages[i].getDisplayMessageBody();
                    adresse = messages[i].getDisplayOriginatingAddress();
                    //time = messages[i].getTimestampMillis();
                }

                Toast.makeText(context, "adresse:" + adresse + "msg:" + msg , Toast.LENGTH_SHORT).show();
            }

        }
    }
}
