package Controllers;


import Application.Game;
import Model.Dice;
import Model.DiceImage;
import Model.Faces;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    Button playButton,undoButton;

    @FXML
    ImageView redLeadImage,blueLeadImage;

    private boolean twoPlayerGame;
    private Player player1;
    private Player player2;
    private Player leadPlayer;

    DiceImage diceImage;
    Game game;

    //TODO: show dialog when game has ended to restart the game
    //TODO: find a way to go back to the previous screen

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
     * @param name  The name to set for player1.
     */

    void setPlayer1Name(String name){
        if(!name.isEmpty()){
            this.player1.setName(name);
        }
    }

    /**
     *
     * @param name   The name to set for player2.
     */

    void setPlayer2Name(String name){
        if(!twoPlayerGame){
            this.player2.setName("Computer");
            return;
        }
        if(name.equals(player1.getName())){
            this.player2.setName(name + "2");
        } else if(!name.isEmpty()){
            this.player2.setName(name);
        }
    }

    /**
     * @param b  set to true if two players.
     */

    void setTwoPlayerGame(boolean b){
        twoPlayerGame = b;
    }

    /**
     *  Sets the player names on the twoPlayerGameScreen.
     */

    void setUpPlayerNames(){
        player1Name.setText(this.player1.getName());
        player2Name.setText(this.player2.getName());
    }

    /**
     * handles all the events involved in the game.
     * @param event  Button Clicks on the 'Roll Button'
     */

    public void playTurn(ActionEvent event){
        game.TakeTurn(player1);
        game.TakeTurn(player2);
        updateFields();
        checkGame();
        setLeadPlayerImage();
    }

    private void checkGame(){
        if(game.checkWin()){
            playButton.setText("Restart");
            playButton.setDisable(true);
            undoButton.setDisable(true);
            if(game.isDraw()){
                System.out.println("Game drawed"); // replace with dialog
            } else {
                System.out.println("Winner: " + game.getWinner().getName()); // replace with dialog.
            }
        }
    }

    public void undoLastMove(ActionEvent event) {
        game.undoLastTurn(player1);
        game.undoLastTurn(player2);
        setLeadPlayerImage();
        updateFields();
    }

    private void updateFields(){
        if(player1.getRollCount() == 0 && player2.getRollCount() == 0){
            // set empty fields
            player1Roll.setText(" ");
            player1Score.setText("0");
            player2Roll.setText(" ");
            player2Score.setText("0");

            // set empty imageViews
            setDefaultImages();
            return;
        }

        // updating player1 fields
        player1Roll.setText(player1.getLastRollAsString() + "  (+" + game.getScoreFromRolls(player1.getLastRoll()) + ")");
        player1Score.setText(String.valueOf(player1.getScore()));
        setPlayer1Images(player1);

        // player2 fields
        player2Roll.setText(player2.getLastRollAsString() + "  (+" + game.getScoreFromRolls(player2.getLastRoll()) + ")");
        player2Score.setText(String.valueOf(player2.getScore()));
        setPlayer2Images(player2);
    }

    private void setDefaultImages(){
        player1Dice1.setImage(null);
        player1Dice2.setImage(null);
        player1Dice3.setImage(null);
        player2Dice1.setImage(null);
        player2Dice2.setImage(null);
        player2Dice3.setImage(null);
        redLeadImage.setImage(null);
        blueLeadImage.setImage(null);
    }


    private void setPlayer2Images(Player player) {
        List<Image> images = diceImage.getImages(player.getColor(),player.getLastRoll());
        player2Dice1.setImage(images.get(0));
        player2Dice2.setImage(images.get(1));
        player2Dice3.setImage(images.get(2));
    }


    private void setPlayer1Images(Player player) {
        List<Image> images = diceImage.getImages(player.getColor(),player.getLastRoll());
        player1Dice1.setImage(images.get(0));
        player1Dice2.setImage(images.get(1));
        player1Dice3.setImage(images.get(2));
    }


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

    private void setLeadPlayerImage(){
        leadPlayer = game.getLeadingPlayer();
        if(leadPlayer == null){
            // no lead so set images to default
            redLeadImage.setImage(null);
            blueLeadImage.setImage(null);
        } else {
            if(leadPlayer.getColor().equals("red")){
                redLeadImage.setImage(new Image("Images/trophy.png"));
                blueLeadImage.setImage(null);
            } else {
                blueLeadImage.setImage(new Image("Images/trophy.png"));
                redLeadImage.setImage(null);
            }
        }
    }


}
