package com.example.android.arkanull;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.arkanull.ui.login.LoginActivity;

public class Classifica extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);
    }

    public void openPunteggi(View view){
        Intent intent = new Intent(this , Punteggi.class);
        startActivity(intent);
    }
}