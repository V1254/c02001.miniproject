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

public class SinglePlayerGameController implements Initializable {

    @FXML
    private Text playerName, playerRoll, playerScore;

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
    private boolean dialogFlag; // true if resetting through one of the created dialogs.


    public SinglePlayerGameController() {
        player1 = new Player("Player 1");
        computer = new Player("Computer");
        game = new Game(new Dice(), player1, computer);
        player1.setColor("blue");
        computer.setColor("red");
        diceImage = new DiceImage();
    }

    /**
     * Called after constructor, and FXML fields are populated.
     *
     * @param location
     * @param resources
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        undoButton.setDisable(true);
        resetButton.setDisable(true);
    }

    /**
     * Sets the player Name to the given param if it is not empty.
     *
     * @param name Name to set for the Current Player.
     */

    void setPlayer1Name(String name) {
        if (!name.isEmpty()) {
            this.player1.setName(name);
        }
    }

    /**
     * Sets the playerName and computerName fields in the SinglePlayerGameScreen.fxml
     */

    void setUpPlayerNames() {
        playerName.setText(this.player1.getName());
        computerName.setText(this.computer.getName());
    }

    /**
     * Plays the turn for the player and the computer.
     */

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

    /**
     * Checks on the status of the game, and calls dialog methods depending on whether a Win,Loss, or a draw.
     */

    private void checkGame() {
        if (game.checkWin()) {
            rollButton.setDisable(true);
            undoButton.setDisable(true);
            if (game.isDraw()) {
                showDrawDialog();
                return;
            }
            if (game.getWinner().getName().equals("Computer")) {
                showLoserDialog();
                return;
            }
            showWinnerDialog(player1);
        }
    }

    /**
     * Removes last players rolls.
     */

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

    /**
     * Resets the game and shows a dialog if reset button is clicked.
     */

    public void onReset() {

        if (dialogFlag) {
            // enter here if resetting through the dialog that pops up at the end of the game.
            resetGame();
        } else {
            // enter here if resetButton is clicked through the fxml.
            Alert alert = createWarningAlert();
            alert.showAndWait();
            if (alert.getResult().getButtonData() == ButtonBar.ButtonData.YES) {
                resetGame();
            }
        }
    }

    /**
     * Creates a Warning alert.
     *
     * @return alert.
     */

    private Alert createWarningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure, you wan't to Restart The Game?", new ButtonType("Confirm", ButtonBar.ButtonData.YES), new ButtonType("Cancel", ButtonBar.ButtonData.NO));
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        return alert;
    }

    /**
     * Resets game scores and rolls.
     */

    private void resetGame() {
        game.resetScores();
        this.dialogFlag = false;
        rollButton.setDisable(false);
        undoButton.setDisable(true);
        resetButton.setDisable(true);
        setDefault("player");
        setDefault("computer");
    }

    /**
     * Loads and shows the previous Screen (playerScreen.fxml).
     *
     * @param event
     */

    public void onBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Screens/playerScreen.fxml"));
            Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            sourceStage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Error loading Previous Screen" + e.getMessage());
        }
    }

    /**
     * Sets all fields related to the given playerName to null.
     *
     * @param playerName
     */

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

    /**
     * Updates playerFields based on the given playerName.
     *
     * @param playerName
     */

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

    /**
     * Sets dice images, based on player Rolls and color.
     *
     * @param playerName Who's dice rolls to Display.
     */

    void setPlayerImages(String playerName) {
        if (playerName.equals("player")) {
            List<Image> images = diceImage.getImageList(player1.getColor(), player1.getLastRoll()); // dice face images corresponding to the player rolls.
            playerDice1.setImage(images.get(0));
            playerDice2.setImage(images.get(1));
            playerDice3.setImage(images.get(2));
        } else {
            List<Image> images = diceImage.getImageList(computer.getColor(), computer.getLastRoll());
            computerDice1.setImage(images.get(0));
            computerDice2.setImage(images.get(1));
            computerDice3.setImage(images.get(2));
        }
    }

    /**
     * Sets the current game target to the given target.
     *
     * @param target The target to set for the game.
     */

    void setTarget(String target) {
        try {
            int value = Integer.valueOf(target);
            game.setTarget(value);
        } catch (NumberFormatException e) {
            // caused by inputting strings
        } catch (IllegalArgumentException e) {
            // caused by number  < 6.
            game.setTarget(10);
        }
    }

    /**
     * Sets a Trophy image beside the highest score or null if they have the same score.
     */

    private void setLeadPlayerImage() {
        leadPlayer = game.getLeadingPlayer();
        if (leadPlayer == null) {
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

    /**
     * Displays a confirmation alert, showing both player names and an option to reset.
     */

    private void showDrawDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Game Has Ended in a Draw!! \n\n\n Thanks For Playing " + player1.getName(), new ButtonType("Close", ButtonBar.ButtonData.NO), new ButtonType("Play Again", ButtonBar.ButtonData.YES));
        alert.setGraphic(new ImageView(new Image(getClass().getResource("../Images/draw.png").toExternalForm())));
        alert.setHeaderText(null);
        alert.setTitle("Draw!");
        alert.showAndWait();
        if (alert.getResult().getButtonData() == ButtonBar.ButtonData.YES) {
            this.dialogFlag = true;
            onReset();
        }
    }

    /**
     * Displays a confirmation alert, showing the given player's name and an option to reset.
     *
     * @param player The winning player.
     */

    private void showWinnerDialog(Player player) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Congratulations " + player.getName() + ", You are Victorious!!", new ButtonType("Close", ButtonBar.ButtonData.NO), new ButtonType("Play Again", ButtonBar.ButtonData.YES));
        alert.setGraphic(new ImageView(new Image(getClass().getResource("../Images/winner.png").toExternalForm())));
        alert.setHeaderText(null);
        alert.setTitle(player.getName() + " Wins!!");
        alert.showAndWait();
        if (alert.getResult().getButtonData() == ButtonBar.ButtonData.YES) {
            this.dialogFlag = true;
            onReset();
        }
    }

    /**
     * Displays a confirmations alert, with a defeat text.
     */

    private void showLoserDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You Have Been Defeated " + player1.getName() + ",Computer Wins!!", new ButtonType("Close", ButtonBar.ButtonData.NO), new ButtonType("Try Again?", ButtonBar.ButtonData.YES));
        alert.setGraphic(new ImageView(new Image(getClass().getResource("../Images/bronze.png").toExternalForm())));
        alert.setHeaderText(null);
        alert.setTitle("Computer Wins");
        alert.showAndWait();
        if (alert.getResult().getButtonData() == ButtonBar.ButtonData.YES) {
            this.dialogFlag = true;
            onReset();
        }
    }
}


