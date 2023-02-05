package Assignments.BattleShip;

public class Coordinate{

    private char letter = 'A';
    private int number = 0;

    public int letterCoordinate(){
        return Main.letterCoord(this.letter);
    }

    public int getNumber(){
        return this.number;
    }

    public char getLetter(){
        return this.letter;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void setLetter(char letter){
        this.letter = letter;
    }

    public void print(){
        System.out.println(this.letter + "" + this.number);
    }
};
