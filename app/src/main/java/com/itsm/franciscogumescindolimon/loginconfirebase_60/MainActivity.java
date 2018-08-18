package com.itsm.franciscogumescindolimon.loginconfirebase_60;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    //Declaracion de variables...
    private EditText mEmailField,mPasswordField;
    private Button mLoginBtn,regis;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progressDialog;
    private ImageButton btnExit;
    TextInputLayout inpemail;
    boolean  Mail=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaracion de Mensaje que accionara cuando entre al programa....
        Toast.makeText(MainActivity.this, "Para el funcionamiento de esta aplicación es necesario tener conexión a wifi o bien a datos", Toast.LENGTH_LONG).show();
        //Casteo de variables...
        mEmailField = (EditText) findViewById(R.id.email);
        mPasswordField= (EditText)findViewById(R.id.password);
        mLoginBtn= (Button) findViewById(R.id.login);
        regis= (Button)findViewById(R.id.Registrar);
        inpemail = (TextInputLayout)findViewById(R.id.EmailT);
        btnExit = (ImageButton) findViewById(R.id.btnExit);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        //Codigo de bienvenida cuando los datos sean validos entrara aqui....
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null) {
                    startActivity(new Intent(MainActivity.this, Principal.class));
                    Toast.makeText(MainActivity.this, "Bienvenido a ITSM-BOT", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "El usuario salio", Toast.LENGTH_SHORT);
                }
            }
        };
        //Metodo que permitira iniciar session con password y email
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
                //Codigo referente a validacion de los datos...
                if (Patterns.EMAIL_ADDRESS.matcher(mEmailField.getText().toString()).matches() == false) {
                    inpemail.setError("Email incorrecto");
                    Mail = false;
                } else {
                    inpemail.setError(null);
                    Mail = true;
                }
                if(Mail = true ){
                    Toast.makeText(getApplicationContext(), "Validacion Correcta", Toast.LENGTH_LONG).show();
                }
            }
        });
        //Btn de acceso a pantalla de registro...
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registro(view);
            }
        });
        //Btn de salida de la App..
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                mEmailField.setText("");
                mPasswordField.setText("");
            }
        });
    }

    //Bloqueo de btns de retroceso de Telefono
    @Override
    public void onBackPressed(){
        //  DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        //   if(drawer.isDrawerOpen(GravityCompat.START)){
        //       drawer.closeDrawer(GravityCompat.START);
        //   }else{
        //      super.onBackPressed();
        //   }
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //Metoodo para iniciar session !!
    public void login( View view){
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        //Mensaje que aparecera antes de Iniciar sesion...
        progressDialog.setMessage("Cargando aplicación, espere...");
        progressDialog.show();
        //Si se cancela el inicio de session se accionara este metodo
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Hubo un error, intentelo de nuevo",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //Metodo para ingresar a pantalla de registro...
    public void registro( View view){
        //  Intent ListSong = new Intent(getApplicationContext(), MainActivity.class);
        Intent ListSong = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(ListSong);
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

}
