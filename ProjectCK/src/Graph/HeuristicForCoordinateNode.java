package Graph;

import com.google.common.graph.*;
import java.util.function.Function;

public class HeuristicForCoordinateNode implements Function<CoordinateNode, Double>{
    
    private final double maxSpeed;
    private final CoordinateNode target;
    
   /**
   * Constructs the heuristic function for the specified graph and target node.
   *
   * @param graph the graph
   * @param target the target node
   */
    public HeuristicForCoordinateNode(
        ValueGraph<CoordinateNode, Double> graph, CoordinateNode target) {
    
        // We need the maximum speed possible on any path in the graph for the heuristic function to
        // calculate the cost for a euclidean distance
        this.maxSpeed = calculateMaxSpeed(graph);
        this.target = target;
    }
  
    /**
    * Calculates the maximum speed possible on any path in the graph. The speed of a path is
    * calculated as: euclidean distance between the path's nodes, divided by its cost.
    *
    * @param graph the graph
    * @return the maximum speed
    */
    private static double calculateMaxSpeed(ValueGraph<CoordinateNode, Double> graph) {
        return graph.edges().stream()
            .map(edge -> calculateSpeed(graph, edge))
            .max(Double::compare)
            .get();
    }

    /**
    * Calculates the speed on a path in the graph. The speed of a path is calculated as: euclidean
    * distance between the path's nodes, divided by its cost.
    *
    * @param graph the graph
    * @param edge the edge (= path)
    * @return the speed
    */
    private static double calculateSpeed(
      ValueGraph<CoordinateNode, Double> graph, EndpointPair<CoordinateNode> edge) {
        double euclideanDistance = calculateEuclideanDistance(edge.source(), edge.target());
        double cost = graph.edgeValue(edge).get();
        double speed = euclideanDistance / cost;

        System.out.println("Calculated speed from " + edge.source() + " to " + edge.target()
                + " : euclideanDistance = :  " + euclideanDistance + ", cost = " + cost + " --> speed = "
                + speed);  

        return speed;
    }

    public static double calculateEuclideanDistance(
        CoordinateNode source, CoordinateNode target) {
        double distanceX = target.getX() - source.getX();
        double distanceY = target.getY() - source.getY();
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    /**
    * Applies the heuristic function to the specified node.
    *
    * @param node the node
    * @return the minimum cost for traveling from the specified node to the target
    */
    @Override
    public Double apply(CoordinateNode node) {
        double euclideanDistance = calculateEuclideanDistance(node, target);
        double minimumCost = euclideanDistance / maxSpeed;

        System.out.println("Applied heuristic to node "+ node + ": euclideanDistance = " +
                        euclideanDistance + ", maxSpeed = " + maxSpeed +" --> minimumCost = " +
                        minimumCost);

        return minimumCost;
    }
  
}
