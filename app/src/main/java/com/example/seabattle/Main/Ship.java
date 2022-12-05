package com.example.seabattle.Main;


import java.util.ArrayList;

public class Ship {
    private int length;
    private int[]rootCell;
    private ArrayList<int[]> corpus;
    private ArrayList<int[]> shadow;
    private boolean rotatedVertically;

    public Ship(int len){
        length=len;
        rootCell=new int[]{-1,-1};
        corpus = new ArrayList<>();
        shadow = new ArrayList<>();
    }

    public void rotate(){
        rotatedVertically=!rotatedVertically;
        initShip();
    }

    public void setPlace(int[] rootCellPos){
        rootCell = rootCellPos;
        rotatedVertically=true;
        initShip();
    }
    public void initShip(){
        corpus.clear();
        if(rotatedVertically){
            for (int i = 0; i < length; i++) {
                corpus.add(new int[]{rootCell[0],(rootCell[1]-i)});
            } }
        else {
            for (int i = 0; i < length; i++) {
                corpus.add(new int[]{(rootCell[0] + i), rootCell[1]});

            } }
        initShadow();
    }
    public void initShadow(){
        shadow.clear();
        if(!corpus.isEmpty()){
            for (int c = 0; c < corpus.size(); c++){
                initCellShadow(c);
            }
        }
    }
    public void initCellShadow(int cellIndex){
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                int[] currentCell = new int[]{corpus.get(cellIndex)[0]+x,corpus.get(cellIndex)[1]+y};
                if(corpus.contains(currentCell));
                else if(shadow.contains(currentCell));
                else{
                    shadow.add(currentCell);
                }
            }
        }
    }
    public ArrayList<int[]> getShadow() {
        return shadow;
    }

    public void setShadow(ArrayList<int[]> shadow) {
        this.shadow = shadow;
    }

    public ArrayList<int[]> getCorpus() {
        return corpus;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getRootCell() {
        return rootCell;
    }

    public void setRootCell(int[] rootCell) {
        this.rootCell = rootCell;
    }

}
