package com.example.android.arkanull;

import android.os.Handler;

public class    UpdateThread extends Thread {
    Handler updatovaciHandler;

    public UpdateThread(Handler uh) {
        super();
        updatovaciHandler = uh;
    }

    public void run() {
        while (true) {
            try {
                sleep(32);
            } catch (Exception ex) {
            }
            updatovaciHandler.sendEmptyMessage(0);
        }
    }
}
