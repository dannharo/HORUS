package com.app.mobi.horus;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLException;

public class MapAct extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    Mensaje sms = new Mensaje(this);
    NotifyActivity alarma = new NotifyActivity();
    //Declaración de variables
    String mensaje="";
    String lat="";
    String lon="";
    String activity ="";
    //Se almacena la ubicación actual
    String latActual = "";
    String lonActual="";
    private DBManager dbmanager;
    //Almacena datos del dispositivo
    int idDisp = DeviceCrudActivity._id;
    String contraseña =DeviceCrudActivity.contrasena;
    String noTelefono = DeviceCrudActivity.telGps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Se crea instancia con la clase que contiene la base de datos
        dbmanager = new DBManager(this);
        try {
            dbmanager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //Intent, obtiene los datos enviados por la clase ReceiveSms
        Intent objIntent = this.getIntent();
        lat = objIntent.getStringExtra("latitud");
        lon = objIntent.getStringExtra("longitud");
        activity = objIntent.getStringExtra("activity");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Se obtiene la posicion guardada en la BD
        String latitudBD="";
        String longitudBD="";
        String nombreDisp="";

        if ((lat != null) && (lon != null))
        {
            latActual = lat;
            lonActual = lon;
            //Se guarda en la tabla la ubicacion actual
            dbmanager.updateDispositivo(idDisp, Double.parseDouble(lat), Double.parseDouble(lon));
            //Muestra en el mapa la ubicación obtenida del sms gps
            LatLng actual = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
            mMap.addMarker(new MarkerOptions().position(actual).title("Actual").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual, 15));
        }
        else
        {
            //Se obtiene la posicion guardada en la bd
            Cursor cursor = dbmanager.fetchDispositivo(idDisp);
            if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                nombreDisp = cursor.getString(cursor.getColumnIndex(HorusDB.T_D_NOMBRE));
                longitudBD = cursor.getString(cursor.getColumnIndex(HorusDB.T_D_LONGITUD));
                latitudBD = cursor.getString(cursor.getColumnIndex(HorusDB.T_D_LATITUD));
                latActual = latitudBD;
                lonActual = longitudBD;
                cursor.moveToNext();
                }
            }
            cursor.close();
            //Se agrega en el mapa la posicion obtenida
            LatLng actualBD = new LatLng(Double.parseDouble(latitudBD), Double.parseDouble(longitudBD));
            mMap.addMarker(new MarkerOptions().position(actualBD).title(nombreDisp).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actualBD, 15));
            //Envia mensaje para obtener la ubicación actual
            //El mensaje se conforma de la sig forma: fix030s(obtener en 30 segundos la posicion)+001n
            // (numero de veces que se quiere obtener la posicion cada 30segunds)+contraseña
            mensaje = "fix030s001n"+contraseña;
            //Se manda mensaje para obtener la ubicación del gps
            sms.enviarMensaje(noTelefono, mensaje);
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                //Se muestra en una nueva ventana la información de la ubicación
                Intent intentInfo = new Intent(MapAct.this, UbicacionActivity.class);
                //Se manda las coordenadas de la ubicación
                intentInfo.putExtra("latitud",latActual);
                intentInfo.putExtra("longitud",lonActual);
                startActivity(intentInfo);
                //Toast.makeText(MapAct.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();
               /* if(arg0 != null && arg0.getTitle().equals(marcador.getTitle().toString())); // if marker  source is clicked
                Toast.makeText(MapAct.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast*/
                return true;
            }

        });
    }


    //Minimiza la aplicación
    @Override
    public void onBackPressed() {
        //Valida si el mapa se mostro por la alerta, regresa al mapa
        if (activity != null && !activity.equals("")) {
            if (activity.equals("alarma")) {
                Intent intentHome = new Intent(MapAct.this, MainActivity.class);
                startActivity(intentHome);
                finish();
            }
        }
        else
       {
            finish();
       }
    }
}
