package com.app.mobi.horus;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by rosario on 13/11/2016.
 */

public class ReceiveSms extends BroadcastReceiver {
    //Declaración de variables
    String mensaje = "";
    String noTelefono = "";
    String latitud = "";
    String longitud = "";
    private DBManager dbManager;
    ArrayList<String> telefonosGps = new ArrayList<String>();
    int totalGps=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        //Se obtienen los numeros de telefono de todos los gps dados de alta en la app
        dbManager = new DBManager(context);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = dbManager.fetchDispositivos();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                //Almacena en las variables la información
                telefonosGps.add(cursor.getString(cursor.getColumnIndex(HorusDB.T_D_NUMERO)));
                cursor.moveToNext();
                totalGps++;
            }
        }
        cursor.close();
        Bundle bundle = intent.getExtras();
        Object[] objArr = (Object[]) bundle.get("pdus");
        String smsBody = "";
        String originNumber = "";

        for (int i = 0; i < objArr.length; i++) {
            SmsMessage smsMsg = SmsMessage.createFromPdu((byte[]) objArr[i]);
            smsBody = smsMsg.getMessageBody();
            originNumber = smsMsg.getDisplayOriginatingAddress();

        }
        if (telefonosGps.size() >0)
        {
            //Se compara para ver si el mensaje pertenece a uno de los numeros del gps
            for (int i =0; i< telefonosGps.size(); i++)
            {
                //Si el mensaje pertenece a uno de los numeros del gps, se muestra el mapa
                if (originNumber.equals(telefonosGps.get(i))) {
                    //Toast.makeText(context, smsBody, Toast.LENGTH_LONG).show();
                    //Se verifica que sea un mensaje de sensor alarma
                    if (smsBody.contains("sensor alarm!")) {
                        //Se muestra el cuadro de dialogo con la alarma
                        Toast.makeText(context, "Alarma", Toast.LENGTH_LONG).show();
                        Intent intentAlert = new Intent(context,NotifyActivity.class);
                        intentAlert.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intentAlert);
                    }
                    else if(smsBody.contains("http://maps.google.com"))
                    {
                        try {
                            Toast.makeText(context, "Mapa", Toast.LENGTH_LONG).show();
                            //Se separa el mensaje para obtener la latitud y longitud
                            String[] infoSms = smsBody.split("&");
                            String latLon = infoSms[1].replace("q=", "");
                            String[] infoUbicacion = latLon.split(",");
                            latitud = infoUbicacion[0].trim();
                            longitud = infoUbicacion[1].trim();
                            //Se manda a llamar la clase del mapa y se muestra
                            Intent intMap = new Intent(context,MapAct.class);
                            intMap.putExtra("longitud", longitud);
                            intMap.putExtra("latitud", latitud);
                            intMap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            context.startActivity(intMap);
                        }
                        catch (Exception e) {
                            Toast.makeText(context, "OCurrió un error", Toast.LENGTH_LONG).show();
                        }

                    }
                    //terminamos el ciclo para que no continue buscando
                    break;
                }
                else
                {
                    continue;
                }
            }
        }


    }

}

