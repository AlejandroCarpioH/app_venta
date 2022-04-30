package com.example.my_app_comision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.my_app_comision.BDD.conexion;
import com.example.my_app_comision.clases.tabla;
import com.example.my_app_comision.clases.version_bd;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;

public class listaProducto extends AppCompatActivity {

    DisplayMetrics medidas;
    Spinner spiner;
    ListView listaview;
    SearchView buscador;
    ArrayAdapter<String> listadapter;
    ArrayList<String> lista;
    tabla t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);

        //System.out.println("lista producto intent");
        medidas = new DisplayMetrics();
        listaview = findViewById(R.id.lista_producto);
        buscador =  findViewById(R.id.buscador_producto);
//        getWindowManager().getDefaultDisplay().getMetrics(medidas);
//        int ancho = medidas.widthPixels;
//        int alto = medidas.heightPixels;
//
//        getWindow().setLayout(ancho-50,alto-50);


        Bundle valores = getIntent().getExtras();
        System.out.println("el valor en intent lista producto: "+valores.getString("fila"));

        //findViewById(R.id.boton_2).setOnClickListener( valor ->{
//            apreta_boton();
//        });

        lista = new ArrayList<>();


        conexion con = new conexion(this,"bd_productos",null, version_bd.id);
        SQLiteDatabase db = con.getReadableDatabase();

        String [] campos = {"NOMBRE"};
        Cursor cursor = db.query("producto",campos,null,null,null,null,"NOMBRE");

        for (int i=0;i<lista.size();i++){
            lista.remove(i);
        }

        //lista.add("hola");
        if(cursor.moveToFirst()){
            do{
                //System.out.println("entro a lista producto");
                String nombre = (cursor.getString(0));
                if(nombre.trim().equals("Todos")){

                }else{
                    lista.add(nombre);
                    //System.out.println(" holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+cursor.getString(0));
                }

            }while(cursor.moveToNext());

        }





        //System.out.println("esta es la cantidad "+lista.size());

        lista.remove(0);
        listadapter = new ArrayAdapter<>(this, R.layout.lista_personalizada,R.id.textview_009,lista);
        listaview.setAdapter(listadapter);

        listaview.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {


                Intent intent = new Intent(listaProducto.this, agrega_venta.class);
                intent.putExtra("valor_producto",""+adapterView.getItemAtPosition(i));
                Bundle valor = getIntent().getExtras();
                System.out.println("el valor en intent lista producto: "+valor.getString("fila"));
                if(valor != null){
                    intent.putExtra("fila", valor.getString("fila"));
                    intent.putExtra("celda", valor.getString("celda"));
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();

        });
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                listadapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listadapter.getFilter().filter(s);
                return false;
            }
        });



    }
}