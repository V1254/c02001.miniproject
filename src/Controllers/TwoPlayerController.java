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

public class TwoPlayerController {

    @FXML
    Button backButton;

    @FXML
    Button playButtons;

    @FXML
    TextField player1NameField, player2NameField;

    @FXML
    TextField targetScore;

    /**
     * Returns to the main screen
     *
     * @param event
     * @throws IOException If main.fxml is not found under Screens Package.
     */

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Screens/main.fxml"));
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);
    }

    public void startGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Screens/DualPlayerGameScreen.fxml"));
        Parent root = (Parent) loader.load();
        DualPlayerGameController controller = loader.getController();
        controller.setPlayer1Name(player1NameField.getText());
        controller.setPlayer2Name(player2NameField.getText());
        controller.setTarget(targetScore.getText());
        controller.setUpPlayerNames();
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);
    }


}
