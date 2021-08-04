package com.example.android.arkanull;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.arkanull.ui.login.LoginActivity;


public class Gioca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioca);
    }

    public void openLivelli(View view){
        Intent intent = new Intent(this , Livelli.class);
        startActivity(intent);
    }

    public void openCarriera(View view){
        Intent intent = new Intent(this , Carriera.class);
        startActivity(intent);
    }

    public void openMultiPlayer(View view){
        Intent intent = new Intent(this , Multiplayer.class);
        startActivity(intent);
    }
}