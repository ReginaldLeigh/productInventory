package software1.software1;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** Creates an app that simulates an Inventory tracking system for bicycle equipment.*/
public class MainMenuApplication extends Application {

    /** Sets and loads the Main Menu for the application. */
    @Override
    public void start(Stage stage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** This is the main method, and is the first method called when the java program is ran.
     * Creates test data upon application launch.
     *
     * FUTURE ENHANCEMENT -- I would implement a feature that would allow the user to sort parts and products by different criteria (ie. value, inventory, etc.).
     */
    public static void main(String[] args) {
        Inventory.setTestData();

        launch(args);
    }
}