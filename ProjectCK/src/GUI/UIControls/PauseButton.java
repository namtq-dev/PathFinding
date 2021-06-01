package GUI.UIControls;

import java.io.InputStream;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PauseButton extends Button {
    public PauseButton(int translateX, int translateY) {
        super();
        InputStream input = getClass().getResourceAsStream("img/pause_icon.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        this.setGraphic(imageView);
        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
        this.setMaxSize(50, 50);

        setVisible(false);
    }
}
