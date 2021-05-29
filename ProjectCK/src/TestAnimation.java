import java.util.ArrayList;
import java.util.List;

import Animation.Animate;
import Containers.GraphPanel;
import GraphFX.EdgeLine;
import GraphFX.VertexFX;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestAnimation extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphPanel graphview = new GraphPanel(1800, 900);
        Group root = new Group(graphview);
        Scene mainScene = new Scene(root);
        List<EdgeLine> steps = new ArrayList<EdgeLine>();
        List<VertexFX> result = new ArrayList<VertexFX>();

        VertexFX A = new VertexFX(200, 200, "A");
        VertexFX B = new VertexFX(400, 200, "B");
        VertexFX C = new VertexFX(400, 400, "C");
        VertexFX D = new VertexFX(200, 400, "D");
        graphview.addVertex(A);
        graphview.addVertex(B);
        graphview.addVertex(C);
        graphview.addVertex(D);

        EdgeLine AB = new EdgeLine(A, B, 12);
        EdgeLine BC = new EdgeLine(B, C, 21);
        EdgeLine CD = new EdgeLine(C, D, 23);
        EdgeLine DA = new EdgeLine(D, A, 34);

        graphview.addEdge(AB);
        graphview.addEdge(BC);
        graphview.addEdge(CD);
        graphview.addEdge(DA);

        steps.add(AB);
        steps.add(BC);
        steps.add(CD);
        steps.add(DA);

        result.add(A);
        result.add(B);
        result.add(C);
        result.add(D);

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        SequentialTransition animation = Animate.makeAnimation(steps, result);
        Animate.playAnimation(graphview, animation, result);
    }

    public static void main(String[] args) {
        launch();
    }
}