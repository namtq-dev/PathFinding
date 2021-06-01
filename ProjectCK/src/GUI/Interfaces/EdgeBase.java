package GUI.Interfaces;

import GUI.GraphFX.Arrow;

public interface EdgeBase {
    public void attachArrow(Arrow arrow);

    public Arrow getAttachedArrow();
}
