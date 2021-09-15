package com.sms.nullpointers.arkanull.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.sms.nullpointers.arkanull.MainActivity;
import com.sms.nullpointers.arkanull.R;
import com.sms.nullpointers.arkanull.record.Record;
import com.sms.nullpointers.arkanull.record.RecordAdapter;

import java.util.ArrayList;

public class ClassificaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    ArrayList<Record> classifica = new ArrayList<>();
    ListView listViewClassifica;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        classifica = getIntent().getParcelableArrayListExtra("classifica");
        for(Record user : classifica ){
            Log.d("ClassificaActivity", user.getDisplayName() + " " + user.getMail() + " " + user.getScore());
        }

        listViewClassifica = findViewById(R.id.classifica);
        RecordAdapter classificaAdapter = new RecordAdapter(this, classifica);

        listViewClassifica.setAdapter(classificaAdapter);

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





