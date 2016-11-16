package com.app.mobi.horus;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.sql.SQLException;

public class DeviceCrudActivity extends AppCompatActivity {

    Button btnMapa;
    EditText textNombre,textNumero,textIMEI,textPassword;
    private DBManager dbManager;
    private int _id;
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
        Intent intent = getIntent();
        String id = intent.getStringExtra("id_device");
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
        btnMapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DeviceCrudActivity.this, MapAct.class);
                startActivity(intent);
            }
        });

    }
}
