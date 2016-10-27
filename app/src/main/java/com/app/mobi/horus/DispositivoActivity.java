package com.app.mobi.horus;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DispositivoActivity extends AppCompatActivity {

    Mensaje sms = new Mensaje(this);
    //Declaración de variables
    String contraseña, noTelGps;
    String mensaje;

    EditText nombreDisp, noTelDisp, passDisp;
    Button btnGuardaDisp;
    String nombreDispText, notTelDispText, passDispText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivo);
        nombreDisp = (EditText)findViewById(R.id.editNombre);
        noTelDisp = (EditText)findViewById(R.id.editTelefono);
        passDisp = (EditText)findViewById(R.id.editContrasena);
        btnGuardaDisp = (Button)findViewById(R.id.btnGuardaDisp);
        btnGuardaDisp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Se obtienen los datos de los editText
               nombreDispText = nombreDisp.getText().toString();
                notTelDispText = noTelDisp.getText().toString();
                passDispText = passDisp.getText().toString();
                //Se valida que ningun campo este vacio
                if(TextUtils.isEmpty(nombreDispText))
                {
                    nombreDisp.setError("El nombre es obligatorio");
                    return;
                }
                if(TextUtils.isEmpty(notTelDispText))
                {
                    noTelDisp.setError("El número de teléfono es obligatorio");
                    return;
                }
                if (notTelDispText.trim().length() <10)
                {
                    noTelDisp.setError("Escriba un número de teléfono válido");
                    return;
                }
                if(TextUtils.isEmpty(passDispText))
                {
                    passDisp.setError("La contraseña es obligatoria");
                    return;
                }
                //Manda a llamar metodo de la clase mensaje
                //Enva mensaje para iniciar el dispositivo
                mensaje = "begin" + passDispText + " " ;
                sms.enviarMensaje(notTelDispText, mensaje);
            }
        });

    }
}
