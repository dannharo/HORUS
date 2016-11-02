package com.app.mobi.horus;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class DBManager {
    private HorusDB dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c){
        context = c;
    }
    public DBManager open() throws SQLException{
        dbHelper = new HorusDB(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        dbHelper.close();
    }
    public void insertDispositivo(String nombre, String  numero, String  pass, double latitud, double longitud){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_D_NOMBRE,nombre);
        contentValue.put(HorusDB.T_D_LATITUD,latitud);
        contentValue.put(HorusDB.T_D_LONGITUD,longitud);
        contentValue.put(HorusDB.T_D_NUMERO,numero);
        contentValue.put(HorusDB.T_D_PASSWORD,pass);
        database.insert(HorusDB.TABLA_DISPOSITIVOS,null,contentValue);
    }
    public void insertUsuario(String usuario, String password, String nombre){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_U_NOMBRE,nombre);
        contentValue.put(HorusDB.T_U_USUARIO,usuario);
        contentValue.put(HorusDB.T_U_PASSWORD,password);
        database.insert(HorusDB.TABLA_USUARIOS,null,contentValue);
    }
    public void insertAdministrador(int id_dispositivo, String numero, String nombre){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_A_ID_DISPOSITIVO,id_dispositivo);
        contentValue.put(HorusDB.T_A_NUMERO,numero);
        contentValue.put(HorusDB.T_A_NOMBRE,nombre);
        database.insert(HorusDB.TABLA_ADMON,null,contentValue);
    }
    public Cursor fetchDispositivos(){
        String[] columns = new String[]{HorusDB.T_D_ID,HorusDB.T_D_NOMBRE,HorusDB.T_D_NUMERO,HorusDB.T_D_LATITUD,HorusDB.T_D_LONGITUD};
        Cursor cursor = database.query(HorusDB.TABLA_DISPOSITIVOS,columns,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchUsuarios(){
        String[] columns = new String[]{HorusDB.T_U_ID,HorusDB.T_U_NOMBRE,HorusDB.T_U_PASSWORD,HorusDB.T_U_USUARIO};
        Cursor cursor = database.query(HorusDB.TABLA_USUARIOS,columns,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchAdministradores(){
        String[] columns = new String[]{HorusDB.T_A_ID,HorusDB.T_A_ID_DISPOSITIVO,HorusDB.T_A_NOMBRE,HorusDB.T_A_NUMERO};
        Cursor cursor = database.query(HorusDB.TABLA_ADMON,columns,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public int updateDispositivo(String id, String nombre, String  numero, double latitud, double longitud){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_D_ID,id);
        contentValue.put(HorusDB.T_D_NOMBRE,nombre);
        contentValue.put(HorusDB.T_D_LATITUD,latitud);
        contentValue.put(HorusDB.T_D_LONGITUD,longitud);
        contentValue.put(HorusDB.T_D_NUMERO,numero);
        int i = database.update(HorusDB.TABLA_DISPOSITIVOS,contentValue,HorusDB.T_D_ID +" = "+id,null);
        return i;
    }
    public int updateUsuarios(String id,String usuario, String password, String nombre){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_U_NOMBRE,nombre);
        contentValue.put(HorusDB.T_U_USUARIO,usuario);
        contentValue.put(HorusDB.T_U_PASSWORD,password);
        int i = database.update(HorusDB.TABLA_USUARIOS,contentValue,HorusDB.T_U_ID +" = "+id,null);
        return i;
    }
    public int updateAdministradores(int id,int id_dispositivo, String numero, String nombre){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_A_ID_DISPOSITIVO,id_dispositivo);
        contentValue.put(HorusDB.T_A_NUMERO,numero);
        contentValue.put(HorusDB.T_A_NOMBRE,nombre);
        int i = database.update(HorusDB.TABLA_ADMON,contentValue,HorusDB.T_A_ID +" = "+id,null);
        return i;
    }

}
