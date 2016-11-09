package com.app.mobi.horus;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton floatButton;
    Button btnConfigurar;
    //Array que contiene los dispositivos dados de alta
    String[] dispositivosArray = {"Dipositivo 1", "Dispositivo de prueba","mapa"};
    private DBManager dbmanager;
    private ListView listview;
    private SimpleCursorAdapter adapter;
    final String[] from = new String[]{HorusDB.T_D_NOMBRE};
    final int[] to = new int[]{R.id.lvnombre};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission is not granted, requesting", Toast.LENGTH_LONG).show();
            //Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
        } else {
            Toast.makeText(this, "Permission is not granted", Toast.LENGTH_LONG).show();
        }
        dbmanager = new DBManager(this);
        try {
            dbmanager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = dbmanager.fetchDispositivos();
        listview = (ListView) findViewById(R.id.listDispositivos);
        listview.setEmptyView(findViewById(R.id.empty));
        adapter = new SimpleCursorAdapter(this,R.layout.list_dispositivos,cursor,from,to,0);
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);
        //Muestra el botón flotante para agreagar un nuevo dispositivo
        floatButton =(ImageButton) findViewById(R.id.imageButton);
        floatButton.setOnClickListener(this);
        btnConfigurar=(Button)findViewById(R.id.childButton);
        //btnConfigurar.setOnClickListenr(this);
        //Muestra en la listview los dispositivos
        //SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, new HorusDB(this).obtenerDispositivos(),new String[]{"ID","NOMBRE"});
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dispositivosArray);
        //final ListView listView = (ListView) findViewById(R.id.listDispositivos);
        //listView.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Muestra mensaje con la posicion del elemento seleccionado
                Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position) + " fue seleccionado", Toast.LENGTH_LONG).show();
                //Obtiene el nombre del elemento seleccionado
                //String data = (String) listview.getAdapter().getItem(position);
                //Se define el intent, indica a la clase a la que se pasara la informacion
                Intent intent = new Intent(MainActivity.this, MapAct.class);
              //  intent.putExtra("idDispositivo", data);
                startActivity(intent);

            }

        });


    }
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.imageButton:
                Intent myIntent = new Intent(this, DispositivoActivity.class);
                startActivityForResult(myIntent, 0);
            case R.id.btnLogin:
                Toast.makeText(getApplicationContext(), "Configurar dispositivo!", Toast.LENGTH_SHORT).show();
        }
    }
}
