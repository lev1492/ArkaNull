package com.example.android.arkanull;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class GiocaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioca);
    }

    public void openLivelli(View view){
        Intent intent = new Intent(this , LivelliActivity.class);
        startActivity(intent);
    }

    public void openCarriera(View view){
        Intent intent = new Intent(this , CarrieraActivity.class);
        startActivity(intent);
    }

    public void openMultiPlayer(View view){
        Intent intent = new Intent(this , MultiplayerActivity.class);
        startActivity(intent);
    }
}