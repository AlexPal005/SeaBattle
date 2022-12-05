package com.example.seabattle.Main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.seabattle.R;

public class TableCreator implements Updater {

    private Context context;
    private TableLayout tableUI;
    private Field shipsField;
    private Field hitField;

    public TableCreator(Context context, TableLayout tableUI, Field shipsField, Field hitField){
        this.context = context;
        this.tableUI = tableUI;
        this.shipsField =shipsField;
        this.hitField = hitField;
    }

    @Override
    public void update(String event){
        if(event =="placing_update") {
            updateTableVal(shipsField, tableUI, R.drawable.red_, true);
            updateTableVal(shipsField, tableUI, R.drawable.new_, false);
        }
        else if(event =="battle_update" ){
            updateTableVal(hitField, tableUI, R.drawable.zero, true);
            updateTableVal(hitField.AND(shipsField), tableUI, R.drawable.hitted, true);
        }
    }
    // сворити таблицю в інтерфейсі користувача
    public void createTable(TableLayout tableLayout){
        for (int i = 0; i < 10; i++) {

            TableRow tr = new TableRow(context);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 10; j++) {

                ImageView view = new ImageView(context);
                view.setImageResource(R.drawable.new_);
                tr.addView(view);
                view.getLayoutParams().height=60;
                view.getLayoutParams().width=60;
            }
            tableLayout.addView(tr);
        }

    }

    public void updateTableVal(Field grid, TableLayout tableLayout, int shipSpriteId, boolean value){

        boolean[][]table = grid.getField();

        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {

                if(table[i][j]==value) {
                    changeCell(tableLayout,true, i, j,shipSpriteId);
                }
            }
        }
    }
    // встановити картинку в комірку
    public void changeCell(TableLayout tableLayout, boolean cellValue, int posX, int posY, int trueSpriteId){
        View row = tableLayout.getChildAt(posY);
        ImageView image =(ImageView) ((ViewGroup) row).getChildAt(posX);

        if(cellValue==true)image.setImageResource(trueSpriteId);
    }
    // отримати координати комірки
    public int[] getCoordsUI(TableLayout tableLayout, int [] coordinates){

        int[] tableCoordinates = new int[2];
        tableLayout.getLocationOnScreen(tableCoordinates);
        int[] cellTableCoordinates = new int[2];
        int cellSize = 60;
        int tableSize = cellSize*10;

        if(tableCoordinates[0]>coordinates[0]||tableCoordinates[1]>coordinates[1])return new int[]{-1,-1};
        if(tableCoordinates[0]+tableSize<coordinates[0]||tableCoordinates[1]+tableSize< coordinates[1])return new int[]{-1,-1};

        coordinates[0]-=tableCoordinates[0];
        coordinates[1]-=tableCoordinates[1];
        cellTableCoordinates[0] = coordinates[0]/cellSize;
        cellTableCoordinates[1] = coordinates[1]/cellSize;

        return cellTableCoordinates;
    }



}
