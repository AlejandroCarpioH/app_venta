package com.example.my_app_comision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_app_comision.BDD.conexion;
import com.example.my_app_comision.clases.version_bd;

import java.util.ArrayList;

public class consulta_edita_producto_cliente extends AppCompatActivity {

    TableLayout tabla;
    TableRow fila_cabecera;
    TableRow fila;
    conexion con;
    ArrayList lista_id = new ArrayList();
    Boolean sw = true;
    boolean valida = true;

    //String nombre_usuario_edit="";

    Button btn_eliminar;
    String opcion;
    String sw_btn="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_edita_producto_cliente);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tabla = findViewById(R.id.tabla);
        con = new conexion(this,"bd_productos",null, version_bd.id);
        btn_eliminar = findViewById(R.id.boton_activa_eliminar);
        btn_eliminar.setVisibility(View.INVISIBLE);
    }

    public void accion_boton_muestra_clientes(View v){
        if(!sw_btn.equals("cliente")){
            muestra_clientes();
            opcion="cliente";
            btn_eliminar.setVisibility(View.VISIBLE);
            sw_btn="cliente";
            btn_eliminar.setText("ACTIVA COLUMNA ELIMINAR");
            sw=true;
        }

        ;    }
    public void accion_boton_muestra_productos(View v){
        if(!sw_btn.equals("procuto")){
            muestra_producto();
            opcion="producto";
            btn_eliminar.setVisibility(View.VISIBLE);
            sw_btn="procuto";
            btn_eliminar.setText("ACTIVA COLUMNA ELIMINAR");
            sw=true;
        }

    }

    public void muestra_clientes(){

        tabla.removeAllViews();

        TextView id = new TextView(this);
        id.setText("ID");
        TextView rut = new TextView(this);
        rut.setText("RUT");
        TextView nombre = new TextView(this);
        nombre.setText("NOMBRE");
        TextView apellido = new TextView(this);
        apellido.setText("APELLIDO");
        TextView direccion = new TextView(this);
        direccion.setText("DIRECCION");
        TextView editar = new TextView(this);
        editar.setText("EDITAR");
        TextView eliminar = new TextView(this);
        eliminar.setText("ELIMINAR");

        fila_cabecera = new TableRow(this);

        fila_cabecera.addView(id);
        id.setLayoutParams(nuevo_parametros_celda(250,80));
        id.setTextSize(20);
        id.setGravity(Gravity.CENTER);
        id.setTextColor(Color.WHITE);
        id.setBackgroundColor(Color.BLUE);
        fila_cabecera.addView(rut);
        rut.setLayoutParams(nuevo_parametros_celda(250,80));
        rut.setTextSize(20);
        rut.setGravity(Gravity.CENTER);
        rut.setTextColor(Color.WHITE);
        rut.setBackgroundColor(Color.BLUE);
        fila_cabecera.addView(nombre);
        nombre.setTextSize(20);
        nombre.setLayoutParams(nuevo_parametros_celda(300,80));
        nombre.setGravity(Gravity.CENTER);
        nombre.setTextColor(Color.WHITE);
        nombre.setBackgroundColor(Color.BLUE);
        fila_cabecera.addView(apellido);
        apellido.setTextSize(20);
        apellido.setLayoutParams(nuevo_parametros_celda(300,80));
        apellido.setGravity(Gravity.CENTER);
        apellido.setTextColor(Color.WHITE);
        apellido.setBackgroundColor(Color.BLUE);
        fila_cabecera.addView(direccion);
        direccion.setTextSize(20);
        direccion.setLayoutParams(nuevo_parametros_celda(300,80));
        direccion.setGravity(Gravity.CENTER);
        direccion.setTextColor(Color.WHITE);
        direccion.setBackgroundColor(Color.BLUE);
        fila_cabecera.addView(editar);
        editar.setTextSize(20);
        editar.setLayoutParams(nuevo_parametros_celda(300,80));
        editar.setGravity(Gravity.CENTER);
        editar.setTextColor(Color.WHITE);
        editar.setBackgroundColor(Color.BLUE);
        fila_cabecera.addView(eliminar);
        eliminar.setTextSize(20);
        eliminar.setLayoutParams(nuevo_parametros_celda(300,80));
        eliminar.setGravity(Gravity.CENTER);
        eliminar.setTextColor(Color.WHITE);
        eliminar.setBackgroundColor(Color.BLUE);



        //fila.setBackgroundColor(Color.BLUE);
        tabla.addView(fila_cabecera);
        tabla.setBackgroundColor(Color.WHITE);

        /*-------------EXTRAE DATOS DE LA BASE DE DATOS*/


        SQLiteDatabase db = con.getReadableDatabase();
        String [] campos = {"ID","RUT","NOMBRE","APELLIDO","DIRECCION"};

        Cursor cursor = db.query("cliente",campos,null,null,null,null,"NOMBRE");

        EditText celda;
        Button boton;

        while(cursor.moveToNext()){
            if(cursor.getString(2).equals("Todos")){

            }else{
                fila = new TableRow(this);
                for(int i=0;i<=6;i++){
                    if(i==5){
                        boton = new Button(this);
                        boton.setText("EDITAR");
                        boton_accion(boton,this);
                        fila.addView(boton);

                    }else if(i==6){
                        boton = new Button(this);
                        boton.setText("ELIMINAR");
                        boton.setBackgroundColor(Color.parseColor("#BC2A2A"));
                        boton.setTextColor(Color.WHITE);

                        boton_accion(boton,this);
                        fila.addView(boton);
                    }else{
                        celda = new EditText(this);
                        celda.setText(cursor.getString(i));
                        celda.setEnabled(false);
                        celda.setTextColor(Color.BLACK);
                        fila.addView(celda);
                    }


                }
                tabla.addView(fila);

            }

        }


        tabla.setColumnCollapsed(6,true);
        tabla.setColumnCollapsed(0,true);
        tabla.setColumnCollapsed(3,false);

    }

    public void muestra_producto() {

        tabla.setColumnCollapsed(3,false);
        tabla.setColumnCollapsed(0,false);
        tabla.setColumnCollapsed(6,false);
        tabla.setColumnCollapsed(8,true);
        tabla.removeAllViews();

        //producto (ID INTEGER, NOMBRE TEXT,DISP INTEGER, PRECIO_BASE REAL, IVA INTEGER, IMPUESTO INTEGER, TOTAL INTEGER)";

        String[] cabecera = {"ID", "NOMBRE", "DISP", "PRECIO BASE","IVA", "IMPUESTO","PRECIO TOTAL","EDITAR","ELIMINAR"};

        fila_cabecera = new TableRow(this);
        for (int i = 0; i <= cabecera.length - 1; i++) {

            int ancho = 0;

            switch (i) {
                case 0:
                    ancho = 100;
                    break;
                case 1:
                    ancho = 300;
                    break;
                case 2:
                    ancho = 200;
                    break;
                case 3:
                    ancho = 200;
                    break;
                case 4:
                    ancho = 250;
                    break;
                default:
                    ancho = 300;

            }

            TextView textv = new TextView(this);
            textv.setText(cabecera[i]);
            textv.setLayoutParams(nuevo_parametros_celda(TableLayout.LayoutParams.MATCH_PARENT, 80));
            textv.setTextSize(20);
            textv.setGravity(Gravity.CENTER);
            textv.setTextColor(Color.WHITE);
            textv.setBackgroundColor(Color.BLUE);
            fila_cabecera.addView(textv);
        }

        tabla.addView(fila_cabecera);

        tabla.setColumnCollapsed(0,true);
        //tabla.setColumnCollapsed(3,false);
        //tabla.setColumnCollapsed(7,true);


        //producto (ID INTEGER, NOMBRE TEXT,DISP INTEGER, PRECIO_BASE REAL, IVA INTEGER, IMPUESTO INTEGER, TOTAL INTEGER)";

        SQLiteDatabase db = con.getReadableDatabase();
        String [] parametros={};
        String [] campos={"ID","NOMBRE","DISP","PRECIO_BASE","IVA","IMPUESTO","TOTAL"};

        Cursor cursor = db.query("producto",campos,null,null,null,null,"NOMBRE");




        // inserta celdas de los datos de los productos

        cursor.moveToNext();
        while(cursor.moveToNext()){
            fila = new TableRow(this);
            for (int i=0;i<=campos.length-1;i++){

                if(i==3){
                    EditText editext = new EditText(this);
                    editext.setText(""+cursor.getDouble(i));
                    editext.setEnabled(false);
                    editext.setTextColor(Color.BLACK);
                    fila.addView(editext);
                    System.out.println("si hay filas");
                }else{
                    EditText editext = new EditText(this);
                    editext.setText(cursor.getString(i));
                    editext.setEnabled(false);
                    editext.setTextColor(Color.BLACK);
                    fila.addView(editext);
                    System.out.println("si hay filas");

                }

            }
            Button boton = new Button(this);
            boton.setText("EDITAR");
            boton_edit_producto(boton,this);

            Button boton_eliminar = new Button(this);
            boton_eliminar.setText("ELIMINAR");
            elimina_producto(boton_eliminar);
            //boton.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


            fila.addView(boton);
            fila.addView(boton_eliminar);
            tabla.addView(fila);

        }




        //Text

    }

    private TableRow.LayoutParams nuevo_parametros_celda(int ancho, int alto){

        TableRow.LayoutParams parametros = new TableRow.LayoutParams(ancho, alto);
        parametros.setMargins(5,0,5,0);
        return parametros;
    }

    public void elimina_producto(Button boton){



        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow tr = (TableRow) v.getParent();
                EditText id = (EditText) tr.getChildAt(0);

                SQLiteDatabase sql = con.getWritableDatabase();
                String [] parametros ={id.getText().toString()};

                sql.delete("producto","ID=?",parametros);
                tabla.removeView(tr);

            }
        });

    }


    public void boton_edit_producto(final Button boton,final Context context){


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button boton_ = (Button) v;
                TableRow tr = (TableRow) v.getParent();

                if(boton_.getText().toString().equals("EDITAR")){  //HACE EDITABLE LAS CELDAS PARA PODER CAMBIAR EL VALOR
                    System.out.println("entro en editar ------------------------------");

                    ArrayList<String> lista = new ArrayList<>();
                    lista.add("31.5");
                    lista.add("20.5");
                    lista.add("18");
                    lista.add("10");

                    EditText impuesto;
                    impuesto = (EditText) tr.getChildAt(5);
                    EditText edit;
                    //System.out.println("cantidad de filas "+ tr.getChildCount());
                    for(int i=0;i<tr.getChildCount();i++){

                        if(i==5){
                            Spinner spinner = new Spinner(context);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item_alejandro, lista);
                            spinner.setAdapter(adapter);
                            switch (impuesto.getText().toString()){
                                case "31.5":
                                    spinner.setSelection(0);
                                    break;
                                case "20.5":
                                    spinner.setSelection(1);
                                    break;
                                case "18":
                                    spinner.setSelection(2);
                                    break;
                                case "10":
                                    spinner.setSelection(3);
                                    break;
                                default:
                            }
                            tr.removeViewAt(5);
                            tr.addView(spinner,5);
                        }else if(i==7){
                            boton_.setText("ACEPTAR");

                        }else if(i==8){

                        }
                        else{
                            edit = new EditText(context);
                            edit = (EditText) tr.getChildAt(i);
                            edit.setEnabled(true);
                        }

                    }


                }else if(boton_.getText().toString().equals("ACEPTAR")){  //INGRESA LOS VALORES CAMBIADOS A LA BASE DE DATOS REALIZA UN UPDATE

                    EditText numero = (EditText) tr.getChildAt(3);

                    for(int i=0;i<numero.length();i++){
                        if(numero.getText().toString().charAt(i)=='0' || numero.getText().toString().charAt(i)=='1' ||numero.getText().toString().charAt(i)=='2'
                                || numero.getText().toString().charAt(i)=='3' || numero.getText().toString().charAt(i)=='4' || numero.getText().toString().charAt(i)=='5'
                                || numero.getText().toString().charAt(i)=='6' || numero.getText().toString().charAt(i)=='7' || numero.getText().toString().charAt(i)=='8'
                                || numero.getText().toString().charAt(i)=='9' || numero.getText().toString().charAt(i)=='.'){
                            valida=true;
                        }else{
                            valida=false;
                        }
                    }

                    System.out.println(numero.getText().toString());

                    if(valida){

                        SQLiteDatabase sql = con.getWritableDatabase();

                        EditText id = (EditText) tr.getChildAt(0);
                        String [] parametros ={id.getText().toString()};
                        //String [] campos ={"NOMBRE","DISP","PRECIO_BASE","IVA","IMPUESTO","TOTAL"};

                        ContentValues values = new ContentValues();

                        ArrayList<String> campos = new ArrayList<>();
                        campos.add("NOMBRE");
                        campos.add("DISP");
                        campos.add("PRECIO_BASE");
                        campos.add("IVA");
                        campos.add("IMPUESTO");
                        campos.add("TOTAL");

                        Spinner spinner = (Spinner) tr.getChildAt(5);
                        EditText precio_base = (EditText) tr.getChildAt(3);
                        int total=0;

                        for (int i=1;i<=campos.size();i++){

                            if(i==3){

                                values.put(campos.get(i-1),precio_base.getText().toString());
                            }else if(i==5){

                                values.put(campos.get(i-1),spinner.getSelectedItem().toString());
                            }else if(i==6) {

                                switch (spinner.getSelectedItem().toString()){ // a todos los valores se le suma el iva 19 para poder sacar el calculo del precio base
                                    case "31.5":
                                        total = (int) Math.round(Double.parseDouble(precio_base.getText().toString())*1.505);
                                        break;
                                    case "20.5":
                                        total = (int) Math.round(Double.parseDouble(precio_base.getText().toString())*1.395);
                                        break;
                                    case "18":
                                        total = (int) Math.round(Double.parseDouble(precio_base.getText().toString())*1.37);
                                        break;
                                    case "10":
                                        total = (int) Math.round(Double.parseDouble(precio_base.getText().toString())*1.29);
                                        break;
                                    default:

                                }

                                values.put(campos.get(i-1),total);
                            }else{
                                EditText editext = (EditText) tr.getChildAt(i);
                                values.put(campos.get(i-1),editext.getText().toString());
                            }

                        }


                        sql.update("producto",values,"ID=?",parametros);


                        SQLiteDatabase SQL_ = con.getReadableDatabase();
                        String []campos_ = {"ID","NOMBRE","DISP","PRECIO_BASE","IVA","IMPUESTO","TOTAL"};
                        String []parametros_ = {id.getText().toString()};

                        Cursor cursor = SQL_.query("producto",campos_,"ID=?",parametros,null,null,null);


                        while (cursor.moveToNext()){

                            System.out.println(tr.getChildCount()+" cantidad de columnas sssssssssssss");

                            tabla.setColumnCollapsed(0,false);

                            for (int i=0;i<tr.getChildCount()-2;i++){
                                if(i==3){
                                    EditText editext_ = new EditText(context);
                                    editext_.setText(""+cursor.getDouble(i));
                                    editext_.setEnabled(false);
                                    editext_.setTextColor(Color.BLACK);
                                    tr.removeViewAt(i);
                                    tr.addView(editext_,i);
                                }else{
                                    EditText editext_ = new EditText(context);
                                    editext_.setText(cursor.getString(i));
                                    editext_.setEnabled(false);
                                    editext_.setTextColor(Color.BLACK);
                                    tr.removeViewAt(i);
                                    tr.addView(editext_,i);
                                }


                                //tr.
                            }
                            tabla.setColumnCollapsed(0,true);


                        }


                        //producto (ID INTEGER, NOMBRE TEXT,DISP INTEGER, PRECIO_BASE REAL, IVA INTEGER, IMPUESTO INTEGER, TOTAL INTEGER)";
                        boton_.setText("EDITAR");
                        System.out.println("entro en aceptar -------------------------------------------------------");
                    }else{
                        Toast.makeText(context,"Ingresa un valor en un formato valido Ejem: '123.456' numero y puntos",Toast.LENGTH_LONG).show();
                    }

                }


            }
        });






    }

    public void boton_accion(final Button b, final Context c){  //edita clientes




        if(b.getText().toString().equals("EDITAR")){
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //System.out.println(nombre_usuario_edit.getText().toString());
                    TableRow fila = (TableRow) v.getParent();

                    fila.getChildAt(1).setEnabled(true);
                    fila.getChildAt(1).setBackgroundColor(Color.parseColor("#D5FFFB"));
                    fila.getChildAt(1).setLayoutParams(nuevo_parametros_celda_editar());

                    fila.getChildAt(2).setEnabled(true);
                    fila.getChildAt(2).setBackgroundColor(Color.parseColor("#D5FFFB"));
                    fila.getChildAt(2).setLayoutParams(nuevo_parametros_celda_editar());

                    fila.getChildAt(3).setEnabled(true);
                    fila.getChildAt(3).setBackgroundColor(Color.parseColor("#D5FFFB"));
                    fila.getChildAt(3).setLayoutParams(nuevo_parametros_celda_editar());

                    fila.getChildAt(4).setEnabled(true);
                    fila.getChildAt(4).setBackgroundColor(Color.parseColor("#D5FFFB"));
                    fila.getChildAt(4).setLayoutParams(nuevo_parametros_celda_editar());


                    fila.removeViewAt(5);
                    Button boton = new Button(c);
                    boton.setText("ACEPTAR");
                    boton.setBackgroundColor(Color.parseColor("#AE4C41"));
                    boton.setTextColor(Color.WHITE);
                    boton_accion(boton,c);
                    fila.addView(boton,5);


                }
            });

        }else if(b.getText().toString().equals("ELIMINAR")){
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TableRow fila = (TableRow) v.getParent();

                    final TableRow tr = fila;

                    SQLiteDatabase db = con.getWritableDatabase();

                    EditText id = (EditText) fila.getChildAt(0);
                    String [] parametros = {id.getText().toString()};

                    db.delete("cliente","ID=?",parametros);

                    Animation an = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animacion_borrar_fila);

                    fila.startAnimation(an);

                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(tabla.getChildCount()>1){
                                tabla.removeView(tr);
                                //volviendo_enumerar();
                                //suma_total_tabla();
                            }
                            //repintar_celda();
                        }

                    },300);
                    //tabla.removeView(fila);
                    //muestra_clientes();
                    //System.out.println("eliminar");

                }
            });

        }else if(b.getText().toString().equals("ACEPTAR")){
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("aceptar");

                    TableRow fila = (TableRow) v.getParent();

                    //EditText ed = (EditText) fila.getChildAt(0);

                    //System.out.println("ESTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE ESSSSSSSSSSSSSSSSSS "+ ed.getText().toString());

                    SQLiteDatabase db = con.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    EditText id = (EditText) fila.getChildAt(0);
                    String [] parametros = {id.getText().toString()};

                    /*   --------------      */

                    SQLiteDatabase db_ = con.getReadableDatabase();

                    String [] campos = {"NOMBRE"};
                    Cursor cursor = db_.query("cliente",campos,"ID=?",parametros,null,null,null);

                    cursor.moveToNext();
                    String nombre_usuario_edit = cursor.getString(0);
                    /*   --------------    */

                    EditText rut = (EditText) fila.getChildAt(1);
                    values.put("RUT",rut.getText().toString());
                    EditText nombre = (EditText) fila.getChildAt(2);
                    values.put("NOMBRE",nombre.getText().toString());
                    EditText apellido = (EditText) fila.getChildAt(3);
                    values.put("APELLIDO",apellido.getText().toString());
                    EditText direccion = (EditText) fila.getChildAt(4);
                    values.put("DIRECCION",direccion.getText().toString());

                    db.update("cliente",values,"ID=?",parametros);

                    SQLiteDatabase db_d = con.getReadableDatabase();

                    EditText id_ = (EditText) fila.getChildAt(0);

                    String [] parametros_ = {id_.getText().toString()};
                    String [] campos_ = {"ID","RUT","NOMBRE","APELLIDO","DIRECCION"};
                    Cursor cursor_ = db_d.query("cliente",campos_,"ID=?",parametros_,null,null,null);

                    //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa    "+);



                    if(cursor_.moveToNext()){
                        for (int i = 1; i <= 4; i++) {
                            EditText et = new EditText(c);
                            et.setText(cursor_.getString(i));
                            fila.removeViewAt(i);
                            fila.addView(et, i);
                        }
                    }




                    fila.removeViewAt(5);
                    Button boton = new Button(c);
                    boton.setText("EDITAR");
                    boton_accion(boton,c);
                    fila.addView(boton,5);

                    for(int i=1;i<=4;i++){
                        fila.getChildAt(i).setEnabled(false);
                        EditText celda = (EditText) fila.getChildAt(i);
                        celda.setTextColor(Color.BLACK);
                    }


                    /*ACTUALIZA LOS NOMBRES A LAS VENTAS */

                    System.out.println(" NOMBREEEEEEEEEEEEEEEEES  "+nombre.getText().toString()+"  "+nombre_usuario_edit);

                    if(nombre.getText().toString().equals(nombre_usuario_edit)){

                    }else{
                        SQLiteDatabase sql_edit_ventas = con.getWritableDatabase();
                        ContentValues values_ventas = new ContentValues();
                        String [] parametros_ventas ={nombre_usuario_edit};

                        values_ventas.put("CLIENTE",nombre.getText().toString());

                        sql_edit_ventas.update("venta",values_ventas,"CLIENTE=?",parametros_ventas);
                        //sql_edit_ventas.query("UPDATE VENTA SET CLIENTE="+nombre.getText().toString()+"",null);
                        //sql_edit_ventas.rawQuery("UPDATE venta SET CLIENTE='"+nombre.getText().toString()+"'WHERE CLIENTE='"+nombre_usuario_edit+"'",null);
                        System.out.println(" NOMBREEEEEEEEEEEEEEEEES  cambiaaaa"+ nombre_usuario_edit+"  "+nombre.getText().toString());

                        System.out.println(nombre_usuario_edit.length()+"      "+nombre.getText().toString().length());



                    }


                }
            });





        }


    }

    private TableRow.LayoutParams nuevo_parametros_celda_editar(){

        TableRow.LayoutParams parametros = new TableRow.LayoutParams();
        parametros.setMargins(5,0,5,0);
        return parametros;
    }

    public void activa_eliminar(View v){

        Button boton =(Button) v;

        if(opcion.equals("cliente")){

            if(sw){
                tabla.setColumnCollapsed(6,false);
                sw=false;
                boton.setText("DESACTIVA COLUMNA ELIMINAR");


            }else if(!sw){
                tabla.setColumnCollapsed(6,true);
                sw=true;
                boton.setText("ACTIVA COLUMNA ELIMINAR");
            }
        }else if(opcion.equals("producto")){
            if(sw){
                tabla.setColumnCollapsed(8,false);
                sw=false;
                boton.setText("DESACTIVA COLUMNA ELIMINAR");


            }else if(!sw){
                tabla.setColumnCollapsed(8,true);
                sw=true;
                boton.setText("ACTIVA COLUMNA ELIMINAR");
            }
        }




    }
}