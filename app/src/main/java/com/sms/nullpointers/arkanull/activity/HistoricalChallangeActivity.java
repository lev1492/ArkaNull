package com.sms.nullpointers.arkanull.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.sms.nullpointers.arkanull.challenge.Challange;
import com.sms.nullpointers.arkanull.challenge.ChallangeAdapter;
import com.sms.nullpointers.arkanull.R;

import java.util.ArrayList;


public class HistoricalChallangeActivity extends AppCompatActivity {

    private String TAG = "HistoricalChallangeActivity";
    ArrayList<Challange> historicalChallange = new ArrayList<>();
    ListView listViewChallange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_challange);
    }

    protected void onStart(){
        super.onStart();
        historicalChallange = getIntent().getParcelableArrayListExtra("challange");
        for(Challange user : historicalChallange ){
            Log.d(TAG + ":ON_START", user.getNamePlayer1() + " " + user.getScorePlayer1() + " " + user.getNamePlayer2() + " " + user.getScorePlaer2());
        }

        listViewChallange = findViewById(R.id.classifica);
        ChallangeAdapter classificaAdapter = new ChallangeAdapter(this, historicalChallange);

        listViewChallange.setAdapter(classificaAdapter);

    }
}