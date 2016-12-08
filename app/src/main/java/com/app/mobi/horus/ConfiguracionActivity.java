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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
