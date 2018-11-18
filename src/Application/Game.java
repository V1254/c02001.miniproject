package Application;

import Model.Dice;
import Model.Player;

import java.util.Arrays;

public class Game {
    private Player player1;
    private Player player2;
    private Dice dice;
    private Player winner;
    private int target;
    private int[] lastRolls;
    // lazy constructor
    public Game() {
        this(new Dice(), new Player("Player1"), new Player("Player2"));
    }


    public Game(Dice d, Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.dice = d;
        target = 100;
        lastRolls = new int[3];
    }

    public void setTarget(int target){
        if(target <= dice.getSides()){
            throw new IllegalArgumentException("Pick a Number > Number of Sides in the Model.Dice You Have Chosen");
        }
        this.target = target;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }




    public int getTarget() {
        return target;
    }

    // change this to take button clicks instead.
//    public void run() {
//        System.out.println("Welcome to the Model.Dice Application.Game");
//        while (!winner) {
//            TakeTurn(player1);
//            TakeTurn(player2);
//            printScores();
//            if (checkWin()) {
//                if (isDraw()) {
//                    printDraw();
//                } else {
//                    printWinner();
//                }
//                break;
//            }
//            printLeadingPlayer();
//            System.out.println("=============");
//        }
//    }

    private void rollThreeTimes() {
        lastRolls = new int[3];
        for (int i = 0; i < 3; i++) {
            lastRolls[i] = dice.roll();
        }
    }

    private int getScoreFromRolls(int[] rolls) {
        Arrays.sort(rolls);
        if (rolls[0] == rolls[2]) { // if [x,?,x]  then ? = x in sorted array.
            return 18;
        } else if (rolls[0] == rolls[1]) {
            return rolls[0] * 2;
        } else if (rolls[1] == rolls[2]) {
            return rolls[1] * 2;
        }
        return 1;
    }

    public Player getLeadingPlayer() {
        return player2.getScore() > player1.getScore() ? player2 : player1;
    }

    public String getLeadingPlayerString() {
        if (player1.getScore() == player2.getScore()) {
            return "None!";
        }
        return getLeadingPlayer().getName();

    }

    public void TakeTurn(Player player) {
        rollThreeTimes();
        player.addScore(getScoreFromRolls(lastRolls));
        switchTurns();
    }

    private void switchTurns(){
        if(player1.isTurn()){
            player2.setTurn(true);
            player1.setTurn(false);
        } else {
            player1.setTurn(true);
            player2.setTurn(false);
        }
    }


    private Player getWinner() {
        return player1.getScore() > target ? player1 : player2;
    }



    public String getRollsAsString(int[] rollls) {
        String formatted = String.format("[%d,%d,%d]",rollls[0],rollls[1],rollls[2]);
        return formatted;
    }

    public int[] getLastRolls(){
        return lastRolls;
    }


}
