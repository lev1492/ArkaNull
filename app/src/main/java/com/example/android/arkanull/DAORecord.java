package com.example.android.arkanull;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class DAORecord {

    public final static String RANKING = "Ranking";
    public final static String MULTIPLAYER = "Challange";
    public final static String PLAYER1 = "Player1";
    public final static String PLAYER2 = "Player2";


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<Record> users = new ArrayList<Record>();

    public DAORecord(String dbName){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(dbName).push();

    }

    public DAORecord(String dbName, String childValue){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(dbName).child(childValue).push();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> add (Record record){
        return databaseReference.setValue(record);
    }

    public Task<Void> add (Record record, String childValue ){
        return databaseReference.child(childValue).setValue(record);
    }

    public Task<Void> update(String key , String childValue, HashMap<String , Object> hashMap){
        return databaseReference.child(childValue).child(key).updateChildren(hashMap);
    }

    public Task<Void> update(String key , HashMap<String , Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }

    public ArrayList<Record> getUsers() {
        return users;
    }

    static class RecordScoreComparator implements Comparator<Record> {
        @Override
        public int compare(Record a, Record b) {
            int compare = b.getScore() - a.getScore();
            if(compare == 0){
                compare = a.getDisplayName().compareTo(b.getDisplayName());
            }
            return compare;
        }

    }

    public ArrayList<Record> readRanking(@NonNull DataSnapshot snapshot) {
        ArrayList<Record> users = new ArrayList<Record>();

        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            String email = String.valueOf(dataSnapshot.child(Record.MAIL).getValue());
            String name = String.valueOf(dataSnapshot.child(Record.DISPLAY_NAME).getValue());
            String scoreString = String.valueOf(dataSnapshot.child(Record.SCORE).getValue());
            int score = Integer.parseInt(scoreString);
            Record record = new Record(email, name, score);
            users.add(record);
        }
        // Sort record by score
        Collections.sort(users, new RecordScoreComparator());
        return users;
    }

    public void saveScore(@NonNull DataSnapshot snapshot, FirebaseUser user, int score) {
        boolean found = false;
        HashMap<String, Object> userUpdate = new HashMap<>();

        if ( user.getEmail() == null ) {
            Log.d("Utente Ospite", "NULL");
        } else if (user.getEmail().isEmpty()){
            Log.d("Utente Ospite", "EMPTY");
        } else {
            Record record = new Record(user.getEmail(), user.getDisplayName(), score);

            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String email = String.valueOf(dataSnapshot.child(Record.MAIL).getValue());
                String name = String.valueOf(dataSnapshot.child(Record.DISPLAY_NAME).getValue());
                String scoreD = String.valueOf(dataSnapshot.child(Record.SCORE).getValue());

                if (user.getEmail().equals(email)) {
                    userUpdate.put(Record.MAIL, user.getEmail());
                    userUpdate.put(Record.DISPLAY_NAME, user.getDisplayName());
                    userUpdate.put(Record.SCORE, score);
                    if(score > Integer.parseInt(scoreD)) {
                        update(dataSnapshot.getKey(), userUpdate);
                    }
                    found = true;
                }
                Log.d("Game Caricamento Dati", dataSnapshot.getKey() + " " +email + "  " + name + "  " + scoreD);

            }
            if(!found) {
                add(record, DAORecord.PLAYER1);
            }
        }
    }

}
