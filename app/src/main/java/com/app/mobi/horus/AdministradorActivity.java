package com.app.mobi.horus;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLException;

public class AdministradorActivity extends AppCompatActivity {

    Mensaje sms = new Mensaje(this);
    //Declaración de variables
    String mensaje;
    private DBManager dbManager;

    EditText nombre, noTelefono;
    Button btnGuardarAdmin;
    String nombreText, telAdminText;
    public static int _id;
    String id="";
    String lastActivity ="";
    //Variables donde se almacenan los datos del dispositivo
    public static String telGps, contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Se almacena el id del dispositivo que fue enviado
        Intent intent = getIntent();
        id = intent.getStringExtra("idDisp");
        lastActivity = intent.getStringExtra("lastActivity");
        _id = Integer.parseInt(id);
        //Se obtienen los datos del dispositivo seleccionado
        Cursor cursor = dbManager.fetchDispositivo(_id);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                //Almacena en las variables la información
                telGps = cursor.getString(cursor.getColumnIndex(HorusDB.T_D_NUMERO));
                contrasena =cursor.getString(cursor.getColumnIndex(HorusDB.T_D_PASSWORD));
                cursor.moveToNext();
            }
        }
        cursor.close();

        nombre = (EditText)findViewById(R.id.editNombreAdmin);
        noTelefono= (EditText)findViewById(R.id.editTelAdmin);
        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btnGuardarAdmin = (Button)findViewById(R.id.btnGuardaAdmin);
        btnGuardarAdmin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nombreText = nombre.getText().toString();
                telAdminText = noTelefono.getText().toString();
                if(TextUtils.isEmpty(nombreText))
                {
                    nombre.setError("El nombre es obligatorio");
                    return;
                }
                else if(TextUtils.isEmpty(telAdminText))
                {
                    noTelefono.setError("El número de teléfono es obligatorio");
                    return;
                }
                else if (telAdminText.trim().length() <10)
                {
                    noTelefono.setError("Escriba un número de teléfono válido");
                    return;
                }
                else
                {
                    final String nombre = nombreText;
                    final String telefono = telAdminText;

                    dbManager.insertAdministrador(_id,telefono,nombre);
                    if(lastActivity.equals("Dispositivo"))
                    {
                        //Muestra el listado de dispositivos
                        Intent intent = new Intent(AdministradorActivity.this, MainActivity.class);
                        startActivityForResult(intent, 0);
                        finish();
                    }else
                    {
                        //Muestra el listado de administradores
                        Intent main = new Intent(AdministradorActivity.this,ListAdminActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(main);
                    }


                }
                //Manda a llamar metodo de la clase mensaje
                mensaje = "admin" + contrasena + " " + telAdminText;
                sms.enviarMensaje(telGps, mensaje);
            }
        });
    }
}
