package Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ShortestPathSolver {
    
    protected List<Step> steps ;
    protected List<Node> result;
    
    public ShortestPathSolver() {
        steps = new ArrayList<>();
        result = new ArrayList<>();
    }
    
    public abstract void run(Graph graph, Node source, Node target);
    
    public void buildPath(NodeWrapper nodeWrapper) {
        
        while (nodeWrapper != null) {
            result.add(nodeWrapper.getNode());
            nodeWrapper = nodeWrapper.getPredecessor();
        }
        
        Collections.reverse(result);  
    }
    
    public List<Step> getSteps() {
        return steps;
    }

    public List<Node> getResult() {
        return result;
    }
    
}
