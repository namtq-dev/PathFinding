package GUI.GraphFX;

import javafx.scene.shape.Circle;
import javafx.animation.SequentialTransition;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

import java.awt.MouseInfo;
import java.util.Optional;

import GUI.Animation.Animate;
import GUI.Containers.GraphPanel;
import GUI.Interfaces.StylableNode;
import GUI.UIControls.Buttons;
import GUI.UIControls.CheckBoxs;
import Graph.AStarAlgorithm;
import Graph.BellmanFordAlgorithm;
import Graph.DijkstraAlgorithm;
import Graph.Node;
import Graph.ShortestPathContext;
import Graph.ShortestPathSolver;

public class VertexFX extends Circle implements StylableNode {
    private static boolean isDragging = false;
    private static boolean isRightDragging = false;
    private static boolean isReadyingToCreateEdge = false;
    private static VertexFX currVertex;

    private ShortestPathContext shortestPathContext = new ShortestPathContext();

    private LabelNode attachedLabel;
    private LabelNode valueLabel;

    private VirtualVertexFX virtualVertex;
    private EdgeLine virtualLine;
    private GraphPanel p = null;

    public final StyleProxy styleProxy;

    private final ContextMenu contextMenu = new ContextMenu();
    private final MenuItem newVertex = new MenuItem("New edge");
    private final MenuItem run_Dijkstra = new MenuItem("Execute Dijkstra");
    private final MenuItem run_BellmanFord = new MenuItem("Execute BellmanFord");
    private final MenuItem run_AStar = new MenuItem("Execute AStar");

    private final ContextMenu deleteOption = new ContextMenu();
    private final MenuItem delete = new MenuItem("Delete");

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
        valueLabel.setStyleClass("vertex-value");

        this.node = new Node(this);
        enableDrag();
        setupContextMenu();
        setupDeleteOption();
        this.setOnContextMenuRequested(evt -> {
            if (isReadyingToCreateEdge) isReadyingToCreateEdge = false;
            else  deleteOption.show(this, MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
        });
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
                    else p.addEdge(new EdgeLine(this, currVertex, Double.parseDouble(this.dialogResult.get())));
                } catch (NumberFormatException nfe) {
                    System.out.println("Invalid input, try again");
                }
            } else {
                System.out.println("Cancelled !");
            }
        });

        run_Dijkstra.setOnAction(evt -> {
            if (p.getGraph().hasNegativeWeight()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Cannot Execute Algorithm");
                alert.setHeaderText("EXECUTION FAILED");
                alert.setContentText("Negative weight detected!");

                alert.showAndWait();
            } else {
                shortestPathContext.setSolver(new DijkstraAlgorithm());
                ShortestPathSolver kq = shortestPathContext.solve(p.getGraph(), this.getNode(), currVertex.getNode());

                if (CheckBoxs.getBindAutoRunCheckbox().isChecking()) {
                    SequentialTransition animation = Animate.makeAnimation(kq.getSteps(), kq.getResult());
                    Animate.bindControlButtons(animation, p, Buttons.getBindPauseButton(), Buttons.getBindContinueButton(), Buttons.getBindStopButton());
                    Animate.playAnimation(p, Buttons.getBindResetButton(), Buttons.getBindPauseButton(), Buttons.getBindContinueButton(), Buttons.getBindStopButton(), animation, kq.getResult());
                } else {
                    Animate.setReadyAndPlay_StepByStep(p, Buttons.getBindResetButton(), Buttons.getBindContinueButton(), kq.getSteps(), kq.getResult());
                }
            }
        });

        run_BellmanFord.setOnAction(evt -> {
            shortestPathContext.setSolver(new BellmanFordAlgorithm());
            ShortestPathSolver kq = shortestPathContext.solve(p.getGraph(), this.getNode(), currVertex.getNode());

            if (CheckBoxs.getBindAutoRunCheckbox().isChecking()) {
                SequentialTransition animation = Animate.makeAnimation(kq.getSteps(), kq.getResult());
                Animate.bindControlButtons(animation, p, Buttons.getBindPauseButton(), Buttons.getBindContinueButton(), Buttons.getBindStopButton());
                Animate.playAnimation(p, Buttons.getBindResetButton(), Buttons.getBindPauseButton(), Buttons.getBindContinueButton(), Buttons.getBindStopButton(), animation, kq.getResult());
            } else {
                Animate.setReadyAndPlay_StepByStep(p, Buttons.getBindResetButton(), Buttons.getBindContinueButton(), kq.getSteps(), kq.getResult());
            }
        });

        run_AStar.setOnAction(evt -> {
            if (p.getGraph().hasNegativeWeight()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Cannot Execute Algorithm");
                alert.setHeaderText("EXECUTION FAILED");
                alert.setContentText("Negative weight detected!");

                alert.showAndWait();
            } else {
                shortestPathContext.setSolver(new AStarAlgorithm());
                ShortestPathSolver kq = shortestPathContext.solve(p.getGraph(), this.getNode(), currVertex.getNode());

                if (CheckBoxs.getBindAutoRunCheckbox().isChecking()) {
                    SequentialTransition animation = Animate.makeAnimation(kq.getSteps(), kq.getResult());
                    Animate.bindControlButtons(animation, p, Buttons.getBindPauseButton(), Buttons.getBindContinueButton(), Buttons.getBindStopButton());
                    Animate.playAnimation(p, Buttons.getBindResetButton(), Buttons.getBindPauseButton(), Buttons.getBindContinueButton(), Buttons.getBindStopButton(), animation, kq.getResult());
                } else {
                    Animate.setReadyAndPlay_StepByStep(p, Buttons.getBindResetButton(), Buttons.getBindContinueButton(), kq.getSteps(), kq.getResult());
                }
            }
        });

        contextMenu.getItems().addAll(newVertex, run_Dijkstra, run_BellmanFord, run_AStar);
    }

    private void setupDeleteOption() {
        delete.setOnAction(evt -> {
            p.removeVertex(this);
        });

        deleteOption.getItems().add(delete);
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
            getScene().setCursor(Cursor.HAND);
            isDragging = false;

            if (isRightDragging) {
                isRightDragging = false;
                p.removeVirtualLine(virtualVertex, virtualLine);
                if (isReadyingToCreateEdge) {
                    if (currVertex != this) contextMenu.show(this, MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                    //isReadyingToCreateEdge = false;
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
            if (p == null) p = (GraphPanel) getParent();
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.HAND);
            }
        });

        setOnMouseExited((MouseEvent mouseEvent) -> {

            if (!isDragging && !isRightDragging) this.styleProxy.setStyleClass("vertex");
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
                getScene().setCursor(Cursor.HAND);
            }
        });

        setOnMouseDragExited(evt -> {
            GraphPanel.contextMenuShowable = true;
            if (isRightDragging) {
                isReadyingToCreateEdge = false;
                this.styleProxy.setStyleClass("vertex");
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
        this.valueLabel = new LabelNode("");

        label.xProperty().bind(centerXProperty().subtract(label.getLayoutBounds().getWidth() / 1.5));
        label.yProperty().bind(centerYProperty().add(getRadius() + label.getLayoutBounds().getHeight()));

        this.valueLabel.xProperty().bind(centerXProperty().subtract(label.getLayoutBounds().getWidth() / 1.5));
        this.valueLabel.yProperty().bind(centerYProperty().subtract(getRadius() + label.getLayoutBounds().getHeight() - 10));
    }

    public LabelNode getAttachedLabel() {
        return attachedLabel;
    }

    public LabelNode getValueLabel () {
        return valueLabel;
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
