package EightPuzzleSolver;

import java.util.ArrayList;

public class Node { // define a node class to store puzzle states
    int costToNode; // g cost
    ArrayList<Integer> state;
    int heuristicValue; // h cost
    Node parent; // keep track of parent for displaying path

    public Node(ArrayList<Integer> state, int heuristicValue, int costToNode, Node parent) { // constructor
        this.costToNode = costToNode;
        this.state = state;
        this.heuristicValue = heuristicValue;
        this.parent = parent;
    }
}