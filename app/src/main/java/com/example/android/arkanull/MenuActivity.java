package com.example.android.arkanull;

import android.content.Intent;
<<<<<<< HEAD:app/src/main/java/com/example/android/arkanull/Menu.java
=======

>>>>>>> 01894d81fed7b558753a2f9b8e22c6f7aa292e1d:app/src/main/java/com/example/android/arkanull/MenuActivity.java
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD:app/src/main/java/com/example/android/arkanull/Menu.java

public class Menu extends AppCompatActivity {

=======
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private String nomeUtente = null;

>>>>>>> 01894d81fed7b558753a2f9b8e22c6f7aa292e1d:app/src/main/java/com/example/android/arkanull/MenuActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
<<<<<<< HEAD:app/src/main/java/com/example/android/arkanull/Menu.java
    }

    public void openAccount(View view){
        Intent intent = new Intent(this , Login.class);
        startActivity(intent);
    }
    public void gioca(View view){
        Intent intent = new Intent(this , MainActivity.class);
=======

        TextView titolo = (TextView) findViewById(R.id.nomeUtente);
        nomeUtente = getIntent().getStringExtra("nomeUtente");
        if( nomeUtente.isEmpty() || nomeUtente.equals(null)) {
            nomeUtente = "Ospite";
        }
        titolo.setText(nomeUtente);
        Log.i("onCreate NomeUtente: ", nomeUtente);

    }

    public void openGioca(View view){
        Intent intent = new Intent(this , GiocaActivity.class);
>>>>>>> 01894d81fed7b558753a2f9b8e22c6f7aa292e1d:app/src/main/java/com/example/android/arkanull/MenuActivity.java
        startActivity(intent);
    }

    public void openClassifica(View view){
        Intent intent = new Intent(this , ClassificaActivity.class);
        startActivity(intent);
    }

    public void openImpostazioni(View view){
        Intent intent = new Intent(this , ImpostazioniActivity.class);
        startActivity(intent);
    }
<<<<<<< HEAD:app/src/main/java/com/example/android/arkanull/Menu.java
=======

    public void logOut (View view) {
        LoginActivity.logOut();
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
        Toast.makeText( view.getContext(), "Logout effettuato con successo", Toast.LENGTH_SHORT).show();
    }
>>>>>>> 01894d81fed7b558753a2f9b8e22c6f7aa292e1d:app/src/main/java/com/example/android/arkanull/MenuActivity.java
}