package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.Minesweeper;

import java.io.ByteArrayInputStream;

class MinesweeperTest {

    @Test
    void testReadMinefield() {
    	
    	// Simulate user input for a 2x2 minefield
    	String input = "2 2\n.*\n*. \n";
    	System.setIn(new ByteArrayInputStream(input.getBytes()));
    	
    	// Expected output after reading the input
    	char[][] expected = {
    			{'0', '0', '0', '0'},
    			{'0', '0', '*', '0'},
    			{'0', '*', '0', '0'},
    			{'0', '0', '0', '0'}
    	};
    	
    	// Call the readMinefield method on our user input
    	char[][] result = Minesweeper.readMinefield();
    	
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
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call the readMinefield method
        char[][] result = Minesweeper.readMinefield();

        // Assert that the result is null due to exceeding size limits
        assertNull(result);
    }

}
