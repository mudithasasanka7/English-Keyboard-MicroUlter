package com.example.microulter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnEnableKeyboard = findViewById(R.id.btnEnableKeyboard);

        // Open keyboard settings on button click
        btnEnableKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                startActivity(intent);
            }
        });
    }
}