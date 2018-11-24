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
import java.util.Optional;
import java.util.ResourceBundle;

public class SinglePlayerGameController implements Initializable {


    @FXML
    private Text playerName, playerRoll, playerScore;

    // fields for the Computer
    @FXML
    private Text computerName, computerRoll, computerScore;

    @FXML
    private ImageView playerDice1, playerDice2, playerDice3;

    @FXML
    private ImageView computerDice1, computerDice2, computerDice3;

    @FXML
    private Button undoButton, resetButton, rollButton;

    @FXML
    private ImageView playerLeadImage, computerLeadImage;

    @FXML
    private Button backButton;


    private Player player1;
    private Player computer;
    private Player leadPlayer;
    private DiceImage diceImage;
    private Game game;


    public SinglePlayerGameController() {
        player1 = new Player("Player 1");
        computer = new Player("Computer");
        diceImage = new DiceImage();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
        game = new Game(new Dice(), player1, computer);
        player1.setColor("blue");
        computer.setColor("red");
        undoButton.setDisable(true);
        resetButton.setDisable(true);
    }

    void setPlayer1Name(String name) {
        if (!name.isEmpty()) {
            this.player1.setName(name);
        }
    }

    void setUpPlayerNames() {
        playerName.setText(this.player1.getName());
        computerName.setText(this.computer.getName());
    }

    public void onRollClick() {
        game.TakeTurn(player1);
        game.TakeTurn(computer);
        undoButton.setDisable(false);
        resetButton.setDisable(false);
        updateFields("player");
        updateFields("computer");
        setLeadPlayerImage();
        checkGame();
    }

    private void checkGame() {
        if (game.checkWin()) {
            rollButton.setDisable(true);
            undoButton.setDisable(true);
            if (game.isDraw()) {
                showDrawDialog();
                return;
            }
            if(game.getWinner().getName().equals("Computer")){
                showLoserDialog();
                return;
            }
            showWinnerDialog(player1);
        }
    }

    public void onUndo() {
        game.undoLastTurn(player1);
        game.undoLastTurn(computer);
        if (player1.getRollCount() == 0 && computer.getRollCount() == 0) {
            setDefault("player");
            setDefault("computer");
            undoButton.setDisable(true);
            resetButton.setDisable(true);
        } else {
            updateFields("player");
            updateFields("computer");
        }
    }

    public void onReset() {
        game.resetScores();
        rollButton.setDisable(false);
        undoButton.setDisable(true);
        resetButton.setDisable(true);
        setDefault("player");
        setDefault("computer");
    }

    void setDefault(String playerName) {
        if (playerName.equals("player")) {
            playerLeadImage.setImage(null);
            playerDice1.setImage(null);
            playerDice2.setImage(null);
            playerDice3.setImage(null);
            playerRoll.setText(null);
            playerScore.setText(null);
            return;
        }
        computerLeadImage.setImage(null);
        computerDice1.setImage(null);
        computerDice2.setImage(null);
        computerDice3.setImage(null);
        computerRoll.setText(null);
        computerScore.setText(null);
    }

    private void updateFields(String playerName) {
        if (playerName.equals("player")) {
            playerRoll.setText(player1.getLastRollAsString() + "  (+" + game.getScoreFromRolls(player1.getLastRoll()) + ")");
            playerScore.setText(String.valueOf(player1.getScore()));
            setPlayerImages("player");
        } else {
            computerRoll.setText(computer.getLastRollAsString() + "  (+" + game.getScoreFromRolls(computer.getLastRoll()) + ")");
            computerScore.setText(String.valueOf(computer.getScore()));
            setPlayerImages("computer");
        }
    }

    void setPlayerImages(String playerName) {
        if (playerName.equals("player")) {
            List<Image> images = diceImage.getImages(player1.getColor(), player1.getLastRoll()); // dice face images corresponding to the player rolls.
            playerDice1.setImage(images.get(0));
            playerDice2.setImage(images.get(1));
            playerDice3.setImage(images.get(2));
        } else {
            List<Image> images = diceImage.getImages(computer.getColor(), computer.getLastRoll());
            computerDice1.setImage(images.get(0));
            computerDice2.setImage(images.get(1));
            computerDice3.setImage(images.get(2));
        }
    }

    void setTarget(String target) {
        try {
            int value = Integer.valueOf(target);
            game.setTarget(value);
        } catch (NumberFormatException e) {
            // caused by inputting strings
        } catch (IllegalArgumentException e) {
            // caused by number  < 6.
            game.setTarget(10);
            System.out.println(e.getMessage());
        }
    }

    private void setLeadPlayerImage() {
        leadPlayer = game.getLeadingPlayer();
        if(leadPlayer == null){
            // no lead so set images to default
            playerLeadImage.setImage(null);
            computerLeadImage.setImage(null);
            return;
        }
        if (leadPlayer.getName().equals("Computer")) {
            computerLeadImage.setImage(new Image("Images/trophy.png"));
            playerLeadImage.setImage(null);
            return;
        }
        playerLeadImage.setImage(new Image("Images/trophy.png"));
        computerLeadImage.setImage(null);
    }

    void showDrawDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Game Has Ended in a Draw!! \n\n\n Thanks For Playing " + player1.getName(), new ButtonType("Close", ButtonBar.ButtonData.NO), new ButtonType("Play Again", ButtonBar.ButtonData.YES));
        alert.setGraphic(new ImageView(new Image(getClass().getResource("../Images/draw.png").toExternalForm())));
        alert.setHeaderText(null);
        alert.setTitle("Draw!!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            switch (result.get().getButtonData()) {
                case YES:
                    onReset();
            }
        }
    }

    void showWinnerDialog(Player player) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Congratulations " + player.getName() + ", You are Victorious!!", new ButtonType("Close", ButtonBar.ButtonData.NO), new ButtonType("Play Again", ButtonBar.ButtonData.YES));
        alert.setGraphic(new ImageView(new Image(getClass().getResource("../Images/winner.png").toExternalForm())));
        alert.setHeaderText(null);
        alert.setTitle("Winner!!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            switch (result.get().getButtonData()) {
                case YES:
                    onReset();
            }
        }
    }

    void showLoserDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"You Have Been Defeated ,Computer Wins!!",new ButtonType("Close", ButtonBar.ButtonData.NO),new ButtonType("Try Again?", ButtonBar.ButtonData.YES));
        alert.setGraphic(new ImageView(new Image(getClass().getResource("../Images/bronze.png").toExternalForm())));
        alert.setHeaderText(null);
        alert.setTitle("You Lose!");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()){
            switch (result.get().getButtonData()){
                case YES:
                    onReset();
            }
        }
    }

    @FXML
    public void onBack(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Screens/playerScreen.fxml"));
            Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            sourceStage.setScene(scene);
        } catch (IOException e){
            System.out.println("Error loading Previous Screen" + e.getMessage());
        }

    }


}


