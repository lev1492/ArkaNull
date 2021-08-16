package com.example.android.arkanull;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Record {
    private int score;
    private String displayName;
    private String mail;


    public Record(String mail , String displayName , int score ){
        this.mail = mail;
        this.displayName = displayName;
        this.score = score;
    }

    public Record(){

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMail(){
        return mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }

    public void readScore(FirebaseDatabase mDatabase , DatabaseReference mReference){
        String returnScore;
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Record");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String score = String.valueOf(dataSnapshot.child("score").getValue());

                    Log.d("OTTENGO" , "SCORE" + score);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
