package com.example.android.arkanull;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DAORecord {

    public final static String RANKING = "Ranking";
    public final static String MULTIPLAYER = "Multiplayer";

    private DatabaseReference databaseReference;
    ArrayList<Record> users = new ArrayList<Record>();

    public DAORecord(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Record.class.getSimpleName());
    }

    public Task<Void> add (Record record, String childValue){

        return databaseReference.child(childValue).push().setValue(record);
    }

    public Task<Void> update(String key , String childValue, HashMap<String , Object> hashMap){
        return databaseReference.child(childValue).child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }


    public ArrayList<Record> getUsers() {
        return users;
    }

    public ArrayList<Record> readClassifica(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    users.add(keyNode.getValue(Record.class));
                    Log.d("Game Download Dati", String.valueOf(snapshot.child("mail").getValue()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return users;

    }


}
