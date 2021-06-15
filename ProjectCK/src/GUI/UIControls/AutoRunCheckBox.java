package GUI.UIControls;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.CheckBox;

public class AutoRunCheckBox extends CheckBox{
    public AutoRunCheckBox(int translateX, int translateY) {
        super("Auto run");

        this.setTranslateX(translateX);
        this.setTranslateY(translateY);

        this.getStyleClass().add("big-check-box");
        loadStylesheet(null);
    }

    public boolean isChecking() {
        return this.isSelected();
    }

    private void loadStylesheet(URI cssFile) {
        try {
            String css;
            if( cssFile != null ) {
                css = cssFile.toURL().toExternalForm();
            } else {
                File f = new File("checkboxStyle.css");
                css = f.toURI().toURL().toExternalForm();
            }

            getStylesheets().add(css);
            this.getStyleClass().add("graph");
        } catch (MalformedURLException ex) {
            Logger.getLogger(AutoRunCheckBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
