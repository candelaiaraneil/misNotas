package com.example.misnotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton anadirNotaBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anadirNotaBoton = findViewById(R.id.anadir_nota_boton);

        anadirNotaBoton.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, detallesNota.class)));
    }
}