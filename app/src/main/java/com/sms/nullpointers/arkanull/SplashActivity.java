package com.sms.nullpointers.arkanull;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this , LoginActivity.class);
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable , 1000);

    }
}