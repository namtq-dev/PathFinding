package interfaces;

import GraphFX.Arrow;

public interface EdgeBase {
    public void attachArrow(Arrow arrow);

    public Arrow getAttachedArrow();
}
