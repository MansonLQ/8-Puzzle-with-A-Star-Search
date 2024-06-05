package EightPuzzleSolver;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        System.out.println("[1] Random\n[2] Manual");
        System.out.print("Select Input Method: ");
        int inputMethod = scan.nextInt();
        scan.nextLine();

        System.out.println();

        ArrayList<Integer> puzzle;

        if (inputMethod == 1) {
            puzzle = Puzzle.randomPuzzle();
        } else if (inputMethod == 2) {
            puzzle = Puzzle.manualPuzzle(scan);
        } else {
            scan.close();
            return;
        }

        scan.close();

        Node goal = SearchFunctions.searchAStar(puzzle, HeuristicFunctions::h1);
        SearchFunctions.displaySearchPath(goal);

        Double h1Time = SearchFunctions.getExecutionTime(puzzle, HeuristicFunctions::h1);
        Double h2Time = SearchFunctions.getExecutionTime(puzzle, HeuristicFunctions::h2);
        System.out.println("H1: " + h1Time + " ms");
        System.out.println("H2: " + h2Time + " ms");

    }

}
