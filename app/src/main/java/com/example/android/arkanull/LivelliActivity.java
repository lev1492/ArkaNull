package com.example.android.arkanull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LivelliActivity extends AppCompatActivity {

    private static int LEVEL;

    public static int getLEVEL() {
        return LEVEL;
    }

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livelli);
        intent = new Intent(this, MainActivity.class);
    }

    public void onLevel (View view){

        switch (view.getId()){
            case R.id.easy:
                LEVEL = Game.getEASY();
                Toast.makeText( view.getContext(), "Livello Easy", Toast.LENGTH_SHORT).show();
                break;

            case R.id.normal:
                LEVEL = Game.getNORMAL();
                Toast.makeText( view.getContext(), "Livello Medium", Toast.LENGTH_SHORT).show();
                break;

            case R.id.hard:
                LEVEL = Game.getHARD();
                Toast.makeText( view.getContext(), "Livello Hard", Toast.LENGTH_SHORT).show();
                break;

        }
        startActivity(intent);
    }
}