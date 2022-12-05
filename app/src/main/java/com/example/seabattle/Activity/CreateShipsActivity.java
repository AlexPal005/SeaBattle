package com.example.seabattle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.seabattle.Main.ShipsManager;
import com.example.seabattle.Main.Field;
import com.example.seabattle.R;
import com.example.seabattle.Main.TableCreator;

public class CreateShipsActivity extends AppCompatActivity {

    private TableCreator tableCreator;
    private ShipsManager shipsManager;
    private TableLayout tableUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ships);


        tableUI = findViewById(R.id.table_create);

        shipsManager = new ShipsManager();
        tableCreator = new TableCreator(this, tableUI, shipsManager.getShipsField(), shipsManager.getHitGrid());
        shipsManager.updateManager.newUpdate("placing_update", tableCreator);
        tableCreator.createTable(tableUI);

        Button autoPlacementButton = findViewById(R.id.buttonAuto);
        autoPlacementButton.setOnClickListener(v -> shipsManager.autoPlaceFleet());

        Button moveToBattleActivityButton = findViewById(R.id.buttonStartGame);
        moveToBattleActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateShipsActivity.this, BattleActivity.class);
            Field shipsField = shipsManager.getShipsField();
            Bundle bundle = new Bundle();
            bundle.putSerializable("field", shipsField.getField());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}