package com.example.my_app_comision.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexion extends SQLiteOpenHelper {

    final String crea_tabla_productos = "create table producto (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT,DISP INTEGER, PRECIO_BASE REAL, IVA INTEGER, IMPUESTO INTEGER, TOTAL INTEGER)";
    final String crea_tabla_ventas = "create table venta (ID INTEGER PRIMARY KEY AUTOINCREMENT, CLIENTE TEXT, PRODUCTO TEXT, UNIDAD TEXT, PRECIO_BASE INTEGER, CANTIDAD INTEGER, VALOR_TOTAL INTEGER, FECHA TEXT, HORA TEXT, PRECIO_COMISION INTEGER)";
    final String crea_tabla_cliente = "create table cliente (ID INTEGER PRIMARY KEY AUTOINCREMENT,RUT TEXT ,NOMBRE TEXT, APELLIDO TEXT, DIRECCION TEXT)";


    public conexion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(crea_tabla_productos);
        db.execSQL(crea_tabla_ventas);
        db.execSQL(crea_tabla_cliente);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("DROP TABLE IF EXISTS venta");
        onCreate(db);
    }
}