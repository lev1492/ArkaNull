package com.example.android.arkanull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaSfideActivity extends AppCompatActivity {

    static ArrayList<Record> classifica = new ArrayList<>();
    ListView listViewClassifica;
    String[] id;
    String selectedId;
    Intent intent;
    public static int SCORE2;
    String TAG ="LISTA_SFIDE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sfide);
        if(savedInstanceState!= null){
            classifica = savedInstanceState.getParcelableArrayList("classifica");
            id = savedInstanceState.getStringArray("id");
            Log.d(TAG, ":ON_START:stampa classifica" + classifica);

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if ( classifica.isEmpty()) {
            classifica = getIntent().getParcelableArrayListExtra("classifica");
            id = getIntent().getStringArrayExtra("id");
        }

        listViewClassifica = findViewById(R.id.classifica);
        RecordAdapter classificaAdapter = new RecordAdapter(this, classifica);

        listViewClassifica.setAdapter(classificaAdapter);

        listViewClassifica.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Record myObject = (Record) classificaAdapter.getItem(position);
                selectedId = id[position];
                Toast.makeText( listViewClassifica.getContext() , id[position], Toast.LENGTH_SHORT).show();
                if(selectedId != null) {
                    intent.putExtra("idSfida", selectedId);
                    SCORE2 = myObject.getScore();
                    intent.putExtra("GameMode", Game.MULTIPLAYER);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList("classifica", classifica);
        state.putStringArray("id", id);
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        classifica = state.getParcelableArrayList("classifica");
        id = state.getStringArray("id");

    }
    public void openNewChallange(View view){
        Intent intent = new Intent(this , MainActivity.class);
        intent.putExtra("GameMode", Game.MULTIPLAYER);
        startActivity(intent);
    }

    public void openHistoricalChallange(View view){
        Intent intent = new Intent(this , HistoricalChallangeActivity.class);
        String gameMode = Game.GAME_MODE[Game.MULTIPLAYER];

        DAORecord dao = new DAORecord(gameMode);
        DatabaseReference mReference = dao.getDatabaseReference();
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Challange> challanges = new ArrayList<>();
                challanges = dao.readHistoricalChallange(snapshot);

                intent.putParcelableArrayListExtra("challange", challanges);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}