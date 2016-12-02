package com.app.mobi.horus;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by rosario on 09/09/2016.
 * Contiene metodos para enviar mensajes al gps, asi como  para
 * recibir los mensajes enviados por el gps
 */
public class Mensaje extends AppCompatActivity {

    static Context c;

    public Mensaje(Context context) {
        c = context;
        }
    public void enviarMensaje(String telefono, String Mensaje) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(
                telefono,
                null,
                Mensaje,
                null,
                null);

        Toast.makeText(c, "Mensaje enviado correctamente", Toast.LENGTH_LONG).show();
    }

    public void permisoMensaje() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            //Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
        } else {
            Toast.makeText(c, "Permission is  granted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission has been granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Permission has been denied or request cancelled", Toast.LENGTH_LONG).show();
            }
        }

    }
}
