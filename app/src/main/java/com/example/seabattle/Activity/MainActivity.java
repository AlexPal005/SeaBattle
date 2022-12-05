package com.example.seabattle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.seabattle.Activity.CreateShipsActivity;
import com.example.seabattle.R;

public class MainActivity extends AppCompatActivity {
     Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button);
        button1.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateShipsActivity.class);
            startActivity(intent);
        });
    }

}