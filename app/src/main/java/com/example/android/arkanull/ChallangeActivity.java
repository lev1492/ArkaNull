package com.example.android.arkanull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChallangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challange);
    }

    public void openNewChallange(View view){
        Intent intent = new Intent(this , MainActivity.class);
        intent.putExtra("GameMode", Game.MULTIPLAYER);
        startActivity(intent);
    }

    public void openReplyChallange(View view){
        Intent intent = new Intent(this , ListaSfideActivity.class);
        String gameMode = Game.GAME_MODE[Game.MULTIPLAYER];

        DAORecord dao = new DAORecord(gameMode);
        DatabaseReference mReference = dao.getDatabaseReference();
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Record> users = dao.readChalleange(snapshot, DAORecord.PLAYER1);
                //ArrayList<Record> users = dao.readChalleange(snapshot, DAORecord.PLAYER1);
                for(Record user : users){
                    Log.d("ClassificaActivity", user.getDisplayName() + " " + user.getMail() + " " + user.getScore());
                }
                intent.putParcelableArrayListExtra("classifica", users);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openHistoricalChallange(View view){
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }
}