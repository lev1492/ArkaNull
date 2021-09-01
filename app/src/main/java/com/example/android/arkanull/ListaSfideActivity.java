package com.example.android.arkanull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ListaSfideActivity extends AppCompatActivity {

    ArrayList<Record> classifica = new ArrayList<>();
    ListView listViewClassifica;
    String[] id;
    String selectedId;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sfide);
        intent = new Intent(this, MainActivity.class);
    }
    @Override
    protected void onStart() {
        super.onStart();
        classifica = getIntent().getParcelableArrayListExtra("classifica");
        id = getIntent().getStringArrayExtra("id");
        int i = 0;
        //String userEmailLogged = LoginActivity.getmFirebaseAuth().getCurrentUser().getEmail();

        for (Record user : classifica) {
          /*  if (user.getMail().equals(userEmailLogged)){
                classifica.remove(user);
            }*/
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
                selectedId = id[position];
                Toast.makeText( listViewClassifica.getContext() , id[position], Toast.LENGTH_SHORT).show();
                if(selectedId != null) {
                    intent.putExtra("idSfida", selectedId);
                    intent.putExtra("GameMode", Game.MULTIPLAYER);
                    startActivity(intent);
                }
            }
        });

    }
}