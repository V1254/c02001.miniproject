package Application;

import Model.Dice;
import Model.Player;

import java.util.Arrays;

public class Game {
    private Player player1;
    private Player player2;
    private Dice dice;
    private boolean winner;
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
        winner = false;
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

    public Dice getDice() {
        return dice;
    }

    public boolean isWinner() {
        return winner;
    }

    public int getTarget() {
        return target;
    }

    // change this to take button clicks instead.
    public void run() {
        System.out.println("Welcome to the Model.Dice Application.Game");
        while (!winner) {
            TakeTurn(player1);
            TakeTurn(player2);
            printScores();
            if (checkWin()) {
                if (isDraw()) {
                    printDraw();
                } else {
                    printWinner();
                }
                break;
            }
            printLeadingPlayer();
            System.out.println("=============");
        }
    }



    private void printDraw() {
        System.out.println("The Application.Game Has Ended in a Draw!");
    }



    private void printWinner() {
        Player winner = getWinner();
        String winnerString = String.format("Congrats %s , You Are THE WINNER!!!!", winner.getName());
        System.out.println(winnerString);
    }



    void rollThreeTimes() {
        for (int i = 0; i < 3; i++) {
            lastRolls[i] = dice.roll();
        }
    }



    int getScoreFromRolls(int[] rolls) {
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



    private boolean checkWin() {
        return player1.getScore() > target || player2.getScore() > target;
    }



    private boolean isDraw() {
        return player2.getScore() > target && player1.getScore() > target;
    }


    private Player leadingPlayer() {
        return player2.getScore() > player1.getScore() ? player2 : player1;
    }



    private void printScores() {
        String scores = String.format("Scores: %s -> %d | %s -> %d", player1.getName(), player1.getScore(), player2.getName(), player2.getScore());
        System.out.println(scores);
    }



    private void printLeadingPlayer() {
        if(player1.getScore() == player2.getScore()){
            System.out.println("Same Score!! No Leading Model.Player");
            return;
        }
        Player lead = leadingPlayer();
        System.out.println(String.format("Leading Model.Player: %s", lead.getName()));
    }



    public void TakeTurn(Player player) {
        rollThreeTimes();
        player.addScore(getScoreFromRolls(lastRolls));
        switchTurns();
    }

    void switchTurns(){
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



    public String getRollsAsString() {
        String formatted = String.format("[%d,%d,%d]",lastRolls[0],lastRolls[1],lastRolls[2]);
        return formatted;
    }


}
