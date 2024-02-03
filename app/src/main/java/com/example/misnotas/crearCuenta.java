package com.example.misnotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
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

        crearCuentaBoton.setOnClickListener(v-> crearUnaCuenta());
        loginBotonTextView.setOnClickListener(v-> finish());

    }

    //lógicas para crear cuenta - recepción de datos
    void crearUnaCuenta(){
        String email = emailEditText.getText().toString();
        String contrasena = contrasenaEditText.getText().toString();
        String confirmarContrasena = confirmarContrasenaeditText.getText().toString();

        boolean estaValidado = validarDatos(email, contrasena, confirmarContrasena);
    }

    //creación de método para validar datos ingresados por el usuario - booleano
    boolean validarDatos(String email, String contrasena, String confirmarContrasena){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("El email ingresado es inválido");
            return false;
        }
        if (contrasena.length()<6){
            contrasenaEditText.setError("Tu contraseña tiene pocos caracteres");
            return false;
        }
        if (!contrasena.equals(confirmarContrasena)){
            confirmarContrasenaeditText.setError("La contraseña ingresada no coincide");
            return false;
        }
        return true;
    }
}