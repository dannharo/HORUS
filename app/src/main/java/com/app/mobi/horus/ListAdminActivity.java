package com.app.mobi.horus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class ListAdminActivity extends AppCompatActivity {
    ImageButton floatButtonAdmin;
    ListView listViewAdmin;
    ArrayAdapter<String> adapter;
    String[] administradoresList = {"Administrador 1", "Administrador 2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_admin);
        //Muestra el botón flotante para agregar un administrador
        floatButtonAdmin =(ImageButton) findViewById(R.id.imageButtonAdmin);
        floatButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdministradorActivity.class);
                startActivityForResult(myIntent, 0);
                //  Toast.makeText(getApplicationContext(), "Button is clicked", Toast.LENGTH_LONG).show();
            }
        });
        //Muestra en la lista los administadores
        listViewAdmin=(ListView)findViewById(R.id.listAdmin);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, administradoresList);
        listViewAdmin.setAdapter(adapter);
    }
}
