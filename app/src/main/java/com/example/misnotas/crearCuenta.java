package com.example.misnotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
        if(!estaValidado){
            return;
        }
        crearCuentaEnFirebase(email, contrasena);
    }

    //creación de método para crear cuenta en Firebase - crea la cuenta, envía email de confirmación y cierra sesión para poder iniciar nuevamente
    void crearCuentaEnFirebase(String email, String contrasena){
        cambioEnProgreso(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, contrasena).addOnCompleteListener(crearCuenta.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        cambioEnProgreso(false);
                        if (task.isSuccessful()){
                            //se creó la cuenta correctamente
                            Toast.makeText(crearCuenta.this, "La cuenta se ha creado correctamente, revisá tu e-mail para corroborarlo",Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                        }else {
                            //la creación de cuenta falló
                            Toast.makeText(crearCuenta.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    //creación de método para espera de creación de cuenta en Firebase
    void cambioEnProgreso(boolean enProgreso){
        if(enProgreso){
            progressBar.setVisibility(View.VISIBLE);
            crearCuentaBoton.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            crearCuentaBoton.setVisibility(View.VISIBLE);
        }
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