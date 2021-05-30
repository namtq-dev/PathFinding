import Containers.GraphPanel;
import Containers.GraphScene;
import UIControls.ResetButton;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphPanel graphview = new GraphPanel(1800, 900);
        Group root = new Group(new GraphScene(graphview));
        Scene mainScene = new Scene(root);

        root.getChildren().add(new ResetButton(graphview, 5, 5));

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}