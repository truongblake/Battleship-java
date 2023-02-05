package Assignments.BattleShip;

public class Ship{

    private int shipID = 3;
    private int size = 0;
    private int hp = 1;
    private boolean onWater = false;

    public int getSize() {
        return this.size;
    }

    public boolean isOnWater(){
        return onWater;
    }

    public void setOnWater(boolean onWater){
        this.onWater = onWater;
    }

    public void setShipID(int id){
        this.shipID = id;
    }

    public int getShipID(){
        return this.shipID;
    }

    public int getHp(){
        return this.hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public Ship(int id, int size, int hp){

        this.hp = hp;

        this.shipID = id;

        this.size = size;

    }

};
