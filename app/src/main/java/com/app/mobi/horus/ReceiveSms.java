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
    public static ArrayList<String> telefonosGps = new ArrayList<String>();
    public static ArrayList<String> idsGps = new ArrayList<String>();
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
                idsGps.add(cursor.getString(cursor.getColumnIndex(HorusDB.T_D_ID)));
                cursor.moveToNext();
            }
        }
        Bundle bundle = intent.getExtras();
        Object[] objArr = (Object[]) bundle.get("pdus");
        String smsBody = "";
        String originNumber = "";

        for (int i = 0; i < objArr.length; i++) {
            SmsMessage smsMsg = SmsMessage.createFromPdu((byte[]) objArr[i]);
            smsBody = smsMsg.getMessageBody();
            originNumber = smsMsg.getDisplayOriginatingAddress();

        }

         if (telefonosGps.size() >0) {
             //Se compara para ver si el mensaje pertenece a uno de los numeros del gps
             for (int i = 0; i < telefonosGps.size(); i++) {
                 //Si el mensaje pertenece a uno de los numeros del gps, se muestra el mapa
                 if (originNumber.equals(telefonosGps.get(i))) {
                     //Se obtiene el saldo actual del dispotitivo
                     Integer id = Integer.parseInt(idsGps.get(i));
                     Float saldoActual;
                     Float smsCosto;
                     Cursor c = dbManager.fetchSaldo(id);
                     if(c.moveToFirst()){
                         while(!c.isAfterLast()){
                             //Almacena en las variables la información
                             String stringSaldoActual = c.getString(c.getColumnIndex(HorusDB.T_S_SALDO));
                             String stringSmsCosto = c.getString(c.getColumnIndex(HorusDB.T_S_COSTO_SMS));
                             saldoActual= Float.parseFloat(stringSaldoActual);
                             smsCosto = Float.parseFloat(stringSmsCosto);
                             saldoActual = saldoActual-smsCosto;
                             //Actualiza el saldo actual
                             dbManager.updateSaldoDispositivo(id,saldoActual);
                             c.moveToNext();
                         }
                     }
                     //Toast.makeText(context, smsBody, Toast.LENGTH_LONG).show();
                     //Se verifica que sea un mensaje de sensor alarma
                     if (smsBody.contains("sensor alarm!")) {
                         //Se obtiene la latitud y longitud
                         String[] infoSms = smsBody.split("&");
                         String latLon = infoSms[1].replace("q=", "");
                         String[] infoUbicacion = latLon.split(",");
                         latitud = infoUbicacion[0].trim();
                         longitud = infoUbicacion[1].trim();
                         //Se muestra el cuadro de dialogo con la alarma
                         Intent intentAlert = new Intent(context, NotifyActivity.class);
                         intentAlert.putExtra("longitud", longitud);
                         intentAlert.putExtra("latitud", latitud);
                         intentAlert.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         context.startActivity(intentAlert);

                     } else if (smsBody.contains("http://maps.google.com")) {
                         try {
                             //Se separa el mensaje para obtener la latitud y longitud
                             String[] infoSms = smsBody.split("&");
                             String latLon = infoSms[1].replace("q=", "");
                             String[] infoUbicacion = latLon.split(",");
                             latitud = infoUbicacion[0].trim();
                             longitud = infoUbicacion[1].trim();
                             //Se manda a llamar la clase del mapa y se muestra
                             Intent intMap = new Intent(context, MapAct.class);
                             intMap.putExtra("longitud", longitud);
                             intMap.putExtra("latitud", latitud);
                             intMap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                             context.startActivity(intMap);
                         } catch (Exception e) {
                             Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show();
                         }

                     }
                     //terminamos el ciclo para que no continue buscando
                     break;
                 } else {
                     continue;
                 }
             }
         }
    }
}

