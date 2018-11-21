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

    public Game() {
        this(new Dice(), new Player("Player1"), new Player("Player2"));
    }

    public Game(Dice d, Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.dice = d;
        target = 100;
    }

    public void setTarget(int target){
        if(target <= dice.getSides()){
            throw new IllegalArgumentException("Pick a Number > Number of sides in the dice");
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

    public boolean checkWin(){
        return player1.getScore() > target || player2.getScore() > target;
    }

    public boolean isDraw(){
        return player1.getScore() > target && player2.getScore() > target;
    }

    public Player getWinner(){
        return player1.getScore() > target?  player1 : player2;
    }

    private int[] rollThreeTimes() {
        int[] rolls = new int[3];
        for (int i = 0; i < 3; i++) {
            rolls[i] = dice.roll();
        }
        Arrays.sort(rolls);
        return rolls;
    }

    public int getScoreFromRolls(int[] rolls) {
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
        return player1.getScore() > player2.getScore() ? player1 : player2.getScore() > player1.getScore() ? player2 : null;
    }

    public void TakeTurn(Player player) {
        int[] playerRolls = rollThreeTimes();
        player.addScore(getScoreFromRolls(playerRolls));
        player.addRoll(playerRolls);
    }

    public void undoLastTurn(Player player){
        // do nothing if the lists are empty.
        if(player.getRollCount() <=0){
            return;
        }

        int [] lastRoll = player.removeLastRoll();
        int scoreFromLast = getScoreFromRolls(lastRoll);
        player.reduceScore(scoreFromLast);
    }


}
