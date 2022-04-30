package com.example.my_app_comision;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_app_comision.BDD.conexion;
import com.example.my_app_comision.clases.ID_PRODUCTO;
import com.example.my_app_comision.clases.version_bd;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class agrega_producto extends AppCompatActivity {

    TextView nombre;
    TextView precio, texto_precio, mensaje_precio, disp;
    TextView prueba;
    Button btn_agregar, boton_excel;
    RadioButton imp_licor, imp_vin_cerv, imp_bedidas, imp_agua_azucaradas, sin_imp_agregado, con_impuesto, sin_impuesto;
    RadioGroup radiogrup, radioGroup_2;
    double impuesto, impuesto_op, total, precio_base;
    String impuesto_string="1.";
    String texto="";
    ArrayList<String> cadena = new ArrayList<>();
    double total_porcentaje=0.19;
    conexion con;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), (uri)->{
        //System.out.println("entrooooooo");

        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);


            POIFSFileSystem filesystem = new POIFSFileSystem(inputStream);

            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(filesystem);
            // We chose the sheet is passed as parameter.
            // Elegimos la hoja que se pasa por parámetro.
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            // An object that allows us to read a row of the excel sheet, and extract from it the cell contents.
            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
            HSSFRow hssfRow;
            // Initialize the object to read the value of the cell
            // Inicializo el objeto que leerá el valor de la celda
            HSSFCell cell;

            int rows = hssfSheet.getLastRowNum();
            // I get the number of columns occupied on the sheet
            // Obtengo el número de columnas ocupadas en la hoja
            int cols = 0;
            // A string used to store the reading cell
            // Cadena que usamos para almacenar la lectura de la celda
            String cellValue;

            Iterator iterator = hssfSheet.iterator();
            ArrayList<String> lista = new ArrayList<>();
            DataFormatter formatter = new DataFormatter();
            while(iterator.hasNext()){
                Row nexRow = (Row) iterator.next();
                Iterator celda_iterator = nexRow.cellIterator();
                lista.clear();
                while(celda_iterator.hasNext()){
                    Cell celda = (Cell) celda_iterator.next();
                    System.out.println("este es el valor "+ formatter.formatCellValue(celda)+"\n");
                    lista.add(formatter.formatCellValue(celda));
                }
                agrega_producto(2,lista);
                System.out.println(" --- ");
            }

            Toast.makeText(getApplicationContext(),"  producto agregado",Toast.LENGTH_SHORT).show();

            //hssfRow = hssfSheet.getRow(0);
            //System.out.println("este es el valor de la celda  "+hssfRow.getCell(1).getStringCellValue());



        } catch (IOException e) {
            e.printStackTrace();
        }



    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_producto);

        con = new conexion(this,"bd_productos",null, version_bd.id);



        setContentView(R.layout.activity_agrega_producto);

        nombre = findViewById(R.id.nombre_producto);
        precio = findViewById(R.id.precio_producto);
        //cambia_formato();
        precio.addTextChangedListener(validacion_precio());
        texto_precio = findViewById(R.id.texto_precio);
        mensaje_precio = findViewById(R.id.mensaje_precio);
        disp = findViewById(R.id.num_disp);

        btn_agregar = findViewById(R.id.btn_agregar);
        boton_excel = findViewById(R.id.boton_excel);

        radiogrup = findViewById(R.id.radioGroup);
        radioGroup_2 = findViewById(R.id.radiogroup_con_sin_iva);
        imp_licor = findViewById(R.id.imp_licor);
        imp_vin_cerv = findViewById(R.id.imp_vino_cerveza);
        imp_bedidas = findViewById(R.id.imp_bebidas);
        imp_agua_azucaradas = findViewById(R.id.imp_agua_azucar);
        sin_imp_agregado = findViewById(R.id.sin_imp);
        con_impuesto = findViewById(R.id.radioButton_coniva);
        sin_impuesto = findViewById(R.id.radioButton_siniva);

        con_impuesto.setChecked(true);

        /*opt_base = findViewById(R.id.rb_base);
        opt_total = findViewById(R.id.rb_total);

        opt_base.setOnClickListener(opcion_check());
        opt_total.setOnClickListener(opcion_check());*/

        /*ocultar_mostrar(View.INVISIBLE);*/

        //cambia_formato();

        boton_excel.setOnClickListener((v)->{
            agregar_excel(v);
        });



    }



    public void ocultar_mostrar(int b){

        precio.setVisibility(b);
        texto_precio.setVisibility(b);
        mensaje_precio.setVisibility(b);
        imp_licor.setVisibility(b);
        imp_vin_cerv.setVisibility(b);
        imp_bedidas.setVisibility(b);
        btn_agregar.setVisibility(b);

    }

    public void agregar_excel(View v){
        mGetContent.launch("*/*");

    }
    public TextWatcher validacion_precio(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if(s.charAt(s.length()-1)!='0' || s.charAt(s.length()-1)!='1' || s.charAt(s.length()-1)!='2' || s.charAt(s.length()-1)!='3'
                 || s.charAt(s.length()-1)!='4' || s.charAt(s.length()-1)!='5' || s.charAt(s.length()-1)!='6'|| s.charAt(s.length()-1)!='7'
                 || s.charAt(s.length()-1)!='8' || s.charAt(s.length()-1)!='.'){
                    s.insert(s.length(),"");
                    System.out.println("entro al char incorrecto");
                    precio.setText(s.toString());
                }else{

                }*/

            }
        };
    }

    public void cambia_formato(){

        precio.setKeyListener(new KeyListener() {
            @Override
            public int getInputType() {
                return 0;
            }

            @Override
            public boolean onKeyDown(View view, Editable text, int keyCode, KeyEvent event) {
                return false;
            }

            @Override
            public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {

                // DecimalFormat dm = new DecimalFormat("##.###");
                // DecimalFormat format = new DecimalFormat("#,###.00");
                //precio.setText(format.format(Integer.parseInt(text.toString())));
                //precio.setText(dm.format(Integer.parseInt(precio.getText().toString())));

                return false;
            }

            @Override
            public boolean onKeyOther(View view, Editable text, KeyEvent event) {
                return false;
            }

            @Override
            public void clearMetaKeyState(View view, Editable content, int states) {

            }
        });


    }



   /* public View.OnClickListener opcion_check(){

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultar_mostrar(View.VISIBLE);
                if(opt_base.isChecked()){
                    //precio_base();
                    precio.setHint("Ingresa el precio base");
                    //Toast.makeText(getApplicationContext(),"base",Toast.LENGTH_LONG).show();
                }
                if(opt_total.isChecked()){
                    //precio_total();
                    precio.setHint("Ingresa el precio total");
                    //Toast.makeText(getApplicationContext(),"total",Toast.LENGTH_LONG).show();
                }

            }
        };

        return listener;
    }*/



    public void agregar(View v){




        if(!nombre.getText().toString().equals("") && !precio.getText().toString().equals("") && !disp.getText().toString().equals("") && (imp_licor.isChecked()
                || imp_agua_azucaradas.isChecked() || imp_bedidas.isChecked() || imp_vin_cerv.isChecked() || sin_imp_agregado.isChecked()) || sin_impuesto.isChecked()){ // compueba si el nombre o precio del producto esta vacio

            boolean precio_switch=true;

            for(int i=0;i<precio.length();i++){
                if(precio.getText().toString().charAt(i)=='0' || precio.getText().toString().charAt(i)=='1' ||precio.getText().toString().charAt(i)=='2'
                        || precio.getText().toString().charAt(i)=='3' || precio.getText().toString().charAt(i)=='4' || precio.getText().toString().charAt(i)=='5'
                        || precio.getText().toString().charAt(i)=='6' || precio.getText().toString().charAt(i)=='7' || precio.getText().toString().charAt(i)=='8'
                        || precio.getText().toString().charAt(i)=='9' || precio.getText().toString().charAt(i)=='.'){
                }else{
                    precio_switch=false;
                }
            }
            if(precio_switch){
                precio_total();
                total();
                agrega_producto(1,null);
                impuesto=0;
            }else{
                Toast.makeText(this,"Ingrese valor valido en precio",Toast.LENGTH_LONG).show();
            }

        }
        else{

            if(nombre.getText().toString().equals("")){
                Toast.makeText(this,"Ingresa el nombre del producto",Toast.LENGTH_SHORT).show();
                nombre.requestFocus();
                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);  //muestra teclado del celular
                imm.showSoftInput(nombre, InputMethodManager.SHOW_IMPLICIT);
            }
            else if(precio.getText().toString().equals("")){
                Toast.makeText(this,"Ingresa el precio del producto",Toast.LENGTH_SHORT).show();

                precio.requestFocus();
                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);  //muestra teclado del celular
                imm.showSoftInput(precio, InputMethodManager.SHOW_IMPLICIT);

            }else if(disp.getText().toString().equals("")){
                disp.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);  //muestra teclado del celular
                imm.showSoftInput(disp, InputMethodManager.SHOW_IMPLICIT);
            }else if(!imp_vin_cerv.isChecked() || !imp_bedidas.isChecked() || !imp_agua_azucaradas.isChecked() || !imp_licor.isChecked() || !sin_imp_agregado.isChecked()){
                Toast.makeText(this,"Selecciona un impuesto",Toast.LENGTH_LONG).show();
            }

        }


    }

    /*public void precio_base(){
        precio_base = Double.parseDouble(precio.getText().toString());
        iva = Double.parseDouble(precio.getText().toString())*0.19;

        if(imp_licor.isChecked()){
            _imp_licor = Double.parseDouble(precio.getText().toString())*0.315;//        31.5 impuesto licores
        }if(imp_vin_cerv.isChecked()){
            _imp_vin_cerv = Double.parseDouble(precio.getText().toString())*0.205;            //20.5 impuesto vino cerveza
        }if(imp_bedidas.isChecked()){
            _imp_bebidas = Double.parseDouble(precio.getText().toString())*0.18;            //18 impuesto bebidas
        }


        total_imp = iva+_imp_vin_cerv+_imp_licor+_imp_bebidas;

    }*/

    public void precio_total(){


        if(con_impuesto.isChecked()){
            impuesto_string="1.";
            if(imp_licor.isChecked()){
                impuesto = 31.5;//        31.5 impuesto licores
                impuesto_string +="505";
            }else if(imp_vin_cerv.isChecked()){
                impuesto = 20.5;//20.5 impuesto vino cerveza
                impuesto_string +="395";
            }else if(imp_bedidas.isChecked()){
                impuesto = 18;            //18 impuesto bebidas
                impuesto_string +="37";
                System.out.println("IMPUESTO BEBIDAS   ");
            }else if(imp_agua_azucaradas.isChecked()){
                impuesto = 10;
                impuesto_string +="29";
            }else if(sin_imp_agregado.isChecked()){
                impuesto = 0;
                impuesto_string +="19";
            }

            precio_base = Double.parseDouble(precio.getText().toString());
        }else if(sin_impuesto.isChecked()){

            impuesto = 0;
            impuesto_string = "0";
            precio_base = Double.parseDouble(precio.getText().toString());

        }






    }



    public void total(){

        if(con_impuesto.isChecked()){

            total = (Double.parseDouble(precio.getText().toString())*Double.parseDouble(impuesto_string));
        }else if(sin_impuesto.isChecked()){
            total = Double.parseDouble(precio.getText().toString());
        }

    }

    public void agrega_producto(int valor,ArrayList<String> lista){


        SQLiteDatabase db = con.getWritableDatabase();

        ID_PRODUCTO id_pro = new ID_PRODUCTO();

        ContentValues values = new ContentValues();

        int iva=0;

        if(con_impuesto.isChecked()){
            iva=19;
        }else if(sin_impuesto.isChecked()){
            iva =0;
        }


        //values.put("ID", ID_PRODUCTO.Get_id());
        if(valor==1){// agrega valor de la manera manual

            values.put("NOMBRE",nombre.getText().toString());
            values.put("DISP",Integer.parseInt(disp.getText().toString()));
            values.put("PRECIO_BASE", precio_base);
            values.put("IVA",iva);
            values.put("IMPUESTO",impuesto);
            values.put("TOTAL",Math.round(total));

            Long idResultado = db.insert("producto","ID",values);
            Toast.makeText(getApplicationContext(),"  producto agregado",Toast.LENGTH_SHORT).show();


        }else if(valor==2){

            System.out.println("NOMBRE: "+lista.get(0));
            System.out.println("DISP: "+Integer.parseInt(lista.get(1))+"\n");
            System.out.println("PRECIO_BASE: "+ Double.parseDouble(lista.get(2))+"\n");
            System.out.println("IVA: "+19+"\n");
            System.out.println("IMPUESTO: "+Double.parseDouble(lista.get(3))+"\n");


            //System.out.println("TOTAL: "+Math.round(Integer.parseInt(lista.get(2))*Double.parseDouble("1."+numero))+"\n");

            values.put("NOMBRE",lista.get(0));
            values.put("DISP",Integer.parseInt(lista.get(1)));
            values.put("PRECIO_BASE", Double.parseDouble(lista.get(2)));
            values.put("IVA",19);
            values.put("IMPUESTO",Double.parseDouble(lista.get(3)));
            String numero="";
            if(Math.round(Double.parseDouble(lista.get(3)))==0){
                numero = (""+ (19+Integer.parseInt(lista.get(3)))).replace(".","");
            }else{
                numero = (""+ (19+Double.parseDouble(lista.get(3)))).replace(".","");
            }
            System.out.println(numero);

            Long idResultado = db.insert("producto","ID",values);

        }





        resetea_datos();
    }

    public void resetea_datos(){
        nombre.setText("");
        precio.setText("");
        disp.setText("");
        imp_licor.setChecked(false);
        imp_agua_azucaradas.setChecked(false);
        imp_bedidas.setChecked(false);
        imp_vin_cerv.setChecked(false);
    }

    public void mostrar_producto(View v){
        agrega_datos_productos_spinner();
        //Toast.makeText(this,"producto" ,Toast.LENGTH_LONG).show();
        SQLiteDatabase db = con.getReadableDatabase();
        String [] parametros = {""};
        String [] campos={"ID","NOMBRE","DISP","PRECIO_BASE","IVA","IMPUESTO","TOTAL"};
        Cursor cursor = db.query("producto",campos,null,null,null,null,"nombre");



           /* System.out.println("ID="+cursor.getString(0)+"  NOMBRE="+cursor.getString(1)+"  PRECIO=" +
                    ""+cursor.getString(2)+"  IVA="+cursor.getString(3)+"  IMP_LICOR="+
                    cursor.getString(4)+"  IMP_VIN_CERV="+cursor.getString(5)+"  IMP_BEBIDA="+
                    cursor.getString(6)+"  TOTAL="+cursor.getString(7)+"\n");*/
        while(cursor.moveToNext()){
            System.out.println("ID="+cursor.getString(0)+"  NOMBRE="+cursor.getString(1)+"  DISP=" +
                    ""+cursor.getString(2)+"  PRECIO= "+
                    ""+cursor.getString(3)+ "precio / 24  = "+Double.parseDouble(cursor.getString(3))/24+ " * 240  =  "+ Math.round((Double.parseDouble(cursor.getString(3))/24)*240)  +"  IVA="+cursor.getString(4)+
                    "  IMPUESTO="+cursor.getString(5)+"  TOTAL="+cursor.getString(6)+"\n");
        }


        con.close();
        db.close();

        /*if(cursor.moveToFirst()){

            while (cursor.moveToNext()){

            }

        }*/


        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat formato = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formato_hora = new SimpleDateFormat("HH:mm");
        String fecha = formato.format(d);
        String hora = formato_hora.format(d);

        System.out.println(" horaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa   "+fecha+" HORAAAAA "+hora);

        if(fecha.equals("23-junio-2020")){
            System.out.println("ENTRO ");
        }
        else{
            System.out.println("NO ENTRO");
        }


    }

    public ArrayList<String> agrega_datos_productos_spinner(){

        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db = con.getReadableDatabase();
        String[] campos = {"NOMBRE"};
        Cursor cursor = db.query("producto",campos,null,null,null,null,null);

        while(cursor.moveToNext()){
            lista.add(cursor.getString(0));
            System.out.println(cursor.getString(0));
        }

        return lista;
    }

}