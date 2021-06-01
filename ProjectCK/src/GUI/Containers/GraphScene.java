package GUI.Containers;

import javafx.scene.layout.BorderPane;

public class GraphScene extends BorderPane {

    public GraphScene(GraphPanel graphView) {
        setCenter(new ContentZoomPane(graphView));
    }
}