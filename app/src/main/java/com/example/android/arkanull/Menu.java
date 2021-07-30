package com.example.android.arkanull;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.arkanull.ui.login.LoginActivity;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


    }

    public void openAccount(View view){
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
    }
    public void gioca(View view){
        Intent intent = new Intent(this , Gioca.class);
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
}