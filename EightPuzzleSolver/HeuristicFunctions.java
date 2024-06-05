package EightPuzzleSolver;

import java.util.ArrayList;

interface Heuristic { // allow h1 and h2 to be passed in as parameters to searchAStar
    int calculate(ArrayList<Integer> puzzle);
}

public class HeuristicFunctions { // categorize h1 and h2 as a heuristic function
    public static int h1(ArrayList<Integer> puzzle) { // number of misplaced tiles
        int misplacedCount = 0; // initialize count

        for (int value : puzzle) { // iterate through each puzzle tile
            if (value != puzzle.indexOf(value)) { // if tile index does not match with value
                misplacedCount++; // increase count
            }
        }

        return misplacedCount;
    }

    public static int h2(ArrayList<Integer> puzzle) { // manhattan algorithm
        int sum = 0; // initialize sum to 0

        for (int value : puzzle) { // iterate through each puzzle tile
            int position = puzzle.indexOf(value);
            int goalPosition = value; // get the current tile and goal tile

            if (position != goalPosition) { // if current tile is not where it needs to be
                int columnPosition = position % 3;
                int columnGoal = goalPosition % 3;
                int xDifference = Math.abs(columnGoal - columnPosition); // calculate x difference

                int rowPosition = position / 3;
                int rowGoal = goalPosition / 3;
                int yDifference = Math.abs(rowPosition - rowGoal); // calculate y difference

                sum += xDifference;
                sum += yDifference; // add differences to sum

            }
        }
        return sum; // returns the sum of distances of all tiles from goal positions
    }
}