package com.example.android.arkanull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

//classe che apre le impostazioni del telefono
public class ImpostazioniActivity extends AppCompatActivity {

    Intent intent;
    private final static String ACCELEROMETRO = "accelerometro";
    private final static String TOUCH = "touch";

    private static int ACCELEROMETER_INPUT = 1;
    private static int TOUCH_INPUT = 2;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        intent = new Intent(this, MenuActivity.class);
    }

    public void onTypeInputClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.accelerometro:
                if (checked)
                    typeInput = ACCELEROMETER_INPUT;
                    break;
            case R.id.touch:
                if (checked)
                    typeInput = TOUCH_INPUT;
                    break;
        }
        intent.putExtra("tipoInput", typeInput);
    }

    public void onSaveSetting(View view) {
        startActivity(intent);
    }

}