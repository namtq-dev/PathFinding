package Graph;

import GraphFX.*;
import java.util.List;

public class Main_other {
    
    public static void main(String args[ ]) {
        Graph graph = new Graph();
        
        Node a = new Node(null);
        Node b = new Node(null);
        Node c = new Node(null);
        Node d = new Node(null);
        Edge e = new Edge(a,b,1.0,null);
        Edge f = new Edge(a,d,2.0,null);
        
        graph.addNode(c);
        graph.addEdge(e);
        graph.addEdge(f);
        

        for( Edge i : graph.getAdjacentEdges(a) )
            System.out.print(i.getEndNode().getId() + " ");
        
//        for ( Edge i : graph.getAllEdges() )
//            System.out.println(i.getStartNode().getId() + "->" + i.getEndNode().getId() + " ");
//        for( Node i : graph.getAllNodes() )
//            System.out.println(i.getId() + " ");

//        System.out.println(graph.getEdgeValue(e));
        
        ShortestPathSolver test = new DijkstraAlgorithm();
        test.run(graph, a, d);
        if(test.result != null) {
            System.out.printf("Shortest path from %s to %s = ", a.getId(), d.getId());
            System.out.println(test.result.toString());
        }
        else
            System.out.printf("No shortest path was found");
               
    }
}
