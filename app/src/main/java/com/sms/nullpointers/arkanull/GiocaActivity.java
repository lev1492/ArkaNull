package com.sms.nullpointers.arkanull;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sms.nullpointers.arkanull.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class GiocaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioca);
    }

    public void openLivelli(View view){
        Intent intent = new Intent(this , LivelliActivity.class);
        startActivity(intent);
    }

    public void openCarriera(View view){
        Intent intent = new Intent(this , CarrieraActivity.class);
        startActivity(intent);
    }

    public void openMultiPlayer(View view){
        Intent intent = new Intent(this , ListaSfideActivity.class);
        String gameMode = Game.GAME_MODE[Game.MULTIPLAYER];
        DAORecord dao = new DAORecord(gameMode);
        DatabaseReference mReference = dao.getDatabaseReference();
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Record> usersId = dao.readChalleange(snapshot, DAORecord.PLAYER1);
                String[] id = usersId.keySet().toArray(new String[0]);
                Record[] usersArray = usersId.values().toArray(new Record[0]);
                ArrayList<Record> users = new ArrayList<>();
                int i = 0;
                for(Record user : usersArray){
                    users.add(user);
                    Log.d("ClassificaActivity", id[i] + " " + user.getDisplayName() + " " + user.getMail() + " " + user.getScore());
                    i++;
                }
                intent.putExtra("id", id);
                intent.putParcelableArrayListExtra("classifica", users);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });    }
}