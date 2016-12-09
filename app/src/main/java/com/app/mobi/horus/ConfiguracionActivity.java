package com.app.mobi.horus;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity {

    private String opcionesConfiguracion[]=new String[]{"Cambiar contraseña", "Administradores", "Alarmas", "Armar", "Desarmar", "Reiniciar","Información"};
    private Integer[] iconOpcion={
            R.drawable.ic_action_secure,
            R.drawable.ic_action_person,
            R.drawable.ic_action_alarms,
            R.drawable.ic_action_merge,
            R.drawable.ic_action_import_export,
            R.drawable.ic_action_refresh,
            R.drawable.ic_action_about
    };
    private ListView lista;
    private String mensaje;
    Mensaje sms = new Mensaje(this);
    private String telefono = DeviceCrudActivity.telGps;
    private String contrasena = DeviceCrudActivity.contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this, MainActivity.currentLayout, Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        OpcionList adapter=new OpcionList(this,opcionesConfiguracion,iconOpcion);
        lista=(ListView)findViewById(R.id.listaConf);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem = opcionesConfiguracion[+position];
                //Dependiendo de la posicion muestra el layout correspondiente
                switch (Slecteditem)
                {
                    case "Cambiar contraseña":
                        Intent intentContrasena = new Intent(ConfiguracionActivity.this, ContrasenaActivity.class);
                        startActivity(intentContrasena);
                    break;
                    case "Administradores":
                        Intent intentAdmin = new Intent(ConfiguracionActivity.this, ListAdminActivity.class);
                        startActivity(intentAdmin);
                        break;
                    case "Alarmas":
                        Intent intentAlarma = new Intent(ConfiguracionActivity.this, PopAlarma.class);
                        startActivity(intentAlarma);
                        break;
                    case "Armar":
                        //Envia mensaje para armar dispositivo;
                        mensaje = "arm"+contrasena;
                        sms.enviarMensaje(telefono, contrasena);
                        break;
                    case "Desarmar":
                        //Envia mensaje para desarmar dispositivo
                        mensaje = "disarm"+contrasena;
                        sms.enviarMensaje(telefono, contrasena);
                        break;
                    case "Reiniciar":
                        //Envia mensaje para reiniciar el dispositivo
                        mensaje = "reset"+contrasena;
                        sms.enviarMensaje(telefono, mensaje);
                        break;
                    case "Información":
                        Intent intentInfo = new Intent(ConfiguracionActivity.this, InformacionActivity.class);
                        startActivity(intentInfo);
                        break;
                }
            }
        });
    }


}
