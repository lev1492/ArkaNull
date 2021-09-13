package com.sms.nullpointers.arkanull.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sms.nullpointers.arkanull.MainActivity;
import com.sms.nullpointers.arkanull.R;
import com.sms.nullpointers.arkanull.game.Game;
import com.sms.nullpointers.arkanull.record.DAORecord;
import com.sms.nullpointers.arkanull.record.Record;

import java.util.ArrayList;

//classe che apre le impostazioni del telefono
public class ImpostazioniActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Intent intent;

    private static int ACCELEROMETER_INPUT = 1;
    private static int TOUCH_INPUT = 2;
    private static boolean SOUND = false;

    private static int typeInput = TOUCH_INPUT;

    public static int getTypeInput() {
        return typeInput;
    }

    public static int getTouchInput() {
        return TOUCH_INPUT;
    }

    public static int getAccelerometerInput() {
        return ACCELEROMETER_INPUT;
    }

    public static boolean isSound() { return SOUND; }

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        intent = new Intent(this, MainActivity.class);
        RadioButton acceleromterButton = findViewById(R.id.acceleromter);
        RadioButton touchButton = findViewById(R.id.touch);

        if ( typeInput == ACCELEROMETER_INPUT) {
            acceleromterButton.setChecked(true);
        } else if ( typeInput == TOUCH_INPUT) {
            touchButton.setChecked(true);
        }
        SwitchCompat audio = findViewById(R.id.audio);
        if ( SOUND ) {
            audio.setChecked(true);
        } else {
            audio.setChecked(false);
        }
    }

    public void onTypeInputClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.acceleromter:
                if (checked)
                    typeInput = ACCELEROMETER_INPUT;
                break;
            case R.id.touch:
                if (checked)
                    typeInput = TOUCH_INPUT;
                break;
        }
    }

    public void onSound(View view) {
        SwitchCompat audio = findViewById(R.id.audio);
        boolean checked = ((SwitchCompat) view).isChecked();
        SOUND = checked;
    }

    public void onSaveSetting(View view) {

        startActivity(intent);
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
                Intent home= new Intent(this,MainActivity.class);
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