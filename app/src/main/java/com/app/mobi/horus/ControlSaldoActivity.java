package com.app.mobi.horus;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class ControlSaldoActivity extends AppCompatActivity {
    private int _id = MenuDispositivoActivity._id;
    String saldoActual="0.0", stringTotalSaldo, stringCostoSms;
    TextView textTotalSaldo;
    Button btnGuardar;
    EditText editSaldoIngresado, editCostoSms;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_saldo);
        //Obtiene los datos actuales almacenados en la bd
        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        textTotalSaldo = (TextView)findViewById(R.id.textSaldoActual);
        editCostoSms = (EditText)findViewById(R.id.editSmsCosto);
        Cursor cursor = dbManager.fetchSaldo(_id);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                //Almacena en las variables la información
                saldoActual = cursor.getString(cursor.getColumnIndex(HorusDB.T_S_SALDO));
                stringCostoSms = cursor.getString(cursor.getColumnIndex(HorusDB.T_S_COSTO_SMS));
                cursor.moveToNext();
            }
        }
        cursor.close();
        //Muestra la información
        textTotalSaldo.setText("$"+saldoActual);
        editCostoSms.setText(stringCostoSms);
        editSaldoIngresado = (EditText)findViewById(R.id.editSaldoIngresado);

        btnGuardar =(Button) findViewById(R.id.btnGuardaSaldo);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Guarda los valores ingresados por el usuario
                //Se obtienen los datos de los editText
                stringCostoSms = editCostoSms.getText().toString();
                stringTotalSaldo = editSaldoIngresado.getText().toString();

                //Se valida que ningun campo este vacio
                 if (TextUtils.isEmpty(stringTotalSaldo)) {
                    editSaldoIngresado.setError("El campo es obligatorio");
                    return;
                }
                else if (TextUtils.isEmpty(stringCostoSms)) {
                    editCostoSms.setError("El campo es obligatorio");
                    return;
                }else {
                    Float saldoIngresado = Float.parseFloat(stringTotalSaldo);
                    Float costoSms =Float.parseFloat(stringCostoSms);
                    Float saldoTotal = Float.parseFloat(saldoActual)+ saldoIngresado;
                    dbManager.updateSaldoDispositivo(_id, saldoTotal, costoSms);
                    textTotalSaldo.setText(String.valueOf(saldoTotal));
                }


            }
        });
    }
}
