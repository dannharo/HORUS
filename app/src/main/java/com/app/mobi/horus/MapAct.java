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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLException;

public class MapAct extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Mensaje sms = new Mensaje(this);
    NotifyActivity alarma = new NotifyActivity();
    //Declaración de variables
    String mensaje="";
    String lat="";
    String lon="";
    //Almacena la latitud y longitud que actualmente estan almacenados en la base de datos
    Double latActual = 0.0;
    Double lonActual=0.0;
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
        //Leer mensaje
     /*   IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arr0, Intent arr1) {
                processReceive(arr0, arr1);
            }
        };
        registerReceiver(receiver, filter);*/
    }
    //Funciones para recibir mensaje de texto
  /*  protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
    }*/
  /*  public void processReceive(Context context, Intent intent)
    {

        //TextView lbs = (TextView)findViewById(R.id.textView3);
        Bundle bundle = intent.getExtras();
        Object[] objArr =(Object[])bundle.get("pdus");
        String sms = "";
        String smsBody ="";
        String originNumber = "";

        for(int i= 0; i<objArr.length; i++)
        {
            SmsMessage smsMsg = SmsMessage.createFromPdu((byte[])objArr[i]);
            smsBody = smsMsg.getMessageBody();
            originNumber = smsMsg.getDisplayOriginatingAddress();

        }
        if (originNumber.equals(noTelefono)) {
            //Separamos la cadena para obtener la latitud y longitud
            try {

                String[] infoSms = smsBody.split("&");
                String latLon = infoSms[1].replace("q=", "");
                String[] infoUbicacion = latLon.split(",");
                latitud = infoUbicacion[0].trim();
                longitud = infoUbicacion[1].trim();
                sms = latitud + ", " + longitud;
            }
            catch (Exception e)
            {
                sms = "Ocurrió un error";
            }
            Toast.makeText(context, sms, Toast.LENGTH_LONG).show();

            //limpia el marcador actual
            mMap.clear();
            //Agrega el nuevo marcador con la mieva posición
            LatLng posAct = new LatLng(Double.parseDouble(latitud), Double.parseDouble(longitud));
            mMap.addMarker(new MarkerOptions().position(posAct).title("Posición actual"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posAct, 15));

            //sms += "From : " + originNumber + "\ncontent: " + smsBody;
        }

        //lbs.setText(sms);
    }*/


    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;
    }
    //Depende la opciòn seleccionada en el menu, se ejecturà una determinada actividad
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.admin) {
            Intent intent1 = new Intent(this,ListAdminActivity.class);
            this.startActivity(intent1);
            return true;
        }
        if (id == R.id.armado) {
           // mensaje = "arm"+contraseña;
            //sms.enviarMensaje(noTelefono, mensaje);
            //Toast.makeText(this, "Armar dispositivo", Toast.LENGTH_LONG).show();
            Intent intentAlert = new Intent(this, NotifyActivity.class);
            this.startActivity(intentAlert);
            //alarma.mostrarAlerta();
            return true;
        }
        if (id == R.id.alarma) {
            Intent intent4 = new Intent(this,PopAlarma.class);
            this.startActivity(intent4);
            return true;
        }
        if (id == R.id.reiniciar) {
            mensaje = "reset"+contraseña;
            sms.enviarMensaje(noTelefono, mensaje);
            //Toast.makeText(this, "Reiniciar dispositivo", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            //Se guarda en la tabla la ubicacion actual
            dbmanager.updateDispositivo(idDisp, Double.parseDouble(lat), Double.parseDouble(lon));
            //Muestra en el mapa la ubicación obtenida del sms gps
            LatLng actual = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
            mMap.addMarker(new MarkerOptions().position(actual).title("Actual"));
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
                cursor.moveToNext();
                }
            }
            cursor.close();
            //Se agrega en el mapa la posicion obtenida
            LatLng actualBD = new LatLng(Double.parseDouble(latitudBD), Double.parseDouble(longitudBD));
            mMap.addMarker(new MarkerOptions().position(actualBD).title(nombreDisp));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actualBD, 15));
            //Envia mensaje para obtener la ubicación actual
            //El mensaje se conforma de la sig forma: fix030s(obtener en 30 segundos la posicion)+001n
            // (numero de veces que se quiere obtener la posicion cada 30segunds)+contraseña
            mensaje = "fix030s001n"+contraseña;
            //Se manda mensaje para obtener la ubicación del gps
            //sms.enviarMensaje(noTelefono, mensaje);
        }

    }
}
