package com.example.android.arkanull;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaSfideActivity extends AppCompatActivity {

    ArrayList<Record> classifica = new ArrayList<>();
    ListView listViewClassifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sfide);
    }
    @Override
    protected void onStart() {
        super.onStart();
        classifica = getIntent().getParcelableArrayListExtra("classifica");
        for (Record user : classifica) {
            Log.d("PunteggiActivity", user.getDisplayName() + " " + user.getMail() + " " + user.getScore());
        }

        listViewClassifica = findViewById(R.id.classifica);
        RecordAdapter classificaAdapter = new RecordAdapter(this, classifica);

        listViewClassifica.setAdapter(classificaAdapter);
    }
}