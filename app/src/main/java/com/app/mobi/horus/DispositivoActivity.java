package com.app.mobi.horus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class DispositivoActivity extends AppCompatActivity {

    Mensaje sms = new Mensaje(this);
    //Declaración de variables
    String contraseña, noTelGps;
    String mensaje;
    private DBManager dbmanager;
    EditText nombreDisp, noTelDisp, passDisp,imeiDisp;
    Button btnGuardaDisp;
    String nombreDispText, notTelDispText, passDispText;
    private ImageButton buttonEye;
    private boolean showPass = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivo);
        nombreDisp = (EditText)findViewById(R.id.editNombre);
        noTelDisp = (EditText)findViewById(R.id.editTelefono);
        passDisp = (EditText)findViewById(R.id.editContrasena);
        imeiDisp = (EditText)findViewById(R.id.editIMEI);
        buttonEye = (ImageButton)findViewById(R.id.imageEyeButton);
        dbmanager = new DBManager(this);
        try {
            dbmanager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnGuardaDisp = (Button)findViewById(R.id.btnGuardaDisp);
        btnGuardaDisp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Se obtienen los datos de los editText
                nombreDispText = nombreDisp.getText().toString();
                notTelDispText = noTelDisp.getText().toString();
                passDispText = passDisp.getText().toString();
                //Se valida que ningun campo este vacio
                if (TextUtils.isEmpty(nombreDispText)) {
                    nombreDisp.setError("El nombre es obligatorio");
                    return;
                } else if (TextUtils.isEmpty(notTelDispText)) {
                    noTelDisp.setError("El número de teléfono es obligatorio");
                    return;
                } else if (notTelDispText.trim().length() < 10) {
                    noTelDisp.setError("Escriba un número de teléfono válido");
                    return;
                } else if (TextUtils.isEmpty(passDispText)) {
                    passDisp.setError("La contraseña es obligatoria");
                    return;
                } else {
                    final String nombre = nombreDisp.getText().toString();
                    final String telefono = noTelDisp.getText().toString();
                    final String password = passDisp.getText().toString();
                    final String imei = imeiDisp.getText().toString();
                     dbmanager.insertDispositivo(nombre, telefono, imei, password, 0, 0);
                    //Intent main = new Intent(DispositivoActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(main);
                    String idInserted = dbmanager.getLastId();
                    //Inicia activity para agregar administrador
                    Intent intentAdmin = new Intent(DispositivoActivity.this, AdministradorActivity.class);
                    //Envia el id del dispositivo insertado
                    intentAdmin.putExtra("idDisp",idInserted);
                    intentAdmin.putExtra("lastActivity","Dispositivo");
                    startActivityForResult(intentAdmin, 0);
                    finish();
                }
                //Manda a llamar metodo de la clase mensaje
                //Enva mensaje para iniciar el dispositivo
                mensaje = "begin" + passDispText + " ";
                sms.enviarMensaje(notTelDispText, mensaje);
                mensaje = "sleep" + passDispText + " shock";
                sms.enviarMensaje(notTelDispText, mensaje);
            }
        });

        buttonEye.setVisibility(View.GONE);
        passDisp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passDisp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passDisp.getText().length() > 0) {
                    buttonEye.setVisibility(View.VISIBLE);
                } else {
                    buttonEye.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buttonEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPass) {
                    v.setBackgroundResource(R.drawable.ic_eye_off);
                    passDisp.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passDisp.setSelection(passDisp.length());
                }
                else
                {
                    v.setBackgroundResource(R.drawable.ic_eye);
                    passDisp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passDisp.setSelection(passDisp.length());
                }
                showPass = !showPass; // reverse
            }
        });
    }
}
