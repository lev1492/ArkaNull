package com.example.android.arkanull;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView titolo = (TextView) findViewById(R.id.nomeUtente);
        String nomeUtente = getIntent().getStringExtra("nomeUtente");
        if(nomeUtente == null) {
            nomeUtente = "Ospite";
        }
        titolo.setText(nomeUtente);
        /*
            TODO il campo email è temporaneo, è da sostituire con nomeUtente
             cosa che verrà fatta dopo avere implementata la registrazione dell'utente
         */

    }


    public void gioca(View view){
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }

    public void classifica(View view){
        Intent intent = new Intent(this , Classifica.class);
        startActivity(intent);
    }

    public void impostazioni(View view){
        Intent intent = new Intent(this , Impostazioni.class);
        startActivity(intent);
    }
    public void logOut (View view) {
        Login.logOut();
        Intent intent = new Intent(this , Login.class);
        startActivity(intent);
        Toast.makeText( view.getContext(), "Logout effettuato con successo", Toast.LENGTH_SHORT).show();

    }
}