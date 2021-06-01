package Graph;

import GUI.GraphFX.EdgeLine;

public class Edge implements Comparable<Edge>{
    
    private static int edgeID;
    
    private final int id;
    private final Node startNode;
    private final Node endNode;
    private final double weight;
    private final EdgeLine edgeFX;
    
    public Edge(Node startNode , Node  endNode , double weight, EdgeLine edgeFX) {
        this.id = edgeID;
        this.startNode = startNode ;
        this.endNode = endNode ;
        this.weight = weight;
        this.edgeFX = edgeFX;
        ++edgeID;
    }

    public int getId() {
        return id;
    }
    
    public Node  getStartNode() {
        return startNode;
    }

    public Node  getEndNode() {
        return endNode;
    }

    public double getWeight() {
        return weight;
    }

    public EdgeLine getEdgeFX() {
        return edgeFX;
    }

    @Override
    public int compareTo(Edge t) {
        return this.id - t.id;
    }
    
}
