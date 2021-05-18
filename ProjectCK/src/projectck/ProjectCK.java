/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectck;

import java.util.Scanner;

/**
 *
 * @author Wind
 */
public class ProjectCK {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Numbers of nodes in the graph: ");
        int nodes = sc.nextInt();
        int[][] graph = new int[nodes+1][nodes+1];
        System.out.println("Numbers of edges in the graph: ");
        int numEdges = sc.nextInt();
        for (int i = 0; i < numEdges; i++) {
            System.out.println("Edge " + (i+1) + ":");
            System.out.println("1st vertex: ");
            int u = sc.nextInt();
            System.out.println("2nd vertex: ");
            int v = sc.nextInt();
            System.out.println("Weight of the edge: ");
            int w = sc.nextInt();
            graph[u][v] = w;
        }
        System.out.println("Choose the start node: ");
        int start = sc.nextInt();
        Graphs.Graph.bellmanford(start, nodes, numEdges, graph);
    }
    
}
