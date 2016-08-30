package com.app.mobi.horus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.admin) {
            Intent intent1 = new Intent(this,AdministradorActivity.class);
            this.startActivity(intent1);
            return true;
        }
        if (id == R.id.pass) {
            Intent intent2 = new Intent(this,ContrasenaActivity.class);
            this.startActivity(intent2);
            return true;
        }
        if (id == R.id.armado) {
            Toast.makeText(this, "Armar dispositivo", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.alarma) {
            Intent intent4 = new Intent(this,PopAlarma.class);
            this.startActivity(intent4);
            return true;
        }
        if (id == R.id.reiniciar) {
            Toast.makeText(this, "Reiniciar dispositivo", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
