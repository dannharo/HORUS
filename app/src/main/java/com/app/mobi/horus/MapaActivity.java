package com.app.mobi.horus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;


public class MapaActivity extends AppCompatActivity {
String idDispositivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        //Se muestra en el texview el dato obtenido de la activity_main
        TextView tv = (TextView)findViewById(R.id.textView);
        Intent intent = getIntent();
        idDispositivo =  intent.getStringExtra("idDispositivo");
        tv.setText(idDispositivo);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;
    }
}
