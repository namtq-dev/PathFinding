package GUI.Interfaces;

import GUI.GraphFX.LabelNode;

public interface LabelAttachable {
    public void attachLabel(LabelNode label);

    public LabelNode getAttachedLabel();
}
