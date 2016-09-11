package com.app.mobi.horus;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by rosario on 09/09/2016.
 * Contiene metodos para enviar mensajes al gps, asi como  para
 * recibir los mensajes enviados por el gps
 */
public class Mensaje {

    static Context c;
    public Mensaje(Context context)
    {
        c= context;
    }
    public static void enviarMensaje(String telefono, String Mensaje)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(
                telefono,
                null,
                Mensaje,
                null,
                null);

        Toast.makeText(c, "Mensaje enviado correctamente", Toast.LENGTH_LONG).show();
    }

}
