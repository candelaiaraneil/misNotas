package com.example.misnotas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton anadirNotaBoton;
    RecyclerView recyclerView;
    ImageButton menuBtn;

    adaptadorNota adaptadorNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anadirNotaBoton = findViewById(R.id.anadir_nota_boton);
        recyclerView = findViewById(R.id.recycler_view);
        menuBtn = findViewById(R.id.menu_btn);

        anadirNotaBoton.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, detallesNota.class)));
        menuBtn.setOnClickListener((v)-> mostrarMenu() );
        iniciarRecyclerView();
    }

    void mostrarMenu(){

    }

    void iniciarRecyclerView(){
        //se obtienen datos almacenados en Firebase como 'notas' para mostrarlos
        Query query = Utility.getCollectionReferencePorNotas().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Nota> options = new FirestoreRecyclerOptions.Builder<Nota>()
                .setQuery(query, Nota.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptadorNota = new adaptadorNota(options, this);
        recyclerView.setAdapter(adaptadorNota);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptadorNota.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorNota.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptadorNota.notifyDataSetChanged();
    }
}