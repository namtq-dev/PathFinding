package GUI.GraphFX;

import GUI.Interfaces.StylableNode;
import javafx.scene.text.Text;

public class LabelNode extends Text implements StylableNode{

    private final StyleProxy styleProxy;

    public LabelNode() {
        this(0,0,"");
    }

    public LabelNode(String text) {
        this(0, 0, text);
    }

    public LabelNode(double x, double y, String text) {
        super(x, y, text);
        styleProxy = new StyleProxy(this);
        addStyleClass("edge-label");
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
}
