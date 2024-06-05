package EightPuzzleSolver;

import java.util.ArrayList;

public class Node { // define a node class to store puzzle states
    private int costToNode; // g cost
    private int heuristicValue; // h cost
    private ArrayList<Integer> state;
    private Node parent; // keep track of parent for displaying path

    int searchCost; // store search cost of goalNode

    public Node(ArrayList<Integer> state, int heuristicValue, int costToNode, Node parent) { // constructor
        this.costToNode = costToNode;
        this.state = state;
        this.heuristicValue = heuristicValue;
        this.parent = parent;
    }

    public int getGCost() { // return g cost
        return costToNode;
    }

    public int getFCost() { // return f cost, which is g cost + h cost
        return costToNode + heuristicValue;
    }

    public ArrayList<Integer> getPuzzleState() { // return puzzle
        return state;
    }

    public Node getParent() { // return parent of node
        return parent;
    }
}
