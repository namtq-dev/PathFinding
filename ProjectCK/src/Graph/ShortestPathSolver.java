package Graph;

import java.util.HashSet;
import java.util.List;

public abstract class ShortestPathSolver {
    
    protected List<Step> steps;
    protected List<Node> result;
    
    public abstract void run(Graph graph, Node source, Node target);
    public HashSet<NodeWrapper> initialize(Graph graph, Node source, Node target) {
        HashSet<NodeWrapper> nodeWrappers = new HashSet<>();
        ///
        ///
        ///
        return nodeWrappers;
    }
    
}
