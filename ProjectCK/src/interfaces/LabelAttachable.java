package Interfaces;

import GraphFX.LabelNode;

public interface LabelAttachable {
    public void attachLabel(LabelNode label);

    public LabelNode getAttachedLabel();
}
