package com.app.mobi.horus;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.sql.SQLException;

public class MenuDispositivoActivity extends AppCompatActivity {

    private String mensaje;
    Mensaje sms = new Mensaje(this);
    Button btnArmar, btnDesarmar, btnUbicacion, btnConfiguracion, btnEstatus, btnSaldo;
    String id="";
    private DBManager dbManager;
    //Variable que almacena el id del dispositivo seleccionado
    public static int _id;
    //Variables donde se almacenan los datos del dispositivo
    public static String nombre, telGps, contrasena;
    public static int alarmSensor, alarmBateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dispositivo);

        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Se almacena el id del dispositivo que fue enviado
        Intent intent = getIntent();
        id = intent.getStringExtra("id_device");
        _id = Integer.parseInt(id);
        //Se obtienen los datos del dispositivo seleccionado
        Cursor cursor = dbManager.fetchDispositivo(_id);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                //Almacena en las variables la información
                nombre = cursor.getString(cursor.getColumnIndex(HorusDB.T_D_NOMBRE));
                telGps = cursor.getString(cursor.getColumnIndex(HorusDB.T_D_NUMERO));
                contrasena =cursor.getString(cursor.getColumnIndex(HorusDB.T_D_PASSWORD));
                alarmSensor = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HorusDB.T_D_ALARMA_MOVIMIENTO)));
                alarmBateria = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HorusDB.T_D_ALARMA_BATERIA)));
                cursor.moveToNext();
            }
        }
        cursor.close();

        //Click en cada uno de los botones del menu
        btnArmar =(Button) findViewById(R.id.armarButton);
        btnArmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Envia mensaje para armar dispositivo;
                mensaje = "arm"+contrasena;
                sms.enviarMensaje(telGps, mensaje);
            }
        });
        btnDesarmar =(Button) findViewById(R.id.desarmarButton);
        btnDesarmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Envia mensaje para desarmar dispositivo;
                mensaje = "disarm"+contrasena;
                sms.enviarMensaje(telGps, mensaje);
            }
        });
        btnUbicacion =(Button) findViewById(R.id.ubicacionButton);
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuDispositivoActivity.this, MapAct.class);
                startActivity(intent);
            }
        });
        btnConfiguracion =(Button) findViewById(R.id.configuracionButton);
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuDispositivoActivity.this, ConfiguracionActivity.class);
                intent.putExtra("activity", "mapa");
                startActivity(intent);
            }
        });
        btnEstatus =(Button) findViewById(R.id.statusButton);
        btnEstatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuDispositivoActivity.this, StatusDispositivoActivity.class);
                startActivity(intent);
            }
        });
        btnSaldo =(Button) findViewById(R.id.saldoButton);
        btnSaldo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuDispositivoActivity.this, ControlSaldoActivity.class);
                startActivity(intent);
            }
        });
    }
}
