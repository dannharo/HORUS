package com.app.mobi.horus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by rosario on 13/11/2016.
 */

public class ReceiveSms extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        Object[] objArr =(Object[])bundle.get("pdus");
        String sms = "";

        for(int i= 0; i<objArr.length; i++)
        {
            SmsMessage smsMsg = SmsMessage.createFromPdu((byte[])objArr[i]);
            String smsBody = smsMsg.getMessageBody();
            String senderNumber = smsMsg.getDisplayOriginatingAddress();
            sms+="From: "+senderNumber + "\nContent: "+ smsBody+"\n";
            //MainActivity.mp.start();
        }
        Toast.makeText(context, sms, Toast.LENGTH_SHORT).show();
       // Toast.makeText(ReceiveSms.this, sms, Toast.LENGTH_SHORT).show();
        //return (sms);
    }
}

