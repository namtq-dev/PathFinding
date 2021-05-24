
import GraphFX.EdgeLine;
import GraphFX.LabelNode;
import GraphFX.VertexFX;
import containers.GraphPanel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphPanel graphview = new GraphPanel();
        VertexFX tmpVertex1, tmpVertex2;
        EdgeLine<VertexFX, LabelNode> edge;

        tmpVertex1 = new VertexFX(100, 100, 20, true, "A");
        graphview.addVertex(tmpVertex1);

        tmpVertex2 = new VertexFX(200, 100, 20, true, "B");
        graphview.addVertex(tmpVertex2);
        edge = new EdgeLine<>(tmpVertex1, tmpVertex2, 10);
        graphview.addEdge(edge);

        tmpVertex2 = new VertexFX(200, 200, 20, true, "C");
        graphview.addVertex(tmpVertex2);
        edge = new EdgeLine<>(tmpVertex1, tmpVertex2, 20);
        graphview.addEdge(edge);

        tmpVertex1 = new VertexFX(100, 300, 20, true, "D");
        graphview.addVertex(tmpVertex1);
        edge = new EdgeLine<>(tmpVertex1, tmpVertex2, 50);
        graphview.addEdge(edge);

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