package com.example.my_app_comision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_app_comision.BDD.conexion;
import com.example.my_app_comision.clases.datepickerfragment;
import com.example.my_app_comision.clases.version_bd;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class consulta_venta extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


    TableLayout tabla;
    conexion con;
    EditText fecha_1;
    EditText fecha_2;
    EditText fecha_opt;
    Spinner sp_cliente;

    String datos="";


    RadioButton radio_btn_1;
    RadioButton radio_btn_2;
    RadioButton radio_btn_3;
    RadioGroup radio_grup;
    boolean btn_activado=false;
    DecimalFormat formato;
    TextView suma_total;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_venta);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tabla = (TableLayout) findViewById(R.id.tabla_edit_venta);
        con = new conexion(this,"bd_productos",null, version_bd.id);
        fecha_1 = findViewById(R.id.fecha_1);
        fecha_2 = findViewById(R.id.fecha_2);
        fecha_1.setVisibility(View.INVISIBLE);
        fecha_2.setVisibility(View.INVISIBLE);
        fecha_1.setOnClickListener(accion_editext());
        fecha_2.setOnClickListener(accion_editext());



        formato = new DecimalFormat("#,###");

        suma_total = findViewById(R.id.suma_total);


        sp_cliente = (Spinner) findViewById(R.id.sp_cliente);
        agrega_clientes_spinner();

        radio_btn_1 = findViewById(R.id.rbtn_op_1);
        radio_btn_2 = findViewById(R.id.rbtn_op_2);
        radio_btn_3 = findViewById(R.id.rbtn_op_3);

        radio_grup = findViewById(R.id.radioGroup);
        radio_grup.setOnCheckedChangeListener(accion_radio_group());

        cabecera();
    }

    public void agrega_clientes_spinner(){
        ArrayList<String> lista = new ArrayList<>();

        conexion con = new conexion(this,"bd_productos",null,version_bd.id);
        SQLiteDatabase db = con.getReadableDatabase();

        String [] campos = {"NOMBRE","APELLIDO"};
        Cursor cursor = db.query("cliente",campos,null,null,null,null,"NOMBRE");

        for (int i=0;i<lista.size();i++){
            lista.remove(i);
        }
        //lista.add("Todas las ventas");

        while(cursor.moveToNext()){
            String nombre = (cursor.getString(0)+" "+cursor.getString(1));
            lista.add(nombre.trim());
            System.out.println(" holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+cursor.getString(0));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_alejandro, lista);
        sp_cliente.setAdapter(adapter);
        for(int i=0;i<sp_cliente.getAdapter().getCount();i++){
            if(sp_cliente.getItemAtPosition(i).toString().trim().equals("Todos")){

                sp_cliente.setSelection(i);

            }
        }


    }

    public RadioGroup.OnCheckedChangeListener accion_radio_group(){
        return (RadioGroup group, int checkedId)-> {
           {
                fecha_1.setVisibility(View.INVISIBLE);
                fecha_2.setVisibility(View.INVISIBLE);
                if(radio_btn_1.isChecked()){
                    fecha_1.setVisibility(View.VISIBLE);

                }else if(radio_btn_2.isChecked()){
                    fecha_1.setVisibility(View.VISIBLE);
                    fecha_2.setVisibility(View.VISIBLE);
                }
                else if(radio_btn_3.isChecked()){
                    fecha_1.setVisibility(View.INVISIBLE);
                    fecha_2.setVisibility(View.INVISIBLE);
                }
            }
        };
    }


    public void cabecera(){

        TableRow filas = new TableRow(this);

        ArrayList<String> cabecera = new ArrayList<>();
        cabecera.add("ID");//id
        cabecera.add("N°");//numero
        cabecera.add("PRODUCTO");//producto
        cabecera.add("UNIDAD");///unidad
        cabecera.add("PRECIO");//valor
        cabecera.add("CANTIDAD");//cantidad
        cabecera.add("TOTAL");//total
        cabecera.add("FECHA");//fecha
        cabecera.add("EDITAR");//editar
        cabecera.add("ELIMINAR");//eliminar




        for (int i=0;i<cabecera.size();i++){
            TextView tv = new TextView(this);
            tv.setText(cabecera.get(i));
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(Color.BLUE);
            tv.setLayoutParams(layoutparams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT));
            tv.setPadding(20,0,20,0);
            tv.setTextSize(20);
            filas.addView(tv);
        }

        tabla.addView(filas);
        tabla.setBackgroundColor(Color.parseColor("#BBBBBB"));

        tabla.setColumnCollapsed(0,false);
        //tabla.setColumnCollapsed(7,true);

    }

    public TableRow.LayoutParams layoutparams(int ancho, int alto){ // le da un ancho y alto y un margen a las celdas

        TableRow.LayoutParams parametros = new TableRow.LayoutParams(ancho,alto);
        parametros.setMargins(5,0,5,0);
        return parametros;
    }

    public void consulta_venta(View view){
        agrega_ventas_a_tabla();
    }

    public void agrega_ventas_a_tabla(){

        String condicio_where ="";
        String condicion_and="";

        if(!sp_cliente.getSelectedItem().toString().equals("Todos")){
            condicio_where ="WHERE CLIENTE = '"+sp_cliente.getSelectedItem().toString()+"'";
            condicion_and = "AND CLIENTE = '"+sp_cliente.getSelectedItem().toString()+"'";
        }else{

        }


        SQLiteDatabase sql = con.getReadableDatabase();

        if(radio_btn_1.isChecked()){
            if(fecha_1.getText().toString().equals("") || sp_cliente.getSelectedItem().toString().equals("")){
                System.out.println("entro a nada");
                Toast.makeText(this,"Ingresa una fecha",Toast.LENGTH_SHORT).show();
            }else{
                //SQLiteDatabase sql = con.getReadableDatabase();
                while(tabla.getChildCount()>1){
                    tabla.removeViewAt(1);
                }

                String [] parametros = {fecha_1.getText().toString()};
                //"table venta (ID INTEGER, CLIENTE TEXT, PRODUCTO TEXT, UNIDAD TEXT, PRECIO_BASE INTEGER, CANTIDAD INTEGER, VALOR_TOTAL INTEGER, FECHA TEXT, HORA TEXT)";
                String  campos = "ID, CLIENTE, PRODUCTO, UNIDAD, PRECIO_BASE, CANTIDAD, VALOR_TOTAL, FECHA";

                //Cursor cursor = sql.query("venta",campos,"FECHA=?",parametros,null,null,null);
                Cursor cursor = sql.rawQuery("SELECT "+campos+" FROM VENTA WHERE FECHA = '"+fecha_1.getText().toString()+"' "+condicion_and+" ORDER BY CLIENTE" ,null);
                //Cursor cursor_com = sql.rawQuery("SELECT NO FROM VENTA WHERE FECHA = '"+fecha_1.getText().toString()+"' "+condicion_and+"" ,null);
                muestra_Datos_en_tabla(cursor);




                //KJKJKJ



            }
        }else if(radio_btn_2.isChecked()){

            System.out.println("esto hay en fecha 2 "+fecha_2.getText().toString());
            if(fecha_1.getText().toString().equals("") || fecha_2.getText().toString().equals("")){
                Toast.makeText(this,"Te falata ingresar un fecha ",Toast.LENGTH_SHORT).show();
            }else{

                while(tabla.getChildCount()>1){
                    tabla.removeViewAt(1);
                }

                String [] parametros = {fecha_1.getText().toString()};
                //"table venta (ID INTEGER, CLIENTE TEXT, PRODUCTO TEXT, UNIDAD TEXT, PRECIO_BASE INTEGER, CANTIDAD INTEGER, VALOR_TOTAL INTEGER, FECHA TEXT, HORA TEXT)";
                //String [] campos = {"ID","PRODUCTO","UNIDAD","PRECIO_BASE","CANTIDAD","VALOR_TOTAL"};
                String  campos = "ID, CLIENTE, PRODUCTO,UNIDAD,PRECIO_BASE,CANTIDAD,VALOR_TOTAL,FECHA";
                //Cursor cursor = sql.query("venta",campos,"FECHA=?",parametros,null,null,null);
                String sql_ ="";
                Cursor cursor = sql.rawQuery("SELECT "+campos+" FROM VENTA WHERE FECHA BETWEEN '" +
                        ""+fecha_1.getText().toString()+"' AND '"+fecha_2.getText().toString()+"' "+condicion_and+" ORDER BY CLIENTE",null);

                muestra_Datos_en_tabla(cursor);



            }


        }else if(radio_btn_3.isChecked()){

            while(tabla.getChildCount()>1){
                tabla.removeViewAt(1);
            }

            String  campos = "ID, CLIENTE, PRODUCTO, UNIDAD, PRECIO_BASE, CANTIDAD, VALOR_TOTAL, FECHA";

            //Cursor cursor = sql.query("venta",campos,"FECHA=?",parametros,null,null,null);
            Cursor cursor = sql.rawQuery("SELECT "+campos+" FROM VENTA "+condicio_where+" ORDER BY CLIENTE" ,null);



            muestra_Datos_en_tabla(cursor);

        }

        //tabla.setColumnCollapsed(0,true);
    }

    public void muestra_Datos_en_tabla(Cursor cursor){



        int cantidad=0;
        int indice;
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                indice=1;
                System.out.println("ENTROOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                cantidad++;
                TableRow fila = new TableRow(this);
                for(int i=0;i<=9;i++){
                    if(i==1) {
                        EditText numero = new EditText(this);
                        numero.setText("" + cantidad);
                        numero.setEnabled(false);
                        numero.setTextColor(Color.BLACK);
                        numero.setBackgroundColor(Color.WHITE);
                        fila.addView(numero);
                    }else if(i==4) {
                        EditText editext = new EditText(this);
                        System.out.println(cursor.getString(indice));
                        editext.setText(""+formato.format(Integer.parseInt(cursor.getString(indice))));
                        editext.setBackgroundColor(Color.WHITE);
                        //System.out.println(indice);
                        editext.setEnabled(false);
                        editext.setTextColor(Color.BLACK);
                        indice++;
                        fila.addView(editext);
                    }else if(i==6){
                        EditText editext = new EditText(this);
                        editext.setText(""+formato.format(Integer.parseInt(cursor.getString(indice))));
                        editext.setBackgroundColor(Color.WHITE);
                        //System.out.println(indice);
                        editext.setEnabled(false);
                        editext.setTextColor(Color.BLACK);
                        indice++;
                        fila.addView(editext);
                    }else if(i==8){
                        Button btn_editar = new Button(this);
                        btn_editar.setText("EDITAR");
                        btn_editar.setOnClickListener(accion_editar());
                        fila.addView(btn_editar);
                    }else if(i==9){
                        Button btn_eliminar = new Button(this);
                        btn_eliminar.setText("ELIMINAR");
                        fila.addView(btn_eliminar);
                        btn_eliminar.setOnClickListener(accion_eliminar());

                    }else{
                        EditText editext = new EditText(this);
                        editext.setText(cursor.getString(i));
                        editext.setBackgroundColor(Color.WHITE);
                        //System.out.println(indice);
                        editext.setEnabled(false);
                        editext.setTextColor(Color.BLACK);
                        indice++;
                        fila.addView(editext);

                    }

                }
                tabla.addView(fila);
            }
        }else{
            Toast.makeText(this,"No existe ninguna venta",Toast.LENGTH_SHORT).show();
        }
        suma_total();


        String nombre="";
        datos="";
        int numero=1;
        int suma=0;
        boolean sw=true;

        if(sp_cliente.getSelectedItem().toString().equals("Todos")){
            System.out.println("ENTRO EN TODOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOS");

            if(cursor.moveToFirst()){

                //String  campos = "ID, CLIENTE, PRODUCTO, UNIDAD, PRECIO_BASE, CANTIDAD, VALOR_TOTAL, FECHA";

                nombre = cursor.getString(1);
                do{

                    if(!nombre.equals(cursor.getString(1))){
                        datos+="\n";
                        datos+="TOTAL = $ "+formato.format(suma)+"\n\n" +
                                "Comentario: \n\n" +
                                "---------------------------------------------------------\n";
                        nombre=cursor.getString(1);
                        sw=true;
                        numero=1;
                        suma=0;
                    }

                    if(sw){
                        datos += "    Cliente: "+ nombre+"\n\n";
                    }
                    suma += Integer.parseInt(cursor.getString(6));
                    datos += numero+".-   "+cursor.getString(5)+" "+cursor.getString(3)+" " +
                            ""+cursor.getString(2)+"  $ "+formato.format(Integer.parseInt(cursor.getString(6)))+"\n"+"";
                    numero++;
                    sw=false;



                }while(cursor.moveToNext());
                datos+="\n";
                datos+="TOTAL = $ "+formato.format(suma)+"\n\n" +
                        "Comentario: \n\n";



            }



        }else{

            numero=1;
            if(cursor.moveToFirst()){
                System.out.println("numero de datos en la consulta  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ "+cursor.getCount());
                datos += "    Cliente: "+ sp_cliente.getSelectedItem().toString()+"\n\n";
                do{
                    suma += Integer.parseInt(cursor.getString(6));
                    datos += numero+".-   "+cursor.getString(5)+" "+cursor.getString(3)+" " +
                            ""+cursor.getString(2)+"  $ "+formato.format(Integer.parseInt(cursor.getString(6)))+"\n"+"";
                    numero++;

                }while (cursor.moveToNext());
                datos+="\n";

                datos+="TOTAL = $ "+formato.format(suma)+"\n\n" +
                        "Comentario: \n\n";

            }

        }



    }

    public View.OnClickListener accion_eliminar(){
        return new View.OnClickListener(){
            public void onClick(View view){

                TableRow fila = (TableRow) view.getParent();
                TextView id = (TextView) fila.getChildAt(0);

                SQLiteDatabase sql = con.getWritableDatabase();
                String [] parametros={id.getText().toString()};
                System.out.println("id      dddv "+id.getText().toString());
                sql.delete("venta","ID=?",parametros);
                tabla.removeView(fila);
                volver_enumerar();


            }

        };

    }

    public void volver_enumerar(){
        for(int i=1;i<tabla.getChildCount();i++){
            TableRow fila = (TableRow) tabla.getChildAt(i);
            TextView num = (TextView) fila.getChildAt(1);
            num.setText(""+i);
        }


    }

    public View.OnClickListener accion_editext(){
        return new View.OnClickListener(){
            public void onClick(View v){
                fecha_opt(v);
                muestra_calendario(v);
            }
        };
    }

    public void fecha_opt(View v){
        fecha_opt = (EditText) v;
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c =  Calendar.getInstance();
        NumberFormat f = new DecimalFormat("00");
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        System.out.println("año: "+year+"   mes:"+ f.format(month+1)+"   dia:"+f.format(dayOfMonth));
        String current = DateFormat.getInstance().format(c.getTime());
        String fecha =(year+"-"+f.format(month+1)+"-"+f.format(dayOfMonth));
        fecha_opt.setText(fecha);

    }

    public void muestra_calendario(View v){

        DialogFragment newfragment = new datepickerfragment();
        newfragment.show(getSupportFragmentManager(),"date");

    }

    public View.OnClickListener accion_editar(){

        return (View v) ->  {
            {
                Button boton = (Button)v;
                TableRow fila = (TableRow) v.getParent();

                if(boton.getText().equals("EDITAR")){
                    boton.setText("ACEPTAR");
                    agrega_edit_fila(fila);

                    //System.out.println("ENTRO A LA OPCION EDITAR");



                }else if(boton.getText().equals("ACEPTAR")){
                    boton.setText("EDITAR");
                    inserta_edicion(fila);
                    suma_total();
                    //System.out.println("ENTRO A LA OPCION ACEPTAR");
                }
            }
        };

    }

    public void inserta_edicion(TableRow fila){

        EditText id = (EditText) fila.getChildAt(0);
        Spinner sp_producto = (Spinner) fila.getChildAt(2);
        Spinner sp_unidad = (Spinner) fila.getChildAt(3);
        EditText precio_und = (EditText) fila.getChildAt(4);
        EditText cantidad = (EditText) fila.getChildAt(5);
        EditText total = (EditText) fila.getChildAt(6);

        SQLiteDatabase sql_read = con.getReadableDatabase();
        String [] campos_read = {"PRECIO_BASE","DISP"};
        String [] parametros_read = {sp_producto.getSelectedItem().toString()};

        Cursor cursor_read = sql_read.query("producto",campos_read,"NOMBRE=?",parametros_read,null,null,null);





        SQLiteDatabase sql = con.getWritableDatabase();

        String [] parametros ={id.getText().toString()};
        ContentValues values = new ContentValues();
        values.put("PRODUCTO",sp_producto.getSelectedItem().toString());
        values.put("UNIDAD",sp_unidad.getSelectedItem().toString());
        values.put("PRECIO_BASE",precio_und.getText().toString().replace(".","").replace(",",""));
        values.put("CANTIDAD",cantidad.getText().toString());
        values.put("VALOR_TOTAL",total.getText().toString().replace(".","").replace(",",""));

        if(sp_unidad.getSelectedItem().toString().equals("UND")){
            System.out.println("entro a unidad @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            cursor_read.moveToNext();
            values.put("PRECIO_COMISION",""+Math.round(cursor_read.getDouble(0)*Integer.parseInt(cantidad.getText().toString())));

        }else if(sp_unidad.getSelectedItem().toString().equals("DISP")){
            System.out.println("entro a display @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            cursor_read.moveToNext();
            values.put("PRECIO_COMISION",""+Math.round((cursor_read.getDouble(0)*Integer.parseInt(cursor_read.getString(1)))*Integer.parseInt(cantidad.getText().toString())));
        }


        sql.update("venta",values,"ID=?",parametros);



        SQLiteDatabase sql_ = con.getReadableDatabase();
        String [] campos = {"PRODUCTO","UNIDAD","PRECIO_BASE","CANTIDAD","VALOR_TOTAL"};
        Cursor cursor = sql_.query("venta",campos,"ID=?",parametros,null,null,null);

        while(cursor.moveToNext()){
            for(int i=0;i<campos.length;i++){
                String valor;
                if(i==2){
                    valor = ""+formato.format(Integer.parseInt(cursor.getString(i)));
                }else if(i==4){
                    valor = ""+formato.format(Integer.parseInt(cursor.getString(i)));
                }else{
                    valor = cursor.getString(i);
                }

                fila.removeViewAt(i+2);
                EditText editText = new EditText(this);
                editText.setText(valor);
                editText.setEnabled(false);
                editText.setTextColor(Color.BLACK);
                editText.setBackgroundColor(Color.WHITE);
                fila.addView(editText,i+2);
            }
        }

    }


    public void agrega_edit_fila(TableRow fila){

        EditText nombre_producto = (EditText) fila.getChildAt(2);
        EditText und = (EditText) fila.getChildAt(3);
        EditText cantidad = (EditText) fila.getChildAt(5);
        cantidad.setEnabled(true);
        cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
        cantidad.addTextChangedListener(calculo_total(fila));

        Spinner spinner_producto = new Spinner(this);
        ArrayList<String> lista_producto = new ArrayList<>();

        SQLiteDatabase sql = con.getReadableDatabase();
        String [] campos={"NOMBRE"};

        Cursor cursor = sql.query("producto",campos,null,null,null,null,"NOMBRE");
        while(cursor.moveToNext()){
            lista_producto.add(cursor.getString(0));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_alejandro, lista_producto);
        spinner_producto.setAdapter(adapter);
        spinner_producto.setOnItemSelectedListener(accion_spinne_producto(fila));


        for(int i=0;i<spinner_producto.getAdapter().getCount();i++){ //compara el nombre que tiene el view con el del spinner
            if(spinner_producto.getItemAtPosition(i).toString().equals(nombre_producto.getText().toString())){
                spinner_producto.setSelection(i);
                break;
            }
        }

        Spinner spinner_und = new Spinner(this);
        ArrayList<String> lista_und = new ArrayList<>();
        lista_und.add("DISP");
        lista_und.add("UND");

        ArrayAdapter<String> adapter_und = new ArrayAdapter<String>(this, R.layout.spinner_item_alejandro, lista_und);
        spinner_und.setAdapter(adapter_und);
        spinner_und.setOnItemSelectedListener(accion_spinner_tipo(fila,spinner_producto));

        if(spinner_und.getItemAtPosition(0).toString().equals(und.getText().toString())){
            spinner_und.setSelection(0);
        }else if(spinner_und.getItemAtPosition(1).toString().equals(und.getText().toString())){
            spinner_und.setSelection(1);
        }


        fila.removeViewAt(2);
        fila.addView(spinner_producto,2);

        fila.removeViewAt(3);
        fila.addView(spinner_und,3);

        System.out.println(spinner_producto.getAdapter().getCount());

    }

    public AdapterView.OnItemSelectedListener accion_spinne_producto(final TableRow fila){

        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner_producto = (Spinner) fila.getChildAt(2);
                accion_spinner_tipo(fila,spinner_producto);
                System.out.println("entro spinnerrrrrrrrrrrrrrrrrrrrrrrrrr");
                operacion_spinners(fila, spinner_producto);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

    }

    public AdapterView.OnItemSelectedListener accion_spinner_tipo(final TableRow fila, final Spinner sp_producto){

        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                operacion_spinners(fila,sp_producto);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

    }

    public TextWatcher calculo_total(final TableRow fila){

        final EditText precio = (EditText) fila.getChildAt(4);
        final EditText cantidad = (EditText) fila.getChildAt(5);
        final EditText total = (EditText) fila.getChildAt(6);

        if(!cantidad.getText().toString().equals("")){
            total.setText(""+formato.format(Integer.parseInt(precio.getText().toString().replace(".","").replace(",",""))*Integer.parseInt(cantidad.getText().toString())));
        }else{
            total.setText("0");
        }


        return new TextWatcher(){


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) { // realiza el calculo de cantidad de display x precio
                if(!cantidad.getText().toString().equals("")){
                    total.setText(""+formato.format(Integer.parseInt(precio.getText().toString().replace(".","").replace(",",""))*Integer.parseInt(cantidad.getText().toString())));
                }else{
                    total.setText("0");
                }

            }
        };
    }

    public void suma_total(){

        int sum_total=0;

        for(int i=1;i<tabla.getChildCount();i++){
            TableRow fila_ = (TableRow) tabla.getChildAt(i);
            TextView valor = (TextView) fila_.getChildAt(6);
            sum_total+=Integer.parseInt(valor.getText().toString().replace(".","").replace(",",""));
            System.out.println("el valor es     "+valor.getText().toString());
        }

        suma_total.setText("$"+formato.format(sum_total));

    }

    public void operacion_spinners(TableRow fila, Spinner sp_producto){ // realiza las operaciones matematica al cambiar el valor del spinner producto o tipo

        Spinner spinner_und = (Spinner) fila.getChildAt(3);
        EditText precio = (EditText) fila.getChildAt(4);
        //Spinner spinner_producto = (Spinner) fila.getChildAt(2);
        String nombre = sp_producto.getSelectedItem().toString();
        SQLiteDatabase sql = con.getReadableDatabase();
        String [] parametros_ = {nombre};
        String [] campos = {"DISP","TOTAL"};
        Cursor cursor = sql.query("producto",campos,"NOMBRE=?",parametros_,null,null,null);

        System.out.println("ENTRO A SPINNER TIPOOOOOOOOOOOO");

        int disp=0;
        int precio_=0;

        System.out.println("ANTES DE ENTRAR!!!   "+nombre);

        if(cursor.moveToNext()){
            System.out.println("entro en la consulta de nombre");
            disp = Integer.parseInt(cursor.getString(0));
            precio_ = Integer.parseInt(cursor.getString(1));
        }

        if(spinner_und.getSelectedItem().toString().equals("DISP")){
            precio.setText(""+formato.format(precio_*disp));
        }else if(spinner_und.getSelectedItem().toString().equals("UND")){
            precio.setText(""+formato.format(precio_));
        }

        calculo_total(fila);

    }

    public void ir_a_ventana_compartir(View view){


        Intent intent = new Intent(getApplicationContext(), compartir_ventas.class);
        intent.putExtra("DATOS",datos);
        startActivity(intent);

    }
}