package com.sms.nullpointers.arkanull.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.sms.nullpointers.arkanull.R;
import com.sms.nullpointers.arkanull.record.Record;
import com.sms.nullpointers.arkanull.record.RecordAdapter;

import java.util.ArrayList;

public class PunteggiActivity extends AppCompatActivity {


    ArrayList<Record> classifica = new ArrayList<>();
    ListView listViewClassifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punteggi);
    }

    @Override
    protected void onStart() {
        super.onStart();
        classifica = getIntent().getParcelableArrayListExtra("classifica");
        for(Record user : classifica ){
            Log.d("PunteggiActivity", user.getDisplayName() + " " + user.getMail() + " " + user.getScore());
        }

        listViewClassifica = findViewById(R.id.classifica);
        RecordAdapter classificaAdapter = new RecordAdapter(this, classifica);

        listViewClassifica.setAdapter(classificaAdapter);

    }
}





