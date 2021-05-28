package com.mycompany.projectoop;

public class CoordinateNode extends Node{
    
    private final double minRemainingCost;
    private double costSum;
  
    public CoordinateNode(String name, double cost, Node predecessor,
                                                 double minRemainingCost) {
        super(name, cost, predecessor);
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
    
    
}
