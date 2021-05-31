package Graph;

import GraphFX.VertexFX;

public class Node implements Comparable<Node>{

    protected static int nodeID;
    
    protected final int id;
    protected final VertexFX nodeFX;

    public Node(VertexFX nodeFX) {
        this.id = nodeID;
        this.nodeFX = nodeFX;
        ++nodeID;
    }

    public int getId() {
        return id;
    }

    public VertexFX getNodeFX() {
        return nodeFX;
    }

    @Override
    public int compareTo(Node t) {
        return this.id - t.id;
    }

}
