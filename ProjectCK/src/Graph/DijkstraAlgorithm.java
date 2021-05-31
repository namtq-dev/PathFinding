package Graph;

import com.google.common.graph.*;
import java.util.*;

public class DijkstraAlgorithm extends ShortestPathSolver {

    /**
    * Finds the shortest path from {@code source} to {@code target}.
    *
    * @param graph the graph
    * @param source the source node
    * @param target the target node
    * @return the shortest path; or {@code null} if no path was found
    */
    public void run(Graph graph, Node source, Node target) {
        
        TreeSet<Node> queue = new TreeSet<>();
        Set<Node> shortestPathFound = new HashSet<>();

// tao List<Step> steps = new List()
        
        // Add source to queue
        source.setCost(0);
        source.setPredecessor(null);
        queue.add(source);

        while (!queue.isEmpty()) {
// s = new Step( currn)            
            Node currentNode = queue.pollFirst();
// s.setCurrNode()            
            shortestPathFound.add(currentNode);

            // Have we reached the target? --> Build and return the path
            if (currentNode.equals(target))
                return buildPath(currentNode);

            //Get adjacent nodes
            Set<Node> neighbors = graph.adjacentNodes(currentNode);
 
            // Iterate over all neighbors
            for (Node neighbor : neighbors) {
//s.getEdges().addEdge()                
                //Ignore neighbor if it is startNode and currentNode is endNode
                if (graph.edgeValue(currentNode, neighbor).isEmpty())
                    continue;
                
                // Ignore neighbor if shortest path already found
                if (shortestPathFound.contains(neighbor))
                    continue;

                // Calculate total cost from start to neighbor via current node
                double cost = graph.edgeValue(currentNode, neighbor).get();
                double newCost = currentNode.getCost() + cost;

                // Neighbor not yet discovered?
                if (!queue.contains(neighbor)) {
                    neighbor.setCost(newCost);
                    neighbor.setPredecessor(currentNode);
 // s.getnewnodevalues                   
                    queue.add(neighbor);
                }

                // Neighbor discovered, but total cost via current node is lower?
                // --> Update cost and predecessor
                else if (newCost < neighbor.getCost()) {
                    
                    // The position in the TreeSet won't change automatically;
                    // we have to remove and reinsert the node.
                    // Because TreeSet uses compareTo() to identity a node to remove,
                    // we have to remove it *before* we change cost!
                    queue.remove(neighbor);
                    neighbor.setCost(newCost);
                    neighbor.setPredecessor(currentNode);
                    queue.add(neighbor);
                }
            }
            //s.setvisitd(currnode)
           // steps.add(s)
        }

        // All nodes were visited but the target was not found
        return null;
    }

    private static List<Node> buildPath(Node node) {
        
        List<Node> path = new ArrayList<>();
       
        while (node != null) {
            path.add(node);
            node = node.getPredecessor();
        }
        
        Collections.reverse(path);
        
        return path;
    }


  
    
}
