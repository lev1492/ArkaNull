package com.example.android.arkanull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;

//classe che apre le impostazioni del telefono
public class ImpostazioniActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        intent = new Intent(this, MenuActivity.class);

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

}