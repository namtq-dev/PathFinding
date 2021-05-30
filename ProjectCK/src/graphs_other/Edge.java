package Graphs;

public class Edge {
    int u;
    int v;
    int w;

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }
 
    public Edge(int u, int v) {
        this.u = u;
        this.v = v;
    }
 
    @Override
    public String toString() {
        return this.getU() + " " + this.getV();
    }
}