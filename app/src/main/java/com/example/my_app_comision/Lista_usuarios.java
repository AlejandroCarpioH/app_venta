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
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_app_comision.BDD.conexion;
import com.example.my_app_comision.clases.version_bd;

import java.util.ArrayList;

public class Lista_usuarios extends AppCompatActivity {

    DisplayMetrics medidas;
    ListView listaview;
    SearchView buscador;
    ArrayAdapter<String> listadapter;
    ArrayList<String> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);


        medidas = new DisplayMetrics();
        listaview = findViewById(R.id.lista_cliente);
        buscador =  findViewById(R.id.buscador_cliente);
        getWindowManager().getDefaultDisplay().getMetrics(medidas);
        int ancho = medidas.widthPixels;
        int alto = medidas.heightPixels;

        getWindow().setLayout(ancho-50,alto-50);

        //findViewById(R.id.boton_2).setOnClickListener( valor ->{
//            apreta_boton();
//        });

            lista = new ArrayList<>();

        conexion con = new conexion(this,"bd_productos",null, version_bd.id);
        SQLiteDatabase db = con.getReadableDatabase();

        String [] campos = {"NOMBRE","APELLIDO"};
        Cursor cursor = db.query("cliente",campos,null,null,null,null,"NOMBRE");

        for (int i=0;i<lista.size();i++){
            lista.remove(i);
        }

        while(cursor.moveToNext()){
            String nombre = (cursor.getString(0)+" "+cursor.getString(1));
            if(nombre.trim().equals("Todos")){

            }else{
                lista.add(nombre.trim());
                System.out.println(" holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+cursor.getString(0));
            }

        }


            listadapter = new ArrayAdapter<>(this, R.layout.lista_personalizada,R.id.textview_009,lista);
            listaview.setAdapter(listadapter);

            listaview.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l)-> {
                {


                    Intent intent = new Intent(Lista_usuarios.this, agrega_venta.class);
                    intent.putExtra("valor",""+adapterView.getItemAtPosition(i));
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
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