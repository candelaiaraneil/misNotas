package com.example.misnotas;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utility {
    static void mostrarToast(Context contexto, String mensaje){
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
    }
    //cada usuario tendra su colección de notas
    static CollectionReference getCollectionReferencePorNotas(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notas").document(currentUser.getUid()).collection("mis_notas");
    }

    //se crea método para que el timestamp de cada nota se pase a string cuando sea necesario
    static String timestampAString(Timestamp timestamp) {
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }
}
