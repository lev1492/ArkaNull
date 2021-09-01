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

    public final static String[] GameMode = {"Ranking", "Challange"};
    public final static String PLAYER1 = "Player1";
    public final static String PLAYER2 = "Player2";


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<Record> users = new ArrayList<Record>();

    public DAORecord(String dbName){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(dbName);

    }

    public DAORecord(String dbName, String childValue){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(dbName).child(childValue);
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> add (Record record){
        return databaseReference.push().setValue(record);
    }

    public Task<Void> add (Record record, String childValue ){
        return databaseReference.push().child(childValue).setValue(record);
    }

    public Task<Void> update(String key , String childValue, HashMap<String , Object> hashMap){
        return databaseReference.child(key).child(childValue).updateChildren(hashMap);
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
                //add per il ranking
                add(record);


            }
        }
    }

    public void newChallange(FirebaseUser user, int score) {
        if ( user.getEmail() == null ) {
            Log.d("Utente Ospite", "NULL");
        } else if (user.getEmail().isEmpty()){
            Log.d("Utente Ospite", "EMPTY");
        } else {
            Record record = new Record(user.getEmail(), user.getDisplayName(), score);
            add(record, DAORecord.PLAYER1);

        }
    }

    public void replyChallange(FirebaseUser user, int score, String id) {
        HashMap<String, Object> userUpdate = new HashMap<>();
        if ( user.getEmail() != null ) {
            userUpdate.put(Record.MAIL, user.getEmail());
            userUpdate.put(Record.DISPLAY_NAME, user.getDisplayName());
            userUpdate.put(Record.SCORE, score);
            update(id, DAORecord.PLAYER2, userUpdate);
        } else {
            Log.d("Utente Ospite", "OSPITE");
        }
    }

    public HashMap<String, Record> readChalleange(@NonNull DataSnapshot snapshot, String player) {
        //ArrayList<Record> users = new ArrayList<Record>();
        HashMap<String, Record> users = new HashMap<>();
        Log.d("READ_CHALLANGE", "Sono nella funzione read challange");

        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            String id = String.valueOf(dataSnapshot.getKey());
            String email = String.valueOf(dataSnapshot.child(player).child(Record.MAIL).getValue());
            String name = String.valueOf(dataSnapshot.child(player).child(Record.DISPLAY_NAME).getValue());
            String scoreString = String.valueOf(dataSnapshot.child(player).child(Record.SCORE).getValue());
            int score = Integer.parseInt(scoreString);
            Record record = new Record(email, name, score);
            users.put(id, record);
            Log.d("READ_CHALLANGE", id + " " + email + "  " + name + "  " + scoreString);

        }
        // Sort record by score
        return users;
    }

}
