package com.sms.nullpointers.arkanull.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sms.nullpointers.arkanull.MainActivity;
import com.sms.nullpointers.arkanull.game.Arkanull;
import com.sms.nullpointers.arkanull.challenge.Challange;
import com.sms.nullpointers.arkanull.record.DAORecord;
import com.sms.nullpointers.arkanull.game.Game;
import com.sms.nullpointers.arkanull.R;
import com.sms.nullpointers.arkanull.record.Record;
import com.sms.nullpointers.arkanull.record.RecordAdapter;

import java.util.ArrayList;

public class MultiplayerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    NavigationView navigationView;
    Typeface typeface = Typeface.defaultFromStyle(Typeface.BOLD);
    private Button historicalChallanges;
    private Button newChallange;
    static ArrayList<Record> classifica = new ArrayList<>();
    ListView listViewClassifica;
    String[] id;
    String selectedId;
    Intent intent;
    public static int SCORE2;
    String TAG ="LISTA_SFIDE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        historicalChallanges = findViewById(R.id.historicalChallanges);
        newChallange = findViewById(R.id.newChallange);
        historicalChallanges.setTypeface(typeface);
        newChallange.setTypeface(typeface);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView logout = findViewById(R.id.logout);
        FirebaseUser user = LoginActivity.getmFirebaseAuth().getCurrentUser();
        String nomeUtente = user.getDisplayName();
        String logoutText = getResources().getString(R.string.logout);
        if( nomeUtente == null ) {
            nomeUtente = "";
            logoutText = getResources().getString(R.string.login);
            logout.setText(logoutText);
        } else if (nomeUtente.isEmpty() ) {
            nomeUtente = "";
            logoutText = getResources().getString(R.string.login);
        }
        logout.setText(logoutText);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState != null){
            classifica = savedInstanceState.getParcelableArrayList("classifica");
            id = savedInstanceState.getStringArray("id");
            Log.d(TAG, ":ON_START:stampa classifica" + classifica);

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this , Arkanull.class);
        if ( classifica.isEmpty()) {
            classifica = getIntent().getParcelableArrayListExtra("classifica");
            id = getIntent().getStringArrayExtra("id");
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
                    SCORE2 = myObject.getScore();
                    intent.putExtra("GameMode", Game.MULTIPLAYER);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList("classifica", classifica);
        state.putStringArray("id", id);
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        classifica = state.getParcelableArrayList("classifica");
        id = state.getStringArray("id");

    }
    public void openNewChallange(View view){
        Intent intent = new Intent(this , Arkanull.class);
        intent.putExtra("GameMode", Game.MULTIPLAYER);
        startActivity(intent);
    }

    public void openHistoricalChallange(View view){
        Intent intent = new Intent(this , ListaSfideActivity.class);
        String gameMode = Game.GAME_MODE[Game.MULTIPLAYER];

        DAORecord dao = new DAORecord(gameMode);
        DatabaseReference mReference = dao.getDatabaseReference();
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Challange> challanges = new ArrayList<>();
                challanges = dao.readHistoricalChallange(snapshot);

                intent.putParcelableArrayListExtra("challange", challanges);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
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
                Intent classifica= new Intent(this, ClassificaActivity.class);
                startActivity(classifica);
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

    public void logOut (View view) {
        LoginActivity.logOut();
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
    }
}