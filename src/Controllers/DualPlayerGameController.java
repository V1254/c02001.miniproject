package Controllers;

import Application.Game;
import Model.Dice;
import Model.DiceImage;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DualPlayerGameController implements Initializable {

    @FXML
    private Text player1Name,player1Roll,player1Score;

    @FXML
    private ImageView player1LeadImage;

    @FXML
    private Text player2Name,player2Roll,player2Score;

    @FXML
    private ImageView player2LeadImage;

    @FXML
    private ImageView player1Dice1,player1Dice2,player1Dice3;

    @FXML
    private ImageView player2Dice1,player2Dice2,player2Dice3;

    @FXML
    private Button exitButton,resetButton,player1RollButton,player2RollButton;

    private Player player1;
    private Player player2;
    private Player leadPlayer;
    private DiceImage diceImage;
    private Game game;


    public DualPlayerGameController(){
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        diceImage = new DiceImage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game(new Dice(),player1,player2);
        player1.setColor("blue");
        player2.setColor("red");
        player2RollButton.setDisable(true);
        resetButton.setDisable(true);
    }

    void setPlayer1Name(String name){
        if(!name.isEmpty()){
            this.player1.setName(name);
        }
    }

    void setPlayer2Name(String name){
        if(!name.isEmpty()){
            if(name.equals(player1.getName())){
                player2.setName(name + " 2");
                return;
            }
            player2.setName(name);
        }
    }

    void setUpPlayerNames(){
        player1Name.setText(player1.getName());
        player2Name.setText(player2.getName());
    }

    void setTarget(String number){
        if(!number.isEmpty()){
            try{
                int value = Integer.valueOf(number);
                game.setTarget(value);
            } catch (NumberFormatException e){

            } catch (IllegalArgumentException e){
                game.setTarget(20);
            }
        }
    }

    @FXML
    public void onPlayer1Roll(ActionEvent event) {
            game.TakeTurn(player1);
            player1RollButton.setDisable(true);
            player2RollButton.setDisable(false);
            resetButton.setDisable(false);
            updateField(player1);
            updateImages(player1);
            setLeadPlayerImage();
    }

    @FXML
    public void onPlayer2Roll(ActionEvent event) {
        game.TakeTurn(player2);
        player2RollButton.setDisable(true);
        player1RollButton.setDisable(false);
        updateField(player2);
        updateImages(player2);
        setLeadPlayerImage();
        checkGame();
    }

    // set up a warning dialog incase they misclick reset button.
    @FXML
    public void onReset() {
        Alert alert = new Alert(Alert.AlertType.WARNING,"Are you sure, you wan't to Restart The Game?",new ButtonType("Confirm", ButtonBar.ButtonData.YES),new ButtonType("Cancel", ButtonBar.ButtonData.NO));
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        alert.showAndWait();
        if(alert.getResult().getButtonData() == ButtonBar.ButtonData.YES){
            game.resetScores();
            player1RollButton.setDisable(false);
            setDefaultFields();
            resetButton.setDisable(true);
        }
    }

    @FXML
    public void onBack(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Screens/twoPlayerScreen.fxml"));
            Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            sourceStage.setScene(scene);
        } catch (IOException e){
            System.out.println("Error loading Previous Screen" + e.getMessage());
        }

    }

    private void updateField(Player player){
        if(player.getColor().equals("blue")){
            player1Roll.setText(player1.getLastRollAsString() + "  (+" + game.getScoreFromRolls(player1.getLastRoll()) + ")");
            player1Score.setText(String.valueOf(player1.getScore()));
        } else {
            player2Roll.setText(player2.getLastRollAsString() + "  (+" + game.getScoreFromRolls(player2.getLastRoll()) + ")");
            player2Score.setText(String.valueOf(player2.getScore()));
        }
    }

    private void updateImages(Player player){
        List<Image> imageList;
        if(player.getColor().equals("blue")){
            imageList = diceImage.getImages("blue",player.getLastRoll());
            player1Dice1.setImage(imageList.get(0));
            player1Dice2.setImage(imageList.get(1));
            player1Dice3.setImage(imageList.get(2));
        } else {
            imageList = diceImage.getImages("red",player.getLastRoll());
            player2Dice1.setImage(imageList.get(0));
            player2Dice2.setImage(imageList.get(1));
            player2Dice3.setImage(imageList.get(2));
        }
    }

    private void setLeadPlayerImage(){
        leadPlayer = game.getLeadingPlayer();
        if(leadPlayer == null){
            player1LeadImage.setImage(null);
            player2LeadImage.setImage(null);
            return;
        }

        if(leadPlayer.getColor().equals("blue")){
            player1LeadImage.setImage(new Image("Images/trophy.png"));
            player2LeadImage.setImage(null);
        } else {
            player2LeadImage.setImage(new Image("Images/trophy.png"));
            player1LeadImage.setImage(null);
        }
    }

    private void checkGame(){
        if(game.checkWin()){
            player1RollButton.setDisable(true);
            player2RollButton.setDisable(true);
            if(game.isDraw()){
                showDrawDialog();
            } else {
                showWinnerDialog(game.getWinner());
            }
        }
    }

    private void showDrawDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Game Has Ended in a Draw!! \n\n\n Thanks For Playing " + player1.getName() + " & " + player2.getName(), new ButtonType("Close", ButtonBar.ButtonData.NO), new ButtonType("Play Again", ButtonBar.ButtonData.YES));
        alert.setGraphic(new ImageView(new Image(getClass().getResource("../Images/draw.png").toExternalForm())));
        alert.setHeaderText(null);
        alert.setTitle("Draw!!");
        alert.showAndWait();
        if (alert.getResult().getButtonData() == ButtonBar.ButtonData.YES) {
            resetWithoutDialog();
        }
    }

    private void showWinnerDialog(Player player){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Congratulation " + player.getName() + ", You are the Victor!", new ButtonType("Close", ButtonBar.ButtonData.NO), new ButtonType("Play Again", ButtonBar.ButtonData.YES));
        alert.setGraphic(new ImageView(new Image(getClass().getResource("../Images/winner.png").toExternalForm())));
        alert.setHeaderText(null);
        alert.setTitle("Winner!!");
        alert.showAndWait();
        if (alert.getResult().getButtonData() == ButtonBar.ButtonData.YES) {
            resetWithoutDialog();
        }
    }

    private void setDefaultFields(){
        player1Roll.setText(null);
        player1Score.setText(null);
        player1Dice1.setImage(null);
        player1Dice2.setImage(null);
        player1Dice3.setImage(null);
        player1LeadImage.setImage(null);

        player2Roll.setText(null);
        player2Score.setText(null);
        player2Dice1.setImage(null);
        player2Dice2.setImage(null);
        player2Dice3.setImage(null);
        player2LeadImage.setImage(null);
    }

    private void resetWithoutDialog(){
        game.resetScores();
        player1RollButton.setDisable(false);
        setDefaultFields();
        resetButton.setDisable(true);
    }

}
