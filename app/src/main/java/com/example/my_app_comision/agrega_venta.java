package com.example.my_app_comision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_app_comision.BDD.conexion;
import com.example.my_app_comision.clases.ID_VENTA;
import com.example.my_app_comision.clases.tabla;
import com.example.my_app_comision.clases.version_bd;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class agrega_venta extends AppCompatActivity {

    //Spinner spinner_cliente;
    TextView text;
    TableLayout tabla;
    Button boton_limpiar;
    tabla t = new tabla();
    private String [] cabecera ={"N°","producto","tipo","$$","cant","total","Eliminar","COMISION"};
    private ArrayList<String[]> filas = new ArrayList<>();
    com.example.my_app_comision.clases.tabla tb;
    ScrollView scroll;
    TextView suma_total, listaCliente;
    DecimalFormat formato;
    int comision;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_venta);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        boton_limpiar = findViewById(R.id.boton_refresh);

        boton_limpiar.setOnClickListener((v)->{
            Intent intent = new Intent(this,agrega_venta.class);
            startActivity(intent);
            finish();
        });


        tabla = findViewById(R.id.tabla);
        //spinner_cliente = findViewById(R.id.spinner_cliente);
        formato = new DecimalFormat("#,###");
        suma_total = findViewById(R.id.total_v);
        listaCliente = findViewById(R.id.lista_cliente);
        scroll = findViewById(R.id.scroll_tabla);
        tb = new tabla(tabla,getApplicationContext());
        tb.cabecera(cabecera);
        tb.recibe_textview(suma_total);
        //tb.datos(datos());
        //agrega_cliente_a_spinner();

        listaCliente.setOnClickListener(v->{
            list_cliente();
            //finish();
        });





    }

    public void onBackPressed(){
        Intent intent = new Intent(agrega_venta.this, principal.class);
        startActivity(intent);
        //Toast.makeText(this,"no hace nada",Toast.LENGTH_SHORT).show();
    }

    public void list_cliente(){
        Intent intent_cliente = new Intent(agrega_venta.this,Lista_usuarios.class);
        startActivity(intent_cliente);
        //finishActivity(intent_cliente.getFlags());

    }

    public void textv(int fila, int celda){
        TableRow f = (TableRow) tabla.getChildAt(fila);
        text = (TextView) f.getChildAt(celda);
        System.out.println("el valor del text es :"+text.getText().toString());
    }
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Bundle valores = intent.getExtras();
        System.out.println("valooooor");


        if(valores!=null){
            System.out.println("este es el valor del intent cliente: "+valores.get("valor"));
            System.out.println("este es el valor del intent producto: "+valores.get("valor_producto"));
            //System.out.println("entro el valor"+valores.getString("valor"));
            if(valores.get("valor")!=null){

                listaCliente.setText(valores.getString("valor"));
            }
            if(valores.get("valor_producto")!=null){

                System.out.println("producto : "+valores.getString("valor_producto"));
                textv(Integer.parseInt(valores.getString("fila")),Integer.parseInt(valores.getString("celda")));
                text.setText(valores.getString("valor_producto"));
//                System.out.println("fila : "+);
//                System.out.println("celda : "+valores.getString("celda"));

            }

        }


    }

    /*public void agrega_fila(View v){

        tb.agrega_fila();

    }*/

    public void remover_fila(View v){

    }

    public void agrega_fila(View v){

        fila();

        //tb.focus_fila();
        //tb.color_filas();
        // scroll.fullScroll(View.FOCUS_DOWN);
    }

    public void fila(){
        tb.agrega_nueva_fila();
    }

    /*public void agrega_cliente_a_spinner(){

        ArrayList<String> lista = new ArrayList<>();

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
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_alejandro, lista);
        //spinner_cliente.setAdapter(adapter);


    }*/



    private ArrayList<String[]> datos(){
        filas.add(new String[]{"uno","dos","tres","dd","ff"});





        return filas;
    }

    public void obten_text_suma_total(TableLayout t){


        System.out.println("ESTE ES LA CANTIDAD DE FILAS QUE TIENE LA TABLA    "+t.getChildCount());
        //suma_total.setText("total");



        //return suma_total;
    }



    public void agrega_venta(View v){



        System.out.println(tabla.getChildCount());

        conexion con = new conexion(this,"bd_productos",null,version_bd.id);
        SQLiteDatabase db = con.getWritableDatabase();

        /*final String crea_tabla_ventas = "create table venta (ID INTEGER, CLIENTE TEXT, PRODUCTO TEXT," +
                " UNIDAD TEXT, PRECIO_BASE INTEGER, CANTIDAD INTEGER, VALOR_TOTAL INTEGER, FECHA TIMESTAMP)";*/

        ContentValues values = new ContentValues();
        Calendar c = Calendar.getInstance();

        Date d = Calendar.getInstance().getTime();

        SimpleDateFormat formato_fecha = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat formato_hora = new SimpleDateFormat("HH:mm");
        String fecha = formato_fecha.format(d);
        System.out.println("fechaaaaaaaaaaaaa b    "+fecha);
        String hora = formato_hora.format(d);

        if(listaCliente.getText().toString().equals("")){
            Toast.makeText(this,"Selecciona algun cliente",Toast.LENGTH_SHORT).show();
        }else {

            for (int i = 1; i < tabla.getChildCount(); i++) {

                TableRow tr = (TableRow) tabla.getChildAt(i);

                ID_VENTA id_v = new ID_VENTA();
                System.out.println("producto " + ((TextView) tr.getChildAt(1)).getText().toString());
                if (((TextView) tr.getChildAt(1)).getText().toString().trim().equals("")) {

                } else {
                    values.put("CLIENTE", listaCliente.getText().toString());
                    TextView celda_1 = (TextView) tr.getChildAt(1);
                    values.put("PRODUCTO", celda_1.getText().toString());
                    Spinner celda_2 = (Spinner) tr.getChildAt(2);
                    values.put("UNIDAD", celda_2.getSelectedItem().toString());
                    TextView celda_3 = (TextView) tr.getChildAt(3);
                    values.put("PRECIO_BASE", celda_3.getText().toString().replace(".", "").replace(",",""));
                    TextView celda_4 = (TextView) tr.getChildAt(4);
                    values.put("CANTIDAD", celda_4.getText().toString());
                    TextView celda_5 = (TextView) tr.getChildAt(5);
                    values.put("VALOR_TOTAL", celda_5.getText().toString().replace(".", "").replace(",",""));
                    values.put("FECHA", fecha);
                    values.put("HORA", hora);
                    TextView celda_7 = (TextView) tr.getChildAt(7);
                    values.put("PRECIO_COMISION", celda_7.getText().toString());


                    id += db.insert("venta", null, values);

                }


            }


            if (id > 0) {
                Toast.makeText(this, "Venta ingresada corrctamente", Toast.LENGTH_SHORT).show();

                System.out.println("cantidad de filas " + tabla.getChildCount());
                int cantidad = tabla.getChildCount();
                for (int i_ = 1; i_ < cantidad; i_++) {
                    tabla.removeViewAt(1);
                }

                fila();
                tb.volviendo_enumerar();
                id = 0;
            } else {
                Toast.makeText(this, "La venta no se ingreso", Toast.LENGTH_SHORT).show();
            }

        }

        System.out.println(tabla.getChildCount());

        /*Intent intent = getIntent();
        finish();
        startActivity(intent);*/



    }

    public void ver_ventas(View v){
        conexion con = new conexion(this,"bd_productos",null, version_bd.id);
        SQLiteDatabase db = con.getReadableDatabase();

        /*final String crea_tabla_ventas = "create table venta (ID INTEGER, CLIENTE TEXT, PRODUCTO TEXT," +
                " UNIDAD TEXT, PRECIO_BASE INTEGER, CANTIDAD INTEGER, VALOR_TOTAL INTEGER, FECHA TEXT, HORA TEXT)";*/

        String [] parametos ={};
        String [] campos ={"ID","CLIENTE","PRODUCTO","UNIDAD","PRECIO_BASE","CANTIDAD","VALOR_TOTAL","FECHA","HORA","PRECIO_COMISION"};

        Cursor cursor = db.query("venta",campos,null,null,null,null,null);

        while(cursor.moveToNext()){
            System.out.println("ID = "+cursor.getString(0)+"\n");
            System.out.println(cursor.getString(1).length()+"\n");
            System.out.println("CLIENTE = "+cursor.getString(1)+"\n");
            System.out.println("PRODUCTO = "+cursor.getString(2)+"\n");
            System.out.println("UNIDAD = "+cursor.getString(3)+"\n");
            System.out.println("PRECIO BASE = "+cursor.getString(4)+"\n");
            System.out.println("CANTIDAD = "+cursor.getString(5)+"\n");
            System.out.println("VALOR TOTAL = "+cursor.getString(6)+"\n");
            System.out.println("FECHA = "+cursor.getString(7)+"\n");
            System.out.println("HORA = "+cursor.getString(8)+"\n");
            System.out.println("PRECIO_COMISION = "+cursor.getString(9)+"\n\n");
            System.out.println("---------------------------------------------");

        }

    }






}