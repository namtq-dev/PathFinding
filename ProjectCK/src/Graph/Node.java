package Graph;

import GraphFX.VertexFX;

public abstract class Node implements Comparable<Node>{

    protected static int nodeID;
    
    protected final int id;
    protected double cost;
    protected Node predecessor;
    protected VertexFX nodeFX;

    public Node(double cost, Node predecessor, VertexFX nodeFX) {
        this.id = nodeID;
        this.cost = cost;
        this.predecessor = predecessor;
        this.nodeFX = nodeFX;
        ++nodeID;
    }

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public VertexFX getNodeFX() {
        return nodeFX;
    }

    @Override
    public int compareTo(Node t) {
        int compare = Double.compare(this.cost, t.cost);
        if (compare == 0) {
            compare = this.id - t.id;
        }
        return compare;
    }

}
