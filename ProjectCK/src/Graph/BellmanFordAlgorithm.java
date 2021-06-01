package Graph;

import java.util.*;

public class BellmanFordAlgorithm extends ShortestPathSolver{
    
    @Override
    public void run(Graph graph, Node source, Node target) {

        Map<Node, NodeWrapper> nodeWrappers = new HashMap<>();
        
        // Add all node wrappers
        for (Node node : graph.getAllNodes()) {
            Double initialCostFromStart = node.equals(source) ? 0 : Double.MAX_VALUE;
            NodeWrapper nodeWrapper = new NodeWrapper(node, initialCostFromStart, null);
            nodeWrappers.put(node, nodeWrapper);
        }
        
        // Iterate n-1 times + 1 time for the negative cycle detection
        int n = nodeWrappers.size();
        boolean lastIteration = false;
        for (int i = 0; i < n; i++) {
        
            // Last iteration for detecting negative cycle?
            if(i == n - 1)
                lastIteration = true;

            boolean atLeastOneChanged = false;
            
            for (Node currentNode : graph.getAllNodes()) {
                
                NodeWrapper currentNodeWrapper = nodeWrappers.get(currentNode);

                
                //Get adjacent edges
                Set<Edge> adjacentEdges = graph.getAdjacentEdges(currentNode);
 
                // Iterate over all adjacent edges
                for (Edge adjacentEdge : adjacentEdges) {
         
                    Step step = new Step();
                    step.setCurrentNode(currentNode);
                    step.setCurrentNodeMarked(false);
                
                    Node adjacentNode = adjacentEdge.getEndNode();
                    NodeWrapper adjacentNodeWrapper = nodeWrappers.get(adjacentNode);

                    step.getCheckedEdges().add(adjacentEdge);

                    // If no path to current node was found
                    if (currentNodeWrapper.getCost() == Double.MAX_VALUE) {                    
                        step.getNewCheckedCostValues().add(adjacentNodeWrapper.getCost());
                        continue;
                    }

                    // Calculate total cost from source to adjacent node via current node
                    double newCost = currentNodeWrapper.getCost() + adjacentEdge.getWeight();

                    // Cheaper path found?
                    //      + Regular iteration? --> update cost and predecessor
                    //      + Negative cycle detection? --> throw exception
                    if (newCost < adjacentNodeWrapper.getCost()) {
                        
                        // Throw IllegalArgumentException if a negative cycle was discovered
                        if (lastIteration) {
                            throw new IllegalArgumentException("Negative cycle detected");
                        }

                        adjacentNodeWrapper.setCost(newCost);
                        adjacentNodeWrapper.setPredecessor(currentNodeWrapper);
                        atLeastOneChanged = true;

                        step.getNewCheckedCostValues().add(newCost);
                    }

                    else {
                        step.getNewCheckedCostValues().add(adjacentNodeWrapper.getCost());
                    }
                    
                    steps.add(step);
                }        
            }
            
            // Optimization: terminate if nothing was changed
            if (!atLeastOneChanged) 
                break;
        }
    
        // Build the path if it was found
        NodeWrapper targetWrapper = nodeWrappers.get(target);
        if (targetWrapper.getPredecessor() != null) {
            buildPath(targetWrapper);
        } 
        else {
            result = null;
        }
        
    }
    
}
