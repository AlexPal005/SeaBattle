package com.example.seabattle.Main;

import java.util.Arrays;

public class Field {

    private boolean[][] field;

    private int height;
    private int width;

    public boolean[][] getField() {
        return field;
    }

    public Field(int width, int height){
        this.width = width;
        this.height = height;
        this.field = new boolean[this.width][this.height];
    }
    public Field(boolean[][] field){

        this.field = field.clone();
        width =field.length;
        height =field.length;
    }
    public void fillValues(boolean filler){
        for (boolean[] row: field)
            Arrays.fill(row, filler);
    }
    public boolean isInside(int posX, int posY){
        if(posX> width ||posY> height){
            return false;
        }
        if(posX<0||posY<0){
            return false;
        }
        return true;
    }
    public void setCell(int xPos, int yPos, boolean value){
        field[xPos][yPos] = value;
    }

    public boolean getCell(int posX, int posY){
        if(isInside(posX,posY)){
            return field[posX][posY];
        }
        return false;
    }

    public Field AND(Field field){
        Field result = new Field(10,10);

        result.fillValues(false);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(getCell(i,j)== field.getCell(i,j)&& getCell(i,j)==true) result.setCell(i,j,true);
            }
        }
        return result;
    }
    public void setField(boolean[][] field) {
        this.field = field;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
