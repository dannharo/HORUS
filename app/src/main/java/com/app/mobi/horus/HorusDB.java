package com.app.mobi.horus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HorusDB extends SQLiteOpenHelper {
    // tables names
    public static final String TABLA_DISPOSITIVOS  = "DISPOSITIVOS";
    public static final String TABLA_USUARIOS = "USUARIOS";
    public static final String TABLA_ADMON = "ADMINISTRADORES";

    // tables columns
    //TABLA DE DISPOSITIVOS
    public static final String T_D_ID  = "_id";
    public static final String T_D_NOMBRE  = "NOMBRE";
    public static final String T_D_NUMERO  = "NUMERO";
    public static final String T_D_PASSWORD  = "PASSWORD";
    public static final String T_D_LATITUD  = "LATITUD";
    public static final String T_D_LONGITUD  = "LONGITUD";
    //TABLA USUARIOS
    public static final String T_U_ID = "_id";
    public static final String T_U_USUARIO = "USUARIO";
    public static final String T_U_NOMBRE = "NOMBRE";
    public static final String T_U_PASSWORD = "PASSWORD";
    //TABLA ADMINISTRADORES
    public static final String T_A_ID = "_id";
    public static final String T_A_ID_DISPOSITIVO = "ID_DISPOSITIVO";
    public static final String T_A_NOMBRE = "NOMBRE";
    public static final String T_A_NUMERO = "NUMERO";

    // Database Information
    public static final String DATABASE_NAME = "horus_devices.db";

    // Database Version
    public static final int DATABASE_VERSION = 3;

    // Creating tables query
    private static final String SQL_TABLA_DISPOSITIVOS = "create table "
            +TABLA_DISPOSITIVOS+ "("
            +T_D_ID+" integer primary key autoincrement, "
            +T_D_NOMBRE+" text, "
            +T_D_NUMERO+" text not null, "
            +T_D_PASSWORD+" text, "
            +T_D_LATITUD+" real, "
            +T_D_LONGITUD+" real "
            +");";
    private static final String SQL_TABLA_USUARIOS = "create table "
            +TABLA_USUARIOS+ "("
            +T_U_ID+" integer primary key autoincrement, "
            +T_U_USUARIO+" text not null, "
            +T_U_PASSWORD+" text not null, "
            +T_U_NOMBRE+" text not null "
            +");";
    private static final String SQL_TABLA_ADMON = "create table "
            +TABLA_ADMON+ "("
            +T_A_ID+" integer primary key autoincrement, "
            +T_A_ID_DISPOSITIVO+" integer, "
            +T_A_NUMERO+" text not null, "
            +T_A_NOMBRE+" text "
            +");";

    public HorusDB(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_TABLA_DISPOSITIVOS);
        db.execSQL(SQL_TABLA_USUARIOS);
        db.execSQL(SQL_TABLA_ADMON);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_DISPOSITIVOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_ADMON);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_USUARIOS);
        onCreate(db);
    }
    public void agregarDispositivo(String id, String nombre, String  numero, double latitud, double longitud){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T_D_ID,id);
        values.put(T_D_NOMBRE,nombre);
        values.put(T_D_NUMERO,numero);
        values.put(T_D_LATITUD,latitud);
        values.put(T_D_LONGITUD,longitud);
        db.insert(TABLA_DISPOSITIVOS,null,values);
        db.close();
    }
    public void agregarUsuario(String usuario, String password, String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T_U_NOMBRE,nombre);
        values.put(T_U_USUARIO,usuario);
        values.put(T_U_PASSWORD,password);
        db.insert(TABLA_USUARIOS,null,values);
        db.close();
    }
    public void agregarAdministrador(int id_dispositivo, String numero, String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T_A_ID_DISPOSITIVO,id_dispositivo);
        values.put(T_A_NUMERO,numero);
        values.put(T_A_NOMBRE,nombre);
        db.insert(TABLA_ADMON,null,values);
        db.close();
    }
    public Cursor obtenerDispositivo(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {T_D_ID,T_D_NOMBRE,T_D_NUMERO,T_D_LATITUD,T_D_LONGITUD};
        Cursor cursor = db.query(TABLA_DISPOSITIVOS,projection,"ID = ?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            db.close();
            return cursor;
        }
        else{
            db.close();
            return null;
        }

    }
    public Cursor obtenerDispositivos(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT "+T_U_ID+", "+T_D_NOMBRE+" FROM "+TABLA_DISPOSITIVOS,null);
        return cursor1;

    }

}