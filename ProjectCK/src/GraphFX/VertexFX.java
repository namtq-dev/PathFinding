package GraphFX;

import javafx.scene.shape.Circle;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

import java.awt.MouseInfo;
import java.util.Optional;

import Containers.GraphPanel;
import Graphs.Node;
import Interfaces.StylableNode;

public class VertexFX extends Circle implements StylableNode {
    private static boolean isDragging = false;
    private static boolean isRightDragging = false;
    private static boolean isReadyingToCreateEdge = false;
    private static VertexFX currVertex;

    private LabelNode attachedLabel;

    private VirtualVertexFX virtualVertex;
    private EdgeLine virtualLine;
    private GraphPanel p = null;

    public final StyleProxy styleProxy;

    private final ContextMenu contextMenu = new ContextMenu();
    private final MenuItem newVertex = new MenuItem("New edge");
    private final MenuItem run_Dijkstra = new MenuItem("Execute Dijkstra");
    private final MenuItem run_BellmanFord = new MenuItem("Execute BellmanFord");
    private final MenuItem run_AStar = new MenuItem("Execute AStar");

    private final TextInputDialog dialog = new TextInputDialog();
    private Optional<String> dialogResult;
    private Node node;

    public VertexFX(double x, double y, String label) {
        this(x, y, 20d, label);
    }

    public VertexFX(double x, double y, double radius, String label) {
        super(x, y, radius);

        attachLabel(new LabelNode(label));
        styleProxy = new StyleProxy(this);
        styleProxy.setStyleClass("vertex");
        attachedLabel.setStyleClass("vertex-label");

        this.node = new Node(1, 1, "x");
        enableDrag();
        setupContextMenu();
    }

    public VertexFX(double x, double y) {               //Constructor for VirtualVertexFX
        super(x, y, 0);
        styleProxy = new StyleProxy(this);
    }

    public void setupContextMenu() {
        newVertex.setOnAction(evt -> {
            dialog.setTitle("Input weight");
            dialog.setHeaderText("How much does this edge weigh?");
            dialog.setContentText("Please enter the weight here :");
            dialog.getEditor().clear();

            this.dialogResult = dialog.showAndWait();
            
            if (dialogResult.isPresent()) {
                try {
                    if (this.dialogResult.get().isEmpty()) System.out.println("No input, cancelled !");
                    else p.addEdge(new EdgeLine(this, currVertex, Integer.parseInt(this.dialogResult.get())));
                } catch (NumberFormatException nfe) {
                    System.out.println("Invalid input, try again");
                }
            } else {
                System.out.println("Cancelled !");
            }
        });

        run_Dijkstra.setOnAction(evt -> {
            System.out.println("Execute Dijkstra");
        });

        run_BellmanFord.setOnAction(evt -> {
            System.out.println("Execute BellmanFord");
        });

        run_AStar.setOnAction(evt -> {
            System.out.println("Execute AStar");
        });

        contextMenu.getItems().addAll(newVertex, run_Dijkstra, run_BellmanFord, run_AStar);
    }


    private class PointVector {
        double x, y;

        public PointVector(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private void enableDrag() {
        final PointVector dragDelta = new PointVector(0, 0);
        setOnMousePressed((MouseEvent mouseEvent) -> {
            System.out.println("Pressed VertexFX : " + this.attachedLabel.getText());
            if (mouseEvent.isPrimaryButtonDown()) {
                dragDelta.x = getCenterX() - mouseEvent.getX();
                dragDelta.y = getCenterY() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);
                isDragging = true;

                mouseEvent.consume();
            } else if (mouseEvent.isSecondaryButtonDown()) {
                dragDelta.x = getCenterX() - mouseEvent.getX();
                dragDelta.y = getCenterY() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);
                virtualVertex = new VirtualVertexFX(mouseEvent.getX(), mouseEvent.getY());
                virtualLine = new EdgeLine(this, virtualVertex);

                p.addVirtualLine(virtualVertex, virtualLine);
                isRightDragging = true;
                mouseEvent.consume();
            }

        });

        setOnMouseReleased((MouseEvent mouseEvent) -> {
            System.out.println("Released VertexFX : " + this.attachedLabel.getText());
            getScene().setCursor(Cursor.HAND);
            isDragging = false;
            if (isRightDragging) {
                isRightDragging = false;
                p.removeVirtualLine(virtualVertex, virtualLine);
                if (isReadyingToCreateEdge) {
                    if (currVertex != this) contextMenu.show(this, MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                    isReadyingToCreateEdge = false;
                }
            }
            this.styleProxy.setStyleClass("vertex");

            mouseEvent.consume();
        });

        setOnMouseDragged((MouseEvent mouseEvent) -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                double newX = mouseEvent.getX() + dragDelta.x;
                double x = boundCenterCoordinate(newX, 0, getParent().getLayoutBounds().getWidth());
                setCenterX(x);

                double newY = mouseEvent.getY() + dragDelta.y;
                double y = boundCenterCoordinate(newY, 0, getParent().getLayoutBounds().getHeight());
                setCenterY(y);
                mouseEvent.consume();
            } else if (mouseEvent.isSecondaryButtonDown()) {
                double newX = mouseEvent.getX() + dragDelta.x;
                double x = boundCenterCoordinate(newX, 0, getParent().getLayoutBounds().getWidth());
                virtualVertex.setCenterX(x);

                double newY = mouseEvent.getY() + dragDelta.y;
                double y = boundCenterCoordinate(newY, 0, getParent().getLayoutBounds().getHeight());
                virtualVertex.setCenterY(y);
                mouseEvent.consume();
            }
        });

        setOnMouseEntered((MouseEvent mouseEvent) -> {

            this.styleProxy.setStyleClass("vertex-hovering");
            System.out.println("Entered VertexFX : " + this.attachedLabel.getText());
            if (p == null) p = (GraphPanel) getParent();
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.HAND);
            }
        });

        setOnMouseExited((MouseEvent mouseEvent) -> {

            if (!isDragging && !isRightDragging) this.styleProxy.setStyleClass("vertex");
            System.out.println("Exited VertexFX : " + this.attachedLabel.getText());
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.DEFAULT);
            }
        });

        //===========Dragging==========//

        setOnDragDetected((MouseEvent mouseEvent) -> {
            startFullDrag();
        });

        setOnMouseDragEntered(evt -> {
            GraphPanel.contextMenuShowable = false;
            if (isRightDragging) {
                currVertex = this;
                isReadyingToCreateEdge = true;
                this.styleProxy.setStyleClass("vertex-hovering");
                System.out.println("Dragged on : " + this.attachedLabel.getText());
                getScene().setCursor(Cursor.HAND);
            }
        });

        setOnMouseDragExited(evt -> {
            GraphPanel.contextMenuShowable = true;
            if (isRightDragging) {
                isReadyingToCreateEdge = false;
                this.styleProxy.setStyleClass("vertex");
                System.out.println("Dragged out : " + this.attachedLabel.getText());
            }
        });
    }

    private double boundCenterCoordinate(double value, double min, double max) {
        double radius = getRadius();

        if (value < min + radius) {
            return min + radius;
        } else if (value > max - radius) {
            return max - radius;
        } else {
            return value;
        }
    }

    public void attachLabel(LabelNode label) {
        this.attachedLabel = label;
        label.xProperty().bind(centerXProperty().subtract(label.getLayoutBounds().getWidth() / 1.5));
        label.yProperty().bind(centerYProperty().add(getRadius() + label.getLayoutBounds().getHeight()));
    }

    public LabelNode getAttachedLabel() {
        return attachedLabel;
    }

    public GraphPanel getContainingGP() {
        p = (GraphPanel) getParent();
        return p;
    }

    @Override
    public void addStyleClass(String classname) {
        this.styleProxy.addStyleClass(classname);
    }

    @Override
    public void setStyleClass(String classname) {
        this.styleProxy.setStyleClass(classname);
    }

    @Override
    public boolean removeStyleClass(String classname) {
        return this.styleProxy.removeStyleClass(classname);
    }

    public boolean isEquals(VertexFX vertex) {
        return (this.getNode() == vertex.getNode());
    }

    public Node getNode() {
        return this.node;
    }
}
