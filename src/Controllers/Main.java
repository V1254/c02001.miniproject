package Controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    Scene main;
    Scene playerScreen;
    Scene twoPlayerScreen;


    @Override
    public void start(Stage primaryStage) throws Exception {

//        loadScenes();

        Parent root = FXMLLoader.load(getClass().getResource("/Screens/main.fxml"));
        Scene scene = new Scene(root,600,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dice Game");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/icon.jpg")));
        primaryStage.show();
    }

//    private void loadScenes() throws Exception {
//        main = FXMLLoader.load(getClass().getResource("/Screens/main.fxml"));
//        playerScreen = FXMLLoader.load(getClass().getResource("/Screens/playerScreen.fxml"));
//        twoPlayerScreen = FXMLLoader.load(getClass().getResource("/Screens/twoPlayerScreen.fxml"));
//    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
