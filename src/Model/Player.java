package Model;

public class Player {
    private String name;
    private int score;
    private boolean turn;
    private String color;

    public Player(){
        this("Anonymous");
    }

    public Player(String name){
        this.name = name;
        this.score = 0;
        this.turn = false;
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
}
