package Graph;

public class NodeWrapper implements Comparable<NodeWrapper> {
    
    protected final Node node;
    protected double cost;
    protected NodeWrapper predecessor;
    
    public NodeWrapper(Node node, double cost, NodeWrapper predecessor) {
        this.node = node;
        this.cost = cost;
        this.predecessor = predecessor;
    }

    public Node getNode() {
        return node;
    }
    
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public NodeWrapper getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(NodeWrapper predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public int compareTo(NodeWrapper t) {
        int compare = Double.compare(this.cost, t.cost);
        if (compare == 0)
            compare = this.node.compareTo(t.node);
        return compare;
    }
    
}
