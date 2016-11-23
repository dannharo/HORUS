package com.app.mobi.horus;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLException;

public class ListAdminActivity extends AppCompatActivity {
    ImageButton floatButtonAdmin;
    ListView listViewAdmin;
    private DBManager dbmanager;
    //ArrayAdapter<String> adapter;
    private SimpleCursorAdapter adapter;
    final String[] from = new String[]{HorusDB.T_A_NOMBRE};
    final int[] to = new int[]{R.id.lvnombreAdministrador};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_admin);
        //Muestra el botón flotante para agregar un administrador
        dbmanager = new DBManager(this);
        try {
            dbmanager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = dbmanager.fetchAdministradores();
        listViewAdmin = (ListView) findViewById(R.id.listAdmin);
        listViewAdmin.setEmptyView(findViewById(R.id.empty1));
        adapter = new SimpleCursorAdapter(this,R.layout.list_administradores,cursor,from,to,0);
        adapter.notifyDataSetChanged();
        listViewAdmin.setAdapter(adapter);
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
        //listViewAdmin=(ListView)findViewById(R.id.listAdmin);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, administradoresList);
        //listViewAdmin.setAdapter(adapter);
    }
}
