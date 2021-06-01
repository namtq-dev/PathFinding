package GUI.Containers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SpeedControlPane extends Slider {
    private static final double MIN_SPEED = 0.1;
    private static final double MAX_SPEED = 3;
    private static final double SCROLL_DELTA = 0.25;

    private final Text label;

    public static DoubleProperty speed = new SimpleDoubleProperty(1);

    public SpeedControlPane(int transLateX, int transLateY) {

        super(MIN_SPEED, MAX_SPEED, 1);
        this.setOrientation(Orientation.VERTICAL);

        this.setShowTickMarks(true);
        this.setShowTickLabels(true);
        this.setBlockIncrement(0.25f);
        this.setMajorTickUnit(SCROLL_DELTA);

        this.setTranslateX(transLateX);
        this.setTranslateY(transLateY);

        label = new Text("Latency");
        label.setTranslateX(transLateX - 7);
        label.setTranslateY(transLateY + 160);

        VBox paneSlider = new VBox(this, label);

        paneSlider.setPadding(new Insets(10, 10, 10, 10));
        paneSlider.setSpacing(10);

        this.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                speed.setValue(newValue);
            }
        });
    }

    public Text getAttachedLabel() {
        return this.label;
    }
}
