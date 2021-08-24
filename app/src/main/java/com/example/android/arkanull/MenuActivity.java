package com.example.android.arkanull;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView titolo = findViewById(R.id.nomeUtente);
        String nomeUtente = getIntent().getStringExtra("nomeUtente");
        if( nomeUtente == null ) {
            nomeUtente = "Guest";
        } else if (nomeUtente.isEmpty() ) {
            nomeUtente = "OspiteEmpty";
        }
        titolo.setText(nomeUtente);
        Log.i("onCreate NomeUtente: ", nomeUtente);

    }

    public void openGioca(View view){
        Intent intent = new Intent(this , GiocaActivity.class);
        startActivity(intent);
    }

    public void openClassifica(View view){
        Intent intent = new Intent(this , ClassificaActivity.class);
        startActivity(intent);
    }

    public void openImpostazioni(View view){
        Intent intent = new Intent(this , ImpostazioniActivity.class);
        startActivity(intent);
    }

    public void logOut (View view) {
        LoginActivity.logOut();
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
        Toast.makeText( view.getContext(), "Logout effettuato con successo", Toast.LENGTH_SHORT).show();
    }
}