package Graph;

import GraphFX.VertexFX;

public class CoordinateNode extends Node{
    
    private double x;
    private double y;
    private final double minRemainingCost;
    private double costSum;
    
    public CoordinateNode(double cost, Node predecessor, VertexFX nodeFX,
                                                 double x, double y, double minRemainingCost) {
        super(cost, predecessor, nodeFX);
        this.x = x;
        this.y = y;
        this.minRemainingCost = minRemainingCost;
        calculateCostSum();
  }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
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
    public int compareTo(Node t) {
        CoordinateNode o = (CoordinateNode) t;
        int compare = Double.compare(this.costSum, o.costSum);
        if (compare == 0)
            compare = this.id - o.id;
        return compare;
    }
    
}
