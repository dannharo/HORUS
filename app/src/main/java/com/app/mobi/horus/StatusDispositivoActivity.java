package com.app.mobi.horus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class StatusDispositivoActivity extends AppCompatActivity {

    TextView bateria, gps, gsm, arm, mensajeActualizar;
    ImageView imgBateria, imgGps, imgGsm, imgArm;
    Button btnActualizar;
    Mensaje sms = new Mensaje(this);
    private int _id = MenuDispositivoActivity._id;
    private String telefono = MenuDispositivoActivity.telGps;
    private String contrasena = MenuDispositivoActivity.contrasena;
    private String mensaje ="";
    private String infoBateria, infoGps, infoGsm, infoArm;
    private DBManager dbManager;
    BroadcastReceiver receiver = null;
    private boolean banderaEstatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_dispositivo);

        mensajeActualizar = (TextView)findViewById(R.id.textMensajeActualizar);
        bateria = (TextView)findViewById(R.id.textPorcenBateria);
        gps = (TextView)findViewById(R.id.textStatusGps);
        gsm = (TextView)findViewById(R.id.textStatusGsm);
        arm = (TextView)findViewById(R.id.textStatusArm);

        //ImageView
        imgBateria = (ImageView)findViewById(R.id.imgBateria);
        imgGps = (ImageView)findViewById(R.id.imgGps);
        imgGsm = (ImageView)findViewById(R.id.imgGsm);
        imgArm = (ImageView)findViewById(R.id.imgArm);

        //Obtiene los datos actuales almacenados en la bd
        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = dbManager.fetchEstadoDispositivo(_id);
        if(cursor.moveToFirst()){
            mensajeActualizar.setText("");
            while(!cursor.isAfterLast()){
                //Almacena en las variables la información
                infoArm = cursor.getString(cursor.getColumnIndex(HorusDB.T_E_ARM));
                infoGps = cursor.getString(cursor.getColumnIndex(HorusDB.T_E_GPS));
                infoGsm =cursor.getString(cursor.getColumnIndex(HorusDB.T_E_GSM));
                infoBateria = cursor.getString(cursor.getColumnIndex(HorusDB.T_E_BATERIA));
                cursor.moveToNext();
            }
            muestraValores(infoArm, infoGps, infoBateria, infoGsm);
        }
        else
        {
           mensajeActualizar.setText("La información no esta actualizada.\n De click en el botón.");
        }
        cursor.close();

       btnActualizar =(Button) findViewById(R.id.btnActualizaStatus);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Envia mensaje para obtener el status del dispositivo
                mensaje="check"+contrasena;
                sms.enviarMensaje(telefono, mensaje);
            }
        });

        //Leer mensaje
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arr0, Intent arr1) {
                processReceive(arr0, arr1);
            }
        };
        registerReceiver(receiver, filter);
    }

    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void muestraValores(String infoArm, String infoGps, String infoBateria, String infoGsm)
    {
        //Muestra en las etiquetas los valores
        bateria.setText(infoBateria);
        gsm.setText(infoGsm);
        //Muestra el icono que corresponda
        if(infoBateria.equals("100%"))
        {
            imgGps.setImageResource(0);
            imgBateria.setBackgroundResource(R.drawable.full_battery);
        }
        else if(infoBateria.equals("75%"))
        {
            imgGps.setImageResource(0);
            imgBateria.setBackgroundResource(R.drawable.charged_battery);
        }
        else if(infoBateria.equals("50%"))
        {
            imgGps.setImageResource(0);
            imgBateria.setBackgroundResource(R.drawable.half_battery);
        }
        else if(infoBateria.equals("25%"))
        {
            imgGps.setImageResource(0);
            imgBateria.setBackgroundResource(R.drawable.full_battery);
        }
        else if(infoBateria.equals("15%"))
        {
            imgGps.setImageResource(0);
            imgBateria.setBackgroundResource(R.drawable.low_battery);
        }
        else if(infoBateria.equals("5%"))
        {
            imgGps.setImageResource(0);
            imgBateria.setBackgroundResource(R.drawable.empty_battery);
        }
        else
        {
            imgGps.setImageResource(0);
            imgBateria.setBackgroundResource(R.drawable.charged_battery);
        }
        if(infoGps.equals("ON"))
        {
            imgGps.setImageResource(0);
            imgGps.setBackgroundResource(R.drawable.marker_on);
            gps.setText("Activado");
        }
        else
        {
            imgGps.setImageResource(0);
            imgGps.setBackgroundResource(R.drawable.marker_off);
            gps.setText("Desactivado");
        }
        if(infoArm.equals("ON"))
        {
            imgArm.setImageResource(0);
            imgArm.setBackgroundResource(R.drawable.lock);
            arm.setText("Armado");
        }
        else
        {
            imgArm.setImageResource(0);
            imgArm.setBackgroundResource(R.drawable.unlock);
            arm.setText("Desarmado");
        }
    }

    public String processReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        Object[] objArr =(Object[])bundle.get("pdus");
        String sms = "";

        for(int i= 0; i<objArr.length; i++)
        {
            SmsMessage smsMsg = SmsMessage.createFromPdu((byte[])objArr[i]);
            String smsBody = smsMsg.getMessageBody();
            String senderNumber = smsMsg.getDisplayOriginatingAddress();
            if(senderNumber.equals(telefono))
            {
                if (smsBody.contains("GPRS")) {
                    String[] infoSms = smsBody.split(":");
                    //Muestra los nuevos datos
                    String statusArm = infoSms[8];
                    String statusBateria= infoSms[1].replace("GPRS", "");
                    String statusGps = infoSms[3].replace("GSM", "");
                    String statusGsm = infoSms[4].replace("APN", "");
                    muestraValores(statusArm, statusGps, statusBateria, statusGsm);
                    //Guarda los valores en la tabla
                    dbManager.updateEstadoDispositivo(_id, statusBateria, statusGps, statusArm, statusGsm);
                }

            }
        }
        return (sms);
    }
}
