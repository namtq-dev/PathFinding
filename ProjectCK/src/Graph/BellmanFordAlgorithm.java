package Graph;

import com.google.common.graph.*;
import java.util.*;

public class BellmanFordAlgorithm extends ShortestPathSolver{
    
   /**
   * Finds the shortest path from {@code source} to {@code target}.
   *
   * @param graph the graph
   * @param source the source node
   * @param target the target node
   * @return the shortest path; or {@code null} if no path was found
   * @throws IllegalArgumentException if a negative cycle was discovered
   */
    public void run(Graph graph, Node source, Node target) {

        

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
