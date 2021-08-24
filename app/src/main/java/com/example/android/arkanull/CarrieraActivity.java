package com.example.android.arkanull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CarrieraActivity extends AppCompatActivity {

    private static int LEVEL;

    public static int getLEVEL() {
        return LEVEL;
    }

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carriera);
    }

    public void level1(View view){
        intent = new Intent(this , MainActivity.class);
        LEVEL = Game.getEASY();
        Toast.makeText( view.getContext(), "Livello 1", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}