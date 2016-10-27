package com.app.mobi.horus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

public class AdministradorActivity extends AppCompatActivity {

    Mensaje sms = new Mensaje(this);
    //Declaración de variables
    String contraseña ="123456";
    String mensaje;
    String noTelGps = "3121105454";

    EditText nombre, noTelefono;
    Button btnGuardarAdmin;
    String nombreText, telAdminText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        nombre = (EditText)findViewById(R.id.editNombreAdmin);
        noTelefono= (EditText)findViewById(R.id.editTelAdmin);
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
                if(TextUtils.isEmpty(telAdminText))
                {
                    noTelefono.setError("El número de teléfono es obligatorio");
                    return;
                }
                if (telAdminText.trim().length() <10)
                {
                    noTelefono.setError("Escriba un número de teléfono válido");
                    return;
                }
                //Manda a llamar metodo de la clase mensaje
                mensaje = "admin" + contraseña + " " + noTelefono;
                sms.enviarMensaje(noTelGps, mensaje);
            }
        });
    }
}
