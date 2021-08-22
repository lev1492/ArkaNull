package com.example.android.arkanull;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ClassificaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);
    }

    public void openPunteggi(View view){

        DAORecord dao = new DAORecord(DAORecord.RANKING);
        DatabaseReference mReference = dao.getDatabaseReference();
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //FirebaseUser user = LoginActivity.getmFirebaseAuth().getCurrentUser();
                //DAORecord.saveDate(dao, snapshot, user, score, DAORecord.RANKING);
                ArrayList<Record> users = new ArrayList<Record>();

                users = dao.readClassifica(snapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Intent intent = new Intent(this , PunteggiActivity.class);
        startActivity(intent);
    }
}