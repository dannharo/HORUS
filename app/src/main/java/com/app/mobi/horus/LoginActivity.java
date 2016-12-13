package com.app.mobi.horus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText usuario, contrasena;
    private TextView mnsError;
    private CheckBox checkBoxRecordar;
    //Para recordar el login
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean recuerdaLogin;

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
        checkBoxRecordar = (CheckBox)findViewById(R.id.checkGuardaLogin);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        recuerdaLogin = loginPreferences.getBoolean("recuerdaLogin",false);
        if (recuerdaLogin == true) {
            usuario.setText(loginPreferences.getString("user", ""));
            contrasena.setText(loginPreferences.getString("pass", ""));
            checkBoxRecordar.setChecked(true);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mnsError.setVisibility(View.INVISIBLE);
                //Almacena en las variables los datos ingresados por el usuario
                user = usuario.getText().toString();
                pass = contrasena.getText().toString();

                if (checkBoxRecordar.isChecked()) {
                    loginPrefsEditor.putBoolean("recuerdaLogin", true);
                    loginPrefsEditor.putString("user", user);
                    loginPrefsEditor.putString("pass", pass);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                //Se comparan los datos ingresados por el usuario con los guardados en la base de datos
                String resultado = dbmanager.getPassLogin(user);
                if (resultado != "NOT EXIST") {
                    //Compara el password
                    if (resultado.equals(pass)) {
                        //Se abre la nueva ventana, con el listado de dispositivos
                        Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                        startActivityForResult(myIntent, 0);
                    }
                    else
                    {
                        mnsError.setVisibility(View.VISIBLE);
                        mnsError.setBackgroundColor(Color.RED);
                    }
                } else {
                    mnsError.setVisibility(View.VISIBLE);
                    mnsError.setBackgroundColor(Color.RED);
                }
            }
        });

    }


}
