package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main Entry for the Application.
 */

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Screens/main.fxml"));
        Scene scene = new Scene(root,780,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dice Game");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/icon.jpg")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
