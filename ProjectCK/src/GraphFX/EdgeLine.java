package GraphFX;

import interfaces.EdgeBase;
import interfaces.LabelAttachable;
import interfaces.StylableNode;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class EdgeLine<E, V> extends Line implements EdgeBase, StylableNode, LabelAttachable {
    //private final Edge<E, V> underlyingEdge;

    private final VertexFX toVertex;
    private final VertexFX fromVertex;
    private int weight;

    private LabelNode attachedLabel = null;
    private Arrow attachedArrow = null;

    private final StyleProxy styleProxy;
    private DoubleProperty sin;
    private DoubleProperty cos;

    public EdgeLine(/*Edge<E, V> edge,*/ VertexFX fromVertex, VertexFX toVertex, int weight) {
        System.out.println("New EdgeLine : " + fromVertex.getAttachedLabel().getText() + " - " + toVertex.getAttachedLabel().getText());
        if(toVertex == null || fromVertex == null) {
            throw new IllegalArgumentException("Cannot connect null vertices.");
        }

        this.toVertex = toVertex;
        this.fromVertex = fromVertex;

        //this.underlyingEdge = edge;

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
        attachLabel(new LabelNode(String.valueOf(weight)));
        attachArrow(new Arrow(7));

        this.weight = weight;
    }

    public EdgeLine(VertexFX toVertex, VirtualVertexFX fromVertex) {
        if( toVertex == null || fromVertex == null) {
            throw new IllegalArgumentException("Cannot connect null vertices.");
        }

        this.toVertex = null;
        this.fromVertex = null;

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

    // @Override
    // public Edge<E, V> getUnderlyingEdge() {
    //     return underlyingEdge;
    // }

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
