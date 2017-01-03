package com.app.mobi.horus;

import android.content.Intent;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;
import android.location.Address;
import java.util.List;

public class UbicacionActivity extends AppCompatActivity {

    private double latitud, longitud;
    Geocoder geocoder;
    List<Address> addresses;
    TextView informacion;
    String textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        informacion = (TextView)findViewById(R.id.textInfUbicacion);
        //Se obtiene la información obtenida
        Intent intent = getIntent();
        latitud = Double.parseDouble(intent.getStringExtra("latitud"));
        longitud = Double.parseDouble(intent.getStringExtra("longitud"));
        //Toast.makeText(UbicacionActivity.this,intent.getStringExtra("latitud") , Toast.LENGTH_SHORT).show();
        //se agregaron unas lineas en build.gradle,
        getInformation(latitud, longitud);

    }
    //Este metodo obtiene la información a partir de las coordenadas y lo muestra al usuario
    private void getInformation(double latitude, double longitude){
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            textInfo = "Dirección: "+ address + "\nCiudad: "+city + "\nEstado: "+ state + "\nPaís: "+ country + "\nCódigo Postal: "+postalCode;
            informacion.setText(textInfo);
        } catch (Exception e) {
            e.printStackTrace();
            textInfo = "Los detalles de la ubicación no fueron obtenidos";
            informacion.setText(textInfo);
        }
    }
}
