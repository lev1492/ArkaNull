package com.sms.nullpointers.arkanull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sms.nullpointers.arkanull.R;

public class LivelliActivity extends AppCompatActivity {

    public boolean flagGMode;

    private static int LEVEL;

    public static int getLEVEL() {
        return LEVEL;
    }

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livelli);
        intent = new Intent(this, Arkanull.class);
    }

    public void onLevel (View view){
        String level = "";
        //flag che indica la modalità di gioco aracade
        switch (view.getId()){
            case R.id.easy:
                LEVEL = Game.EASY;
                level = "Livello Easy";
                break;

            case R.id.normal:
                LEVEL = Game.NORMAL;
                level = "Livello Medium";
                break;

            case R.id.hard:
                LEVEL = Game.HARD;
                level = "Livello Hard";
                break;

        }
        Toast.makeText( view.getContext(), level, Toast.LENGTH_SHORT).show();
        intent.putExtra("GameMode", Game.ARKANULL);
        this.startActivity(intent);
    }
}