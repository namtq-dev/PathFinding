package Graph;

import java.util.ArrayList;
import java.util.List;

public class Step {

    private Node currentNode;
    boolean currentNodeMarked;
    List<Edge> checkedEdges;
    List<Double> newCheckedNodeValues;

    public Step() {
        currentNode = null;
        currentNodeMarked = false;
        checkedEdges = new ArrayList<Edge>();
        newCheckedNodeValues = new ArrayList<Double>();
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node node) {
        this.currentNode = node;
    }

    public boolean isCurrentNodeMarked() {
        return currentNodeMarked;
    }

    public void setCurrentNodeMarked() {
        this.currentNodeMarked = true;
    }

    public List<Edge> getCheckedEdges() {
        return checkedEdges;
    }

    public List<Double> getNewCheckedNodeValues() {
        return newCheckedNodeValues;
    }

}
