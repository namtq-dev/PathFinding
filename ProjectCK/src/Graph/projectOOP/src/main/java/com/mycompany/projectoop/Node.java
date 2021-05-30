package com.mycompany.projectoop;

public abstract class Node implements Comparable<Node>{

    protected final String name;
    protected double cost;
    protected Node predecessor;

    public Node(String name, double cost, Node predecessor) {
        this.name = name;
        this.cost = cost;
        this.predecessor = predecessor;
    }

    public String getName() {
        return name;
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

    @Override
    public int compareTo(Node t) {
        return this.name.compareTo(t.getName());
    }

}
