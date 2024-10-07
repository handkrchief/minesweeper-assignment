package model;

import java.util.Scanner;

public class Minesweeper {
	
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		int fieldCount = 1;
		
		while (true) {
			// Read the mine field
			char[][] minefield = readMinefield();
			
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
	
	static char[][] readMinefield() {
		
		// Read in the dimensions of the mine field 
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		
		if (scanner.hasNextLine()) {
			scanner.nextLine();
		}
		
		// Termination condition
		if (n == 0 && m == 0) {
			return null; // return null to signal end of input
		}
		
		// Create a blank mine field with buffer around it
		char[][] bufferedMinefield = new char[n + 2][m + 2];
		
		// Fill the buffer spots with 0's
		for (int i = 0; i < n + 2; i++) {
			for (int j = 0; j < m + 2; j++) {
				bufferedMinefield[i][j] = '0';
			}
		}
		
		// Fill the rest of the mine field according to the input
		for (int i = 1; i <= n; i++) {
			String row = scanner.nextLine();
			for (int j = 1; j <= m; j++) {
				char currentChar = row.charAt(j - 1);
				// Replace the safe spaces with '0' otherwise keep mines as '*'
				if (currentChar == '.') {
					bufferedMinefield[i][j] = '0';
				} else {
					bufferedMinefield[i][j] = currentChar;
				}
			}
		}
		
		return bufferedMinefield;
	}
	
	static void produceHints(char[][] minefield) {
		
		int n = minefield.length - 2;
		int m = minefield[0].length - 2;
		
		// Iterate over the mine field, skipping the buffer
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (minefield[i][j] == '*') {
					incrementNeighbors(minefield, i, j);
				}
			}
		}
	}
	
	static void incrementNeighbors(char[][] minefield, int i, int j) {
		
		// Define all 8 possible directions
		int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
		
		// Loop through all of the directions
		for (int k = 0; k < 8; k++) {
			int ni = i + dx[k];
			int nj = j + dy[k];
			
			// Increment the neighbor if it's not a mine
			if (minefield[ni][nj] != '*') {
				minefield[ni][nj] = (char) (minefield[ni][nj] + 1);
			}
		}
	}
	
	static void printMinefield(char[][] minefield, int fieldNumber) {
		int n = minefield.length - 2;
		int m = minefield[0].length -2;
		
		System.out.println("Field #" + fieldNumber + ":");
		
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				System.out.print(minefield[i][j]);
			}
			System.out.println();
		}
		
		System.out.println();
	}
}
