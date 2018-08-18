package com.itsm.franciscogumescindolimon.loginconfirebase_60;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    //Definicion de las variables.........
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar, btnRegresar;
    private ProgressDialog progressDialog;
    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText) findViewById(R.id.TxtPassword);
        btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
        btnRegresar= (Button) findViewById(R.id. btnRegresar);

        progressDialog = new ProgressDialog(this);
        //adjuntar listener al botón
        btnRegistrar.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar(view);
            }
        });
    }
    //Bloque de codigo para poder registrar usuarios....
    private void registrarUsuario(){
        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();
        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        //Mensaje cuando el registro esta correcto...
        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();
        //creando un nuevo usuario
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Checando la accion
                        if(task.isSuccessful()){
                            Toast.makeText(Main2Activity.this,"Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Main2Activity.this, MainActivity.class));
                        }else{
                            Toast.makeText(Main2Activity.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        //Invocamos al método:
        registrarUsuario();
    }

    public void regresar( View view){
        Intent ListSong = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(ListSong);
    }
}
