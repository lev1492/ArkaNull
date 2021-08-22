package com.example.android.arkanull;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private UpdateThread myThread;
    private Handler updateHandler;

    //Classic is the original game without modifications
    private static final int CLASSIC = 0;

    //Arkanull is the main game, with power-ups and different levels
    private static final int ARKANULL = 1;
    private static int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the screen orientation
        // se si vuole ripristinare lo stato precedente inserire SCREEN_ORIENTATION_PORTRAIT
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        orientation = getRequestedOrientation();

        Log.d("MainActivity","Sono in onCreate");


        // create a new game
        game = new Game(this, 3, 0, ARKANULL, LivelliActivity.getLEVEL());
        setContentView(game);

        // create handler a thread
        createHandler();
        myThread = new UpdateThread(updateHandler);
        myThread.start();

    }

    @Override
    protected void onStart() {
        super.onStart();


        Log.d("MainActivity","Sono in onStart");


    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        Log.d("MainActivity","Sono in onSaveInstanceState");


    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Log.d("MainActivity","Sono in onRestoreInstanceState");

    }

    private void createHandler() {
        updateHandler = new Handler() {
            public void handleMessage(Message msg) {
                game.invalidate();
                game.update();
                super.handleMessage(msg);
            }
        };
    }

    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","Sono in onPause");

        game.detectionStop();
    }

    protected void onResume() {
        super.onResume();

        Log.d("MainActivity","Sono in onResume");

        game.detectionStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("MainActivity","Sono in onDestroy");


    }
}
/*
    TextView vstup;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whatever);
        vstup = (TextView)findViewById(R.id.whatever);

    }

    @Override public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putCharSequence(App.VSTUP, vstup.getText());
    }

    @Override public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        vstup.setText(state.getCharSequence(App.VSTUP));
    }
*/