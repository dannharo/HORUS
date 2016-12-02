package com.app.mobi.horus;

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

public class NotifyActivity extends DialogFragment {

    private MediaPlayer mp;
    private boolean alertIsOpen = false;
  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Metodos del constructor
        builder.setTitle("Alarma de movimiento");
        builder.setMessage("Mensaje de prueba para la alerta de movimiento!");
        builder.setPositiveButton("Ver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(NotifyActivity.this, "Acepto la alerta", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        //Se reproduce el sonido de la alerta
        mp = MediaPlayer.create(NotifyActivity.this, R.raw.alerta1);
        mp.start();
        if (alertIsOpen) return;
        alertIsOpen = true;
        //se crea y muestra el cuadro de dialogo
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void mostrarAlerta()
    {
        //Se crea el contructor de los cuadros de dialogo

    }*/
  @Override
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
  }

}
