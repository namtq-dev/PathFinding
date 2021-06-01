package GUI.GraphFX;

import Graph.Edge;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

import java.awt.MouseInfo;

import GUI.Containers.GraphPanel;
import GUI.Interfaces.EdgeBase;
import GUI.Interfaces.LabelAttachable;
import GUI.Interfaces.StylableNode;

public class EdgeLine extends Line implements EdgeBase, StylableNode, LabelAttachable {

    public final VertexFX startVertex;
    public final VertexFX endVertex;
    private double weight;
    private Edge edge;

    private LabelNode attachedLabel = null;
    private Arrow attachedArrow = null;

    public final StyleProxy styleProxy;
    private DoubleProperty sin;
    private DoubleProperty cos;

    private final ContextMenu deleteOption = new ContextMenu();
    private final MenuItem delete = new MenuItem("Delete");
    private GraphPanel p = null;

    public EdgeLine(VertexFX fromVertex, VertexFX toVertex, double weight) {
        this.startVertex = fromVertex;
        this.endVertex = toVertex;
        this.weight = weight;
        this.edge = new Edge(fromVertex.getNode(), toVertex.getNode(), weight, this);

        styleProxy = new StyleProxy(this);
        styleProxy.addStyleClass("edge");

        sin = new SimpleDoubleProperty();
        sin.bind(CustomBinding.sin(toVertex.centerXProperty().subtract(fromVertex.centerXProperty()), toVertex.centerYProperty().subtract(fromVertex.centerYProperty())));

        cos = new SimpleDoubleProperty();
        cos.bind(CustomBinding.cos(toVertex.centerXProperty().subtract(fromVertex.centerXProperty()), toVertex.centerYProperty().subtract(fromVertex.centerYProperty())));

        //From
        this.startXProperty().bind(fromVertex.centerXProperty().add(cos.multiply(fromVertex.getRadius())));
        this.startYProperty().bind(fromVertex.centerYProperty().add(sin.multiply(fromVertex.getRadius())));
        //To
        this.endXProperty().bind(toVertex.centerXProperty().subtract(cos.multiply(toVertex.getRadius())));
        this.endYProperty().bind(toVertex.centerYProperty().subtract(sin.multiply(toVertex.getRadius())));
        attachLabel(new LabelNode(String.valueOf(this.weight)));
        attachArrow(new Arrow(7));
        setupDeleteOption();

        this.setOnContextMenuRequested(evt -> {
            deleteOption.show(this, MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
        });
    }

    public EdgeLine(VertexFX fromVertex, VirtualVertexFX toVertex) {
        if( toVertex == null || fromVertex == null) {
            throw new IllegalArgumentException("Cannot connect null vertices.");
        }

        this.startVertex = null;
        this.endVertex = null;

        styleProxy = new StyleProxy(this);
        styleProxy.addStyleClass("edge");

        sin = new SimpleDoubleProperty();
        sin.bind(CustomBinding.sin(toVertex.centerXProperty().subtract(fromVertex.centerXProperty()), toVertex.centerYProperty().subtract(fromVertex.centerYProperty())));

        cos = new SimpleDoubleProperty();
        cos.bind(CustomBinding.cos(toVertex.centerXProperty().subtract(fromVertex.centerXProperty()), toVertex.centerYProperty().subtract(fromVertex.centerYProperty())));

        //From
        this.startXProperty().bind(fromVertex.centerXProperty().add(cos.multiply(fromVertex.getRadius())));
        this.startYProperty().bind(fromVertex.centerYProperty().add(sin.multiply(fromVertex.getRadius())));
        //To
        this.endXProperty().bind(toVertex.centerXProperty());
        this.endYProperty().bind(toVertex.centerYProperty());
        attachArrow(new Arrow(7));
    }

    private void setupDeleteOption() {
        delete.setOnAction(evt -> {
            if (p == null) p = (GraphPanel) getParent();
            p.removeEdge(this);
        });

        deleteOption.getItems().add(delete);
    }

    public Edge getEdge() {
        return this.edge;
    }

    @Override
    public void setStyleClass(String cssClass) {
        styleProxy.setStyleClass(cssClass);
    }

    @Override
    public void addStyleClass(String cssClass) {
        styleProxy.addStyleClass(cssClass);
    }

    @Override
    public boolean removeStyleClass(String cssClass) {
        return styleProxy.removeStyleClass(cssClass);
    }
    

    @Override
    public void attachLabel(LabelNode label) {
        this.attachedLabel = label;
        label.xProperty().bind(startXProperty().add(endXProperty()).divide(2).subtract(label.getLayoutBounds().getWidth() / 2));
        label.yProperty().bind(startYProperty().add(endYProperty()).divide(2).add(label.getLayoutBounds().getHeight()));
        attachedLabel.setStyleClass("edge-label");
    }

    @Override
    public LabelNode getAttachedLabel() {
        return attachedLabel;
    }

    @Override
    public void attachArrow(Arrow arrow) {
        this.attachedArrow = arrow;

        /* attach arrow to line's endpoint */
        arrow.translateXProperty().bind(endXProperty());
        arrow.translateYProperty().bind(endYProperty());

        /* rotate arrow around itself based on this line's angle */
        Rotate rotation = new Rotate();
        rotation.pivotXProperty().bind(translateXProperty());
        rotation.pivotYProperty().bind(translateYProperty());
        rotation.angleProperty().bind(CustomBinding.toDegrees(CustomBinding.arctan(endXProperty().subtract(startXProperty()), endYProperty().subtract(startYProperty()))));

        arrow.getTransforms().add(rotation);
    }

    @Override
    public Arrow getAttachedArrow() {
        return this.attachedArrow;
    }
}
