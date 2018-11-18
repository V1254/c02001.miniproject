package Controllers;

import Application.Game;
import Model.Dice;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

public class TwoPlayerController {

    @FXML
    Button backButton;

    @FXML
    Button playButtons;

    @FXML
    TextField player1NameField,player2NameField;

    @FXML
    TextField targetScore;

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Screens/main.fxml"));
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);
    }

    public void startGame(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Screens/twoPlayerGameScreen.fxml"));
        Parent root = (Parent) loader.load();
        GameController gm = loader.getController();
        gm.setTwoPlayerGame(true);
        gm.setPlayer1Name(player1NameField.getText());
        gm.setPlayer2Name(player2NameField.getText());
        gm.setTarget(targetScore.getText());
        gm.setUpPlayerNames();
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);
    }


}
