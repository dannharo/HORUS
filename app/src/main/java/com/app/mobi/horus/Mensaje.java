package com.app.mobi.horus;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;
/**
 * Created by rosario on 09/09/2016.
 * Contiene metodos para enviar mensajes al gps, asi como  para
 * recibir los mensajes enviados por el gps
 */
public class Mensaje {

    /*static Context c;
    public Mensaje(Context context)
    {
        c= context;
    }*/
    public static void enviarMensaje(String telefono, String Mensaje)
    {
         String phoneNumber = "3121105454";
        String message = "Hola, esto es una prueba";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(
                phoneNumber,
                null,
                message,
                null,
                null);

        //Toast.makeText(c, "Mensaje enviado correctamente", Toast.LENGTH_LONG).show();
    }
}
