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
    public static final String T_D_ALARMA_BATERIA  = "ALARM_BAT";
    public static final String T_D_ALARMA_MOVIMIENTO  = "ALARM_MOV";
    public static final String T_D_NOMBRE  = "NOMBRE";
    public static final String T_D_NUMERO  = "NUMERO";
    public static final String T_D_IMEI  = "IMEI";
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
    public static final int DATABASE_VERSION = 5;

    // Creating tables query
    private static final String SQL_TABLA_DISPOSITIVOS = "create table "
            +TABLA_DISPOSITIVOS+ "("
            +T_D_ID+" integer primary key autoincrement, "
            +T_D_NOMBRE+" text, "
            +T_D_IMEI+" text, "
            +T_D_NUMERO+" text not null, "
            +T_D_PASSWORD+" text, "
            +T_D_ALARMA_BATERIA+" integer, "
            +T_D_ALARMA_MOVIMIENTO+" integer, "
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
}