package com.example.my_app_comision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_app_comision.BDD.conexion;
import com.example.my_app_comision.clases.version_bd;

public class Agrega_cliente extends AppCompatActivity {

    conexion con;
    EditText nombre, apellido, rut, direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_cliente);
        con = new conexion(this,"bd_productos",null, version_bd.id);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        nombre = findViewById(R.id.nombre_ciente);
        apellido = findViewById(R.id.apellido_cliente);
        rut = findViewById(R.id.rut_cliente);
        direccion = findViewById(R.id.direccion_cliente);
    }

    public void ingresa_cliente(View v){

        if(!nombre.getText().toString().trim().equals("")){

            SQLiteDatabase SQL_usuario = con.getReadableDatabase();
            String [] param = {nombre.getText().toString().trim()};
            Cursor cursor = SQL_usuario.query("cliente",null,"NOMBRE=?",param,null,null,null);

            if(!cursor.moveToNext()){
                SQLiteDatabase db = con.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("NOMBRE", nombre.getText().toString().trim());
                values.put("APELLIDO", apellido.getText().toString());
                values.put("RUT", rut.getText().toString());
                values.put("DIRECCION", direccion.getText().toString());
                Long id = db.insert("cliente",null,values);
                if(id<0){
                    Toast.makeText(this,"No se pudo registrar cliente",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(this,"Cliente Ingresado correctamente",Toast.LENGTH_SHORT).show();
                    resetea_Datos();
                }
            }else{
                Toast.makeText(this,"El nombre del cliente ya existe",Toast.LENGTH_LONG).show();
            }



        }else{
            Toast.makeText(this,"Ingresa almenos el nombre del cliente",Toast.LENGTH_SHORT).show();
        }




    }
    public void resetea_Datos(){
        nombre.setText("");
        apellido.setText("");
        rut.setText("");
        direccion.setText("");
    }

    public void prueba_BD_cliente(View v){

        SQLiteDatabase db = con.getReadableDatabase();
        String [] campos = {"ID","NOMBRE","APELLIDO","RUT","DIRECCION"};

        Cursor cursor = db.query("cliente",campos,null,null,null,null,null);




        while(cursor.moveToNext()){

            System.out.println("ID "+cursor.getString(0));
            System.out.println("NOMBRE "+cursor.getString(1)+"\n");
            System.out.println("APELLIDO "+cursor.getString(2)+"\n");
            System.out.println("RUT "+cursor.getString(3)+"\n");
            System.out.println("DIRECCION "+cursor.getString(4)+"\n");
            System.out.println("-----------------------------------");


        }


    }


}