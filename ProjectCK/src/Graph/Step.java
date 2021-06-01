package Graph;

import java.util.ArrayList;
import java.util.List;

public class Step {
    
    private Node currentNode;
    private boolean currentNodeMarked;
    private List<Edge> checkedEdges;
    private List<Double> newCheckedCostValues;

    public Step() {
        currentNode = null;
        currentNodeMarked = false;
        checkedEdges = new ArrayList<>();
        newCheckedCostValues = new ArrayList<>();
    }
    
    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public boolean isCurrentNodeMarked() {
        return currentNodeMarked;
    }

    public void setCurrentNodeMarked(boolean currentNodeMarked) {
        this.currentNodeMarked = currentNodeMarked;
    }

    public List<Edge> getCheckedEdges() {
        return checkedEdges;
    }

    public void setCheckedEdges(List<Edge> checkedEdges) {
        this.checkedEdges = checkedEdges;
    }

    public List<Double> getNewCheckedCostValues() {
        return newCheckedCostValues;
    }

    public void setNewCheckedCostValues(List<Double> newCheckedCostValues) {
        this.newCheckedCostValues = newCheckedCostValues;
    }

}
