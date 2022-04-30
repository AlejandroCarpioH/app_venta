package com.example.my_app_comision.clases;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.my_app_comision.BDD.conexion;
import com.example.my_app_comision.Lista_usuarios;
import com.example.my_app_comision.R;
import com.example.my_app_comision.agrega_producto;
import com.example.my_app_comision.agrega_venta;
import com.example.my_app_comision.listaProducto;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class tabla {

    private TableLayout tabla;
    private Context context;
    private String[] cabecera;
    private ArrayList<String[]> datos;
    private TableRow filas;
    private TextView celdas;
    private int pos_fila, pos_celda;
    TextView precio;
    EditText cantidad;
    TextView total;
    TextView comision;
    TextView enumerando;
    TextView enumerando_2;
    View vv;
    String nombre_producto="";
    int enume=1;
    Button borrar;
    //private Spinner producto;
    TextView producto_text;
    int numero=1;
    boolean color = true;
    Spinner tipo; // tipo de unidad o display
    agrega_producto spinner_add = new agrega_producto();
    conexion con;
    TextView texto_total_Tabla;
    double imp_precio;


    public tabla(TableLayout tabla, Context context) {
        this.tabla = tabla;
        this.context = context;
        this.tabla.setBackgroundColor(Color.parseColor("#BBBBBB"));
        con = new conexion(context,"bd_productos",null,version_bd.id);


        //accion();

    }

    public void accion_lista_producto(View v){
        v.setOnClickListener((valor)->{
            list_producto((TextView) valor);

            
        });
    }

    public void list_producto(TextView v){

        System.out.println("la fila es "+ tabla.indexOfChild((TableRow)v.getParent()));
        TableRow fila = (TableRow) tabla.getChildAt(tabla.indexOfChild((TableRow)v.getParent()));
        System.out.println("la celda es "+ fila.indexOfChild(v));
        Intent intent_producto = new Intent(this.context, listaProducto.class);
        intent_producto.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent_producto.putExtra("fila",""+tabla.indexOfChild((TableRow)v.getParent()));
        intent_producto.putExtra("celda",""+fila.indexOfChild(v));

        this.context.startActivity(intent_producto);

    }

    public tabla(){
        //con = new conexion(context,"bd_productos",null,3);
    }

    public conexion Get_con(){
        return con;
    }

    public void cabecera(String[] cabecera){
        this.cabecera = cabecera;
        crea_cabecera();

    }

    public void datos(ArrayList<String[]> datos){
        this.datos = datos;
        //crea_datos_de_tabla();

    }

    public void nueva_fila(){
        filas = new TableRow(context);
        filas.setBackgroundColor(Color.parseColor("#BBBBBB"));



        filas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    public void focus_fila(){
        //tabla.getChildAt(tabla.getChildCount()-1).requestFocus();
        TableRow tb = (TableRow) tabla.getChildAt(tabla.getChildCount()-1);
        View tv = tb.getChildAt(1);
        tv.requestFocus();
    }

    public void remover(View v){

        vv=v;


        Animation an = AnimationUtils.loadAnimation(context,R.anim.animacion_borrar_fila);

        v.startAnimation(an);

        Handler handler = new Handler();

        handler.postDelayed(()-> {


                if(tabla.getChildCount()>2){
                    tabla.removeView(vv);
                    volviendo_enumerar();
                    suma_total_tabla();
                }
                repintar_celda();

        },300);











        //View fila = tabla.getChildAt(fila_pos);
        /*if(tabla.getChildCount()>2){
            tabla.removeView(v);
            volviendo_enumerar();
        }
        repintar_celda();
*/
        //recontar_btn();
    }

    public void nueva_celda(){
        celdas = new TextView(context);
        celdas.setGravity(Gravity.CENTER);
        celdas.setTextSize(20);


    }


    public void agrega_contenido(){
        enumerando = new EditText(context);
        enumerando_2 = new TextView(context);
        enumerando.setEnabled(false);
        enumerando.setTextColor(Color.parseColor("#000000"));
        //producto = new Spinner(context);
        producto_text = new TextView(context);
        producto_text.setGravity(Gravity.CENTER);
        producto_text.setTextColor(Color.parseColor("#000000"));
        accion_text_producto(producto_text);
        accion_lista_producto(producto_text);
        cantidad = new EditText(context);
        precio = new EditText(context);
        precio.setEnabled(false);
        precio.setTextColor(Color.parseColor("#000000"));
        precio.setGravity(Gravity.CENTER);
        total = new EditText(context);
        total.setEnabled(false);
        total.setTextColor(Color.parseColor("#000000"));
        comision = new TextView(context);

        cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);




    }

    public void color_filas(){
        if(color){
            filas.setBackgroundColor(Color.parseColor("#A9FDF8"));
            color=!color;
        }else{
            filas.setBackgroundColor(Color.parseColor("#DFF1EE"));
            color=!color;
        }

    }

    //agrega boton eliminar a la tabla
    public void agrega_btn(){
        borrar = new Button(context);
        //borrar.setTag(numero);

        borrar.setText("Eliminar N°  "+numero);
        numero++;
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View fila_padre = (View) v.getParent();
                remover(fila_padre);
                //Toast.makeText(context,"numero "+.getText(),Toast.LENGTH_LONG).show();

            }
        });
    }


//    public void nueva_celda_spinner(){
//
//        producto = new Spinner(context);
//
//        //String[] lista = {"lunes","martes","miercoles","martes","miercoles","martesgggggggggggggggggg","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles","martes","miercoles"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item_alejandro, agrega_datos_productos_spinner());
//        producto.setAdapter(adapter);
//        accion_spinner(producto);
//
//    }

    public void accion_text_producto(TextView tView){ // le da un evento al spinner producto al cambiar de valor llama a otros metodos

        tView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                TableRow tr = (TableRow) tView.getParent();
                Spinner sp_tipo = (Spinner)tr.getChildAt(2);
//                Spinner sp_producto = (Spinner)tr.getChildAt(1);
  //              EditText cantidad = (EditText) tr.getChildAt(4);

                /*if(!sp_producto.getSelectedItem().toString().equals("")){
                    cantidad.setText(1);
                }*/

                agrega_datos_sgun_spinner_tipo(tr, sp_tipo, tView);


            }
        });

//
//        sp_producto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                /*TableRow tr = (TableRow) view.getParent().getParent();
//                Spinner sp = (Spinner) view.getParent();
//                agrega_datos_a_tabla(tr,sp);
//                System.out.println(" producto                                   sss"+producto.getSelectedItem().toString());*/
//
//                TableRow tr = (TableRow) view.getParent().getParent();
//                Spinner sp_tipo = (Spinner)tr.getChildAt(2);
//                Spinner sp_producto = (Spinner)tr.getChildAt(1);
//                EditText cantidad = (EditText) tr.getChildAt(4);
//
//                /*if(!sp_producto.getSelectedItem().toString().equals("")){
//                    cantidad.setText(1);
//                }*/
//
//                agrega_datos_sgun_spinner_tipo(tr,sp_tipo);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    public void agrega_datos_a_tabla(TableRow tr , Spinner sp){  // agrega datos a la tabla precio POR EL NOMBRE DEL SPINNER SELECCIONADO
        //Toast.makeText(context,"aqui",Toast.LENGTH_SHORT).show();
        //TableRow tr =(TableRow) producto.getParent();
        /*TextView precio = (TextView) tr.getChildAt(3);
        SQLiteDatabase db = con.getReadableDatabase();
        String[] parametros ={sp.getSelectedItem().toString()};
        String[] campos = {"TOTAL"};
       // Toast.makeText(context,"aqui "+sp.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

        Cursor cursor = db.query("producto",campos,"NOMBRE=?",parametros,null,null,null);

        try {
            cursor.moveToNext();
            precio.setText(cursor.getString(0));
        }catch (Exception e){

        }*/

    }

    public ArrayList<String> agrega_datos_productos_textview(){

        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db = con.getReadableDatabase();
        String[] campos = {"NOMBRE"};
        Cursor cursor = db.query("producto",campos,null,null,null,null,"NOMBRE");

        while(cursor.moveToNext()){
            if (cursor.getString(0)!=""){

                lista.add(cursor.getString(0));
            }
            //System.out.println("este es el valor  rrrrrrrrrrrrrrr  "+cursor.getString(0));
        }
        con.close();
        db.close();
        return lista;
    }

//    public Spinner get_spinner(){ //retorna el spinner creado
//
//        return producto;
//    }


    public void spinner_tipo() {
        tipo = new Spinner(context);
        String[] lista_tipo = {"DSP", "UND"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item_alejandro, lista_tipo);
        tipo.setAdapter(adapter);
        //tipo.getSelectedItemPosition();
        spinner_tipo_accion(tipo);
    }

    //agrega accion al spinne de seleccion display o unidad
    public void spinner_tipo_accion(Spinner tipo_){

        System.out.println("entrooooooooooooooooooooooooooooooooo");
        tipo_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView) ((TableRow) ((Spinner) view.getParent()).getParent()).getChildAt(1);
                if(!tv.getText().toString().equals("")){


                    TableRow tr = (TableRow) view.getParent().getParent();
                    Spinner sp = (Spinner) view.getParent();

                    agrega_datos_sgun_spinner_tipo(tr,sp,tv);
                    //EditText
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void agrega_datos_sgun_spinner_tipo(TableRow tr, Spinner sp, TextView celda){


//        System.out.println(sp.getSelectedItem().toString());
//
//        System.out.println("ENTROOOOOOOOOOOO2222222222222222222222222222");

        TextView precio = (TextView) tr.getChildAt(3);
       // Spinner spinner_producto = (Spinner) tr.getChildAt(1);

        SQLiteDatabase db = con.getReadableDatabase();
        String[] parametros = {celda.getText().toString()};
        String[] campos={"DISP", "PRECIO_BASE", "IVA", "IMPUESTO"};
        Cursor cursor = db.query("producto",campos,"NOMBRE=?",parametros,null,null,null);

        int display;
        double precio_, total_precio_unidad;

        if(cursor.moveToFirst()){

            display = Integer.parseInt(cursor.getString(0));
            precio_ = Double.parseDouble(cursor.getString(1));

            if(!cursor.getString(2).equals("0")){
                switch (cursor.getString(3)){
                    case "31.5":
                        imp_precio = 1.505;
                        break;
                    case "20.5":
                        imp_precio = 1.395;
                        break;
                    case "18":
                        imp_precio = 1.37;
                        break;
                    case "10":
                        imp_precio = 1.29;
                        break;
                    case "0":
                        imp_precio = 1.19;
                        break;

                }
            }else{
                imp_precio = 1;
            }



            if(sp.getSelectedItem().toString().equals("DSP")){
                //Toast.makeText(context,"display",Toast.LENGTH_SHORT).show();
                DecimalFormat format = new DecimalFormat("##,###.##");
                precio.setText(""+format.format(Math.round((precio_*imp_precio)*display)));

            }else if(sp.getSelectedItem().toString().equals("UND")){
                total_precio_unidad = precio_*imp_precio;
                //Toast.makeText(context,""+total_precio_unidad,Toast.LENGTH_SHORT).show();
                DecimalFormat format = new DecimalFormat("##,###.##");
                precio.setText(""+format.format(Math.round(total_precio_unidad)));
            }

            //EditText total = (EditText) tr.getChildAt(5);
            //total.setText(""+precio_);

            calculo_cantidad_x_precio(tr);
            //accion_cantidad(tr);
            suma_total_tabla();


        }else{
            ((TextView) tr.getChildAt(3)).setText("");
            ((TextView) tr.getChildAt(4)).setText("1");
            ((TextView) tr.getChildAt(5)).setText("");
        }





    }

    public void crea_cabecera(){

        pos_celda=0;
        nueva_fila();
        while(pos_celda<cabecera.length){
            nueva_celda();
            celdas.setText(cabecera[pos_celda++]);
            celdas.setLayoutParams(nuevo_parametros_celda(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            celdas.setTextColor(Color.parseColor("#FFFFFF"));
            filas.addView(celdas);

        }

        tabla.addView(filas);
        TableRow tr = (TableRow) tabla.getChildAt(0);
        for (int i=0;i<tr.getChildCount();i++){
            tr.getChildAt(i).setBackgroundColor(Color.parseColor("#1B4BFF"));
        }
        agrega_nueva_fila();
        //agrega_datos_a_tabla();
        //filas.setBackgroundColor(Color.parseColor("#DFF1EE"));
        //Toast.makeText(context,"N° "+tabla.getChildCount(),Toast.LENGTH_LONG).show();

    }




    public void agrega_fila(){
        nueva_fila();
        for(int i=1;i<=cabecera.length;i++){
            //nueva_celda();
            celdas.setText("agregado");
            //filas.addView(celdas,nuevo_parametros_fila());
        }
        tabla.addView(filas);
    }

    public void volviendo_enumerar(){
        int num = tabla.getChildCount();
        TableRow v =(TableRow) tabla.getChildAt(0);
        int cantidad_colm = v.getChildCount();
        for (int i =1;i<num;i++){
            TableRow tr = (TableRow) tabla.getChildAt(i);
            TextView tv = (TextView)tr.getChildAt(0);
            tv.setText(""+i);
            Button b = (Button)tr.getChildAt(cantidad_colm-2);
            b.setText("Eliminar N° "+i);
        }
    }

    public void repintar_celda(){
        color=true;
        int filas = tabla.getChildCount();
        TableRow tr;
        String _color;
        for (int i=1;i<filas;i++){

            if(color){
                _color="#FFFFFF";
                color=!color;
            }else{
                _color="#A8ECFE";
                color=!color;
            }
            tr = (TableRow) tabla.getChildAt(i);
            for (int j=0;j<tr.getChildCount();j++){
                if(j!=1 && j!=2 &&j!=6){
                    tr.getChildAt(j).setBackgroundColor(Color.parseColor(_color));
                }

            }
        }
    }

    public void  agrega_nueva_fila(){

        String _color;
        if(color){
            _color="#FFFFFF";
            color=!color;
        }else{
            _color="#A8ECFE";
            color=!color;
        }

        nueva_fila();
        agrega_contenido();
        agrega_btn();


        filas.addView(enumerando);
        enumerando.setLayoutParams(nuevo_parametros_celda(60, TableRow.LayoutParams.WRAP_CONTENT));
        enumerando.setBackgroundColor(Color.parseColor(_color));
        enumerando.setGravity(Gravity.CENTER);
        enumerando.setText(""+enume);
        enume++;

        //nueva_celda_spinner();
        filas.addView(producto_text);
        //producto_text.setText(nombre_producto);
        producto_text.setLayoutParams(nuevo_parametros_celda(TableRow.LayoutParams.WRAP_CONTENT,100));
        producto_text.setBackgroundColor(Color.parseColor(_color));
        producto_text.setGravity(Gravity.CENTER);
        producto_text.setTextSize(20);
        //producto_text.setLayoutParams(new TableRow());


        enumerando_2.setLayoutParams(nuevo_parametros_celda(60, TableRow.LayoutParams.WRAP_CONTENT));
        enumerando_2.setBackgroundColor(Color.parseColor(_color));
        enumerando_2.setGravity(Gravity.CENTER);
        enumerando_2.setText("4");
        //producto_text.setLayoutParams(nuevo_parametros_celda(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


        spinner_tipo();
        filas.addView(tipo);
        tipo.setLayoutParams(nuevo_parametros_celda(350, TableRow.LayoutParams.WRAP_CONTENT));
        //tipo.setBackgroundColor(Color.parseColor(_color));

        filas.addView(precio);
        precio.setLayoutParams(nuevo_parametros_celda(200,TableRow.LayoutParams.WRAP_CONTENT));
        precio.setBackgroundColor(Color.parseColor(_color));

        filas.addView(cantidad);
        cantidad.setText("1");
        cantidad.setLayoutParams(nuevo_parametros_celda(150,TableRow.LayoutParams.WRAP_CONTENT));
        cantidad.setBackgroundColor(Color.parseColor(_color));

        filas.addView(total);
        total.setLayoutParams(nuevo_parametros_celda(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
        total.setBackgroundColor(Color.parseColor(_color));

        filas.addView(borrar);
        //borrar.setLayoutParams(nuevo_parametros_celda(350,TableRow.LayoutParams.WRAP_CONTENT));
        //borrar.setBackgroundColor(Color.parseColor(_color));




        filas.addView(comision);
        comision.setLayoutParams(nuevo_parametros_celda(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
        comision.setBackgroundColor(Color.parseColor(_color));
        comision.setText("0");
        comision.setTextSize(20);

        tabla.addView(filas);

        tabla.setColumnCollapsed(7,true);
        TableRow tr= (TableRow) cantidad.getParent();
        accion_cantidad(tr);


    }

    private TableRow.LayoutParams nuevo_parametros_celda(int ancho, int alto){

        TableRow.LayoutParams parametros = new TableRow.LayoutParams(ancho, alto);
        parametros.setMargins(5,5,5,5);
        return parametros;
    }

    public void accion(){

        tabla.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //Toast.makeText(context,"posicion "+ filas.getTag().toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void accion_cantidad(final TableRow tr){


        cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                //System.out.println(precio.getText().toString()+"   "+cantidad_.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

                calculo_cantidad_x_precio(tr);
                suma_total_tabla();

            }
        });

    }

    public void calculo_cantidad_x_precio(TableRow tr){


        TextView textview_producto = (TextView) tr.getChildAt(1);
        Spinner sp_und = (Spinner) tr.getChildAt(2);

        DecimalFormat format = new DecimalFormat("#,###");

        SQLiteDatabase db = con.getReadableDatabase();
        String[] parametros = {textview_producto.getText().toString()};
        String[] campos={"DISP","PRECIO_BASE","IMPUESTO"};
        Cursor cursor = db.query("producto",campos,"NOMBRE=?",parametros,null,null,null);

        EditText cantidad_= (EditText) tr.getChildAt(4);
        EditText precio = (EditText) tr.getChildAt(3);
        EditText total_ = (EditText) tr.getChildAt(5);
        TextView comision = (TextView) tr.getChildAt(7);

        if(cursor.moveToFirst()){
            int display = Integer.parseInt(cursor.getString(0));
            double precio_base = Double.parseDouble(cursor.getString(1));
            double total_precio_unidad;
            switch (cursor.getString(2)){
                case "31.5":
                    imp_precio = 1.505;
                    break;
                case "20.5":
                    imp_precio = 1.395;
                    break;
                case "18":
                    imp_precio = 1.37;
                    break;
                case "10":
                    imp_precio = 1.29;
                    break;

            }



            if(sp_und.getSelectedItem().toString().equals("UND")){
                if(cantidad_.getText().toString().equals("")){
                    total_.setText("0");
                    comision.setText("0");
                }
                else{
                    total_.setText(""+format.format(Math.round((precio_base*imp_precio)*Integer.parseInt(cantidad_.getText().toString()))));
                    comision.setText(""+Math.round(precio_base*Integer.parseInt(cantidad_.getText().toString())));
                }

            }else if(sp_und.getSelectedItem().toString().equals("DSP")){
                if(cantidad_.getText().toString().equals("")){
                    total_.setText("0");
                    comision.setText("0");
                }
                else{
                    total_.setText(""+format.format(Math.round(((precio_base*imp_precio)*display)*Integer.parseInt(cantidad_.getText().toString()))));
                    comision.setText(""+Math.round((precio_base*display)*Integer.parseInt(cantidad_.getText().toString())));
                }
            }

        }else{
            total_.setText("0");
        }


        //System.out.println("no se que saldra aqui "+s);



        //suma_total_tabla();

    }

    public void suma_total_tabla(){

        agrega_venta v = new agrega_venta();

        int suma=0;
        TextView suma_total;
        System.out.println(" NUMERO DE FILAS EN LA TABLA"+tabla.getChildCount());

        for (int i=1;i<=tabla.getChildCount()-1;i++){
            TableRow tr = (TableRow) tabla.getChildAt(i);
            suma_total = (TextView) tr.getChildAt(5);
            System.out.println("aquiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii  "+suma_total.getText().toString()  +"   "+i);
            if(!suma_total.getText().toString().equals("")){
                suma+= Integer.parseInt(suma_total.getText().toString().replace(".","").replace(",",""));
            }
        }

        //v.obten_text_suma_total(tabla);

        envia_valor_total_atxtv(suma);

    }

    public void recibe_textview(TextView tv){

        texto_total_Tabla = tv;
        //suma_total_tabla();

    }

    public void envia_valor_total_atxtv(int suma){

        DecimalFormat formato = new DecimalFormat("#,###");

        texto_total_Tabla.setText("$ "+formato.format(suma));

    }

    public void enviar_valor_productotext(String valor) {
       // producto_text.setText(valor);

        System.out.println("el valor del producto es : "+valor);

    }
}
