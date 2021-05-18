/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphs;

import java.util.HashMap;

/**
 *
 * @author Wind
 */
public class Graph {
    public static Edge[] getEdges(int numEdges, int[][] graph) {
        Edge[] edgeList = new Edge[numEdges];
 
        int idx = 0;
        for (int u = 1; u < graph.length && idx < edgeList.length; u++) {
        for (int v = 1; v < graph[u].length && idx < edgeList.length; v++) {
                edgeList[idx] = new Edge(u, v);
                idx = graph[u][v] != 0 ? idx + 1 : idx;
            }
        }
 
        return edgeList;
    }
    
    public static void bellmanford(int src, int nodes, int edges, int[][] graph) {
        /*
         * we use hashmap to store the nodes of every vertex,
         * (vertex name, node) will be the key, value pair
         */
        HashMap<Integer, Node> nodesMap = new HashMap<>();
        for (int i = 1; i < graph.length; i++) {
            /*
             * initialize the shortest distance of the every
             *  vertex equal to Infinity as for this vertex 
             *  the shortest distance is yet to be calculated,
             *  and initialize an empty path. But if the vertex 
             *  is source vertex itself, then the shortest distance
             *  for it will be 0 and path will be initialized with 
             *  vertex name.
             */
            nodesMap.put(i, new Node(i, i == src ? 0 : (int) 1e9, i == src ? 
                Integer.toString(i) : ""));
        }
 
        /* outer loop will run for vertices - 1 times */
        for (int var = 1; var <= nodes - 1; var++) {
            /* running inner loop on the set of edges returned 
             * from getEdges function */
            for (Edge e : getEdges(edges, graph)) {
                Node u = nodesMap.get(e.getU());
                Node v = nodesMap.get(e.getV());
 
                /*
                 * the basic condition for updation of shortest
                 * distance of any node as mentioned in the above 
                 * discussion.
                 */
                if (v.getDist() > u.getDist() + graph[u.getVal()][v.getVal()]) {
                    v.setDist(u.getDist() + graph[u.getVal()][v.getVal()]);
                    v.setPath(u.getPath() + "->" + Integer.toString(v.getVal())); 
                }
            }
        }
 
        /*
         * one more loop in the random set of edges to detect if
         *  there is any negative cycle or not
         */
        for (Edge e : getEdges(edges, graph)) {
            Node u = nodesMap.get(e.getU());
            Node v = nodesMap.get(e.getV());
 
            /*
             * if we still are able to find shorted distance
             * this simply means that there is a negative cycle
             * for sure and hence we return from the function as
             * shortest distance for every vertex from source can
             * not be found for such graph as we can get even 
             * shorter distance by looping once again in that
             * negative cycle.
             */
            if (v.getDist() > u.getDist() + graph[u.getVal()][v.getVal()]) {
                System.out.println("Negative Cycle Detected");
                return;
            }
        }

        for (int node : nodesMap.keySet()) {
            System.out.println(nodesMap.get(node));
        }

    }
}
