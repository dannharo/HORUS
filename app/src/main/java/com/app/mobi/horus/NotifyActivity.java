package com.app.mobi.horus;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

public class NotifyActivity extends Activity {

    private MediaPlayer mp;
    private String lat, lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Intent, obtiene los datos enviados por la clase ReceiveSms
        Intent objIntent = this.getIntent();
        lat = objIntent.getStringExtra("latitud");
        lon = objIntent.getStringExtra("longitud");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //bloquea el boton atras del celular
        builder.setCancelable(false);
        //Metodos del constructor
        builder.setTitle("Alarma de movimiento");
        builder.setMessage("Mensaje de prueba para la alerta de movimiento!");
        builder.setPositiveButton("Ver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Abre el mapa mostrando la ubicación actual del dispositivo
                Intent intMap = new Intent(NotifyActivity.this, MapAct.class);
                intMap.putExtra("longitud", lon);
                intMap.putExtra("latitud", lat);
                intMap.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intMap);
                //Cierra todas las activity
                finish();
            }
        });
        builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        });
        //Se reproduce el sonido de la alerta
        mp = MediaPlayer.create(NotifyActivity.this, R.raw.alerta1);
        mp.start();
        //se crea y muestra el cuadro de dialogo
        Dialog dialog = builder.create();
        dialog.show();
    }
  /*@Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

      AlertDialog.Builder builder =
              new AlertDialog.Builder(getActivity());

      builder.setMessage("Esto es un mensaje de alerta.")
              .setTitle("Información")
              .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                      dialog.cancel();
                  }
              });

      return builder.create();
  }*/

}
