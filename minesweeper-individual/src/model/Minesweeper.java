package model;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Minesweeper class that takes in pre-formatted input and translates it to a board complete
 * with hints while keeping the mines in place. Outputs to a txt file specified on the command line.
 * 
 * 
 * @author Ethan Moore
 * @version 1.0
 */
public final class Minesweeper {

    /** The maximum size limit for the minesweeper board. */
    static final int MAX_SIZE = 100;
    /** The number of neighbors in a given square. */
    static final int NUMBER_OF_DIRECTIONS = 8;
    /** Scanner used to pass in and output information. */
    private static Scanner scanner = new Scanner(System.in);
    
    /** Private constructor to prohibit instantiation. */
    private Minesweeper() { }

    /**
     * Main method that runs the Minesweeper translation. It reads minefield input from 
     * a file (using file redirection), processes the minefields by adding hints 
     * to each, and prints the result. The process continues until a termination 
     * condition (input of 0 0) is reached.
     * 
     * @param theArgs Command-line arguments (not used).
     */
    public static void main(final String[] theArgs) {

        int fieldCount = 1;

        while (true) {
            // Read the mine field
            final char[][] minefield = readMinefield();

            // Check for termination (when n = 0 and m = 0)
            if (minefield == null) {
                break;
            }

            // Produce the final mine field
            produceHints(minefield);

            // Print the processed mine field
            printMinefield(minefield, fieldCount);

            // Increment the field number for the next mine field
            fieldCount++;
        }
    }

    /**
     * Reads the dimensions and minefield data from input and constructs a buffered 
     * minefield where the outer layer is filled with '0' to simplify neighbor 
     * checking. The method returns the minefield with buffered rows and columns.
     * 
     * @return A 2D char array representing the buffered minefield, or null if 
     *         the termination condition is met (n = 0, m = 0) or invalid input 
     *         dimensions are provided.
     */
    public static char[][] readMinefield() {
        // Check if there is an integer available for rows
        if (!scanner.hasNextInt()) {
            return null;  // No more input, terminate processing
        }
        
        // Read dimensions (n = rows, m = columns)
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        
        // Termination condition (when n = 0 and m = 0)
        if (n == 0 && m == 0) {
            return null;  // End of input
        }

        // Consume the newline after the integers
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        // Create a 2D char array with an additional 2 rows and 2 columns for the buffer
        char[][] paddedMinefield = new char[n + 2][m + 2];

        // Initialize the padded minefield buffer with '0' instead of spaces
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < m + 2; j++) {
                paddedMinefield[i][j] = '0';  // Fill buffer with '0'
            }
        }

        // Read the minefield row by row and place it inside the padded array (starting at index 1,1)
        for (int i = 1; i <= n; i++) {
            if (scanner.hasNextLine()) {
                String row = scanner.nextLine();  // Read each row as a string
                for (int j = 1; j <= m; j++) {
                    char currentChar = row.charAt(j - 1);
                    if (currentChar == '.') {
                        // Replace safe squares with '0'
                        paddedMinefield[i][j] = '0';
                    } else {
                        // Keep mines as '*'
                        paddedMinefield[i][j] = currentChar;
                    }
                }
            } else {
                // If no more lines, return null or throw an exception if the format is wrong
                throw new NoSuchElementException("Expected more rows for the minefield but found none.");
            }
        }

        return paddedMinefield;  // Return the padded minefield
    }


    /**
     * Processes the minefield and updates it with hint values. Each hint represents
     * the number of mines in the surrounding 8 cells. It skips over the buffer and 
     * increments neighbors for each mine ('*') found.
     * 
     * @param theMinefield A 2D char array representing the buffered minefield.
     */
    public static void produceHints(final char[][] theMinefield) {

        final int n = theMinefield.length - 2;
        final int m = theMinefield[0].length - 2;

        // Iterate over the mine field, skipping the buffer
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (theMinefield[i][j] == '*') {
                    incrementNeighbors(theMinefield, i, j);
                }
            }
        }
    }

    /**
     * Increments the hint values for all valid neighboring cells around a mine ('*'). 
     * The surrounding 8 cells are incremented by 1 for each mine in the corresponding 
     * position.
     * 
     * @param theMinefield A 2D char array representing the buffered minefield.
     * @param theI The row index of the current mine in the minefield.
     * @param theJ The column index of the current mine in the minefield.
     */
    public static void incrementNeighbors(final char[][] theMinefield, final int theI, final int theJ) {

        // Define all 8 possible directions
        final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        // Loop through all of the directions
        for (int k = 0; k < NUMBER_OF_DIRECTIONS; k++) {
            final int ni = theI + dx[k];
            final int nj = theJ + dy[k];

            // Increment the neighbor if it's not a mine
            if (theMinefield[ni][nj] != '*') {
                theMinefield[ni][nj] = (char) (theMinefield[ni][nj] + 1);
            }
        }
    }

    /**
     * Prints the minefield to the console, excluding the buffer rows and columns. 
     * This method is used to output the final processed minefield with hints after 
     * all mines have been processed.
     * 
     * @param theMinefield A 2D char array representing the buffered minefield.
     * @param theFieldNumber The number of the field being printed (for labeling).
     */
    static void printMinefield(final char[][] theMinefield, final int theFieldNumber) {
        final int n = theMinefield.length - 2;
        final int m = theMinefield[0].length - 2;

        System.out.println("Field #" + theFieldNumber + ":");

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                System.out.print(theMinefield[i][j]);
            }
            System.out.println();
        }

        System.out.println();
    }
}
