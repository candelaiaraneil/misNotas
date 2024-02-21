package com.example.misnotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class adaptadorNota extends FirestoreRecyclerAdapter<Nota, adaptadorNota.tituloVistaNota> {
    Context context;

    public adaptadorNota(@NonNull FirestoreRecyclerOptions<Nota> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull tituloVistaNota holder, int position, @NonNull Nota Nota) {
        holder.tituloTextView.setText(Nota.titulo);
        holder.contenidoTextView.setText(Nota.contenido);
        holder.timestampTextView.setText(Utility.timestampAString(Nota.timestamp));
    }

    @NonNull
    @Override
    public tituloVistaNota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_nota_item, parent, false);
        return new tituloVistaNota(view);
    }

    class tituloVistaNota extends RecyclerView.ViewHolder{

        TextView tituloTextView, contenidoTextView, timestampTextView;
        public tituloVistaNota(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.tituloNota_textView);
            contenidoTextView = itemView.findViewById(R.id.tituloContenido_textView);
            timestampTextView = itemView.findViewById(R.id.tituloTimestamp_textView);
        }
    }
}
