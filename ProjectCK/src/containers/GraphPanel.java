package containers;

import java.util.logging.Level;
import java.util.logging.Logger;

import GraphFX.EdgeLine;
import GraphFX.LabelNode;
import GraphFX.VertexFX;
import GraphFX.VirtualVertexFX;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import javafx.scene.layout.Pane;

public class GraphPanel extends Pane{
    public GraphPanel() {
        super();
        setMinSize(1800, 900);
        setMaxSize(1800, 900);
        loadStylesheet(null);
    }

    private void loadStylesheet(URI cssFile) {
        try {
            String css;
            if( cssFile != null ) {
                css = cssFile.toURL().toExternalForm();
            } else {
                File f = new File("graphStyle.css");
                css = f.toURI().toURL().toExternalForm();
            }

            getStylesheets().add(css);
            this.getStyleClass().add("graph");
        } catch (MalformedURLException ex) {
            Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addVertex(VertexFX v) {
        this.getChildren().add(v);
        this.getChildren().add(v.getAttachedLabel());
    }

    public void addEdge(EdgeLine<VertexFX, LabelNode> edge) {
        this.getChildren().add(edge);
        this.getChildren().add(edge.getAttachedArrow());
        this.getChildren().add(edge.getAttachedLabel());
    }

    public void addVirtualLine(VirtualVertexFX virtualVertex, EdgeLine<VertexFX, LabelNode> virtualLine) {
        this.getChildren().add(virtualVertex);
        this.getChildren().add(virtualLine);
    }

    public void removeVirtualLine(VirtualVertexFX virtualVertex, EdgeLine<VertexFX, LabelNode> virtualLine) {
        this.getChildren().remove(virtualVertex);
        this.getChildren().remove(virtualLine);
    }
}