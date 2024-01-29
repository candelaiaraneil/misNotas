package com.example.misnotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class presentacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);

        //se ejecuta unos segundos y luego pasa a la pantalla principal
        //se agrega temporizador

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(presentacion.this,crearCuenta.class));
                finish(); //termina la pantalla de presentación
            }
        }, 1000);
    }
}