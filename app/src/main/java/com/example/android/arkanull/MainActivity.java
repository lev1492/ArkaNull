package com.example.android.arkanull;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private UpdateThread myThread;
    private Handler updateHandler;

    //Classic is the original game without modifications
    private static final int CLASSIC = 0;

    //Arkanull is the main game, with power-ups and different levels
    private static final int ARKANULL = 1;

    private final int EASY = 0;
    private final int NORMAL = 1;
    private final int HARD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // nastavi orientaciu obrazovky
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // vytvori novu hru
        game = new Game(this, 3, 0, ARKANULL, NORMAL);
        setContentView(game);

        // vytvori handler a thread
        VytvorHandler();
        myThread = new UpdateThread(updateHandler);
        myThread.start();
    }

    private void VytvorHandler() {
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
        game.zastavSnimanie();
    }

    protected void onResume() {
        super.onResume();
        game.spustiSnimanie();
    }



}
