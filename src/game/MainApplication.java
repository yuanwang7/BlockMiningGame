package game;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application class which starts the WolrdMap JavaFx appliction
 */
public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This is the method that set the window of the application
     *
     * @param primaryStage  the stage of the JavaFX application
     */
    @Override
    public void start(Stage primaryStage) {
        //set the window title to be Block World
        primaryStage.setTitle("Block World");

        //create a view and every element is inside the view
        View view = new View(primaryStage);

        Controller controller = new Controller(view);

        primaryStage.setScene(view.getScene());

        //show the window
        primaryStage.show();

    }

}
