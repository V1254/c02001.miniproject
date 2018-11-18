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

    private boolean twoPlayerGame;
    private Player player1;
    private Player player2;

    DiceImage diceImage;
    Game game;

    public GameController(){
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        twoPlayerGame = true;
        diceImage = new DiceImage();

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game(new Dice(),player1,player2);
        player1.setTurn(true);
        player1.setColor("blue");
        player2.setColor("red");
    }
    /**
     *
     * @param name
     */

    void setPlayer1Name(String name){
        if(name.isEmpty()){
            return;
        }
        this.player1.setName(name);
    }

    /**
     *
     * @param name
     */

    void setPlayer2Name(String name){
        if(!twoPlayerGame){
            this.player2.setName("Computer");
            return;
        } else if(name.isEmpty()) return;
        this.player2.setName(name);
    }

    void setTwoPlayerGame(boolean b){
        twoPlayerGame = b;
    }

    void setUpPlayerNames(){
        player1Name.setText(this.player1.getName());
        player2Name.setText(this.player2.getName());
    }

    /**
     *
     */


    private void updateScore(){
        player1Score.setText(String.valueOf(game.getPlayer1().getScore()));
        player2Score.setText(String.valueOf(game.getPlayer2().getScore()));
    }

    /**
     *
     * @param event
     */

    public void playGame(ActionEvent event){

        System.out.println(player1.getName());
        System.out.println(player2.getName());
        System.out.println(game.getTarget());
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

    /**
     *
     * @param player2Images
     */

    private void setPlayer2Images(List<Image> player2Images) {
        player2Dice1.setImage(player2Images.get(0));
        player2Dice2.setImage(player2Images.get(1));
        player2Dice3.setImage(player2Images.get(2));
    }

    /**
     *
     * @param player1Images
     */

    private void setPlayer1Images(List<Image> player1Images) {
        player1Dice1.setImage(player1Images.get(0));
        player1Dice2.setImage(player1Images.get(1));
        player1Dice3.setImage(player1Images.get(2));
    }

    /**
     *
     * @param target
     */

    void setTarget(String target){
        try{
            int value = Integer.valueOf(target);
            if(value >= 0){
                this.game.setTarget(value);
            }
        } catch (NumberFormatException e){
            System.out.println("Illegal Number " + e.getMessage());
        } catch (IllegalArgumentException e){
            // enter here when value <6 ,
            game.setTarget(10);
            System.out.println(e.getMessage());
        }
    }

}
