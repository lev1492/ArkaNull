package com.example.android.arkanull;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class ClassificaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);
    }

    public void openPunteggi(View view){
        Intent intent = new Intent(this , PunteggiActivity.class);
        startActivity(intent);
    }
}