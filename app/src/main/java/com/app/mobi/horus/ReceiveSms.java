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
    String contraseña = "123456";
    String mensaje = "";
    String noTelefono = "3121884228";
    String latitud = "";
    String longitud = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] objArr = (Object[]) bundle.get("pdus");
        String sms = "";
        String smsBody = "";
        String originNumber = "";

        for (int i = 0; i < objArr.length; i++) {
            SmsMessage smsMsg = SmsMessage.createFromPdu((byte[]) objArr[i]);
            smsBody = smsMsg.getMessageBody();
            originNumber = smsMsg.getDisplayOriginatingAddress();

        }
        if (originNumber.equals(noTelefono)) {
            //Toast.makeText(context, smsBody, Toast.LENGTH_LONG).show();
            //Se verifica que sea un mensaje de sensor alarma
            if (smsBody.contains("sensor alarm!")) {
                //Se muestra el cuadro de dialogo con la alarma
                Intent intentAlert = new Intent(context, NotifyActivity.class);
                context.startActivity(intentAlert);
            }
            else
            {
              // try {

                    String[] infoSms = smsBody.split("&");
                    String latLon = infoSms[1].replace("q=", "");
                    String[] infoUbicacion = latLon.split(",");
                    latitud = infoUbicacion[0].trim();
                    longitud = infoUbicacion[1].trim();
                    sms = latitud + ", " + longitud;


                    //Abrimos el layout del mapa
                  /*  Intent intentMapa = new Intent(context.getApplicationContext(), MapAct.class);
                    intentMapa.putExtra("longitud", longitud);
                    intentMapa.putExtra("latitud", latitud);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentMapa);*/

                Intent intentMapa = new Intent();
                    intentMapa.setClassName("com.app.mobi.horus", "com.app.mobi.horus.MapAct");

                    intentMapa.putExtra("longitud", longitud);
                    intentMapa.putExtra("latitud", latitud);
                Toast.makeText(context, sms, Toast.LENGTH_LONG).show();
                    intentMapa.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intentMapa);

           /*     Intent i = new Intent();
                i.setClassName("com.app.mobi.horus", "com.app.mobi.horus.MapAct");
                //Intent i = new Intent(context, MapAct.class);
                i.putExtra("longitud", longitud);
                i.putExtra("latitud", latitud);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(i);*/
             /*   }
                catch (Exception e)
                {
                    sms = "Ocurrió un error";
                }*/
                //Toast.makeText(context, sms, Toast.LENGTH_LONG).show();

            }
        }

    }

}

