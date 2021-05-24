package GraphFX;

import javafx.scene.shape.Circle;
import containers.GraphPanel;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class VertexFX extends Circle {
    private static boolean isDragging = false;
    private static boolean isRightDragging = false;
    private static boolean isReadyingToCreateEdge = false;
    private static VertexFX currVertex;
    private LabelNode attachedLabel;

    private VirtualVertexFX virtualVertex;
    private EdgeLine<VertexFX, LabelNode> virtualLine;
    private GraphPanel p = null;

    private final StyleProxy styleProxy;

    public VertexFX(double x, double y, double radius, boolean allowMove, String label) {
        super(x, y, radius);
        enableDrag();
        attachLabel(new LabelNode(label));
        styleProxy = new StyleProxy(this);
        styleProxy.setStyleClass("vertex");
        attachedLabel.setStyleClass("vertex-label");
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
                virtualLine = new EdgeLine<>(this, virtualVertex);

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
                    p.addEdge(new EdgeLine<>(this, currVertex, 20));
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
            if (isRightDragging) {
                currVertex = this;
                isReadyingToCreateEdge = true;
                this.styleProxy.setStyleClass("vertex-hovering");
                System.out.println("Dragged on : " + this.attachedLabel.getText());
            }
        });

        setOnMouseDragExited(evt -> {
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
}
