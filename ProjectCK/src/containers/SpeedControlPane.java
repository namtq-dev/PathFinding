package Containers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;

public class SpeedControlPane extends Pane {
    public static DoubleProperty speed = new SimpleDoubleProperty(1);
    //...
}
