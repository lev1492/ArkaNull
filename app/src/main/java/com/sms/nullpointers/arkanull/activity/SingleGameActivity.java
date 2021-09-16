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
import android.widget.Button;
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
import com.sms.nullpointers.arkanull.game.Game;
import com.sms.nullpointers.arkanull.R;
import com.sms.nullpointers.arkanull.record.DAORecord;
import com.sms.nullpointers.arkanull.record.Record;

import java.util.ArrayList;

public class SingleGameActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Button easy;
    private Button normal;
    private Button hard;
    Typeface typeface = Typeface.defaultFromStyle(Typeface.BOLD);

    public boolean flagGMode;

    private static int LEVEL;

    public static int getLEVEL() {
        return LEVEL;
    }

    Intent intent;

    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlegame);
        easy = findViewById(R.id.easy);
        normal = findViewById(R.id.normal);
        hard = findViewById(R.id.hard);
        easy.setTypeface(typeface);
        normal.setTypeface(typeface);
        hard.setTypeface(typeface);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        intent = new Intent(this, Arkanull.class);
    }

    public void onLevel (View view){
        String level = "";
        //flag che indica la modalità di gioco aracade
        switch (view.getId()){
            case R.id.easy:
                LEVEL = Game.EASY;
                level = "Livello Easy";
                break;

            case R.id.normal:
                LEVEL = Game.NORMAL;
                level = "Livello Medium";
                break;

            case R.id.hard:
                LEVEL = Game.HARD;
                level = "Livello Hard";
                break;

        }
        Toast.makeText( view.getContext(), level, Toast.LENGTH_SHORT).show();
        intent.putExtra("GameMode", Game.ARKANULL);
        this.startActivity(intent);
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
        Intent intent = new Intent(this , ClassificaActivity.class);
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

    public void logOut (View view) {
        LoginActivity.logOut();
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
    }

}