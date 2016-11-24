package com.app.mobi.horus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.SQLException;

public class DeviceCrudActivity extends AppCompatActivity {

    Button btnMapa,btnEditar,btnEliminar;
    EditText textNombre,textNumero,textIMEI,textPassword;
    private DBManager dbManager;
    //Variable que almacena el id del dispositivo seleccionado
    public static int _id;
    String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_crud);
        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Se almacena el id del dispositivo que fue enviado
        Intent intent = getIntent();
        id = intent.getStringExtra("id_device");
        _id = Integer.parseInt(id);
        Cursor cursor = dbManager.fetchDispositivo(_id);
        textNombre = (EditText)findViewById(R.id.editNombreDisp);
        textNumero = (EditText)findViewById(R.id.editNumeroDisp);
        textIMEI = (EditText)findViewById(R.id.editIMEIDisp);
        textPassword = (EditText)findViewById(R.id.editPassDisp);
        //textNombre.setText(id);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                textNombre.setText(cursor.getString(cursor.getColumnIndex(HorusDB.T_D_NOMBRE)));
                textNumero.setText(cursor.getString(cursor.getColumnIndex(HorusDB.T_D_NUMERO)));
                textIMEI.setText(cursor.getString(cursor.getColumnIndex(HorusDB.T_D_IMEI)));
                textPassword.setText(cursor.getString(cursor.getColumnIndex(HorusDB.T_D_PASSWORD)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        btnMapa =(Button) findViewById(R.id.btnOptMapa);
        btnEditar = (Button) findViewById(R.id.btnOptEditar);
        btnEliminar = (Button) findViewById(R.id.btnOptEliminar);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DeviceCrudActivity.this, MapAct.class);
                startActivity(intent);
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeviceCrudActivity.this);
                //Metodos del constructor
                builder.setTitle("Eliminar Registro");
                builder.setMessage("¿Desea borrar el registro?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.deleteDispositivo(_id);
                        Intent intent = new Intent(DeviceCrudActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(DeviceCrudActivity.this, "Se borró el registro", Toast.LENGTH_SHORT ).show();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeviceCrudActivity.this);
                //Metodos del constructor
                builder.setTitle("Editar Registro");
                builder.setMessage("¿Desea guardar los cambios?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.updateDispositivo(_id, textNombre.getText().toString(), textNumero.getText().toString(), textIMEI.getText().toString(),textPassword.getText().toString());
                        Intent intent = new Intent(DeviceCrudActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(DeviceCrudActivity.this, "Se editó el registro", Toast.LENGTH_SHORT ).show();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
