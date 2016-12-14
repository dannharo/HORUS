package com.app.mobi.horus;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.SQLException;

/**
 * Created by Rosario on 29/08/2016.
 */
public class PopAlarma extends Activity{
    private DBManager dbmanager;
    private int idDisp = DeviceCrudActivity._id;
    private String contrasena  =DeviceCrudActivity.contrasena;
    private String telefono = DeviceCrudActivity.telGps;
    private static int alarmaSensor = DeviceCrudActivity.alarmSensor;
    private static int alarmaBateria = DeviceCrudActivity.alarmBateria;
    Mensaje sms = new Mensaje(this);
    String mensaje = "";
    ToggleButton toogleSensor;
    ToggleButton toogleBateria;
    @Override
    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.activity_alarma);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.6));
        //base de datos
        dbmanager = new DBManager(this);
        try {
            dbmanager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        toogleSensor = (ToggleButton)findViewById(R.id.toggleAlarmMov);
        toogleBateria = (ToggleButton)findViewById(R.id.toggleAlarmBat);
        //Establece el estado del toogleButton en On u Off de alarma de movimiento
        if (alarmaSensor == 1)
        {
            //Se muestra activada la alerta de movimiento
            toogleSensor.setChecked(true);

        }
        else
        {
            toogleSensor.setChecked(false);
        }
        //Establece el estado del toogleButton en On u Off de alarma de bateria
        if (alarmaBateria == 1)
        {
            //Se muestra activada la alerta de movimiento
            toogleBateria.setChecked(true);
        }
        else
        {
            toogleBateria.setChecked(false);
        }


    }

    public void toggleClickSensor(View view)
    {
        boolean on = ((ToggleButton) view).isChecked();
        if (on)
        {
            //Almacena en la base de datos el nuevo estado en 1, indica activado
            dbmanager.updateAlarmMov(idDisp, 1);
            alarmaSensor = 1;
            //Envia el mensaje para activar la alerta, shock+contraseña
            mensaje = "shock"+contrasena;
            sms.enviarMensaje(telefono, mensaje);
        }else
        {
            //Almacena en la base de datos el nuevo estado en 0, indica desactivado
            dbmanager.updateAlarmMov(idDisp, 0);
            alarmaSensor = 0;
            //Envia el mensaje para activar la alerta, noshock+contraseña
            mensaje = "noshock"+contrasena;
            sms.enviarMensaje(telefono, mensaje);
        }
    }
    public void toggleClickBateria(View view)
    {
        boolean on = ((ToggleButton) view).isChecked();
        if (on)
        {
            //Almacena en la base de datos el nuevo estado en 1, indica activado
            dbmanager.updateAlarmBat(idDisp, 1);
            alarmaBateria = 1;
            //Envia el mensaje para activar la alerta
            mensaje = "lowbattery" + contrasena + " on";
            sms.enviarMensaje(telefono, mensaje);
        }else
        {
            //Almacena en la base de datos el nuevo estado en 0, indica desactivado
            dbmanager.updateAlarmBat(idDisp, 0);
            alarmaBateria = 0;
            //Envia el mensaje para activar la alerta
            mensaje = "lowbattery" + contrasena + " off";
            sms.enviarMensaje(telefono, mensaje);
        }
    }

}
