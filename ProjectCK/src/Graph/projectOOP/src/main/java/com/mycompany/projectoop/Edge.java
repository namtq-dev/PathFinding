package com.mycompany.projectoop;

public class Edge {
    
    private final String startEdge;
    private final String endEdge;
    private final double weight;

    public Edge(String startEdge, String endEdge, double weight) {
        this.startEdge = startEdge;
        this.endEdge = endEdge;
        this.weight = weight;
    }

    public String getStartEdge() {
        return startEdge;
    }

    public String getEndEdge() {
        return endEdge;
    }

    public double getWeight() {
        return weight;
    }
    
}
