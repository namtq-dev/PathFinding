package Graph;

import java.util.*;

public class AStarAlgorithm extends ShortestPathSolver {
    
    @Override
    public void run(Graph graph, Node source, Node target) {
        
        // Throw IllegalArgumentException if the graph has negative weight
        if (graph.hasNegativeWeight()) {
            throw new IllegalArgumentException("Negative weight detected");
        }

        Map<Node, AStarNodeWrapper> nodeWrappers = new HashMap<>();
        TreeSet< AStarNodeWrapper> queue = new TreeSet<>();
        Set<Node> visitedNodes = new HashSet<>();
    
        // Add source to queue
         AStarNodeWrapper sourceWrapper = new  AStarNodeWrapper(source, 0, null, calculateHeuristicValue(source, target));
        nodeWrappers.put(source, sourceWrapper);
        queue.add(sourceWrapper);
        
        while (!queue.isEmpty()) {

            AStarNodeWrapper currentNodeWrapper = queue.pollFirst();
       
            Node currentNode = currentNodeWrapper.getNode();
            
            visitedNodes.add(currentNode);
          
            // Build the path if target was reached
            if (currentNode.equals(target)) {
                buildPath(currentNodeWrapper);                
                return;
            }

            Step step = new Step();
            step.setCurrentNode(currentNode);
            step.setCurrentNodeMarked(true);

            //Get adjacent edges
            Set<Edge> adjacentEdges = graph.getAdjacentEdges(currentNode);
 
            // Iterate over all adjacent edges
            for (Edge adjacentEdge : adjacentEdges) {
         
                Node adjacentNode = adjacentEdge.getEndNode();
             
                // Ignore adjacent node if shortest path already found
                if (visitedNodes.contains(adjacentNode))
                    continue;

                step.getCheckedEdges().add(adjacentEdge);
                
                // Calculate total cost from source to adjacent node via current node
                double newCost = currentNodeWrapper.getCost() + adjacentEdge.getWeight();

                // Adjacent node not yet discovered
                AStarNodeWrapper adjacentNodeWrapper = nodeWrappers.get(adjacentNode);
                if (adjacentNodeWrapper == null) {
                    adjacentNodeWrapper = new AStarNodeWrapper(adjacentNode, newCost, currentNodeWrapper,
                                                                                    calculateHeuristicValue(adjacentNode, target));
                    nodeWrappers.put(adjacentNode, adjacentNodeWrapper);
                    queue.add(adjacentNodeWrapper);
                    
                    step.getNewCheckedCostValues().add(newCost);
                }                   

                // Adjacent node discovered, but total cost via current node is lower
                // --> Update cost and predecessor
                else if (newCost < adjacentNodeWrapper.getCost()) {
                    
                    // The position in the TreeSet won't change automatically
                    // --> Remove and reinsert the node
                    queue.remove(adjacentNodeWrapper);

                    adjacentNodeWrapper.setCost(newCost);
                    adjacentNodeWrapper.setPredecessor(currentNodeWrapper);

                    queue.add(adjacentNodeWrapper);
                    
                    step.getNewCheckedCostValues().add(newCost);
                }
                
                else {
                    step.getNewCheckedCostValues().add(adjacentNodeWrapper.getCost());
                }
                
            }
            
            steps.add(step);
        }

        // All nodes were visited but the target was not found
        result = null;
    }

    // Calculate heuristic value by using Euclidean distance
    private double calculateHeuristicValue(Node node, Node target) {
        double distanceX = target.getNodeFX().getCenterX() - node.getNodeFX().getCenterX();
        double distanceY = target.getNodeFX().getCenterY() - node.getNodeFX().getCenterY();
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }
    
}