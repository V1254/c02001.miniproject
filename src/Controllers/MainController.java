package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import java.io.IOException;


public class MainController {

    // TODO: finish controller

    @FXML
    Button singleButton;

    @FXML
    Button multiButton;


    public void handleSinglePlayerAction(ActionEvent event) throws  IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/Screens/playerScreen.fxml"));
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);

        System.out.println("Single Clicked");
    }

    public void handleMultiPlayerAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Screens/twoPlayerScreen.fxml"));
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        sourceStage.setScene(scene);
        System.out.println("Multi Clicked");
    }

}
