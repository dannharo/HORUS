package com.app.mobi.horus;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;

public class StatusDispositivoActivity extends AppCompatActivity {

    TextView bateria, gps, gsm, arm;
    Button btnActualizar;
    Mensaje sms = new Mensaje(this);
    private int _id = MenuDispositivoActivity._id;
    private String telefono = MenuDispositivoActivity.telGps;
    private String contrasena = MenuDispositivoActivity.contrasena;
    private String mensaje ="";
    private String infoBateria, infoGps, infoGsm, infoArm;
    private DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_dispositivo);

        //Obtiene los datos actuales almacenados en la bd
        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = dbManager.fetchEstadoDispositivo(_id);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                //Almacena en las variables la información
                infoArm = cursor.getString(cursor.getColumnIndex(HorusDB.T_E_ARM));
                infoGps = cursor.getString(cursor.getColumnIndex(HorusDB.T_E_GPS));
                infoGsm =cursor.getString(cursor.getColumnIndex(HorusDB.T_E_GSM));
                infoBateria = cursor.getString(cursor.getColumnIndex(HorusDB.T_E_BATERIA));
                cursor.moveToNext();
            }
        }
        cursor.close();

        bateria = (TextView)findViewById(R.id.textBateria);
        gps = (TextView)findViewById(R.id.textGps);
        gsm = (TextView)findViewById(R.id.textGsm);
        arm = (TextView)findViewById(R.id.textArm);
        //Muestra en las etiquetas los valores
        bateria.setText(infoBateria);
        gps.setText(infoGps);
        arm.setText(infoArm);
        gsm.setText(infoGsm);

        btnActualizar =(Button) findViewById(R.id.btnActualizaStatus);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Envia mensaje para obtener el status del dispositivo
                mensaje="check"+contrasena;
                sms.enviarMensaje(telefono, mensaje);
            }
        });
    }
}
