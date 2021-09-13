package com.sms.nullpointers.arkanull.activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sms.nullpointers.arkanull.MainActivity;
import com.sms.nullpointers.arkanull.game.Arkanull;
import com.sms.nullpointers.arkanull.record.DAORecord;
import com.sms.nullpointers.arkanull.game.Game;
import com.sms.nullpointers.arkanull.R;
import com.sms.nullpointers.arkanull.record.Record;

import java.util.ArrayList;
import java.util.HashMap;


public class GiocaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Button buttonLivelli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioca);
    }

    public void openLivelli(View view){
        Intent intent = new Intent(this , LivelliActivity.class);
        startActivity(intent);
        buttonLivelli = findViewById(R.id.livelli);
    }

    public void openCarriera(View view){
        Intent intent = new Intent(this , CarrieraActivity.class);
        startActivity(intent);
    }

    public void openCustomLevel(View view){
        Intent intent = new Intent(this, Arkanull.class);
        intent.putExtra("GameMode", Game.EDITOR_LEVEL);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /**if (id == R.id.action_settings) {
         return true;
         }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_home:
                Intent home= new Intent(this, MainActivity.class);
                startActivity(home);
                break;

            case R.id.nav_classifica:
                openClassifica();
                break;

            case R.id.nav_impostazioni:
                Intent impostazioni= new Intent(this,ImpostazioniActivity.class);
                startActivity(impostazioni);
                break;
            // this is done, now let us go and intialise the home page.
            // after this lets start copying the above.
            // FOLLOW MEEEEE>>>
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openClassifica(){
        Intent intent = new Intent(this , PunteggiActivity.class);
        String gameMode = Game.GAME_MODE[Game.ARKANULL];

        DAORecord dao = new DAORecord(gameMode);
        DatabaseReference mReference = dao.getDatabaseReference();
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Record> users = dao.readRanking(snapshot);
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
}