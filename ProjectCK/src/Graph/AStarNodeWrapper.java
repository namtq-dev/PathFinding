package Graph;

public class AStarNodeWrapper extends NodeWrapper {
    
    private final double minRemainingCost;
    private double costSum;
    
    public AStarNodeWrapper(Node node, double cost, NodeWrapper predecessor, double minRemainingCost) {
        
        super(node, cost, predecessor);
        this.minRemainingCost = minRemainingCost;
        calculateCostSum();
  }

    private void calculateCostSum() {
        this.costSum = this.cost + this.minRemainingCost;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
        calculateCostSum();
    }
    
    @Override
    public int compareTo(NodeWrapper t) {
        AStarNodeWrapper o = (AStarNodeWrapper) t;
        int compare = Double.compare(this.costSum, o.costSum);
        if (compare == 0)
            compare = this.node.compareTo(o.node);
        return compare;
    }
    
}
