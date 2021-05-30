package Containers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import GraphFX.EdgeLine;
import GraphFX.VertexFX;
import GraphFX.VirtualVertexFX;

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

    private List<EdgeLine> edges;
    private List<VertexFX> vertices; 

    private final TextInputDialog dialog = new TextInputDialog();
    private Optional<String> dialogResult;

    public GraphPanel(int width, int height) {
        super();
        edges = new ArrayList<EdgeLine>();
        vertices = new ArrayList<VertexFX>();
        setMinSize(width, height);
        setMaxSize(width, height);
        loadStylesheet(null);
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
        final ContextMenu contextMenu = new ContextMenu();
        
        contextMenu.setAutoHide(true);

        MenuItem newVertex = new MenuItem("New node");
        newVertex.setOnAction(evt -> {
            int x = MouseInfo.getPointerInfo().getLocation().x - 50;
            int y = MouseInfo.getPointerInfo().getLocation().y - 50;
            dialog.setTitle("Input label");
            dialog.setHeaderText("what label do you want to attach to this Node?");
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

    public void addVertex(VertexFX v) {
        vertices.add(v);
        this.getChildren().add(v);
        this.getChildren().add(v.getAttachedLabel());
    }

    public void addEdge(EdgeLine edge) {
        edges.add(edge);
        this.getChildren().add(edge);
        this.getChildren().add(edge.getAttachedArrow());
        this.getChildren().add(edge.getAttachedLabel());
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
        for (int i = 0; i < this.vertices.size(); i++) this.vertices.get(i).setStyleClass("vertex");
        for (int i = 0; i < this.edges.size(); i++) {
            this.edges.get(i).setStyleClass("edge");
            this.edges.get(i).getAttachedArrow().setStyleClass("arrow");
        }
    }
}