package com.app.mobi.horus;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton floatButton;
    Button btnConfigurar;
    //Array que contiene los dispositivos dados de alta
    String[] dispositivosArray = {"Dipositivo 1", "Dispositivo de prueba","mapa"};
    private DBManager dbmanager;
    private ListView listview;
    private SimpleCursorAdapter adapter;
    final String[] from = new String[]{HorusDB.T_D_NOMBRE,HorusDB.T_D_ID};
    final int[] to = new int[]{R.id.lvnombre,R.id.lvid};


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
        //floatButton.setOnClickListener(this);
       // btnConfigurar = (Button) findViewById(R.id.childButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, DispositivoActivity.class);
                startActivityForResult(myIntent, 0);
            }


        });
       // btnConfigurar=(Button)findViewById(R.id.childButton);
        //btnConfigurar.setOnClickListener(this);
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
                Intent intent = new Intent(MainActivity.this, DeviceCrudActivity.class);
                //  intent.putExtra("idDispositivo", data);
                startActivity(intent);

            }

        });





    }
   /* public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.imageButton:
                Intent myIntent = new Intent(this, DispositivoActivity.class);
                startActivityForResult(myIntent, 0);
        }
    }*/


    //Adapter
/*    private class MyListAdapter extends ArrayAdapter<String>{
        private int layout;
        private MyListAdapter(Context context, int resource, List<String> objects)
        {
            super(context, resource, objects);
            layout = resource;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ViewHolder mainViewHolder = null;
            if(convertView == null)
            {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new  ViewHolder();
                viewHolder.btnConfigurar = (Button)convertView.findViewById(R.id.childButton);
                viewHolder.btnConfigurar.setOnClickListener(new View.OnClickListener(){
                        @Override
                    public void onClick(View v){
                        Toast.makeText(getContext(), "Configuración de dispositivo"+ position, Toast.LENGTH_LONG).show();
                    }
                });
                convertView.setTag(viewHolder);

            }

            return super.getView(position, convertView, parent);
        }

    }
    public class ViewHolder
    {
        Button btnConfigurar;
    }*/
}
