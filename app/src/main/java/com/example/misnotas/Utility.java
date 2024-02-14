package com.example.misnotas;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    static void mostrarToast(Context contexto, String mensaje){
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
    }
}
