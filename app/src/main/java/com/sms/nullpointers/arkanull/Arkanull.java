package com.sms.nullpointers.arkanull;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Arkanull extends AppCompatActivity {

    private Game game;
    private UpdateThread myThread;
    private Handler updateHandler;
    private Intent intent = new Intent();
    private int gameMode;
    private static String idSfida;
    private static int orientation;


    public static String getIdSfida() {
        return idSfida;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the screen orientation
        // se si vuole ripristinare lo stato precedente inserire SCREEN_ORIENTATION_PORTRAIT
        //TODO aggiungere nelle impostazioni la possibilità di scegliere l'orientamento del display
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        orientation = getRequestedOrientation();

        Log.d("Arkanull","Sono in onCreate");

        gameMode = getIntent().getExtras().getInt("GameMode", Game.CLASSIC);
        Log.d("gameMode Intent", "L'intent contiene: " + gameMode);
        idSfida = getIntent().getStringExtra("idSfida");
        Log.d("idMainReplyChallange", "idMainReplyChallange" + idSfida);

        // create a new game
        game = new Game(this, 3, 0, gameMode, LivelliActivity.getLEVEL());

        setContentView(game);

        // create handler a thread
        createHandler();
        myThread = new UpdateThread(updateHandler);
        myThread.start();


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
        game.detectionStop();
    }

    protected void onResume() {
        super.onResume();
        game.detectionStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

