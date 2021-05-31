package Graph;

public class ShortestPathContext {
    
    private ShortestPathSolver  shortestPathSolver;
    
    public void setSolver(ShortestPathSolver shortestPathSolver) {
        this.shortestPathSolver = shortestPathSolver;
    }
    
    public ShortestPathSolver solve(Graph graph, Node source, Node target) {
        shortestPathSolver.run(graph, source, target);
        return shortestPathSolver;
    }
    
}
