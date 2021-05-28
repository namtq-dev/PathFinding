package Graphs;

public class Node {
    int val;
    int dist;
    String path;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
 
    
    public Node(int val, int dist, String path) {
        this.val = val;
        this.dist = dist;
        this.path = path;
    }
    
    @Override
    public String toString() {
        return "Distance of node " + this.getVal() + " from source is " + this.getDist() + " and path followed is " + this.getPath();
    }
}
