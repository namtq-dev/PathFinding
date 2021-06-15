package GUI.Animation;

import java.util.List;

import GUI.Containers.GraphPanel;
import GUI.Containers.SpeedControlPane;
import GUI.GraphFX.EdgeLine;
import GUI.GraphFX.VertexFX;
import GUI.UIControls.ContinueButton;
import GUI.UIControls.PauseButton;
import GUI.UIControls.ResetButton;
import GUI.UIControls.StopButton;
import Graph.Edge;
import Graph.Node;
import Graph.Step;
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
    private static int stepIndex;
    private static int stepsSize;

    public static void playAnimation(GraphPanel graphview, ResetButton resetBtn, PauseButton pauseButton, ContinueButton continueButton, StopButton stopButton, Animation animation, List<Node> result) {
        graphview.ReadyToSimulate();
        playAnimation(graphview, resetBtn, pauseButton, continueButton, stopButton, animation, result != null);
    }

    public static void setReadyAndPlay_StepByStep(GraphPanel graphview, ResetButton resetButton, ContinueButton continueButton, List<Step> steps, List<Node> result) {
        if (isInited) {
            graphview.ReadyToSimulate();
            graphview.setDisable(true);
            bindControlButtons_StepByStep(steps, result, graphview, resetButton, continueButton, result != null);
        } else {
            System.out.println("Class Animate has not been setup yet, automatically setup by default.");
            setupAnimation();
            setReadyAndPlay_StepByStep(graphview, resetButton, continueButton, steps, result);
        }
    }

    public static void playAnimation(GraphPanel graphview, ResetButton resetBtn, PauseButton pauseButton, ContinueButton continueButton, StopButton stopButton, Animation animation, boolean isShortestPathFound) {
        graphview.setDisable(true);
        
        String message;
        String header;
        if (isShortestPathFound) {
            header = "SUCCESS";
            message = "Shortest path found!";
        }
        else {
            header = "FAILED";
            message = "Can not find shortest path!";
        }
        
        animation.setOnFinished(evt -> {
            resetBtn.setVisible(true);
            pauseButton.setVisible(false);
            continueButton.setVisible(false);
            stopButton.setVisible(false);
            Alert alert = new Alert(isShortestPathFound ? AlertType.INFORMATION : AlertType.WARNING);
            alert.setTitle("Result");
            alert.setHeaderText(header);
            alert.setContentText(message);
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
            System.out.println("Class Animate has not been setup yet, automatically setup by default.");
            setupAnimation();
            return makeAnimation(steps, result);
        }
    }

    public static SequentialTransition makeAnimationSteps(List<Step> steps) {               // Making animations for steps
        SequentialTransition listAnimation = new SequentialTransition();
        setAnimateBeingVisited(listAnimation, steps.get(0).getCurrentNode().getNodeFX(), 0f);
        for (int i = 0; i < steps.size(); i++) {
            listAnimation.getChildren().add(makeAnimationStep(steps.get(i)));
        }
        return listAnimation;
    }

    public static SequentialTransition makeAnimationStep(Step step) {                       // Making animations for step
        SequentialTransition listAnimation = new SequentialTransition();
        setAnimateChecking(listAnimation, step.getCurrentNode().getNodeFX());
        for (int j = 0; j < step.getCheckedEdges().size(); j++) {
            setAnimateBeingVisited(listAnimation, step.getCheckedEdges().get(j).getEdgeFX());
            setAnimateBeingVisited(listAnimation, step.getCheckedEdges().get(j).getEdgeFX().endVertex, step.getNewCheckedCostValues().get(j));
        }

        if (step.isCurrentNodeMarked()) setAnimateVisited(listAnimation, step.getCurrentNode().getNodeFX());
        else {
            resetCheckedEgdes(listAnimation, step.getCheckedEdges());
            resetCheckedNode(listAnimation, step);
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

    private static void resetCheckedNode(SequentialTransition listAnimation, Step step) {
        listAnimation.getChildren().add(changeVertexColorImmediately(step.getCurrentNode().getNodeFX()));
        for (int i = 0;  i < step.getCheckedEdges().size(); i++) {
            listAnimation.getChildren().add(changeVertexColorImmediately(step.getCheckedEdges().get(i).getEndNode().getNodeFX()));
        }
    }

    public static Animation animateChecking(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        //fill.setToValue(Color.rgb(255, 182, 179));
        fill.setToValue(Color.rgb(10, 49, 247));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateChecked(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.millis(200));
        //fill.setToValue(Color.rgb(177, 223, 247));     
        fill.setToValue(Color.rgb(87, 115, 255));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateBeingVisited(VertexFX vertex, double value) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        //fill.setToValue(Color.rgb(93, 187, 238));
        fill.setToValue(Color.rgb(16, 196, 76));
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
        //fill.setToValue(Color.rgb(26, 117, 255));
        fill.setToValue(Color.rgb(239, 247, 10));
        fill.setShape(vertex);

        return fill;
    }

    public static Animation animateVertexDoneVisited(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()));
        //fill.setToValue(Color.rgb(255, 194, 179));
        fill.setToValue(Color.rgb(239, 247, 10));
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
        //stroke.setToValue(Color.rgb(0, 179, 60));
        stroke.setToValue(Color.rgb(16, 196, 76));
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
        //stroke.setToValue(Color.rgb(0, 179, 60));
        stroke.setToValue(Color.rgb(16, 196, 76));
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
        stroke.setDuration(Duration.seconds(speed.get()/5));
        //stroke.setToValue(Color.rgb(255, 109, 102));
        stroke.setToValue(Color.rgb(117, 147, 255));
        stroke.setShape(edgeLine);

        return stroke;
    }

    private static Animation changeArrowColorImmediately(EdgeLine edgeLine) {
        StrokeTransition stroke = new StrokeTransition();
        stroke.setAutoReverse(true);
        stroke.setDuration(Duration.seconds(speed.get()/5));
        //stroke.setToValue(Color.rgb(255, 109, 102));
        stroke.setToValue(Color.rgb(117, 147, 255));
        stroke.setShape(edgeLine.getAttachedArrow());

        return stroke;
    }

    private static Animation changeVertexColorImmediately(VertexFX vertex) {
        FillTransition fill = new FillTransition();
        fill.setAutoReverse(true);
        fill.setDuration(Duration.seconds(speed.get()/5));
        fill.setToValue(Color.rgb(177, 223, 247));
        fill.setShape(vertex);

        return fill;
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

    private static void bindControlButtons_StepByStep(List<Step> steps, List<Node> result, GraphPanel graphview, ResetButton resetButton, ContinueButton continueButton, boolean isShortestPathFound) {
        resetButton.setVisible(true);
        continueButton.setVisible(true);
        Animate.stepsSize = steps.size();
        Animate.stepIndex = 0;
        String header;
        String message;
        if (isShortestPathFound) {
            header = "SUCCESS";
            message = "Shortest path found!";
        }
        else {
            header = "FAILED";
            message = "Can not find shortest path!";
        }

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetButton.setVisible(false);
                continueButton.setVisible(false);

                graphview.Reset();
                graphview.setDisable(false);
            }
        });

        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Animate.stepIndex >= Animate.stepsSize) {
                    Animation tmp = makeAnimationResult(result);
                    continueButton.setVisible(false);

                    tmp.setOnFinished(evt -> {
                        continueButton.setVisible(false);
                        Alert alert = new Alert(isShortestPathFound ? AlertType.INFORMATION : AlertType.WARNING);
                        alert.setTitle("Result");
                        alert.setHeaderText(header);
                        alert.setContentText(message);
                        Platform.runLater(alert::showAndWait);
                    });

                    tmp.play();
                } else {
                    Animation tmp = makeAnimationStep(steps.get(Animate.stepIndex++));
                    tmp.play();
                }
            }
        });
    }
}
