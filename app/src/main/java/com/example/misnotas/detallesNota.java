package com.example.misnotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class detallesNota extends AppCompatActivity {

    EditText tituloEditText, contenidoEditText;
    ImageButton guardarNotaBtn;
    TextView paginaTituloTextView;
    String titulo, contenido, docId;
    boolean enModoEdicion = false;
    TextView borrarNotaTextViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_nota);

        tituloEditText = findViewById(R.id.tituloNotas_texto);
        contenidoEditText = findViewById(R.id.contenidoNotas_texto);
        guardarNotaBtn = findViewById(R.id.guardarNota_btn);
        paginaTituloTextView = findViewById(R.id.titulo_pagina);
        borrarNotaTextViewBtn = findViewById(R.id.borrar_nota_text_view_btn);

        //se reciben datos
        titulo = getIntent().getStringExtra("titulo");
        contenido = getIntent().getStringExtra("contenido");
        docId = getIntent().getStringExtra("docId");

        //verificaciones para entrar en modo edición o no
        if(docId!=null && !docId.isEmpty()) {
            enModoEdicion = true;
        }

        //se establecen configuraciones para poder editar la nota, y si da validado TRUE se realiza lo siguiente
        tituloEditText.setText(titulo);
        contenidoEditText.setText(contenido);
        if(enModoEdicion){
            paginaTituloTextView.setText("Edita tu nota");
            borrarNotaTextViewBtn.setVisibility(View.VISIBLE);
        }

        guardarNotaBtn.setOnClickListener((v)-> guardarNota());

        borrarNotaTextViewBtn.setOnClickListener((v)-> borrarNotaDesdeFirebase());

    }

    void guardarNota(){
        String notaTitulo = tituloEditText.getText().toString();
        String notaContenido = contenidoEditText.getText().toString();
        //validaciones
        if(notaTitulo==null || notaTitulo.isEmpty()) {
            tituloEditText.setError("El título es necesario");
            return;
        }
        Nota nota = new Nota();
        nota.setTitulo(notaTitulo);
        nota.setContenido(notaContenido);
        nota.setTimestamp(Timestamp.now());

        guardarNotaEnFirebase(nota);
    }

    void guardarNotaEnFirebase(Nota nota){
        DocumentReference documentReference;
        if(enModoEdicion) {
            //se edita la nota
            documentReference = Utility.getCollectionReferencePorNotas().document(docId);
        } else {
            //se crea nueva nota
            documentReference = Utility.getCollectionReferencePorNotas().document();
        }


        documentReference.set(nota).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //se creó la nota
                    Utility.mostrarToast(detallesNota.this, "La nota ha sido creada con éxito");
                    finish();
                }else {
                    //no se creó la nota
                    Utility.mostrarToast(detallesNota.this, "La nota no ha sido creada, verificar");
                }
            }
        });
    }

    void borrarNotaDesdeFirebase() {
        DocumentReference documentReference;

            documentReference = Utility.getCollectionReferencePorNotas().document(docId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //se eliminó la nota
                    Utility.mostrarToast(detallesNota.this, "La nota ha sido eliminada con éxito");
                    finish();
                }else {
                    //no se creó la nota
                    Utility.mostrarToast(detallesNota.this, "La nota no ha sido eliminada, verificar");
                }
            }
        });
    }
}