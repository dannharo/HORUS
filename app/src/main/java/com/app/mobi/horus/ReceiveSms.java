package com.app.mobi.horus;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by rosario on 13/11/2016.
 */

public class ReceiveSms extends BroadcastReceiver {
    //Declaración de variables
    String contraseña ="123456";
    String mensaje="";
    String noTelefono = "3121884228";
    String latitud="";
    String longitud= "";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        Object[] objArr =(Object[])bundle.get("pdus");
        String sms = "";
        String smsBody ="";
        String originNumber = "";

        for(int i= 0; i<objArr.length; i++)
        {
            SmsMessage smsMsg = SmsMessage.createFromPdu((byte[])objArr[i]);
            smsBody = smsMsg.getMessageBody();
            originNumber = smsMsg.getDisplayOriginatingAddress();

        }
        if (originNumber.equals(noTelefono)) {
            //Se verifica que sea un mensaje de sensor alarma
            if(smsBody.contains("sensor alarm!"))
            {
                //Se muestra el cuadro de dialogo con la alarma

            }
           /* //Separamos la cadena para obtener la latitud y longitud
            try {

                String[] infoSms = smsBody.split("&");
                String latLon = infoSms[1].replace("q=", "");
                String[] infoUbicacion = latLon.split(",");
                latitud = infoUbicacion[0].trim();
                longitud = infoUbicacion[1].trim();
                sms = latitud + ", " + longitud;
            }
            catch (Exception e)
            {
                sms = "Ocurrió un error";
            }
            Toast.makeText(context, sms, Toast.LENGTH_LONG).show();*/


        }
    }

   
}

