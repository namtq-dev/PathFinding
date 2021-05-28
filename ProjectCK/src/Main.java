import Containers.GraphPanel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphPanel graphview = new GraphPanel(1800, 900);
        Group root = new Group(graphview);
        Scene mainScene = new Scene(root);

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}