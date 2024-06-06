package EightPuzzleSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Puzzle {
    public static ArrayList<Integer> randomPuzzle() {
        ArrayList<Integer> puzzle = new ArrayList<>(); // puzzles are stored in ArrayList as ints

        for (int i = 0; i < 9; i++) {
            puzzle.add(i); // add numbers 0-8 in the puzzle
        }

        boolean solveable = false;
        while (!solveable) { // keep shuffling puzzle until it is solvable
            Collections.shuffle(puzzle); // shuffles the puzzle
            if (isPuzzleSolvable(puzzle)) { // ensures puzzle is solvable
                solveable = true;
            }
        }

        return puzzle;
    }

    public static ArrayList<Integer> manualPuzzle(Scanner scan) { // function allows user to enter puzzle
        ArrayList<Integer> puzzle = new ArrayList<>(); // create ArrayList to store puzzle
        boolean solvable = false;

        while (!solvable) { // will keep prompting user to enter a valid puzzle
            puzzle = new ArrayList<>(); // create new ArrayList in case previous one is not solvable
            System.out.print("Enter a puzzle: ");
            String manualPuzzle = scan.nextLine();
            System.out.println();

            String[] puzzleArray = {};

            if (manualPuzzle.length() == 9) { // accept inputs like "012345678" or "0 1 2 3 4 5 6 7 8"
                puzzleArray = manualPuzzle.split("");
            } else if (manualPuzzle.length() == 17) {
                puzzleArray = manualPuzzle.split(" "); // splits user input into array
            } else {
                System.out.println("Please enter a correctly formatted puzzle...");
                continue; // ask user for another puzzle
            }

            for (String stringNumber : puzzleArray) { // iterate through array
                int number = Integer.parseInt(stringNumber);
                puzzle.add(number); // add all integers to puzzle ArrayList
            }

            if (!isPuzzleSolvable(puzzle)) { // check if puzzle is no solvable
                System.out.println("Entered Puzzle is not solvable.");
            } else {
                solvable = true; // if solvable, return puzzle
            }
        }

        return puzzle;
    }

    public static void displayPuzzle(ArrayList<Integer> puzzle) { // function to print puzzle as grid
        String puzzleString = stringifyPuzzle(puzzle); // transform ArrayList to string ex: "012345678"

        for (int i = 0; i < puzzleString.length(); i++) {
            char value = puzzleString.charAt(i); // get each char at string index

            if (i == 2 || i == 5 || i == 8) { // new rows start after index 2, 5, and 8
                System.out.print(value + "\n");
            } else {
                System.out.print(value + " ");
            }
        }
    }

    public static String stringifyPuzzle(ArrayList<Integer> puzzle) { // transform ArrayList to string
        String puzzleString = puzzle.toString().replace(", ", ""); // remove toString() spaces
        puzzleString = puzzleString.substring(1, puzzleString.length() - 1); // remove toString() brackets

        return puzzleString; // return puzzle strings in the form "012345678"
    }

    private static boolean isPuzzleSolvable(ArrayList<Integer> puzzle) {
        int inversionCount = 0; // keep track of number of inversions

        for (int i = 0; i < 9; i++) { // iterate through puzzle
            int value = puzzle.get(i); // value

            for (int j = i + 1; j < 9; j++) { // iterate through puzzle after previous puzzle value
                int nextValue = puzzle.get(j); // rest of the puzzle values

                if (value != 0 && nextValue != 0 && value > nextValue) { // skip if values are 0
                    inversionCount++; // if larger values show up before smaller values, add an inversion

                }
            }
        }
        return inversionCount % 2 == 0; // even number of inversions mean puzzle can be solved
    }

    public static void generateDepthPuzzles(int depth, int total) {
        int count = 0; // generate x amount of puzzles with a specific solution depth
        while (count < total) {
            ArrayList<Integer> puzzle = randomPuzzle(); // get a random puzzle

            Node goalNode = SearchFunctions.searchAStar(puzzle, HeuristicFunctions::h1);
            int solutionDepth = SearchFunctions.displaySearchPath(goalNode); // collect solution depth

            while (solutionDepth != depth) { // if the solution depth is desired
                puzzle = randomPuzzle(); // create a new random puzzle and get its solution depth
                goalNode = SearchFunctions.searchAStar(puzzle, HeuristicFunctions::h1);
                solutionDepth = SearchFunctions.displaySearchPath(goalNode);

            }

            System.out.println(stringifyPuzzle(puzzle)); // display the puzzle in a concise string
            count++; // increase count to generate the next valid puzzle
        }

    }
}
