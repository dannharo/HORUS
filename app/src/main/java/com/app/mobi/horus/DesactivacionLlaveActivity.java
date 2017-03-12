package com.app.mobi.horus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class DesactivacionLlaveActivity extends AppCompatActivity {
    private DBManager dbManager;
    EditText editNoVeces, editNoIntentos;
    String noVeces, noIntentos;
    //Variable que almacena el id del dispositivo seleccionado
    public static int _id;
    String id, mensaje, telGps, password;
    Button btnGuardar;
    Mensaje sms = new Mensaje(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desactivacion_llave);

        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        editNoIntentos = (EditText)findViewById(R.id.editIntentos);
        editNoVeces = (EditText)findViewById(R.id.editNoAct);

        //Se almacena el id del dispositivo que fue enviado
        Intent intent = getIntent();
        id = intent.getStringExtra("id_device");
        _id = Integer.parseInt(id);
        //Almacena la información del administrador
        Cursor cursor = dbManager.fetchDispositivo(_id);
        //textNombre.setText(id);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                //Almacena en las variables la información
                noVeces = cursor.getString(cursor.getColumnIndex(HorusDB.T_D_VECES_DESARMAR));
                noIntentos = cursor.getString(cursor.getColumnIndex(HorusDB.T_D_NUMERO_INTENTOS));
                telGps = cursor.getString(cursor.getColumnIndex(HorusDB.T_D_NUMERO));
                password = cursor.getString(cursor.getColumnIndex(HorusDB.T_D_PASSWORD));
                cursor.moveToNext();
            }
        }
        cursor.close();
        //Muestra en los edits la informacion obtenida
        editNoIntentos.setText(noIntentos);
        editNoVeces.setText(noVeces);

        btnGuardar = (Button)findViewById(R.id.btnGuardaLlave);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Se obtienen los datos de los editText
                noVeces = editNoVeces.getText().toString();
                noIntentos = editNoIntentos.getText().toString();

                //Se valida que ningun campo este vacio
                if (TextUtils.isEmpty(noVeces)) {
                    editNoVeces.setError("El campo es obligatorio");
                    return;
                } else if (TextUtils.isEmpty(noIntentos)) {
                    editNoIntentos.setError("El campo es obligatorio");
                    return;
                }  else {
                    Integer intNoIntentos = Integer.parseInt(noIntentos);
                    Integer intNoVeces =Integer.parseInt(noVeces);
                    dbManager.updateLlaveDispositivo(_id, intNoVeces, intNoIntentos);
                }
                //Manda a llamar metodo de la clase mensaje
                //Enva mensaje para activar la llave
                mensaje = "disarm"+password+" "+noVeces+" "+noIntentos;
                sms.enviarMensaje(telGps, mensaje);
            }
        });
    }
}
