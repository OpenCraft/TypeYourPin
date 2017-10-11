package com.opencraft.gcherubini.typeyourpinsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.opencraft.gcherubini.typeyourpin.TypeYourPinInterface;
import com.opencraft.gcherubini.typeyourpin.TypeYourPinLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTypeYourPin();
    }

    private void setupTypeYourPin() {
        TypeYourPinLayout layout = (TypeYourPinLayout) findViewById(R.id.type_your_pin_layout);
        layout.setTypeYourPinInterface(new TypeYourPinInterface() {
            @Override
            public void onPinTyped(String pin) {
                Toast.makeText(getApplicationContext(), pin, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
