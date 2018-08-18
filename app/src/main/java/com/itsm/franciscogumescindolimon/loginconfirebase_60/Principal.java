package com.itsm.franciscogumescindolimon.loginconfirebase_60;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class Principal extends AppCompatActivity {

    ImageButton btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnSalir = (ImageButton) findViewById(R.id.btnSalir);

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir(view);
            }
        });
    }
    //Metoodo para salir de la autenticacion firebase
    public void salir(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(Principal.this, MainActivity.class));
    }
}
