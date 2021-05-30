package Graph;

import GraphFX.VertexFX;

public class NonCoordinateNode extends Node{
    
    public NonCoordinateNode(double cost, Node predecessor, VertexFX nodeFX) {
        super(cost, predecessor, nodeFX);
    }
}
