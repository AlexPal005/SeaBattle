package com.example.seabattle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;

import com.example.seabattle.Main.ShipsManager;
import com.example.seabattle.Main.Field;
import com.example.seabattle.R;
import com.example.seabattle.Main.TableCreator;

import java.util.Timer;
import java.util.TimerTask;

public class BattleActivity extends AppCompatActivity implements View.OnTouchListener {
    private TableCreator tableCreator1;
    private TableCreator tableCreator2;

    private ShipsManager shipsManager1;
    private ShipsManager shipsManager2;

    private TableLayout tableUI1;
    private TableLayout tableUI2;

    private View globalView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        globalView = findViewById(R.id.main_battle);

        tableUI1 = findViewById(R.id.tablePlayer1);
        tableUI2 = findViewById(R.id.tablePlayer2);

        boolean[][] arrayReceived=null;
        Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("field");

        if(objectArray!=null){

            arrayReceived = new boolean[objectArray.length][];

            for(int i=0;i<objectArray.length;i++){
                arrayReceived[i]=(boolean[]) objectArray[i];
            }
        }

        Field shipsField = new Field(arrayReceived);

        shipsManager1 = new ShipsManager(shipsField);
        tableCreator1 = new TableCreator(this, tableUI1, shipsManager1.getShipsField(), shipsManager1.getHitGrid());
        shipsManager1.updateManager.newUpdate("battle_update", tableCreator1);

        tableCreator1.createTable(tableUI1);


        shipsManager2 = new ShipsManager();
        shipsManager2.autoPlaceFleet();
        tableCreator2 = new TableCreator(this, tableUI2, shipsManager2.getShipsField(), shipsManager2.getHitGrid());
        shipsManager2.updateManager.newUpdate("battle_update", tableCreator2);

        tableCreator2.createTable(tableUI2);

        tableUI2.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int [] arr = tableCreator2.getCoordsUI(tableUI2,new int[]{(int)event.getRawX(),(int)event.getRawY()});
        attack(shipsManager2,arr[0],arr[1]);

        if(shipsManager1.isFinished){
            createDialog(v, "Ви програли!");


        }
        if(shipsManager2.isFinished){
            createDialog(v, "Ви виграли!");

        }

        return true;
    }
    public void attack(ShipsManager fleet, int posX, int posY){
        if(fleet.takeHit(posX,posY)){
            randAttack(shipsManager1);
        }
    }
    public void randAttack(ShipsManager shipsManager){
        int randomXPos,randomYPos;

        do {
            randomYPos = 0 + (int) (Math.random() * 10);
            randomXPos = 0 + (int) (Math.random() * 10);
        }
        while(!shipsManager.takeHit(randomXPos, randomYPos));
    }
    private void createDialog(View v, String massage){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle(massage);
        builder.setCancelable(true);

        final AlertDialog dlg = builder.create();

        dlg.show();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                dlg.dismiss();
                finish();
                timer.cancel();
            }
        }, 5000);

    }
}