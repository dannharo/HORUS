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
    public static final String TABLA_ESTADO_DISPOSITIVOS = "ESTADO_DISPOSITIVOS";
    public static final String TABLA_SALDO_DISPOSITIVO = "SALDO_DISPOSITIVO";

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
    public static final String T_D_VECES_DESARMAR = "VECES_DESARM";
    public static final String T_D_NUMERO_INTENTOS  = "NO_INTENTOS";
    //TABLA ESTADO DE DISPOSITIVOS
    public static final String T_E_ID  = "_id";
    public static final String T_E_ID_DISPOSITIVO  = "ID_DISPOSITIVO";
    public static final String T_E_BATERIA  = "BATERIA";
    public static final String T_E_GPS  = "GPS";
    public static final String T_E_GSM  = "GSM";
    public static final String T_E_ARM  = "ARM";
    //TABLA SALDO DISPOSITIVO
    public static final String T_S_ID  = "_id";
    public static final String T_S_ID_DISPOSITIVO  = "ID_DISPOSITIVO";
    public static final String T_S_SALDO  = "SALDO";
    public static final String T_S_COSTO_SMS  = "COSTO_SMS";
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
    public static final int DATABASE_VERSION = 15;

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
            +T_D_LONGITUD+" real, "
            +T_D_VECES_DESARMAR+" integer, "
            +T_D_NUMERO_INTENTOS+" integer "
            +");";
    private static final String SQL_TABLA_ESTADO_DISPOSITIVOS = "create table "
            +TABLA_ESTADO_DISPOSITIVOS+ "("
            +T_E_ID+" integer primary key autoincrement, "
            +T_E_ID_DISPOSITIVO+" integer, "
            +T_E_BATERIA+" text, "
            +T_E_GPS+" text, "
            +T_E_GSM+" text, "
            +T_E_ARM+" text"
            +");";
    private static final String SQL_TABLA_SALDO_DISPOSITIVO = "create table "
            +TABLA_SALDO_DISPOSITIVO+ "("
            +T_E_ID+" integer primary key autoincrement, "
            +T_E_ID_DISPOSITIVO+" integer, "
            +T_S_SALDO+" real default 0.0, "
            +T_S_COSTO_SMS+" real default 0.0"
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
        db.execSQL(SQL_TABLA_ESTADO_DISPOSITIVOS);
        db.execSQL(SQL_TABLA_SALDO_DISPOSITIVO);
        db.execSQL("INSERT INTO " + TABLA_USUARIOS+ "("+T_U_USUARIO+", "+T_U_PASSWORD+", "+T_U_NOMBRE+") VALUES ('horus', 'M0B1H0RU5','Horus User')");
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_DISPOSITIVOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_ADMON);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_ESTADO_DISPOSITIVOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_SALDO_DISPOSITIVO);
        onCreate(db);
    }
}