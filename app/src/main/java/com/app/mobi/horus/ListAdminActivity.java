package com.app.mobi.horus;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


import java.sql.SQLException;

public class ListAdminActivity extends AppCompatActivity {
    ImageButton floatButtonAdmin;
    ListView listViewAdmin;
    private DBManager dbmanager;
    //ArrayAdapter<String> adapter;
    private SimpleCursorAdapter adapter;
    final String[] from = new String[]{HorusDB.T_A_NOMBRE,HorusDB.T_A_ID};
    final int[] to = new int[]{R.id.lvnombreAdministrador,R.id.lvidAdmon};


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
        listViewAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idTView = (TextView) view.findViewById(R.id.lvidAdmon);
                String ids = idTView.getText().toString();
                //Muestra mensaje con la posicion del elemento seleccionado
                Toast.makeText(getBaseContext(), ids + " fue seleccionado", Toast.LENGTH_LONG).show();
                //Obtiene el nombre del elemento seleccionado
                //Se define el intent, indica a la clase a la que se pasara la informacion
                Intent intent = new Intent(ListAdminActivity.this, AdminCrudActivity.class);
                intent.putExtra("id_admon",ids);
                startActivity(intent);

            }

        });
    }
}
