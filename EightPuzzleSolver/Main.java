package EightPuzzleSolver;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        System.out.println("[1] Random\n[2] Manual\n[3] Performance");
        System.out.print("Select Input Method: ");
        int inputMethod = scan.nextInt();
        scan.nextLine();

        System.out.println();

        ArrayList<Integer> puzzle;

        if (inputMethod == 1) { // generate a random puzzle
            puzzle = Puzzle.randomPuzzle();
        } else if (inputMethod == 2) {
            puzzle = Puzzle.manualPuzzle(scan); // input random puzzle
        } else {
            scan.close();
            String[] files = { "Length4.txt", "Length8.txt", "Length12.txt", "Length16.txt", "Length20.txt" };
            // read each file
            int solutionDepth = 4; // each file increases solution depth by 4
            for (String file : files) {
                double[] averages = aStarAverages(file);
                // output each average result
                System.out.println("Solution Depth: " + solutionDepth);
                System.out.println("Average H1 - Search Cost: " + averages[0] + " in " + averages[2] + " ms");
                System.out.println("Average H2 - Search Cost: " + averages[1] + " in " + averages[3] + " ms");
                System.out.println();
                solutionDepth += 4;
            }

            return;
        }

        scan.close();

        Node goal1 = SearchFunctions.searchAStar(puzzle, HeuristicFunctions::h1);
        Node goal2 = SearchFunctions.searchAStar(puzzle, HeuristicFunctions::h2);
        int solutionDepth = SearchFunctions.displaySearchPath(goal1); // visualize path

        Double h1Time = SearchFunctions.getExecutionTime(puzzle, HeuristicFunctions::h1);
        Double h2Time = SearchFunctions.getExecutionTime(puzzle, HeuristicFunctions::h2);
        // compare search costs and speeds of heuristic functions
        System.out.println("Solution Depth: " + solutionDepth);
        System.out.println("Search Cost H1: " + goal1.searchCost + " nodes generated in " + h1Time + " ms");
        System.out.println("Search Cost H2: " + goal2.searchCost + " nodes generated in " + h2Time + " ms");

    }

    public static double[] aStarAverages(String fileName) { // function to test A Star seach at different depths
        int totalH1SearchCost = 0;
        int totalH2SearchCost = 0;
        double totalH1Time = 0;
        double totalH2Time = 0;
        int puzzleCount = 0; // return averge seach costs and times

        try {
            File file = new File(fileName); // read each file, every line contains a valid puzzle

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" "); // convert each line to an ArrayList puzzle
                    ArrayList<Integer> puzzle = new ArrayList<>();
                    for (String part : parts) {
                        puzzle.add(Integer.parseInt(part)); // add puzzle tile into an ArrayList
                    }

                    puzzleCount++; // increase puzzle count to calculate averages
                    // perform A Star searches
                    Node goal1 = SearchFunctions.searchAStar(puzzle, HeuristicFunctions::h1);
                    Node goal2 = SearchFunctions.searchAStar(puzzle, HeuristicFunctions::h2);
                    // get execution time of each search
                    double h1Time = SearchFunctions.getExecutionTime(puzzle, HeuristicFunctions::h1);
                    double h2Time = SearchFunctions.getExecutionTime(puzzle, HeuristicFunctions::h2);

                    totalH1SearchCost += goal1.searchCost;
                    totalH2SearchCost += goal2.searchCost;
                    totalH1Time += h1Time;
                    totalH2Time += h2Time; // calculate the total values for each variable
                }
            }
        } catch (Exception e) { // catch error in case of error reading in file
            e.printStackTrace();
        }
        // calculate each average, and its rounded value to return
        double averageH1SearchCost = (totalH1SearchCost / (double) puzzleCount);
        double roundedAverageH1SearchCost = Math.round(averageH1SearchCost * 1000.0) / 1000.0;

        double averageH2SearchCost = (totalH2SearchCost / (double) puzzleCount);
        double roundedAverageH2SearchCost = Math.round(averageH2SearchCost * 1000.0) / 1000.0;

        double averageH1Time = (totalH1Time / puzzleCount);
        double roundedAverageH1Time = Math.round(averageH1Time * 1000.0) / 1000.0;

        double averageH2Time = (totalH2Time / puzzleCount);
        double roundedAverageH2Time = Math.round(averageH2Time * 1000.0) / 1000.0;

        return new double[] { roundedAverageH1SearchCost, roundedAverageH2SearchCost, roundedAverageH1Time,
                roundedAverageH2Time }; // return each value into a double Array
    }

}
