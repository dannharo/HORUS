package com.app.mobi.horus;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    //public static ArrayList<String> telefonosGps = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "Permission is not granted, requesting", Toast.LENGTH_LONG).show();
            //Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
        } else {
           // Toast.makeText(this, "Permission is not granted", Toast.LENGTH_LONG).show();
        }
        dbmanager = new DBManager(this);
        try {
            dbmanager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

      /*  Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        double latitude = 19.255730;
        double longitude = -103.735201;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            Toast.makeText(this, "dirección: "+address, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No contiene dirección", Toast.LENGTH_LONG).show();
        }*
       /* String user="", pass="", name="";
        Cursor usuario = dbmanager.fetchUsuarios();
        if(usuario.moveToFirst()){
            while(!usuario.isAfterLast()){
                 user = usuario.getString(usuario.getColumnIndex(HorusDB.T_U_USUARIO));
                 pass = usuario.getString(usuario.getColumnIndex(HorusDB.T_U_PASSWORD));
                 name = usuario.getString(usuario.getColumnIndex(HorusDB.T_U_NOMBRE));
                usuario.moveToNext();
            }
        }
        usuario.close();
        Toast.makeText(this, user+", "+pass+", "+name, Toast.LENGTH_LONG).show();*/
       Cursor cursor = dbmanager.fetchDispositivos();
       /* if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                //Almacena en las variables la información
                telefonosGps.add(cursor.getString(cursor.getColumnIndex(HorusDB.T_D_NUMERO)));
                cursor.moveToNext();
            }
        }*/
        listview = (ListView) findViewById(R.id.listDispositivos);
        listview.setEmptyView(findViewById(R.id.empty));
        adapter = new SimpleCursorAdapter(this,R.layout.list_dispositivos,cursor,from,to,0);
       /* adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
            //Binds the Cursor column defined by the specified index to the specified view
            public boolean setViewValue(View view, Cursor cursor, int columnIndex){
                R.id.i.setBackgroundResource(R.drawable.marker_on);
                ((ImageView)view).setImageResource(R.drawable.lock);
                if(view.getId() == R.id.imgListDips){
                    //...
                    ((ImageView)view).setImageResource(R.drawable.lock);
                    return true; //true because the data was bound to the view
                }
                return false;
            }
        });*/
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
                TextView idTView = (TextView) view.findViewById(R.id.lvid);
                String ids = idTView.getText().toString();
                //Muestra mensaje con la posicion del elemento seleccionado
                //Toast.makeText(getBaseContext(), ids + " fue seleccionado", Toast.LENGTH_LONG).show();
                //Obtiene el nombre del elemento seleccionado
                //String data = (String) listview.getAdapter().getItem(position);
                //Se define el intent, indica a la clase a la que se pasara la informacion
                Intent intent = new Intent(MainActivity.this, MenuDispositivoActivity.class);
                intent.putExtra("id_device",ids);
                //  intent.putExtra("idDispositivo", data);
                startActivity(intent);

            }

        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ajustes) {
            Intent intent1 = new Intent(this, ConfiguracionActivity.class);
            intent1.putExtra("activity", "home");
            this.startActivity(intent1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Minimiza la aplicación
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
