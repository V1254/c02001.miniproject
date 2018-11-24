package Model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int score;
    private boolean turn;
    private String color;
    private List<int[]> rolls;

    public Player(){
        this("Anonymous");
    }

    public Player(String name){
        this.name = name;
        this.score = 0;
        this.turn = false;
        rolls = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public void setColor(String s){
        this.color = s;
    }

    public String getColor(){
        return this.color;
    }

    public int[] removeLastRoll(){
        int[] last = rolls.get(rolls.size() -1);
        rolls.remove(last);
        return last;
    }

    public int[] getLastRoll(){
        return rolls.get(rolls.size()-1);
    }

    public String getLastRollAsString(){
        int[] lastRoll = getLastRoll();
        String formatted = String.format("[%d,%d,%d]",lastRoll[0],lastRoll[1],lastRoll[2]);
        return formatted;
    }

    public void addRoll(int[] roll){
        this.rolls.add(roll);
    }

    public void reduceScore(int score){
        this.score-=score;
    }

    public int getRollCount(){
        return this.rolls.size();
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setRolls(List<int[]> rolls){
        this.rolls = rolls;
    }
}
