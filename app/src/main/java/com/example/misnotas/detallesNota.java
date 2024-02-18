package com.example.misnotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

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
        Nota nota = new Nota();
        nota.setTitulo(notaTitulo);
        nota.setContenido(notaContenido);
        nota.setTimestamp(Timestamp.now());

        guardarNotaEnFirebase(nota);
    }

    void guardarNotaEnFirebase(Nota nota){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferencePorNotas().document();

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
}