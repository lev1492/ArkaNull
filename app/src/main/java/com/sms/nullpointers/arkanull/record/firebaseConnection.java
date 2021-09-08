package com.sms.nullpointers.arkanull.record;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class firebaseConnection extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
