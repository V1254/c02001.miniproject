package Controllers;


import Application.Game;
import Model.Dice;
import Model.DiceImage;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    // player1 Controls
    @FXML
    Text player1Name,player1Roll,player1Score;
    @FXML
    ImageView player1Dice1,player1Dice2,player1Dice3;

    // player 2 Controls
    @FXML
    Text player2Name,player2Roll,player2Score;
    @FXML
    ImageView player2Dice1,player2Dice2,player2Dice3;

    // play buttons
    @FXML
    Button player1Play,player2Play;

    @FXML
    Label targetLabel;

    private boolean twoPlayerGame = true;

    DiceImage diceImage;
    Game game;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        diceImage = new DiceImage();
        Player player1 = new Player(player1Name.getText());
        player1.setTurn(true);
        Player player2 = new Player(player2Name.getText());
        game = new Game(new Dice(),player1,player2);
        targetLabel.setText(game.getTarget() + "");
        setUpFields();
    }

    // for external handler
    public void setPlayer1Name(String x){
        if(x.isEmpty()){
            this.player1Name.setText("Anonymous");
            return;
        }
        this.player1Name.setText(x);
    }

    public void setPlayer2Name(String x){
        if(!twoPlayerGame){
            this.player2Name.setText("Computer");
            return;
        } else if(x.isEmpty()){
            player2Name.setText("Anonymous");
            return;
        }
        this.player2Name.setText(x);
    }

    public void setTwoPlayerGame(boolean b){
        twoPlayerGame = b;
    }

    void setUpFields(){
        player1Name.setText(game.getPlayer1().getName());
        player2Name.setText(game.getPlayer2().getName());
    }

    void updateRolls(int[] rolls){
        // update the rolls here
    }

    void updateScore(){
        // TODO: update lastroll
        player1Score.setText(String.valueOf(game.getPlayer1().getScore()));
        player2Score.setText(String.valueOf(game.getPlayer2().getScore()));



    }

    public void rollPlayer1(ActionEvent event){
        if(game.getPlayer1().isTurn()){
            game.TakeTurn(game.getPlayer1());
            player1Roll.setText(game.getRollsAsString());
            updateScore();
        }

    }

    public void rollPlayer2(ActionEvent event){
        if(game.getPlayer2().isTurn()){
            game.TakeTurn(game.getPlayer2());
            player2Roll.setText(game.getRollsAsString());
            updateScore();
        }
    }
}
