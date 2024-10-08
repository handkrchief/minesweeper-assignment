package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Minesweeper;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

class MinesweeperTest {
    
    @Test
    void testReadMinefield() {
        // Simulate user input for a 2x2 minefield
        String input = "2 2\n.*\n*.\n";  // Correct input format
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Expected output after reading the input
        char[][] expected = {
            {'0', '0', '0', '0'},  // Buffer row
            {'0', '0', '*', '0'},  // Row 1 of the minefield (buffer + actual row)
            {'0', '*', '0', '0'},  // Row 2 of the minefield (buffer + actual row)
            {'0', '0', '0', '0'}   // Buffer row
        };

        // Call the readMinefield method with the scanner
        char[][] result = Minesweeper.readMinefield(testScanner);

        // Compare with our expected result
        assertArrayEquals(expected, result);
    }

    
    @Test
    void testProduceHints() {
        // Define a small minefield (including buffer)
        char[][] minefield = {
            {'0', '0', '0', '0'}, // Buffer row
            {'0', '*', '0', '0'}, // Buffer + actual minefield row 1
            {'0', '0', '*', '0'}, // Buffer + actual minefield row 2
            {'0', '0', '0', '0'}  // Buffer row
        };

        // Call the produceHints method to process the minefield
        Minesweeper.produceHints(minefield);

        // Extract the actual minefield excluding the buffer
        char[][] processedMinefield = {
            {minefield[1][1], minefield[1][2]},  // Extract row 1 (without buffer)
            {minefield[2][1], minefield[2][2]}   // Extract row 2 (without buffer)
        };

        // Expected result after processing
        char[][] expected = {
            {'*', '2'},  // Row 1 of the expected original minefield
            {'2', '*'}   // Row 2 of the expected original minefield
        };

        // Assert that the processed inner minefield matches the expected output
        assertArrayEquals(expected, processedMinefield);
    }

    @Test
    void testIncrementNeighbors() {
        // Create a small minefield
        char[][] minefield = {
            {'0', '0', '0'},   
            {'0', '*', '0'},   
            {'0', '0', '0'}    
        };

        // Call incrementNeighbors around the mine at (1, 1)
        Minesweeper.incrementNeighbors(minefield, 1, 1);

        // Expected result after incrementing neighbors
        char[][] expected = {
            {'1', '1', '1'},  
            {'1', '*', '1'},  
            {'1', '1', '1'} 
        };

        // Assert that the result matches the expected minefield after increment
        assertArrayEquals(expected, minefield);
    }

    @Test
    void testInvalidInputExceedsMaxSize() {
        // Simulate user input where dimensions exceed MAX_SIZE
        String input = "101 101\n";  // Both n and m exceed MAX_SIZE (100)
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Call the readMinefield method with the scanner
        char[][] result = Minesweeper.readMinefield(testScanner);

        // Assert that the result is null due to exceeding size limits
        assertNull(result);
    }
    
    @Test
    void testSingleCellMinefield() {
        String input = "1 1\n*\n";  // Single cell minefield with a mine
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        char[][] expected = {
            {'0', '0', '0'},  // Buffer row
            {'0', '*', '0'},  // Row 1 (single cell)
            {'0', '0', '0'}   // Buffer row
        };

        char[][] result = Minesweeper.readMinefield(testScanner);
        assertArrayEquals(expected, result);
    }

    @Test
    void testZeroDimensionInput() {
        String input = "0 0\n";  // Input indicating end of input (termination)
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Call the readMinefield method
        char[][] result = Minesweeper.readMinefield(testScanner);

        // Assert that the result is null, indicating end of input
        assertNull(result);
    }

    @Test
    void testSingleRowMinefield() {
        String input = "1 3\n.*.\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        char[][] expected = {
            {'0', '0', '0', '0', '0'},  // Buffer row
            {'0', '0', '*', '0', '0'},  // Row 1
            {'0', '0', '0', '0', '0'}   // Buffer row
        };

        char[][] result = Minesweeper.readMinefield(testScanner);
        assertArrayEquals(expected, result);
    }

    @Test
    void testSingleColumnMinefield() {
        String input = "3 1\n*\n.\n*\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        char[][] expected = {
            {'0', '0', '0'},  // Buffer row
            {'0', '*', '0'},  // Row 1
            {'0', '0', '0'},  // Row 2
            {'0', '*', '0'},  // Row 3
            {'0', '0', '0'}   // Buffer row
        };

        char[][] result = Minesweeper.readMinefield(testScanner);
        assertArrayEquals(expected, result);
    }

    @Test
    void testAllMines() {
        String input = "2 2\n**\n**\n";  // All mines
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        char[][] expected = {
            {'0', '0', '0', '0'},  // Buffer row
            {'0', '*', '*', '0'},  // Row 1
            {'0', '*', '*', '0'},  // Row 2
            {'0', '0', '0', '0'}   // Buffer row
        };

        char[][] result = Minesweeper.readMinefield(testScanner);
        assertArrayEquals(expected, result);
    }

    @Test
    void testMixedMinesAndSafeSquares() {
        String input = "3 3\n*..\n.*.\n..*\n";
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        char[][] expected = {
            {'0', '0', '0', '0', '0'},  // Buffer row
            {'0', '*', '0', '0', '0'},  // Row 1
            {'0', '0', '*', '0', '0'},  // Row 2
            {'0', '0', '0', '*', '0'},  // Row 3
            {'0', '0', '0', '0', '0'}   // Buffer row
        };

        char[][] result = Minesweeper.readMinefield(testScanner);
        assertArrayEquals(expected, result);
    }

    
}
