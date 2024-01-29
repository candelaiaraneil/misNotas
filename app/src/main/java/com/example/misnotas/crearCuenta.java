package com.example.misnotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class crearCuenta extends AppCompatActivity {

    EditText emailEditText, contrasenaEditText, confirmarContrasenaeditText;
    Button crearCuentaBoton;
    ProgressBar progressBar;
    TextView loginBotonTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        //vinculación con .xml
        emailEditText = findViewById(R.id.email_input);
        contrasenaEditText = findViewById(R.id.contrasena_input);
        confirmarContrasenaeditText = findViewById(R.id.confirmarContrasena_input);
        crearCuentaBoton = findViewById(R.id.crearCuenta_btn);
        progressBar = findViewById(R.id.barraDeProgreso);
        loginBotonTextView = findViewById(R.id.loginTextView_btn);

        //configuración de detector de clics y validación de datos
    }
}