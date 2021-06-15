package GUI.Containers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import GUI.GraphFX.EdgeLine;
import GUI.GraphFX.VertexFX;
import GUI.GraphFX.VirtualVertexFX;
import GUI.UIControls.CheckBoxs;
import Graph.Graph;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.awt.MouseInfo;

public class GraphPanel extends Pane{
    public static boolean contextMenuShowable = true;

    private final ContextMenu contextMenu;

    private List<EdgeLine> edges;
    private List<VertexFX> vertices; 
    private Graph graph;

    private final TextInputDialog dialog = new TextInputDialog();
    private Optional<String> dialogResult;

    public GraphPanel(int width, int height) {
        super();
        edges = new ArrayList<EdgeLine>();
        vertices = new ArrayList<VertexFX>();
        graph = new Graph();
        setMinSize(width, height);
        setMaxSize(width, height);
        loadStylesheet(null);

        contextMenu = new ContextMenu();
        setupContextMenu();
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

    private void setupContextMenu() {
        contextMenu.setAutoHide(true);

        MenuItem newVertex = new MenuItem("New node");
        newVertex.setOnAction(evt -> {
            int x = MouseInfo.getPointerInfo().getLocation().x - 50;
            int y = MouseInfo.getPointerInfo().getLocation().y - 50;
            dialog.setTitle("Input label");
            dialog.setHeaderText("What label do you want to attach to this Node?");
            dialog.setContentText("Please enter the label here :");
            dialog.getEditor().clear();

            this.dialogResult = dialog.showAndWait();
            
            if (dialogResult.isPresent()) {
                try {
                    if (this.dialogResult.get().isEmpty()) System.out.println("No input, cancelled !");
                    else this.addVertex(new VertexFX(x, y, this.dialogResult.get()));
                } catch (Exception e) {
                    System.out.println("Invalid input, try again");
                }
            } else {
                System.out.println("Cancelled !");
            }
        });

        contextMenu.getItems().addAll(newVertex);

        this.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (contextMenuShowable)
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(this, MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                } else if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    contextMenu.hide();
                }
            else {

            }
        });
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public void addVertex(VertexFX v) {
        vertices.add(v);
        graph.addNode(v.getNode());
        this.getChildren().add(v);
        this.getChildren().add(v.getAttachedLabel());
        this.getChildren().add(v.getValueLabel());
    }

    public void removeVertex(VertexFX v) {
        for (int i = this.edges.size() - 1; i >= 0; i--) {
            if (this.edges.get(i).startVertex.isEquals(v) || this.edges.get(i).endVertex.isEquals(v)) {
                this.removeEdge(edges.get(i));
            }
        }
        vertices.remove(v);
        graph.removeNode(v.getNode());
        this.getChildren().remove(v);
        this.getChildren().remove(v.getAttachedLabel());
        this.getChildren().remove(v.getValueLabel());
    }

    public void addEdge(EdgeLine edge) {
        edges.add(edge);
        graph.addEdge(edge.getEdge());
        this.getChildren().add(edge);
        this.getChildren().add(edge.getAttachedArrow());
        this.getChildren().add(edge.getAttachedLabel());
    }

    public void removeEdge(EdgeLine edge) {
        edges.remove(edge);
        graph.removeEdge(edge.getEdge());
        this.getChildren().remove(edge);
        this.getChildren().remove(edge.getAttachedArrow());
        this.getChildren().remove(edge.getAttachedLabel());
    }

    public void addVirtualLine(VirtualVertexFX virtualVertex, EdgeLine virtualLine) {
        this.getChildren().add(virtualVertex);
        this.getChildren().add(virtualLine);
        this.getChildren().add(virtualLine.getAttachedArrow());
    }

    public void removeVirtualLine(VirtualVertexFX virtualVertex, EdgeLine virtualLine) {
        this.getChildren().remove(virtualLine.getAttachedArrow());
        this.getChildren().remove(virtualVertex);
        this.getChildren().remove(virtualLine);
    }

    public EdgeLine getEdge(VertexFX startVertex, VertexFX endVertex) {
        for (int i = 0; i < this.edges.size(); i++) {
            if (this.edges.get(i).startVertex.isEquals(startVertex) && this.edges.get(i).endVertex.isEquals(endVertex)) return this.edges.get(i);
        }

        System.out.println("No EdgeLine found, the program maybe run wrong !");
        return this.edges.get(1);
    }

    public void Reset() {
        CheckBoxs.getBindAutoRunCheckbox().setVisible(true);
        for (int i = 0; i < this.vertices.size(); i++) {
            this.vertices.get(i).setStyleClass("vertex");
            this.vertices.get(i).getValueLabel().setText("");
        }
        for (int i = 0; i < this.edges.size(); i++) {
            this.edges.get(i).setStyleClass("edge");
            this.edges.get(i).getAttachedArrow().setStyleClass("arrow");
        }
    }

    public void ReadyToSimulate() {
        CheckBoxs.getBindAutoRunCheckbox().setVisible(false);
        for (int i = 0; i < this.vertices.size(); i++) {
            this.vertices.get(i).getValueLabel().setText("∞");
        }
    }

    public Graph getGraph() {
        return graph;
    }
}