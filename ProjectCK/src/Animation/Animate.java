package Animation;

import Containers.SpeedControlPane;
import GraphFX.EdgeLine;
import GraphFX.VertexFX;
import GraphFX.VirtualVertexFX;

import javafx.animation.TranslateTransition;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Animate {
    private static boolean isInited = false;
    private static TranslateTransition transition = null;
    private static DoubleProperty speed = null;
    private static VirtualVertexFX virtualVertex;
    private static EdgeLine virtualLine;

    public static void setupAnimate() {
        speed = new SimpleDoubleProperty();
        speed.bind(SpeedControlPane.speed);
        isInited = true;
    }

    public static void setBeingVisited(VertexFX vertex) {
        if (isInited) {
            vertex.setStyleClass("vertex-being-visited");
        } else {
            setupAnimate();
            setBeingVisited(vertex);
        }
    }

    public static void setDoneVisited(VertexFX vertex) {
        if (isInited) {
            vertex.setStyleClass("vertex-done-visited");
        } else {
            setupAnimate();
            setBeingVisited(vertex);
        }
    }

    public static TranslateTransition setBeingVisited(EdgeLine edgeline) {
        if (isInited) {
            transition = new TranslateTransition();

            virtualVertex = new VirtualVertexFX(edgeline.getStartX(), edgeline.getStartY());
            virtualVertex.setRadius(5);
            virtualLine = new EdgeLine(edgeline.startVertex, virtualVertex);
            virtualLine.setStyleClass("edge-being-visited");
            virtualLine.getAttachedArrow().setStyleClass("arrow-animate");
            edgeline.startVertex.getContainingGP().addVirtualLine(virtualVertex, virtualLine);

            transition.setDuration(Duration.seconds(speed.get()));
            System.out.println("speed = " + speed.get() + ", startVertex : " + edgeline.startVertex.getAttachedLabel().getText());

            transition.setToX(edgeline.getEndX() - edgeline.getStartX());
            transition.setToY(edgeline.getEndY() - edgeline.getStartY());
            System.out.printf("endX = %f, endY = %f\n", edgeline.getEndX(), edgeline.getEndY());
            transition.setAutoReverse(true);
            transition.setNode(virtualVertex);
            System.out.println("Transition : "+ transition.getNode().toString());
            return transition;

            //edgeline.startVertex.getContainingGP().removeVirtualLine(virtualVertex, virtualLine);
        } else {
            setupAnimate();
            return setBeingVisited(edgeline);
        }
    }

    public static TranslateTransition test(Circle cir) throws InterruptedException {
        if (isInited) {
            transition = new TranslateTransition();
            transition.setToX(200);
            transition.setToY(200);
            transition.setDuration(Duration.seconds(speed.get()));
            transition.setNode(cir);
            return transition;
        } else {
            setupAnimate();
            return test(cir);
        }
    }
}