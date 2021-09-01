package com.example.android.arkanull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaSfideActivity extends AppCompatActivity {

    ArrayList<Record> classifica = new ArrayList<>();
    ListView listViewClassifica;
    String[] id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sfide);
    }
    @Override
    protected void onStart() {
        super.onStart();
        classifica = getIntent().getParcelableArrayListExtra("classifica");
        id = getIntent().getStringArrayExtra("id");
        int i = 0;
        for (Record user : classifica) {
            Log.d("PunteggiActivity", id[i] + " " + user.getDisplayName() + " " + user.getMail() + " " + user.getScore());
            i++;
        }

        listViewClassifica = findViewById(R.id.classifica);
        RecordAdapter classificaAdapter = new RecordAdapter(this, classifica);

        listViewClassifica.setAdapter(classificaAdapter);

        listViewClassifica.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Record myObject = (Record) classificaAdapter.getItem(position);
                Toast.makeText( listViewClassifica.getContext() , id[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}