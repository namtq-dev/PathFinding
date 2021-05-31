package Graph;

import java.util.*;

public class AStarAlgorithm extends ShortestPathSolver {
    
    /**
    * Finds the shortest path from {@code source} to {@code target}.
    *
    * @param graph the graph
    * @param source the source node
    * @param target the target node
    */
    @Override
    public void run(Graph graph, Node source, Node target) {
        
        Map<Node, NodeWrapper> nodeWrappers = new HashMap<>();
        TreeSet<NodeWrapper> queue = new TreeSet<>();
        Set<Node> visitedNodes = new HashSet<>();
    
        // Add source to queue
        NodeWrapper sourceWrapper = new NodeWrapper(source, 0, null);
        nodeWrappers.put(source, sourceWrapper);
        queue.add(sourceWrapper);
        
        while (!queue.isEmpty()) {

            Step step = new Step();
            
            NodeWrapper currentNodeWrapper = queue.pollFirst();
       
            Node currentNode = currentNodeWrapper.getNode();
            
            visitedNodes.add(currentNode);
            
            step.setCurrentNode(currentNode);
            step.setCurrentNodeMarked(true);

            // Have we reached the target? --> Build the path
            if (currentNode.equals(target)) {
                buildPath(currentNodeWrapper);
                
                step.setCheckedEdges(null);
                step.setNewCheckedCostValues(null);
                steps.add(step);
                
                return;
            }

            //Get adjacent edges
            Set<Edge> adjacentEdges = graph.getAdjacentEdges(currentNode);
 
            // Iterate over all adjacent edges
            for (Edge adjacentEdge : adjacentEdges) {
         
                Node adjacentNode = adjacentEdge.getEndNode();
             
                // Ignore adjacent node if shortest path already found
                if (visitedNodes.contains(adjacentNode))
                    continue;

                step.getCheckedEdges().add(adjacentEdge);
                
                // Calculate total cost from start to adjacent node via current node
                double newCost = currentNodeWrapper.getCost() + graph.getEdgeValue(adjacentEdge);

                // Adjacent node not yet discovered?
                NodeWrapper adjacentNodeWrapper = nodeWrappers.get(adjacentNode);
                if (adjacentNodeWrapper == null) {
                    adjacentNodeWrapper = new NodeWrapper(adjacentNode, newCost, currentNodeWrapper);
                    nodeWrappers.put(adjacentNode, adjacentNodeWrapper);
                    queue.add(adjacentNodeWrapper);
                    
                    step.getNewCheckedCostValues().add(newCost);
                }                   

                // Neighbor discovered, but total cost via current node is lower?
                // --> Update cost and predecessor
                else if (newCost < adjacentNodeWrapper.getCost()) {
                    
                    // The position in the TreeSet won't change automatically;
                    // we have to remove and reinsert the node.
                    // Because TreeSet uses compareTo() to identity a node to remove,
                    // we have to remove it *before* we change cost!
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

}