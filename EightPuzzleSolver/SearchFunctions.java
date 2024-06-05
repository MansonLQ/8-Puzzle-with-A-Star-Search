package EightPuzzleSolver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class SearchFunctions {
    private static ArrayList<ArrayList<Integer>> getNeighbors(ArrayList<Integer> puzzle) {
        ArrayList<ArrayList<Integer>> neighbors = new ArrayList<>(); // return ArrayList containing neighbor puzzles

        int emptyTileIndex = puzzle.indexOf(0); // get the index of the empty tile

        int columnOfEmptyTile = emptyTileIndex % 3; // get collumn of empty tile to calculate possible moves

        int[] possibleMoves = new int[] {}; // store the possible moves of a tile, validate with isValidMove function

        if (columnOfEmptyTile == 0) { // empty tiles in the first column can move up down or right
            possibleMoves = new int[] { 3, -3, 1 };
        }

        if (columnOfEmptyTile == 1) { // empty tiles in second column are free to move in all directions
            possibleMoves = new int[] { 3, -3, 1, -1 };
        }

        if (columnOfEmptyTile == 2) { // empty tiles in last column can move up down or left
            possibleMoves = new int[] { 3, -3, -1 };
        }

        for (int move : possibleMoves) { // iterate through all moves, moves cannot be empty

            if (isValidMove(move, emptyTileIndex)) { // if empty tile is able to make the move
                neighbors.add(swapTiles(new ArrayList<>(puzzle), emptyTileIndex, emptyTileIndex + move));
            } // make new copy of parent puzzle and swap the tiles to create neighbor puzzle
        }

        return neighbors; // return ArrayList of all neighbor puzzles
    }

    private static boolean isValidMove(int move, int emptyTileIndex) {
        int tileToSwitchWithIndex = emptyTileIndex + move; // original empty tile index + move = new empty tile position

        if (tileToSwitchWithIndex < 0 || tileToSwitchWithIndex > 8) {
            return false; // new empty tile index cannot be out of range of the puzzle (handles invalid
                          // rows)
        }

        if ((emptyTileIndex % 3 == 0) && (tileToSwitchWithIndex == emptyTileIndex - 1)) {
            return false; // empty tile cannot move to the left if it is already in the first column
        }

        if (emptyTileIndex % 3 == 2 && tileToSwitchWithIndex == emptyTileIndex + 1) {
            return false; // empty tile cannot move to the right if it is in the third column (last)
        }

        return true; // if new empty tile position passes all cases, it is a valid move
    }

    private static ArrayList<Integer> swapTiles(ArrayList<Integer> puzzle, int index1, int index2) {
        int saveIndex1Value = puzzle.get(index1); // create variabe to store index1 value
        puzzle.set(index1, puzzle.get(index2)); // set the index1 to value of index2
        puzzle.set(index2, saveIndex1Value); // set the index2 to value of index1

        return puzzle;
    }

    public static Double getExecutionTime(ArrayList<Integer> puzzle, Heuristic heuristic) {
        long startTime = System.nanoTime(); // start timer
        searchAStar(puzzle, heuristic); // execute A* search with heuristic function of choice
        long endTime = System.nanoTime(); // stop timer
        long executionTime = (endTime - startTime); // calculate execution time
        Double executionTimeDouble = executionTime / 1000000.0; // convert ns to ms

        return executionTimeDouble;
    }

    public static void displaySearchPath(Node goalNode) { // show all consecutive steps to solving a puzzle
        Stack<Node> path = new Stack<>(); // create stack to get correct path order

        Node currentNode = goalNode;

        path.push(currentNode); // push goal node to stack, it will be the last in the path

        while (currentNode.parent != null) { // traverse to root node from goal node
            currentNode = currentNode.parent; // move up a node
            path.push(currentNode); // push node to stack
        }

        int step = 0; // initialize step counter

        while (!path.empty()) {
            if (step == 0) {
                System.out.println("Puzzle: "); // step zero is to display entered puzzle
            } else {
                System.out.println("Step " + step + ": ");
            }

            currentNode = path.pop(); // get top node of the stack
            Puzzle.displayPuzzle(currentNode.state); // display the top node

            System.out.println();

            step++; // increase step counter
        }

    }

    public static Node searchAStar(ArrayList<Integer> puzzle, Heuristic heuristic) {
        int searchCost = 0; // initialize searchCost counter

        HashMap<String, Node> visited = new HashMap<>(); // hashmap to keep track of visited nodes
        // priority queue sorts nodes based on int value of g cost + h cost
        PriorityQueue<Node> frontier = new PriorityQueue<>(
                Comparator.comparingInt(n -> n.costToNode + n.heuristicValue));

        int rootHeuristic = heuristic.calculate(puzzle); // get heuristic of root

        Node rootNode = new Node(puzzle, rootHeuristic, 0, null);
        frontier.add(rootNode); // add the root node to frontier
        searchCost++; // increase search cost everytime node is added to frontier

        while (!frontier.isEmpty()) { // iterate through frontier nodes from least to greatest cost
            Node currentNode = frontier.poll();

            String stateString = Puzzle.stringifyPuzzle(currentNode.state); // convert puzzle to stirng

            if (stateString.equals("012345678")) { // found goal node
                System.out.println("Search Cost: " + searchCost); // output search cost

                return currentNode; // return goal node
            }

            visited.put(stateString, currentNode); // mark current node as visited

            ArrayList<ArrayList<Integer>> neighborsArrayList = getNeighbors(currentNode.state);

            for (ArrayList<Integer> neighbor : neighborsArrayList) { // for each neighbor of currentNode
                int neighborHeuristic = heuristic.calculate(neighbor); // get heuristic of neighbor
                int costToNeighbor = currentNode.costToNode + 1; // cost to neighbor is +1 of currentNode
                // create a node for the neighbor
                Node neighborNode = new Node(neighbor, neighborHeuristic, costToNeighbor, currentNode);
                // convert neighbor puzzle to string to check if stirng exists in visited
                // dicitonary
                String neighborStateString = Puzzle.stringifyPuzzle(neighborNode.state);

                if (!visited.containsKey(neighborStateString)) { // if nieghbor node has not been visited
                    frontier.add(neighborNode); // add neighbor to the frontier
                    searchCost++; // increase search cost
                }
            }
        }
        return null; // no solution found
    }
}
