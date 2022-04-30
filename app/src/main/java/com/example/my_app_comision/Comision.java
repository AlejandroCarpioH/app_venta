package com.example.my_app_comision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_app_comision.BDD.conexion;
import com.example.my_app_comision.clases.version_bd;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Comision extends AppCompatActivity {

    TextView ventas;
    EditText comision;
    TextView ganancia;
    conexion con;
    DecimalFormat formato;
    Spinner mes, anho;
    String [] lista_mes = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comision);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ventas = findViewById(R.id.ventas_totales);
        comision = findViewById(R.id.porcentaje);
        ganancia = findViewById(R.id.ganancia);
        con = new conexion(this,"bd_productos",null, version_bd.id);
        mes = findViewById(R.id.spinner_mes);
        anho = findViewById(R.id.spinner_anho);

        formato = new DecimalFormat("##,###");

        spinner();
    }

    public void spinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_alejandro, lista_mes);
        mes.setAdapter(adapter);

        ArrayList<String> lista_anho = new ArrayList<>();
        int numero = 2012;
        for(int i=0;i<100;i++){
            numero+=1;
            lista_anho.add(""+numero);
        }

        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(this,R.layout.spinner_item_alejandro,lista_anho);
        anho.setAdapter(adapter_2);

        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat formato_fecha = new SimpleDateFormat("YYYY");
        String fecha = formato_fecha.format(d);

        for (int i=0;i<anho.getAdapter().getCount();i++){
            if(fecha.equals(anho.getItemAtPosition(i).toString())){
                anho.setSelection(i);
            }
        }

    }




    public void ventas_totales(View view){

        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        int dias=0;
        int mes_numero=0;
        switch (mes.getSelectedItem().toString()){

            case "ENERO":
            case "MARZO":
            case "MAYO":
            case "JULIO":
            case "AGOSTO":
            case "OCTUBRE":
            case "DICIEMBRE":
                dias=31;
                break;
            case "ABRIL":
            case "JUNIO":
            case "SEPTIEMBRE":
            case "NOVIEMBRE":
                dias=30;
                break;
            case "FEBRERO":
                if(cal.isLeapYear(Integer.parseInt(anho.getSelectedItem().toString()))){
                    dias=29;
                }else{
                    dias=28;
                }
        }


        for(int i=1;i<=lista_mes.length;i++){
            if(mes.getSelectedItem().toString().equals(lista_mes[i-1])){
                mes_numero=i;
            }
        }


        String fecha1= anho.getSelectedItem().toString()+"-"+String.format("%02d",mes_numero)+"-01";
        String fecha2= anho.getSelectedItem().toString()+"-"+String.format("%02d",mes_numero)+"-"+dias;

        System.out.println(fecha1+"     "+fecha2);

        SQLiteDatabase sql = con.getReadableDatabase();
        String campos = "PRECIO_COMISION";

        Cursor cursor = sql.rawQuery("SELECT "+campos+" FROM VENTA WHERE FECHA BETWEEN '" +
                ""+fecha1+"' AND '"+fecha2+"'",null);
        int total=0;
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                total+=Integer.parseInt(cursor.getString(0));
            }
            ventas.setText("$ "+formato.format(total));

        }else{
            ventas.setText("$ 0");
            Toast.makeText(this,"No existe ventas para el mes o año seleccionado",Toast.LENGTH_LONG).show();
        }


    }

    public void calcula_comision(View view){

        if(!comision.getText().toString().equals("")){

            boolean sw_numerico=true;

            for(int i=0;i<comision.length();i++){
                if(comision.getText().toString().charAt(i)=='0' || comision.getText().toString().charAt(i)=='1' || comision.getText().toString().charAt(i)=='2' ||
                        comision.getText().toString().charAt(i)=='3' || comision.getText().toString().charAt(i)=='4' || comision.getText().toString().charAt(i)=='5' ||
                        comision.getText().toString().charAt(i)=='6' || comision.getText().toString().charAt(i)=='7' || comision.getText().toString().charAt(i)=='8' ||
                        comision.getText().toString().charAt(i)=='9' || comision.getText().toString().charAt(i)=='.'){

                }else{
                    sw_numerico=false;
                }
            }

            if(sw_numerico){
                String valor1 = ventas.getText().toString().replace(".","").replace("$","").replace(",","").trim();
                String valor2 = "0."+comision.getText().toString().replace(".","").replace(",","");
                System.out.println(Math.round(Integer.parseInt(valor1)*Double.parseDouble(valor2)));

                ganancia.setText("$ "+formato.format(Math.round(Integer.parseInt(valor1)*Double.parseDouble(valor2))));

                //System.out.println(""+ventas.getText().toString().replace(".","").replace("$","")+"  1."+comision.getText().toString().replace(".",""));

            }else{
                Toast.makeText(this,"Ingrese un valor valido ejemplo: 123.456",Toast.LENGTH_LONG).show();
            }


        }else{
            Toast.makeText(this,"Ingresa el porcentaje para sacar la comision",Toast.LENGTH_LONG).show();
        }


    }
}