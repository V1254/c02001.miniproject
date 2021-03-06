package Controllers;

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


public class SingleScreenController {

    @FXML
    Button backButton;

    @FXML
    TextField nameField, targetField;

    @FXML
    Button playButton;

    /**
     * Loads and shows the main screen.
     *
     * @param event
     * @throws IOException If main.fxml is not found under Screens.
     */

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Screens/main.fxml"));
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);
    }

    /**
     * Loads Single Player Game Screen and sets the field with the values provided.
     *
     * @param event
     * @throws IOException If SinglePlayerGame.fxml is not found under Screens.
     */

    public void startGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Screens/SinglePlayerGameScreen.fxml"));
        Parent root = loader.load();
        SinglePlayerGameController tc = loader.getController();
        tc.setPlayer1Name(nameField.getText());
        tc.setUpPlayerNames();
        tc.setTarget(targetField.getText());
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);
    }
}
