package Graph;

import java.util.List;

public class Step {
    
    private Node currentNode;
    boolean currentNodeMarked;
    List<Edge> checkedEdges;
    List<Double> newCheckedNodeValues;

    public Node getCurrentNode() {
        return currentNode;
    }

    public boolean isCurrentNodeMarked() {
        return currentNodeMarked;
    }

    public List<Edge> getCheckedEdges() {
        return checkedEdges;
    }

    public List<Double> getNewCheckedNodeValues() {
        return newCheckedNodeValues;
    }

}
