package Graphs;

import java.util.HashMap;

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
        HashMap<Integer, Node> nodesMap = new HashMap<>();
        for (int i = 1; i < graph.length; i++) {
            nodesMap.put(i, new Node(i, i == src ? 0 : (int) 1e9, i == src ? 
                Integer.toString(i) : ""));
        }

        for (int var = 1; var <= nodes - 1; var++) {

            for (Edge e : getEdges(edges, graph)) {
                Node u = nodesMap.get(e.getU());
                Node v = nodesMap.get(e.getV());
 
                if (v.getDist() > u.getDist() + graph[u.getVal()][v.getVal()]) {
                    v.setDist(u.getDist() + graph[u.getVal()][v.getVal()]);
                    v.setPath(u.getPath() + "->" + Integer.toString(v.getVal())); 
                }
            }
        }

        for (Edge e : getEdges(edges, graph)) {
            Node u = nodesMap.get(e.getU());
            Node v = nodesMap.get(e.getV());

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
