package Animation;

import java.util.List;

import Containers.GraphPanel;
import Containers.SpeedControlPane;
import Graph.Edge;
import Graph.Node;
import Graph.Step;
import GraphFX.EdgeLine;
import GraphFX.VertexFX;
import UIControls.ResetButton;
import UIControls.StopButton;
import UIControls.PauseButton;
import UIControls.ContinueButton;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Animate {
    private static boolean isInited = false;
    private static DoubleProperty speed = null;

    public static void playAnimation(GraphPanel graphview, ResetButton resetBtn, PauseButton pauseButton, ContinueButton continueButton, StopButton stopButton, Animation animation, List<Node> result) {
        graphview.ReadyToSimulate();
        playAnimation(graphview, resetBtn, pauseButton, continueButton, stopButton, animation, result != null);
    }

    public static void playAnimation(GraphPanel graphview, ResetButton resetBtn, PauseButton pauseButton, ContinueButton continueButton, StopButton stopButton, Animation animation, boolean isShortestPathFound) {
        graphview.setDisable(true);
        
        String message1;
        if (isShortestPathFound) message1 = "Found shortest path !";
        else message1 = "Cannot find shortest path !";
        
        animation.setOnFinished(evt -> {
            resetBtn.setVisible(true);
            pauseButton.setVisible(false);
            continueButton.setVisible(false);
            stopButton.setVisible(false);
            Alert alert = new Alert(isShortestPathFound ? AlertType.INFORMATION : AlertType.WARNING);
            alert.setTitle("Result");
            alert.setHeaderText("Results :");
            alert.setContentText(message1);
            Platform.runLater(alert::showAndWait);
        });

        animation.play();
    }

    public static void setupAnimation() {
        speed = new SimpleDoubleProperty();
        speed.bind(SpeedControlPane.speed);
        isInited = true;
    }

    public static SequentialTransition makeAnimation(List<Step> steps, List<Node> result) {
        if (isInited) {
            SequentialTransition listAnimation = new SequentialTransition();
            
            listAnimation.getChildren().add(makeAnimationSteps(steps));
            if (result != null) listAnimation.getChildren().add(makeAnimationResult(result));

            return listAnimation;
        } else {
            System.out.println("Animations has not been setup yet, automatically setup by default.");
            setupAnimation();
            return makeAnimation(steps, result);
        }
    }

    public static SequentialTransition makeAnimationSteps(List<Step> steps) {               // Making animations for steps
        SequentialTransition listAnimation = new SequentialTransition();
        setAnimateBeingVisited(listAnimation, steps.get(0).getCurrentNode().getNodeFX(), 0f);
        for (int i = 0; i < steps.size(); i++) {
            setAnimateChecking(listAnimation, steps.get(i).getCurrentNode().getNodeFX());
            for (int j = 0; j < steps.get(i).getCheckedEdges().size(); j++) {
                setAnimateBeingVisited(listAnimation, steps.get(i).getCheckedEdges().get(j).getEdgeFX());
                setAnimateBeingVisited(listAnimation, steps.get(i).getCheckedEdges().get(j).getEdgeFX().endVertex, steps.get(i).getNewCheckedCostValues().get(j));
            }

            if (steps.get(i).isCurrentNodeMarked()) setAnimateVisited(listAnimation, steps.get(i).getCurrentNode().getNodeFX());
            else {
                resetCheckedEgdes(listAnimation, steps.get(i).getCheckedEdges());
                setAnimateChecked(listAnimation, steps.get(i).getCurrentNode().getNodeFX());
            }
        }
        return listAnimation;
    }

    public static SequentialTransition makeAnimationResult(List<Node> result) {         // Making animations for result
        SequentialTransition listAnimation = new SequentialTransition();
        GraphPanel graphview;
        EdgeLine tmpEdge;

        setAnimateVertexInShortestPath(listAnimation, result.get(0).getNodeFX());
        graphview = result.get(0).getNodeFX().getContainingGP();
        for (int i = 1; i < result.size(); i++) {
            tmpEdge = graphview.getEdge(result.get(i-1).getNodeFX(), result.get(i).getNodeFX());
            setAnimateEdgeInShortestPath(listAnimation, tmpEdge);
            setAnimateVertexInShortestPath(listAnimation, result.get(i).getNodeFX());
        }
        return listAnimation;
    }

    public static SequentialTransition finalPath(List<VertexFX> nodes) {
        return null;
    }

    public static void setAnimateChecking(SequentialTransition listAnimation, VertexFX vertex) {
        listAnimation.getChildren().add(animateChecking(vertex));
    }

    public static void setAnimateChecked(SequentialTransition listAnimation, VertexFX vertex) {
        listAnimation.getChildren().add(animateChecked(vertex));
    }

    private static void setAnimateBeingVisited(SequentialTransition listAnimation, EdgeLine edge) {                     //Animation for Step : Edges + Arrow
        listAnimation.getChildren().add(animateEdgeBeingVisited(edge));
        listAnimation.getChildren().add(animateArrowBeingVisited(edge));
    }

    public static void setAnimateBeingVisited(SequentialTransition listAnimation, VertexFX vertex, double value) {      //Animation for Step : Vertices
        listAnimation.getChildren().add(animateBeingVisited(vertex, value));
    }

    public static void setAnimateVisited(SequentialTransition listAnimation, VertexFX vertex) {
        listAnimation.getChildren().add(animateVisited(vertex));
    }

    public static void setAnimateVertexInShortestPath(SequentialTransition listAnimation, VertexFX vertex) {            //Animation for Result : Vertices
        listAnimation.getChildren().add(animateVertexInShortestPath(vertex));
    }

    public static void setAnimateEdgeInShortestPath(SequentialTransition listAnimation, EdgeLine edge) {                //Animation for Result : Edges + Arrow
        listAnimation.getChildren().add(animateEdgeInShortestPath(edge));
        listAnimation.getChildren().add(animateArrowInShortestPath(edge));
    }

    private static void resetCheckedEgdes(SequentialTransition listAnimation, List<Edge> edges) {
        for (int i = 0;  i < edges.size(); i++) {
            listAnimation.getChildren().add(changeEgdeColorImmediately(edges.get(i).getEdgeFX()));
            listAnimation.getChildren().add(changeArrowColorImmediately(edges.get(i).getEdgeFX()));
        }
    }

    public static Animation animateChecking(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        fill.setToValue(Color.rgb(255, 182, 179));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateChecked(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.millis(200));
        fill.setToValue(Color.rgb(177, 223, 247));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateBeingVisited(VertexFX vertex, double value) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        fill.setToValue(Color.rgb(93, 187, 238));
        fill.setShape(vertex);
        fill.setOnFinished(evt -> {
            vertex.getValueLabel().setText(String.valueOf(value));
        });

        return fill;
    }

    public static Animation animateVisited(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        fill.setToValue(Color.rgb(26, 117, 255));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateVertexDoneVisited(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        fill.setToValue(Color.rgb(255, 194, 179));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateVertexInShortestPath(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        fill.setToValue(Color.rgb(255, 51, 0));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateEdgeBeingVisited(EdgeLine edge) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.seconds(speed.get()));
        stroke.setToValue(Color.rgb(0, 179, 60));
        stroke.setShape(edge);

        return stroke;
    }

    public static Animation animateEdgeInShortestPath(EdgeLine edge) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.seconds(speed.get()/3));
        stroke.setToValue(Color.rgb(255, 26, 26));
        stroke.setShape(edge);

        return stroke;
    }

    private static Animation animateArrowBeingVisited(EdgeLine edge) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.seconds(speed.get()/5));
        stroke.setToValue(Color.rgb(0, 179, 60));
        stroke.setShape(edge.getAttachedArrow());

        return stroke;
    }

    private static Animation animateArrowInShortestPath(EdgeLine edge) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.seconds(speed.get()/15));
        stroke.setToValue(Color.rgb(255, 26, 26));
        stroke.setShape(edge.getAttachedArrow());

        return stroke;
    }

    private static Animation changeEgdeColorImmediately(EdgeLine edgeLine) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.millis(200));
        stroke.setToValue(Color.rgb(255, 109, 102));
        stroke.setShape(edgeLine);

        return stroke;
    }

    private static Animation changeArrowColorImmediately(EdgeLine edgeLine) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.millis(200));
        stroke.setToValue(Color.rgb(255, 109, 102));
        stroke.setShape(edgeLine.getAttachedArrow());

        return stroke;
    }

    public static void bindControlButtons(Animation animation, GraphPanel graphview, PauseButton pauseButton, ContinueButton continueButton, StopButton stopButton) {
        pauseButton.setVisible(true);
        continueButton.setVisible(true);
        stopButton.setVisible(true);

        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animation.pause();
            }
        });

        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animation.play();
            }
        });

        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopButton.setVisible(false);
                continueButton.setVisible(false);
                pauseButton.setVisible(false);

                animation.stop();
                graphview.Reset();
                graphview.setDisable(false);
            }
        });
    }
}