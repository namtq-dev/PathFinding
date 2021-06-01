package Graph;

import GUI.GraphFX.VertexFX;

public class Node implements Comparable<Node>{

    private static int nodeID;
    
    private final int id;
    private final VertexFX nodeFX;

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
