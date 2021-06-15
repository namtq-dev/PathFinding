import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import GUI.Containers.GraphPanel;
import GUI.Containers.GraphScene;
import GUI.Containers.SpeedControlPane;
import GUI.UIControls.AutoRunCheckBox;
import GUI.UIControls.Buttons;
import GUI.UIControls.CheckBoxs;
import GUI.UIControls.ContinueButton;
import GUI.UIControls.PauseButton;
import GUI.UIControls.ResetButton;
import GUI.UIControls.StopButton;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphPanel graphview = new GraphPanel(1800, 900);
        Group root = new Group(new GraphScene(graphview));
        Scene mainScene = new Scene(root);

        ResetButton resetButton = new ResetButton(graphview, 5, 5);
        PauseButton pauseButton = new PauseButton(75, 5);
        ContinueButton continueButton = new ContinueButton(145, 5);
        StopButton stopButton = new StopButton(215, 5);
        AutoRunCheckBox autoRunCheckBox = new AutoRunCheckBox(15, 20);
        root.getChildren().addAll(resetButton, pauseButton, continueButton, stopButton, autoRunCheckBox);
        Buttons.bindButtons(resetButton, pauseButton, continueButton, stopButton);
        CheckBoxs.bindCheckBoxs(autoRunCheckBox);

        SpeedControlPane speedControlPane = new SpeedControlPane(20, 75);
        root.getChildren().addAll(speedControlPane, speedControlPane.getAttachedLabel());

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}