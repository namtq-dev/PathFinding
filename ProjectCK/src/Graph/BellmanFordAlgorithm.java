package Graph;

import com.google.common.graph.*;
import java.util.*;

public class BellmanFordAlgorithm {
    
   /**
   * Finds the shortest path from {@code source} to {@code target}.
   *
   * @param graph the graph
   * @param source the source node
   * @param target the target node
   * @return the shortest path; or {@code null} if no path was found
   * @throws IllegalArgumentException if a negative cycle was discovered
   */
    public static List<Node> findShortestPath(
        ValueGraph<Node, Double> graph, Node source, Node target) {

        
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
