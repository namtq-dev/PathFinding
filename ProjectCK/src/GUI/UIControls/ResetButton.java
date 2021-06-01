package GUI.UIControls;

import java.io.InputStream;

import GUI.Containers.GraphPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResetButton extends Button {
    public ResetButton(GraphPanel graphview, int translateX, int translateY) {
        super();
        InputStream input = getClass().getResourceAsStream("img/reset_icon.jpg");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        this.setGraphic(imageView);
        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
        this.setMaxSize(50, 50);

        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphview.Reset();
                graphview.setDisable(false);
                setVisible(false);
            }
        });

        setVisible(false);
    }
}
