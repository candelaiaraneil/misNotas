package com.example.misnotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText emailEditText, contrasenaEditText;
    Button loginBoton;
    ProgressBar progressBar;
    TextView crearCuentaBotonTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //vinculación con .xml
        emailEditText = findViewById(R.id.email_input);
        contrasenaEditText = findViewById(R.id.contrasena_input);
        loginBoton = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.barraDeProgreso);
        crearCuentaBotonTextView = findViewById(R.id.crearCuenta_btn);

        //configuración de oyentes al hacer click
        loginBoton.setOnClickListener((v)-> loginUsuario() );
        //se establece que con boton 'Crear cuenta' se vuelva a la activity crearCuenta
        crearCuentaBotonTextView.setOnClickListener((v)->startActivity(new Intent(login.this,crearCuenta.class)));
    }

    //lógica para que el usuario inicie sesión
    void loginUsuario() {

        String email = emailEditText.getText().toString();
        String contrasena = contrasenaEditText.getText().toString();

        boolean estaValidado = validarDatos(email, contrasena);
        if(!estaValidado){
            return;
        }
        loguearCuentaEnFirebase(email, contrasena);
    }

    //creación de método para loguearse en Firebase
    void loguearCuentaEnFirebase(String email, String contrasena) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        cambioEnProgreso(true);
        firebaseAuth.signInWithEmailAndPassword(email, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                cambioEnProgreso(false);
                if(task.isSuccessful()){
                    //logueo correcto y si el email está verificado
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        //vaya a la actividad principal
                        startActivity(new Intent(login.this,MainActivity.class));
                        finish();
                    }else{
                        //si el email no está verificado
                        Utility.mostrarToast(login.this, "El email no está verificado, por favor revisa tu correo");
                    }
                }else{
                    //logueo falló
                    Utility.mostrarToast(login.this, "El logueo falló, por favor verificar");
                }
            }
        });
    }

    void cambioEnProgreso(boolean enProgreso){
        if(enProgreso){
            progressBar.setVisibility(View.VISIBLE);
            loginBoton.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            loginBoton.setVisibility(View.VISIBLE);
        }
    }

    //creación de método para validar datos ingresados por el usuario - booleano
    boolean validarDatos(String email, String contrasena){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("El email ingresado es inválido");
            return false;
        }
        if (contrasena.length()<6){
            contrasenaEditText.setError("Tu contraseña tiene pocos caracteres");
            return false;
        }
        return true;
    }
}