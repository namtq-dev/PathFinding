import java.util.ArrayList;
import java.util.List;

import Animation.Animate;
import Containers.GraphPanel;
import Containers.GraphScene;
import GraphFX.EdgeLine;
import GraphFX.VertexFX;
import UIControls.ContinueButton;
import UIControls.PauseButton;
import UIControls.ResetButton;
import UIControls.StopButton;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestAnimation extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphPanel graphview = new GraphPanel(1800, 900);
        Group root = new Group(new GraphScene(graphview));
        Scene mainScene = new Scene(root);

        ResetButton resetButton = new ResetButton(graphview, 5, 5);
        PauseButton pauseButton = new PauseButton(75, 5);
        ContinueButton continueButton = new ContinueButton(145, 5);
        StopButton stopButton = new StopButton(215, 5);
        root.getChildren().addAll(resetButton, pauseButton, continueButton, stopButton);

        List<EdgeLine> steps = new ArrayList<EdgeLine>();
        List<VertexFX> result = new ArrayList<VertexFX>();

        VertexFX A = new VertexFX(200, 200, "A");
        VertexFX B = new VertexFX(400, 200, "B");
        VertexFX C = new VertexFX(400, 400, "C");
        VertexFX D = new VertexFX(200, 400, "D");
        VertexFX E = new VertexFX(600, 200, "E");
        VertexFX F = new VertexFX(600, 400, "F");
        VertexFX G = new VertexFX(800, 200, "G");
        VertexFX H = new VertexFX(800, 400, "H");
        
        graphview.addVertex(A);
        graphview.addVertex(B);
        graphview.addVertex(C);
        graphview.addVertex(D);
        graphview.addVertex(E);
        graphview.addVertex(F);
        graphview.addVertex(G);
        graphview.addVertex(H);

        EdgeLine AB = new EdgeLine(A, B, 12);
        EdgeLine BC = new EdgeLine(B, C, 21);
        EdgeLine CD = new EdgeLine(C, D, 23);
        EdgeLine DA = new EdgeLine(D, A, 34);
        EdgeLine BF = new EdgeLine(B, F, 16);
        EdgeLine BE = new EdgeLine(B, E, 12);
        EdgeLine BH = new EdgeLine(B, H, 34);
        EdgeLine HG = new EdgeLine(H, G, 22);
        EdgeLine GC = new EdgeLine(G, C, 14);
        EdgeLine GF = new EdgeLine(G, F, 27);
        EdgeLine GE = new EdgeLine(G, E, 34);

        graphview.addEdge(AB);
        graphview.addEdge(BC);
        graphview.addEdge(CD);
        graphview.addEdge(DA);
        graphview.addEdge(BF);
        graphview.addEdge(BE);
        graphview.addEdge(BH);
        graphview.addEdge(HG);
        graphview.addEdge(GC);
        graphview.addEdge(GF);
        graphview.addEdge(GE);

        steps.add(AB);
        steps.add(BC);
        steps.add(CD);
        steps.add(DA);
        steps.add(BF);
        steps.add(BE);
        steps.add(BH);
        steps.add(HG);
        steps.add(GC);
        steps.add(GF);
        steps.add(GE);

        result.add(A);
        result.add(B);
        result.add(C);
        result.add(D);

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        SequentialTransition animation = Animate.makeAnimation(steps, result);
        Animate.bindControlButtons(animation, graphview, pauseButton, continueButton, stopButton);
        Animate.playAnimation(graphview, resetButton, pauseButton, continueButton, stopButton, animation, result);
    }

    public static void main(String[] args) {
        launch();
    }
}