package com.example.seabattle.Main;


import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class ShipsManager {
    private Field shipsField;
    private Field shadowField;
    private Field hitField;
    public boolean isFinished;

    private int[] type ={4,3,2,1};
    private  Stack<Ship> ships = new Stack<>();

    public UpdateManager updateManager;
    int count;



    public ShipsManager(){//constructor for placing
        shipsField = new Field(10,10);
        shipsField.fillValues(false);
        shadowField = new Field(10,10);
        shadowField.fillValues(true);
        hitField = new Field(10,10);
        hitField.fillValues(false);
        this.updateManager = new UpdateManager("placing_update","battle_update");
        count = 0;
    }
    public ShipsManager(Field shipsField){
        this.shipsField = shipsField;
        hitField = new Field(10,10);
        hitField.fillValues(false);

        this.updateManager = new UpdateManager("placing_update","battle_update");
        updateManager.startUpdate("battle_update");
    }

    public Field getShipsField() {
        return shipsField;
    }
    public Field getHitGrid() {
        return hitField;
    }

    //чи може бути встановлений корабель в сітці
    boolean canBePlaced(Ship ship){
        ArrayList<int[]> shipPart = ship.getCorpus();
        for (int[] part: shipPart
        ) {
            if(part[0]>9||part[1]>9){
                return false;
            }
            if(part[0]<0||part[1]<0){
                return false;
            }

            if(!shadowField.getField()[part[0]][part[1]]){
                return false;
            }

        }


        return true;
    }
    //перевірка чи отримані координати всередині комірки
    boolean inBounds(int[] cell){

        if(cell[0]>9||cell[1]>9){
            return false;
        }
        if(cell[0]<0||cell[1]<0){
            return false;
        }

        return true;
    }
    // автоматичне розміщення кораблів на сітці
    public void autoPlaceFleet(){
        for (int t = type.length-1; t >=0; t--) {

            int shipsInType = type[t];

            for (int i = 0; i < shipsInType; i++) {
                autoPlaceShip(t+1);
            }


        }
    }
    // розмістити корабель
    public void autoPlaceShip(int shipLength){
        int shipAmmount = ships.size();
        Random r = new Random();
        boolean rotation = r.nextBoolean();
        while (shipAmmount == ships.size()){

            int randomYPos = (shipLength-1) + (int) (Math.random() * 10);
            int randomXPos = 0 + (int) (Math.random() * (11-shipLength));

            placeShip(new int[]{randomXPos,randomYPos},shipLength);

        }
        if(rotation){
            rotateShip();
        }


    }

    public void placeShip(int[] cell, int length) {
        Ship newShip = new Ship(length);
        newShip.setPlace(cell);


        if (!ships.empty()) {
            ArrayList<int[]> shadowList = ships.peek().getShadow();
            for (int[] part : shadowList) {
                if (inBounds(part)) shadowField.setCell(part[0], part[1], false);
            }
        }

        if (!canBePlaced(newShip)) {
            return;
        }
        if (type[length - 1] <= 0) {
            return;
        }


        ships.add(newShip);
        type[length - 1]--;
        ArrayList<int[]> shipList = ships.peek().getCorpus();
        for (int[] part : shipList) {
            shipsField.setCell(part[0], part[1], true);
        }

        updateManager.startUpdate("placing_update");
    }
    // розвернути корабель
    public void rotateShip(){

        Ship rotatedShip = ships.peek();
        rotatedShip.rotate();

        if(!canBePlaced(rotatedShip))return;
        rotatedShip.rotate();
        ArrayList<int[]> rotatedShipList = rotatedShip.getCorpus();


        for (int[] part: rotatedShipList
        ) {
            shipsField.setCell(part[0],part[1],false);
        }
        rotatedShip.rotate();
        rotatedShipList = rotatedShip.getCorpus();
        for (int[] part: rotatedShipList
        ) {
            shipsField.setCell(part[0],part[1],true);
        }
        ships.pop();
        ships.add(rotatedShip);

        updateManager.startUpdate("placing_update");

    }

    public boolean takeHit(int posX, int posY){

        if(!hitField.getCell(posX,posY)) {
            hitField.setCell(posX,posY, true);
                updateManager.startUpdate("battle_update");

            if(shipsField.getField()[posX][posY] == true ){
                count ++;
            }
            isFinished = (count == 20);

            return true;
        }
        else {
            return false;
        }
    }
    public Stack<Ship> getShips() {
        return ships;
    }
    public void setShips(Stack<Ship> ships) {
        this.ships = ships;
    }



}
