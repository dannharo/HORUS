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

    private String opcionesConfiguracion[];
    //Array para almacenar los iconos de las opciones
    private Integer[] iconOpcion;
    private String activity ="";
    private ListView lista;
    private String mensaje;
    Mensaje sms = new Mensaje(this);
    private int idDisp = MenuDispositivoActivity._id;
    private String telefono = MenuDispositivoActivity.telGps;
    private String contrasena = MenuDispositivoActivity.contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Obtiene los datos enviados
        Intent objIntent = this.getIntent();
        activity = objIntent.getStringExtra("activity");
        if (activity.equals("mapa"))
        {
            //Array para almacenar las opciones del menu
            opcionesConfiguracion=new String[7];
            //Se agrega al array las opciones para el menu
            opcionesConfiguracion[0]="Editar dispositivo";
            opcionesConfiguracion[1]="Cambiar contraseña";
            opcionesConfiguracion[2]="Administradores";
            opcionesConfiguracion[3]="Alarmas";
            opcionesConfiguracion[4]="Reiniciar";
            opcionesConfiguracion[5]="Desactivación por llave";
            opcionesConfiguracion[6]="Información";
            //Iconos del menu
            iconOpcion = new Integer[7];
            iconOpcion[0] =  R.drawable.ic_border_color;
            iconOpcion[1] =  R.drawable.ic_action_secure;
            iconOpcion[2] =  R.drawable.ic_action_person;
            iconOpcion[3] =  R.drawable.ic_action_alarms;
            iconOpcion[4] =  R.drawable.ic_action_refresh;
            iconOpcion[5] =  R.drawable.ic_action_refresh;
            iconOpcion[6] =  R.drawable.ic_action_about;
        }
        else
        {
            opcionesConfiguracion=new String[2];
            //Se agrega al array las opciones para el menu en el home
            opcionesConfiguracion[0]="Cambiar contraseña";
            opcionesConfiguracion[1]="Información";
            //Iconos del menu
            iconOpcion = new Integer[2];
            iconOpcion[0] =  R.drawable.ic_action_secure;
            iconOpcion[1] =  R.drawable.ic_action_about;

        }
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
                    case "Editar dispositivo":
                        Intent intentEditar = new Intent(ConfiguracionActivity.this, DeviceCrudActivity.class);
                        intentEditar.putExtra("id_device",String.valueOf(idDisp));
                        startActivity(intentEditar);
                        break;
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
                    case "Reiniciar":
                        //Envia mensaje para reiniciar el dispositivo
                        mensaje = "reset"+contrasena;
                        sms.enviarMensaje(telefono, mensaje);
                        break;
                    case "Información":
                        Intent intentInfo = new Intent(ConfiguracionActivity.this, InformacionActivity.class);
                        startActivity(intentInfo);
                        break;
                    case "Desactivación por llave":
                        Intent intent = new Intent(ConfiguracionActivity.this, DesactivacionLlaveActivity.class);
                        intent.putExtra("id_device",String.valueOf(idDisp));
                        startActivity(intent);
                        break;
                }
            }
        });
    }


}
