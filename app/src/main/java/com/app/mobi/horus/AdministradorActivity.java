package com.app.mobi.horus;

import android.content.Intent;
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
    String contraseña ="123456";
    String mensaje;
    String noTelGps = "3121105454";
    private DBManager dbmanager;

    EditText nombre, noTelefono;
    Button btnGuardarAdmin;
    String nombreText, telAdminText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        nombre = (EditText)findViewById(R.id.editNombreAdmin);
        noTelefono= (EditText)findViewById(R.id.editTelAdmin);
        dbmanager = new DBManager(this);
        try {
            dbmanager.open();
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
                    final int id_disp = 1;
                    dbmanager.insertAdministrador(id_disp,telefono,nombre);
                    Intent main = new Intent(AdministradorActivity.this,ListAdminActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                }
                //Manda a llamar metodo de la clase mensaje
                mensaje = "admin" + contraseña + " " + telAdminText;
                sms.enviarMensaje(noTelGps, mensaje);
            }
        });
    }
}
