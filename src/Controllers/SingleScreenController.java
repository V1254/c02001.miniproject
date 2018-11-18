package Controllers;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
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
    TextField nameField;

    @FXML
    Button playButton;

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
        gm.setTwoPlayerGame(false);
        gm.setPlayer1Name(nameField.getText());
        gm.setPlayer2Name("");
        gm.setUpPlayerNames();
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);
    }
}
