package com.example.misnotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class detallesNota extends AppCompatActivity {

    EditText tituloEditText, contenidoEditText;
    ImageButton guardarNotaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_nota);

        tituloEditText = findViewById(R.id.tituloNotas_texto);
        contenidoEditText = findViewById(R.id.contenidoNotas_texto);
        guardarNotaBtn = findViewById(R.id.guardarNota_btn);

        guardarNotaBtn.setOnClickListener((v)-> guardarNota());


    }

    void guardarNota(){
        String notaTitulo = tituloEditText.getText().toString();
        String notaContenido = contenidoEditText.getText().toString();
        //validaciones
        if(notaTitulo==null || notaTitulo.isEmpty()) {
            tituloEditText.setError("El título es necesario");
            return;
        }
    }
}