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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.List;
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
    Button playButton;

    @FXML
    Label targetLabel;

    @FXML
    Text leadingPlayerName;

    private boolean twoPlayerGame = true;

    DiceImage diceImage;
    Game game;

    String name1;
    String name2;


    int score = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        diceImage = new DiceImage();
        Player player1 = new Player(player1Name.getText());
        player1.setTurn(true);
        player1.setColor("red");
        player1.setName(player1Name.getText());
        Player player2 = new Player(player2Name.getText());
        player2.setColor("blue");
        game = new Game(new Dice(),player1,player2);
//        targetLabel.setText(game.getTarget() + "");
        setUpFields();
    }

    void setGame(Game game){
        this.game = game;
    }


    void setPlayer1Name(String x){
        if(x.isEmpty()){
            this.player1Name.setText("Anonymous");
            return;
        }
        this.player1Name.setText(x);
    }

    void setPlayer2Name(String x){
        if(!twoPlayerGame){
            this.player2Name.setText("Computer");
            return;
        } else if(x.isEmpty()){
            player2Name.setText("Anonymous");
            return;
        }
        this.player2Name.setText(x);
    }

    void setTwoPlayerGame(boolean b){
        twoPlayerGame = b;
    }

    private void setUpFields(){
        player1Name.setText(game.getPlayer1().getName());
        player2Name.setText(game.getPlayer2().getName());
    }


    private void updateScore(){
        player1Score.setText(String.valueOf(game.getPlayer1().getScore()));
        player2Score.setText(String.valueOf(game.getPlayer2().getScore()));
    }

    public void playGame(ActionEvent event){

        // when clicked
        // Roll game for both players
        game.TakeTurn(game.getPlayer1());

        int[] player1Rolls = game.getLastRolls();
        // loadimage for player1
        List<Image> player1Images = diceImage.getImages(game.getPlayer1().getColor(),player1Rolls);

        game.TakeTurn(game.getPlayer2());
        int[] player2Rolls = game.getLastRolls();
        List<Image> player2Images = diceImage.getImages(game.getPlayer2().getColor(),player2Rolls);

        setPlayer1Images(player1Images);
        setPlayer2Images(player2Images);
        player1Roll.setText(game.getRollsAsString(player1Rolls));
        player2Roll.setText(game.getRollsAsString(player2Rolls));
        updateScore();
    }


    private void setPlayer2Images(List<Image> player2Images) {
        player2Dice1.setImage(player2Images.get(0));
        player2Dice2.setImage(player2Images.get(1));
        player2Dice3.setImage(player2Images.get(2));
    }

    private void setPlayer1Images(List<Image> player1Images) {
        player1Dice1.setImage(player1Images.get(0));
        player1Dice2.setImage(player1Images.get(1));
        player1Dice3.setImage(player1Images.get(2));
    }

}
