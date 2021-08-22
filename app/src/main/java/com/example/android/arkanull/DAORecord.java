package com.example.android.arkanull;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DAORecord {

    public final static String RANKING = "Ranking";
    public final static String MULTIPLAYER = "Multiplayer";

    private DatabaseReference databaseReference;
    ArrayList<Record> users = new ArrayList<Record>();

    public DAORecord(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Record.class.getSimpleName());

    }

    public DAORecord(String childValue){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference().child(Record.class.getSimpleName()).child(childValue);

    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> add (Record record){

        return databaseReference.push().setValue(record);
    }

    public Task<Void> add (Record record, String childValue){

        return databaseReference.child(childValue).push().setValue(record);
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

    static class recordScoreComparator implements Comparator<Record> {
        @Override
        public int compare(Record a, Record b) {
            return b.getScore() - a.getScore();
        }

    }

    public ArrayList readClassifica(@NonNull DataSnapshot snapshot) {
        ArrayList<Record> users = new ArrayList<Record>();

        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            String email = String.valueOf(dataSnapshot.child("mail").getValue());
            String name = String.valueOf(dataSnapshot.child("displayName").getValue());
            String scoreString = String.valueOf(dataSnapshot.child("score").getValue());
            int score = Integer.parseInt(scoreString);
            Record record = new Record(email, name, score);
            users.add(record);

            Log.d("Game Caricamento Dati ReadClassifica", dataSnapshot.getKey() + " " +email + "  " + name + "  " + score);


        }
        Collections.sort(users, new recordScoreComparator());
        for(Record user : users){
            Log.d("After Sort", user.getDisplayName() + " " + user.getMail() + " " + user.getScore());
        }
        return users;
    }

    public void saveDate(@NonNull DataSnapshot snapshot, FirebaseUser user, int score, String childValue) {
        boolean found = false;
        HashMap<String, Object> userUpdate = new HashMap<>();

        //TEST readClassifica, da eliminare -- Funziona
        ArrayList<Record> users = readClassifica(snapshot);

        if ( user.getEmail() != null) {
            Record record = new Record(user.getEmail(), user.getDisplayName(), score);

            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String email = String.valueOf(dataSnapshot.child("mail").getValue());
                String name = String.valueOf(dataSnapshot.child("displayName").getValue());
                String scoreD = String.valueOf(dataSnapshot.child("score").getValue());

                if (user.getEmail().equals(email)) {
                    userUpdate.put("mail", user.getEmail());
                    userUpdate.put("displayName", user.getDisplayName());
                    userUpdate.put("score", score);
                    if(score > Integer.parseInt(scoreD)) {
                        update(dataSnapshot.getKey(), userUpdate);
                    }
                    found = true;
                }
                Log.d("Game Caricamento Dati", dataSnapshot.getKey() + " " +email + "  " + name + "  " + scoreD);

            }
            if(!found) {
                add(record);
            }

        }
    }


}
