package Graph;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    private Set<Node> allNodes;
    private Set<Edge> allEdges;
    private boolean hasNegativeWeight;

    public Graph() {
        allNodes = new HashSet<>();
        allEdges = new HashSet<>();
        hasNegativeWeight = false;
    }
    
    public Set<Node> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(Set<Node> allNodes) {
        this.allNodes = allNodes;
    }

    public Set<Edge> getAllEdges() {
        return allEdges;
    }

    public void setAllEdges(Set<Edge> allEdges) {
        this.allEdges = allEdges;
    }

    public boolean isIsNegativeWeight() {
        return hasNegativeWeight;
    }

    public void setIsNegativeWeight(boolean isNegativeWeight) {
        this.hasNegativeWeight = isNegativeWeight;
    }
    
    public void addNode(Node node){
        if (!allNodes.contains(node)) {
            allNodes.add(node);
        } else System.out.println("Node existed!");
    }
    
    public void addEdge(Edge edge){
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        } else System.out.println("Edge existed!");
    }
    
    public Set<Edge> getAdjacentEdges(Node node){
        Set<Edge> adjacentEdges = new HashSet<>();
        for (Edge edge : allEdges) {
            if (edge.getStartNode().getId() == node.getId()) {
                adjacentEdges.add(edge);
            }
        }
        return adjacentEdges;
    }
    
    public double getEdgeValue(Edge edge){
        return edge.getWeight();
    }
    
    public void removeNode(Node node){
        if (allNodes.contains(node)) {
            for (Edge adjacentEdge : this.getAdjacentEdges(node)) {
                allEdges.remove(adjacentEdge);
            }
            allNodes.remove(node);     
        }
    }
    
    public void removeEdge(Edge edge){
        Set<Edge> adjacentEdges = this.getAdjacentEdges(edge.getStartNode());
        if (allEdges.contains(edge)) {
            for (Edge adjacentEdge : adjacentEdges) {
                if((edge.getStartNode().getId() == adjacentEdge.getStartNode().getId())
                        && (edge.getEndNode().getId() == adjacentEdge.getEndNode().getId()))
                    adjacentEdges.remove(edge);
            }
            allEdges.remove(edge);
        }
    }
}