package com.example.my_app_comision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.my_app_comision.BDD.conexion;
import com.example.my_app_comision.clases.version_bd;

public class principal extends AppCompatActivity {

    TextView texto;
    Intent btn_agrega_producto, btn_agrega_venta, btn_agrega_cliente, btn_consulta_edita, btn_consulta_venta, btn_comision;
    conexion con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        btn_agrega_producto = new Intent(getApplicationContext(), agrega_producto.class);
        btn_agrega_venta = new Intent(getApplicationContext(), agrega_venta.class);
        btn_agrega_venta.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        btn_agrega_cliente = new Intent(getApplicationContext(), Agrega_cliente.class);
        btn_consulta_venta = new Intent(getApplicationContext(), consulta_venta.class);
        btn_consulta_edita = new Intent(getApplicationContext(),consulta_edita_producto_cliente.class);
        btn_comision = new Intent(getApplicationContext(),Comision.class);
        con = new conexion(this,"bd_productos",null, version_bd.id);

        datos_de_inicio();
    }

    public void onBackPressed(){

    }

    public void datos_de_inicio(){

        SQLiteDatabase db_read = con.getReadableDatabase();

        Cursor c = db_read.query("producto",null,null,null,null,null,null);
        if(!c.moveToFirst()){

            SQLiteDatabase db_write = con.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("ID", 1);
            values.put("NOMBRE", " ");
            values.put("DISP", 0);
            values.put("PRECIO_BASE", 0);
            values.put("IVA", 0);
            values.put("IMPUESTO", 0);
            values.put("TOTAL", 0);

            db_write.insert("producto",null,values);
        }

        SQLiteDatabase db_c = con.getReadableDatabase();
        Cursor c_c = db_c.query("cliente",null,null,null,null,null,null);

        if(!c_c.moveToFirst()){
            SQLiteDatabase db_cliente = con.getWritableDatabase();
            ContentValues values_ = new ContentValues();
            values_.put("RUT",1);
            values_.put("NOMBRE","Todos");
            values_.put("APELLIDO","");
            values_.put("DIRECCION","");
            db_cliente.insert("cliente",null,values_);
        }
    }


    public void agrega_venta(View v){

        startActivity(btn_agrega_venta);
    }

    public void consulta_venta(View view){
        startActivity(btn_consulta_venta);
    }

    public void agrega_edita_producto(View v){ // aqui se consulta los productos para ver precios al igual que se puede editar

        startActivity(btn_agrega_producto);

    }

    public void consulta_producto(){

    }

    public void ver_comision(View view){
        startActivity(btn_comision);

    }

    public void Agrega_cliente(View v){
        startActivity(btn_agrega_cliente);
    }

    public void consulta_edita(View v){
        startActivity(btn_consulta_edita);
    }

    public void compartir(View view){
        String hola="";
        for(int i =0;i<100;i++){
            hola+="nombre: alejandro david\n" +
                    "apeliido: carpio huamachumo\n\n" +
                    "precio: $200.000\n" +
                    "producto: becker\n\n\n";
        }
    }

}