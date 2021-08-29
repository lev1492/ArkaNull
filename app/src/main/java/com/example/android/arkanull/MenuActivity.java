package com.example.android.arkanull;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    @Override
    protected void onStart(){
        super.onStart();
        TextView titolo = findViewById(R.id.nomeUtente);
        TextView logout = findViewById(R.id.logout);
        FirebaseUser user = LoginActivity.getmFirebaseAuth().getCurrentUser();
        String nomeUtente = user.getDisplayName();
        String logoutText;
        //TODO nomeUtente = ""; AND invece di logout -> accedi
        if( nomeUtente == null ) {
            nomeUtente = "";
            logoutText = "Accedi";
            logout.setText(logoutText);

        } else if (nomeUtente.isEmpty() ) {
            nomeUtente = "";
            logoutText = "Accedi";
            logout.setText(logoutText);
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
    }
}