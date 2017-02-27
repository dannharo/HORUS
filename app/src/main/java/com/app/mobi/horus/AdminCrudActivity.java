package com.app.mobi.horus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AdminCrudActivity extends AppCompatActivity {

    private int idAdmin = 0;
    private DBManager dbManager;
    private String mensaje="";
    private String gpsTelefono = DeviceCrudActivity.telGps;
    private String  contrasena = DeviceCrudActivity.contrasena;
    String nombre, telefono;
    EditText editNombre, editTelefono;
    Mensaje sms = new Mensaje(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_crud);

        dbManager = new DBManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Se almacena el id de administrador
        Intent intent = getIntent();
        idAdmin = Integer.parseInt(intent.getStringExtra("id_admon"));
        //Almacena la información del administrador
        Cursor cursor = dbManager.fetchAdministrador(idAdmin);
        //textNombre.setText(id);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                //Almacena en las variables la información
                nombre = cursor.getString(cursor.getColumnIndex(HorusDB.T_A_NOMBRE));
                telefono = cursor.getString(cursor.getColumnIndex(HorusDB.T_A_NUMERO));
                cursor.moveToNext();
            }
        }
        cursor.close();
        //Se muestra la información del administrador
        editNombre = (EditText)findViewById(R.id.editNombreAdminDet);
        editTelefono = (EditText)findViewById(R.id.editTelAdminDet);
        editNombre.setText(nombre);
        editTelefono.setText(telefono);
    }
    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {
            //Eliminar el administrador
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminCrudActivity.this);
            //Metodos del constructor
            builder.setTitle("Eliminar Registro");
            builder.setMessage("¿Desea borrar el registro?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbManager.deleteAdministrador(idAdmin);
                    Intent intent = new Intent(AdminCrudActivity.this, ListAdminActivity.class);
                    startActivity(intent);
                    //Se envia mensaje para eliminar el admin del dispositivo
                    mensaje = "noadmin"+contrasena;
                    sms.enviarMensaje(gpsTelefono, mensaje);
                    Toast.makeText(AdminCrudActivity.this, "Administrador eliminado correctamente", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancelar", null);
            Dialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
