package com.app.mobi.horus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class ContrasenaActivity extends AppCompatActivity {

    Button btnGuardaPass;
    EditText editPassAct1, editPassAct2, editPassAnt;
    private String nuevoPass1, nuevoPass2, passAct;
    private DBManager dbmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena);

        dbmanager = new DBManager(this);
        try {
            dbmanager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnGuardaPass = (Button)findViewById(R.id.btnGuardaPass);
        editPassAct1 =(EditText)findViewById(R.id.editPassAct1);
        editPassAct2 =(EditText)findViewById(R.id.editPassAct2);
        editPassAnt =(EditText)findViewById(R.id.editPassAnt);

        btnGuardaPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Se obtiene la informacion ingresada por el usuario
                nuevoPass1 = editPassAct1.getText().toString();
                nuevoPass2 = editPassAct2.getText().toString();
                passAct = editPassAnt.getText().toString();
                //Se valida que ningun campo este vacio
                if(TextUtils.isEmpty(passAct))
                {
                    editPassAnt.setError("El campo es obligatorio");
                    return;
                }
                else if(TextUtils.isEmpty(nuevoPass1))
                {
                    editPassAct1.setError("El campo es obligatorio");
                    return;
                }
                else if(TextUtils.isEmpty(nuevoPass2))
                {
                    editPassAct2.setError("El campo es obligatorio");
                    return;
                }

                else
                {
                    //Se obtiene el pass actual y se compara con el ingresado por el usuario
                    String idUser = dbmanager.getPassword(passAct);
                    if (idUser.equals("NOT EXIST")) {
                        editPassAnt.setError("La contraseña no existe");
                        return;

                    }
                    //Se valida que el nuevo password coincida
                    else if (nuevoPass1.equals(nuevoPass2))
                    {
                        //Se guarda la nueva contraseña
                        dbmanager.updateUsuarios(Integer.parseInt(idUser), nuevoPass1);
                        Toast.makeText(ContrasenaActivity.this, "Contraeña editada correctamente", Toast.LENGTH_SHORT ).show();
                    }
                    else
                    {
                        Toast.makeText(ContrasenaActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT ).show();
                    }
                }


            }
        });

    }

}
