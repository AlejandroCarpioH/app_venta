package com.example.my_app_comision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class compartir_ventas extends AppCompatActivity {

    EditText lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir_ventas);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lista = findViewById(R.id.texto);
        Intent intent = getIntent();
        Bundle b =  intent.getExtras();
        lista.setText(b.getString("DATOS"));
    }

    public void compartir(View view){
        if (!lista.getText().toString().equals("")){
            System.out.println(lista);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,lista.getText().toString());
            intent.setType("text/plain");

            Intent compartir = Intent.createChooser(intent, null);

            startActivity(compartir);
        }else{
            Toast.makeText(this,"No hay nada para compartir",Toast.LENGTH_SHORT).show();
        }

    }
}