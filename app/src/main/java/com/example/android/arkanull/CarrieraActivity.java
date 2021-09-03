package com.example.android.arkanull;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CarrieraActivity extends AppCompatActivity {

    private static int LEVEL;

    public static int getLEVEL() {
        return LEVEL;
    }

    private static int levelCareer;

    public static int nextLevel;

    public static Button s1;

    public static LevelEditor levelEdit;
    Intent intent;

    private Handler updateHandler;
    private UpdateThread myThread;

    private void createHandler() {
        updateHandler = new Handler() {
            public void handleMessage(Message msg) {
                levelEdit.invalidate();
                levelEdit.update();
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        levelEdit = new LevelEditor(this);
        setContentView(levelEdit);

        // create handler a thread
        createHandler();
        myThread = new UpdateThread(updateHandler);
        myThread.start();

        /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carriera);

        if (nextLevel>levelCareer){
            levelCareer=nextLevel;
        }
        for(int i=0; i<levelCareer; i++){
            String buttonID = "level" + (i+1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            s1 = ((Button) findViewById(resID));
            s1.setEnabled(true);
        }
         */


    }

    public void level(View view){
        TextView levelText = findViewById(view.getId());
        int level = Integer.parseInt(levelText.getText().toString());
        intent = new Intent(this , MainActivity.class);
        LEVEL = level;
        nextLevel=level;
        Toast.makeText( view.getContext(), "Livello " + level, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
/*
    public void level1(View view){
        MainActivity.flagGMode = false;
        intent = new Intent(this , MainActivity.class);
        LEVEL = 1;
        nextLevel=1;
        Toast.makeText( view.getContext(), "Livello 1", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


    public void level2(View view){
        MainActivity.flagGMode = false;
        intent = new Intent(this , MainActivity.class);
        LEVEL = 2;
        nextLevel=2;
        Toast.makeText( view.getContext(), "Livello 2", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void level3(View view){
        MainActivity.flagGMode = false;
        intent = new Intent(this , MainActivity.class);
        LEVEL = 3;
        nextLevel=3;
        Toast.makeText( view.getContext(), "Livello 3", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void level4(View view){
        MainActivity.flagGMode = false;
        intent = new Intent(this , MainActivity.class);
        LEVEL = 4;
        nextLevel=4;
        Toast.makeText( view.getContext(), "Livello 4", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void level5(View view){
        MainActivity.flagGMode = false;
        intent = new Intent(this , MainActivity.class);
        LEVEL = 5;
        nextLevel=5;
        Toast.makeText( view.getContext(), "Livello 5", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void level6(View view){
        MainActivity.flagGMode = false;
        intent = new Intent(this , MainActivity.class);
        LEVEL = 6;
        nextLevel=6;
        Toast.makeText( view.getContext(), "Livello 6", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void level7(View view){
        MainActivity.flagGMode = false;
        intent = new Intent(this , MainActivity.class);
        LEVEL = 7;
        nextLevel=7;
        Toast.makeText( view.getContext(), "Livello 7", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void level8(View view){
        MainActivity.flagGMode = false;
        intent = new Intent(this , MainActivity.class);
        LEVEL = 8;
        nextLevel=8;
        Toast.makeText( view.getContext(), "Livello 8", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void level9(View view){
        MainActivity.flagGMode = false;
        intent = new Intent(this , MainActivity.class);
        LEVEL = 9;
        nextLevel=9;
        Toast.makeText( view.getContext(), "Livello 9", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void level10(View view){
        MainActivity.flagGMode = false;
        intent = new Intent(this , MainActivity.class);
        LEVEL = 10;
        nextLevel=10;
        Toast.makeText( view.getContext(), "Livello 10", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
*/
}