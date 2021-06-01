package GUI.Interfaces;

public interface StylableNode {
    public void setStyle(String css);

    public void setStyleClass(String cssClass);

    public void addStyleClass(String cssClass);

    public boolean removeStyleClass(String cssClass);
}