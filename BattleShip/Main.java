package Assignments.BattleShip;

import java.util.*;

public class Main {


    public static void main(String[] args) {

        Coordinate AINextCoordinate = new Coordinate();
        shuffleCoordinate(AINextCoordinate);

        int[][] userBoard = createBoard();
        int[][] enemyBoard = createBoard();

        Ship[] userShips = createShips();
        Ship[] enemyShips = createShips();

        autoShipPlacements(userBoard, userShips);
        autoShipPlacements(enemyBoard,enemyShips);

        //Turn starts now!

        while(!(isGameDone(userShips) || isGameDone(enemyShips))){

            printBoardState(enemyBoard, 0);

            printBoardState(userBoard, 1);

            launch(askCoordinate(), enemyBoard, enemyShips);

            noobAILaunch(AINextCoordinate, userBoard, userShips);

        }

        printBoardState(enemyBoard, 1);

        printBoardState(userBoard, 1);

        if(isGameDone(userShips)){
            System.out.println("YOU LOST!");
        }else{
            System.out.println("YOU WIN!");
        }
    }

    public static int[][] createBoard(){
        return new int[10][10];
    } //Fine

    public static void printBoardState(int[][] gameBoard,int user){

        System.out.print("  ");

        for(int i = 0; i < gameBoard.length; i++){
            System.out.printf("%3s",(char)('A' + i));
        }

        System.out.println("");

        for(int i = 0; i < gameBoard.length; i++){

            System.out.printf("%2s", i + 1);

            for(int j = 0; j < gameBoard[i].length; j++){

                if (gameBoard[i][j] == 0) {
                    System.out.printf("%3s", "-");
                } else if (gameBoard[i][j] == 2) {
                    System.out.printf("%3s", "*");
                } else if (gameBoard[i][j] == 1) {
                    System.out.printf("%3s", "X");
                }
                if(user == 1 && gameBoard[i][j] >= 3){
                    System.out.printf("%3s", gameBoard[i][j]);
                }else if(user == 0 && gameBoard[i][j] >= 3){
                    System.out.printf("%3s", "-");
                }

            }
            System.out.println("");
        }
        System.out.println("");
    } //Fine

    public static void launch(Coordinate coordinate, int[][] gameBoard, Ship[] ships){

        /*
         * 0 represents water
         * 1 represents hit a ship
         * 2 represent hit but nothing there
         * 3-6 represents the ships
         * */

        while(isThereADamagedShip(coordinate,gameBoard,ships)
                || isThereNothing(coordinate,gameBoard,ships)) {

            System.out.println("You already Launched a missile there!");
            coordinate = askCoordinate();

        }

        if(isThereAShip(coordinate,gameBoard,ships)){

            System.out.println("HIT!");
            hit(coordinate,gameBoard,ships);

        }
        else{
            hit(coordinate,gameBoard,ships);
        }

    }

    public static boolean isThereAShip(Coordinate coordinate, int[][] gameBoard, Ship[] ships){

        for(Ship ship: ships){

            if(ship.getShipID() == gameBoard[coordinate.getNumber()][coordinate.letterCoordinate()]){

                return true;

            }
        }
        return false;
    }

    public static boolean isThereADamagedShip(Coordinate coordinate, int[][] gameBoard, Ship[] ships){

        return gameBoard[coordinate.getNumber()][coordinate.letterCoordinate()] == 1;

    }

    public static boolean isThereNothing(Coordinate coordinate, int[][] gameBoard, Ship[] ships){

        return gameBoard[coordinate.getNumber()][coordinate.letterCoordinate()] == 2;

    }

    public static boolean isValidateInt(String s){

        try{

            Integer.parseInt(s);

        }catch(Exception e){

            return false;

        }

        return true;
    }

    public static boolean isGameDone(Ship[] ships){

        int totalDeadShips = 0;

        for(Ship ship: ships){

            if(ship.getHp() == 0){
                totalDeadShips++;
            }
        }

        return totalDeadShips == ships.length;

    }

    public static void hitShip(Coordinate coordinate, int[][] gameBoard, Ship[] ships){

        for(Ship ship: ships){

            if(ship.getShipID() == gameBoard[coordinate.getNumber()][coordinate.letterCoordinate()]){

                ship.setHp(ship.getHp()-1);

                gameBoard[coordinate.getNumber()][coordinate.letterCoordinate()] = 1;

            }

        }

    }

    public static void autoShipPlacements(int[][] gameBoard, Ship[] ships){

        Random rand = new Random();

        for(Ship ship: ships){

            while(!ship.isOnWater()){

                int randomLetter = rand.nextInt(10);
                int randomNumber = rand.nextInt(10);
                int randomDirection = rand.nextInt(4); // NEWS (0,1,2,3)

                placeShip(gameBoard,ship,randomDirection,randomNumber,randomLetter);
            }

        }
    } //Auto places Ships

    public static void placeShip(int[][] gameBoard, Ship ship, int direction, int number, int letter){

        int freeSpace = 0;

        if(direction == 0 && (number + ship.getSize()) < gameBoard[1].length){// Checks North bounds

            for(int count = 0; count < ship.getSize(); count++){
                if(gameBoard[number+count][letter] == 0){
                    freeSpace++;
                }
            }

            if(freeSpace == ship.getSize()){
                for(int count = 0; count < ship.getSize(); count++) {
                    gameBoard[number + count][letter] = ship.getShipID();
                }
                ship.setOnWater(true);

            }

        }
        else if(direction == 1 && (letter + ship.getSize()) < gameBoard.length){ //Checks East bounds

            for(int count = 0; count < ship.getSize(); count++){
                if(gameBoard[number][letter+count] == 0){
                    freeSpace++;
                }
            }

            if(freeSpace == ship.getSize()){

                for(int count = 0; count < ship.getSize(); count++){
                    gameBoard[number][letter+count] = ship.getShipID();
                }

                ship.setOnWater(true);

            }


        }else if(direction == 2 && (number - ship.getSize()) > 0){ //Checks South bounds

            for(int count = 0; count < ship.getSize(); count++){
                if(gameBoard[number-count][letter] == 0){
                    freeSpace++;
                }
            }

            if(freeSpace == ship.getSize()){

                for(int count = 0; count < ship.getSize(); count++){
                    gameBoard[number-count][letter] = ship.getShipID();
                }

                ship.setOnWater(true);
            }


        }else if(direction == 3 && (letter - ship.getSize()) > 0) { //Check West bounds

            for (int count = 0; count < ship.getSize(); count++) {
                if (gameBoard[number][letter - count] == 0) {
                    freeSpace++;
                }
            }

            if (freeSpace == ship.getSize()) {

                for (int count = 0; count < ship.getSize(); count++) {
                    gameBoard[number][letter - count] = ship.getShipID();
                }
                ship.setOnWater(true);
            }
        }
    }

    public static Coordinate askCoordinate(){

        Scanner userInput = new Scanner(System.in);

        Coordinate coordinate = new Coordinate();

        boolean exit = true;

        while(exit) { //Verifies if the launch isn't the same

            System.out.println("Please Enter a Coordinate(eg. A1, B2, C3): ");

            String coord = userInput.next();

            char letter = coord.charAt(0);

            String num = coord.substring(1);

            while (!(coord.length() <= 3 //Input Max Length
                    && letter > 64 //First Index is A >=
                    && letter < 75 //First Index is <= J
                    && isValidateInt(num) //Parsing num2 part as integer
                    && Integer.parseInt(num) <= 10 //Integer must be less than or equal 10
                    && Integer.parseInt(num) > 0)) { //Integer must be Greater than 0
                System.out.println("Invalid, Please Enter a Coordinate: ");

                coord = userInput.next();

                letter = coord.charAt(0);

                num = coord.substring(1);
            }

            coordinate.setNumber(Integer.parseInt(num) - 1);
            coordinate.setLetter(letter);

            exit = false;
        }

        return coordinate;
    } //bugged with putting multiple coordinate on one line

    public static void shuffleCoordinate(Coordinate coordinate){

        Random rand = new Random();
        coordinate.setNumber(rand.nextInt(10));
        coordinate.setLetter((char) (rand.nextInt(10) + 'A'));

    }

    public static int letterCoord(char letter){
        return letter - 65;
    }

    public static Ship[] createShips(){

        Ship[] ships = {new Ship(3, 3, 3),
                new Ship(4, 4, 4),
                new Ship(5,5, 5),
                new Ship(6,6, 6)};

        return ships;

    }

    public static void hit(Coordinate coordinate, int[][] gameBoard, Ship[] ships){
        if(isThereAShip(coordinate,gameBoard,ships)){
            hitShip(coordinate,gameBoard,ships);
        }
        else{
            gameBoard[coordinate.getNumber()][coordinate.letterCoordinate()] = 2;
        }
    }

    public static void noobAILaunch(Coordinate AINextCoordinate, int[][] gameBoard, Ship[] ships){
        while(isThereNothing(AINextCoordinate,gameBoard,ships) || isThereADamagedShip(AINextCoordinate,gameBoard,ships)){
            shuffleCoordinate(AINextCoordinate);
        }
        hit(AINextCoordinate,gameBoard,ships);
    }

    //my attempt at creating an AI

    public static void launchAI(Coordinate AINextCoordinate, int[][]gameBoard, Ship[] ships){

        if(isThereDamagedShipNearby(AINextCoordinate,gameBoard,ships) && isThereADamagedShip(AINextCoordinate,gameBoard,ships)){

            AILogicHit(AINextCoordinate,gameBoard,ships);

        }
        else if(isThereADamagedShip(AINextCoordinate,gameBoard,ships) && isFreeSpaceNearby(AINextCoordinate,gameBoard,ships)){
            launchNearbyRandomly(AINextCoordinate,gameBoard,ships);
        }
        else{
            while(isThereNothing(AINextCoordinate,gameBoard,ships) || isThereADamagedShip(AINextCoordinate,gameBoard,ships)){
                shuffleCoordinate(AINextCoordinate);
            }
            hit(AINextCoordinate,gameBoard,ships);
        }
    }

    public static boolean isThereDamagedShipNearby(Coordinate coordinate, int[][] gameBoard, Ship[] ships){
        if(coordinate.getNumber() + 1 < 10 && gameBoard[coordinate.getNumber() + 1][coordinate.letterCoordinate()] == 1){ //North
            return true;
        }else if(coordinate.getNumber() - 1 > 0 && gameBoard[coordinate.getNumber() - 1][coordinate.letterCoordinate()] == 1){ //South
            return true;
        }else if(coordinate.letterCoordinate() + 1 < 10 && gameBoard[coordinate.getNumber()][coordinate.letterCoordinate() + 1] == 1){ //East
            return true;
        }else if(coordinate.letterCoordinate() - 1 > 0 && gameBoard[coordinate.getNumber()][coordinate.letterCoordinate() - 1] == 1){ ///South
            return true;
        }else{
            return false;
        }
    }

    public static void launchNearbyRandomly(Coordinate coordinate, int[][] gameBoard, Ship[] ships){
        Random rand = new Random();

        while(true) {

            int direction = rand.nextInt(4);

            if (direction == 0 && coordinate.getNumber() + 1 < 10) {

                hit(coordinate,gameBoard,ships);
                break;

            } else if (direction == 2 && coordinate.getNumber() - 1 > 0) {

                hit(coordinate,gameBoard,ships);
                break;

            } else if (direction == 1 && coordinate.letterCoordinate() + 1 < 10) {

                hit(coordinate,gameBoard,ships);
                break;

            } else if (direction == 3 && coordinate.letterCoordinate() - 1 > 0) {

                hit(coordinate,gameBoard,ships);
                break;

            }
        }
    }

    public static int checkNEWS(Coordinate coordinate, int[][] gameBoard, Ship[] ships, int direction){

        if(direction == 0 && coordinate.getNumber() + 1 < 10){ // checks north

            return gameBoard[coordinate.getNumber() + 1][coordinate.letterCoordinate()];

        }else if(direction == 2 && coordinate.getNumber() - 1 > 0){ // checks south

            return gameBoard[coordinate.getNumber() - 1][coordinate.letterCoordinate()];

        }else if(direction == 1 && coordinate.letterCoordinate() + 1 < 10){ //checks east

            return gameBoard[coordinate.getNumber()][coordinate.letterCoordinate() + 1];

        }else if(direction == 3 && coordinate.letterCoordinate() - 1 > 0){ //check west

            return gameBoard[coordinate.getNumber()][coordinate.letterCoordinate() - 1];

        }
        else return -1;
    }

    public static boolean isFreeSpaceNearby(Coordinate coordinate, int[][] gameBoard, Ship[] ships){

        if(checkNEWS(coordinate,gameBoard,ships, 0) != 2
                && checkNEWS(coordinate,gameBoard,ships, 0) != 1
                && checkNEWS(coordinate,gameBoard,ships, 1) != 2
                && checkNEWS(coordinate,gameBoard,ships, 1) != 1
                && checkNEWS(coordinate,gameBoard,ships, 2) != 2
                && checkNEWS(coordinate,gameBoard,ships, 2) != 1
                && checkNEWS(coordinate,gameBoard,ships, 3) != 2
                && checkNEWS(coordinate,gameBoard,ships, 3) != 1){
            return true;
        }
        else{
            return false;
        }

    }

    public static void AILogicHit(Coordinate coordinate, int[][] gameBoard, Ship[] ships){

        if(checkNEWS(coordinate,gameBoard,ships, 0) == 1 && checkNEWS(coordinate,gameBoard,ships, 2) == 1){

            for(int i = 1; coordinate.getNumber() + i < 10; i++){

                if(gameBoard[coordinate.getNumber() + i][coordinate.letterCoordinate()] == 2
                        && gameBoard[coordinate.getNumber() + i][coordinate.letterCoordinate()] == 0){
                    break;
                }
                else if(gameBoard[coordinate.getNumber() + i][coordinate.letterCoordinate()] != 1){ // cycle north till end of damaged ship

                    coordinate.setNumber(coordinate.getNumber() + i);
                    hit(coordinate,gameBoard,ships);
                    return;

                }
            }

            for(int i = 1; coordinate.getNumber() - i < 0; i++){

                if(gameBoard[coordinate.getNumber() - i][coordinate.letterCoordinate()] == 2
                        && gameBoard[coordinate.getNumber() - i][coordinate.letterCoordinate()] == 0){
                    break;
                }
                else if(gameBoard[coordinate.getNumber() - i][coordinate.letterCoordinate()] != 1){ // cycle north till end of damaged ship

                    coordinate.setNumber(coordinate.getNumber() - i);
                    hit(coordinate,gameBoard,ships);
                    return;

                }
            }

            shuffleCoordinate(coordinate);
            hit(coordinate,gameBoard,ships);

        }
        else if (checkNEWS(coordinate,gameBoard,ships, 1) == 1 && checkNEWS(coordinate,gameBoard,ships, 3) == 1){

            for(int i = 1; coordinate.letterCoordinate() + i < 10; i++){// cycle East till end of damaged ship

                if(gameBoard[coordinate.getNumber()][coordinate.letterCoordinate() + i] == 2
                        && gameBoard[coordinate.getNumber()][coordinate.letterCoordinate() + i] == 0){
                    break;
                }
                else if(gameBoard[coordinate.getNumber()][coordinate.letterCoordinate() + i] != 1){

                    coordinate.setLetter((char)(coordinate.getLetter() + i));
                    hit(coordinate,gameBoard,ships);
                    return;

                }
            }

            for(int i = 1; coordinate.letterCoordinate() - 1 < 0; i++){// cycle West till end of damaged ship

                if(gameBoard[coordinate.getNumber()][coordinate.letterCoordinate() - i] == 2
                        && gameBoard[coordinate.getNumber()][coordinate.letterCoordinate() - i] == 0){
                    break;
                }
                else if(gameBoard[coordinate.getNumber()][coordinate.letterCoordinate() - i] != 1){

                    coordinate.setLetter((char)(coordinate.getLetter() - i));
                    hit(coordinate,gameBoard,ships);
                    return;

                }
            }

            shuffleCoordinate(coordinate);
            hit(coordinate,gameBoard,ships);

        }
    }

}


