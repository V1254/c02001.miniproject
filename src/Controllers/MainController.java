package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MainController {

    // TODO: finish controller

    @FXML
    Button singleButton;

    @FXML
    Button multiButton;


    public void handleSinglePlayerAction(ActionEvent event){
        System.out.println("Single Clicked");
    }

    public void handleMultiPlayerAction(ActionEvent event){
        System.out.println("Multi Clicked");
    }

}
