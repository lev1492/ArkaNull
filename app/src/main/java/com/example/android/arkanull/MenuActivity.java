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
        String logoutText = getResources().getString(R.string.logout);
        if( nomeUtente == null ) {
            nomeUtente = "";
            logoutText = getResources().getString(R.string.login);
            logout.setText(logoutText);
        } else if (nomeUtente.isEmpty() ) {
            nomeUtente = "";
            logoutText = getResources().getString(R.string.login);
        }
        titolo.setText(nomeUtente);
        logout.setText(logoutText);
        Log.i("onCreate NomeUtente: ", nomeUtente);
    }

    public void openGioca(View view){
        Intent intent = new Intent(this , GiocaActivity.class);
        startActivity(intent);
    }

    public void openClassifica(View view){
        Intent intent = new Intent(this , ClassificaActivity.class);
        intent.putExtra("GameMode", Game.GAME_MODE[Game.ARKANULL]);
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