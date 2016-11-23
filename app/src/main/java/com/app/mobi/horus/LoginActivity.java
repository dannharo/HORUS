package com.app.mobi.horus;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText usuario, contrasena;
    TextView mnsError;
    String user ="";
    String pass ="";
    private DBManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Iniciar servicio para recibir mensajes
        startService(new Intent(this, ServiceCommunicator.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbmanager = new DBManager(this);
        try {
            dbmanager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Se obtiene la información ingresada por el usuario
        usuario = (EditText) findViewById(R.id.editUserLogin);
        contrasena = (EditText) findViewById(R.id.editPassLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        mnsError =(TextView)findViewById(R.id.textMensaje);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mnsError.setVisibility(View.INVISIBLE);
                //Almacena en las variables los datos ingresados por el usuario
                user = usuario.getText().toString();
                pass = contrasena.getText().toString();
                //Se comparan los datos ingresados por el usuario con los guardados en la base de datos
                String resultado = dbmanager.getPassLogin(user);
                if (resultado != "NOT EXIST") {
                    Toast.makeText(getApplicationContext(),
                            "Datos correctos", Toast.LENGTH_SHORT).show();
                    //Se abre la nueva ventana, con el listado de dispositivos
                    Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                    startActivityForResult(myIntent, 0);
                } else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    mnsError.setVisibility(View.VISIBLE);
                    mnsError.setBackgroundColor(Color.RED);
                }
            }
        });

    }

}
