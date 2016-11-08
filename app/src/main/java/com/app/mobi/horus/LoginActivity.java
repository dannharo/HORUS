package com.app.mobi.horus;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText usuario, contrasena;
    TextView mnsError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Se obtiene la información ingresada por el usuario
        usuario = (EditText) findViewById(R.id.editUserLogin);
        contrasena = (EditText) findViewById(R.id.editPassLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        mnsError =(TextView)findViewById(R.id.textMensaje);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mnsError.setVisibility(View.INVISIBLE);
                if (usuario.getText().toString().equals("admin") &&
                        contrasena.getText().toString().equals("admin")) {
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
