package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {

    @FXML
    Button singleButton;

    @FXML
    Button multiButton;

    /**
     * Loads SinglePlayer Screen to set Player Name and target.
     *
     * @param event
     * @throws IOException If Screens/playerScreen.fxml is not found under Screens.
     */

    public void handleSinglePlayerAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Screens/playerScreen.fxml"));
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);
    }

    /**
     * Loads TwoPlayer Screen to set Player Names and target.
     *
     * @param event
     * @throws IOException If Screens/twoPlayerScreen.fxml is not found under Screens.
     */

    public void handleMultiPlayerAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Screens/twoPlayerScreen.fxml"));
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);
    }

}
