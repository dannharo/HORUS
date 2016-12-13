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
    public void insertDispositivo(String nombre, String  numero, String  imei, String  pass, double latitud, double longitud){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_D_NOMBRE,nombre);
        contentValue.put(HorusDB.T_D_IMEI,imei);
        contentValue.put(HorusDB.T_D_ALARMA_BATERIA,0);
        contentValue.put(HorusDB.T_D_ALARMA_MOVIMIENTO,0);
        contentValue.put(HorusDB.T_D_LATITUD,latitud);
        contentValue.put(HorusDB.T_D_LONGITUD,longitud);
        contentValue.put(HorusDB.T_D_NUMERO,numero);
        contentValue.put(HorusDB.T_D_PASSWORD,pass);
        database.insert(HorusDB.TABLA_DISPOSITIVOS, null, contentValue);
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
    public  Cursor fetchDispositivo(int _id){
        String[] columns = new String[]{HorusDB.T_D_ID,HorusDB.T_D_NOMBRE,HorusDB.T_D_NUMERO,HorusDB.T_D_IMEI,HorusDB.T_D_PASSWORD,HorusDB.T_D_LATITUD,
                HorusDB.T_D_LONGITUD, HorusDB.T_D_ALARMA_MOVIMIENTO, HorusDB.T_D_ALARMA_BATERIA };
        Cursor cursor =  database.query(HorusDB.TABLA_DISPOSITIVOS,columns, HorusDB.T_D_ID+" = "+_id,null,null,null,null);
        if(cursor != null){
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
    public int updateDispositivo(int id, String nombre, String  numero, String  imei,String password){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_D_ID,id);
        contentValue.put(HorusDB.T_D_NOMBRE,nombre);
        contentValue.put(HorusDB.T_D_NUMERO,numero);
        contentValue.put(HorusDB.T_D_PASSWORD,password);
        contentValue.put(HorusDB.T_D_IMEI,imei);
        int i = database.update(HorusDB.TABLA_DISPOSITIVOS, contentValue, HorusDB.T_D_ID + " = " + id, null);
        return i;
    }
    public int updateUsuarios(int id,String usuario, String password, String nombre){
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
    public int updateDispositivo(int id, double latitud, double longitud){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_D_LATITUD,latitud);
        contentValue.put(HorusDB.T_D_LONGITUD,longitud);
         int i = database.update(HorusDB.TABLA_DISPOSITIVOS, contentValue, HorusDB.T_D_ID + " = " + id, null);
            return i;
    }
    public int updateAlarmMov(int id, int alarma){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_D_ALARMA_MOVIMIENTO,alarma);
         int i = database.update(HorusDB.TABLA_DISPOSITIVOS, contentValue, HorusDB.T_D_ID + " = " + id, null);
            return i;
    }
    public int updateAlarmBat(int id, int alarma){
        ContentValues contentValue = new ContentValues();
        contentValue.put(HorusDB.T_D_ALARMA_BATERIA,alarma);
         int i = database.update(HorusDB.TABLA_DISPOSITIVOS, contentValue, HorusDB.T_D_ID + " = " + id, null);
            return i;
    }
    public void deleteDispositivo(int _id){
        database.delete(HorusDB.TABLA_DISPOSITIVOS, HorusDB.T_D_ID+" = "+_id,null);
    }
    public void deleteAdministrador(int _id){
        database.delete(HorusDB.TABLA_ADMON, HorusDB.T_A_ID+" = "+_id,null);
    }
    public void deleteUsuario(int _id){
        database.delete(HorusDB.TABLA_USUARIOS, HorusDB.T_U_ID+" = "+_id,null);
    }
    public String getPassLogin(String userName)
    {
        Cursor cursor=database.query(HorusDB.TABLA_USUARIOS, null,HorusDB.T_U_USUARIO+ " =?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex(HorusDB.T_U_PASSWORD));
        cursor.close();
        return password;
    }
    public String getPassword(String password)
    {
        Cursor cursor=database.query(HorusDB.TABLA_USUARIOS, null,HorusDB.T_U_PASSWORD+ " =?", new String[]{password}, null, null, null);
        if(cursor.getCount()<1) // password Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String contrasena= cursor.getString(cursor.getColumnIndex(HorusDB.T_U_PASSWORD));
        cursor.close();
        return password;
    }
}
