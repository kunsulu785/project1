package maze;

import java.io.File;
import java.util.Scanner;

public class Exercise04 {
	private static final String dataPath = "mazedata.txt";
	private static int[][] cell;
	private static int maxRow = 0;			// These are the height and width of the maze
	private static int maxCol = 0;
	private static final int WALL = -1;		// These constants are the encoding used to represent the maze
	private static final int START = -2;	// A zero and positive values are pathways.  A positive value
	private static final int GOAL = -3;		// specifies how many times the MazeRunner has left this position
	
	private static int currentRow;
	private static int currentCol;

	/****
	 * This method returns false if the specified location is outside of the maze or the cell is a WALL, a START, or
	 * a GOAL.  The method works on a delta from the current location of the MazeRunner (e.g., -1, 0, or +1)
	 * 
	 * @param rDelta	- row offset
	 * @param cDelta	- column offset
	 * @return			- true if this is not a wall, false if this is a a WALL, a START, or a GOAL
	 */
	private static boolean notWall(int rDelta, int cDelta) {
		if (currentRow+rDelta < 0 || currentRow+rDelta >= maxRow || currentCol+cDelta < 0 || currentCol+cDelta >= maxCol)
			return false;
		return cell[currentRow+rDelta][currentCol+cDelta] >= 0;
	}

	/*****
	 * This method returns the integer value for a path that indicates how many times this cell has been left.  If the
	 * indicated cell is a WALL, a START, or a GOAL, the method returns a very large positive value.   The method works 
	 * on a delta from the current location of the MazeRunner (e.g., -1, 0, or +1)
	 * 
	 * @param rDelta	- row offset
	 * @param cDelta	- column offset
	 * @return			- the integer value of the number of marks for this cell.
	 */
	private static int getMarks(int rDelta, int cDelta) {
		if (currentRow+rDelta < 0 || currentRow+rDelta >= maxRow || currentCol+cDelta < 0 || currentCol+cDelta >= maxCol) return 9999999;
		if (cell[currentRow+rDelta][currentCol+cDelta] < 0) return 9999999;
		return cell[currentRow+rDelta][currentCol+cDelta];
	}

	/*****
	 * This method returns the context of a cell. The method works on a delta from the current location of the 
	 * MazeRunner (e.g., -1, 0, or +1).  If the specified location is outside of the maze, the value returned is
	 * a WALL.
	 * 
	 * @param rDelta	- row offset
	 * @param cDelta	- column offset
	 * @return			- the content of a maze cell given the delta values
	 */
	private static int getContents(int rDelta, int cDelta) {
		if (currentRow+rDelta < 0 || currentRow+rDelta >= maxRow || currentCol+cDelta < 0 || currentCol+cDelta >= maxCol)
			return WALL;
		return cell[currentRow+rDelta][currentCol+cDelta];
	}


	/**
	 * Create a String representation of the maze
	 */
	private static String mazeToString(){
		return mazeToString(0, maxRow-1, 0, maxCol-1);
	}
	
	/*****
	 * Convert some to all of the maze to a String for output
	 * 
	 * @param strtRow	The index of the row to start the display
	 * @param GOALRow	The index of the row to GOAL the display
	 * @param strtCol	The index of the column to start the display
	 * @param GOALCol	The index of the column to GOAL the display
	 * @return			The String that represents the display
	 */
	private static String mazeToString(int strtRow, int GOALRow, int strtCol, int GOALCol){
		// Start by displaying the column numbers
		String result = "    ";
		for (int c = strtCol; c <= GOALCol; c++) 
			if (c < 10) result += "  " + c + " ";
			else result += " " + c + " ";
		result += '\n';
		
		// Then work through the rows, one at a time
		for (int r = strtRow; r <= GOALRow; r++) {
			
			// Label the row with a row number
			if (r < 10) result += "  " + r + " ";
			else result += " " + r + " ";
			
			// Then display the row
			for (int c = strtCol; c <= GOALCol; c++) {
				
				// If the specified maze cell is that of the Maze Runner, display a special symbol
				if (currentRow == r && currentCol == c) result += " ** ";
				else
					// Otherwise, display a representation for that cell
					switch (cell[r][c]) {
					case -1:
						result += "++++";
						break;
					case -2:
						result += "++S+";
						break;
					case -3:
						result += "++G+";
						break;
					case 0:
						result += "    ";
						break;
					default:
						int v = Math.min(9, cell[r][c]);
						result += "  " + v + " ";
						break;
				}
			}
			// Add a new line at the end of each row
			result += '\n';
		}

		// Display a key to help the user understand what is being displayed
		result += "\nKey: ++++ = Wall; ++S+ = Start; ++G+ = Goal; ** = Maze Runner; 1 - 9 path with that many chalk marks.\n";

		// Return the resulting String to the caller
		return result;
	}
	
	/*****
	 * This is the method you need to write.  The above methods can help make this method much easier to write.
	 */
	public static boolean makeMove(){
		
		if (getContents(-1, 0) == GOAL)
			return true;
		
		if (notWall(1, 0)) {
			if (getMarks(1, 0) <= getMarks(0, 1) && getMarks(1, 0) <= getMarks(-1, 0) && getMarks(1, 0) <= getMarks(0, -1)) {
				cell[currentRow][currentCol]++;
				currentRow++;
				return true;
			}
		}
		
		if (notWall(0, 1)) {
			if (getMarks(0, 1) <= getMarks(-1, 0) && getMarks(0, 1) <= getMarks(0, -1) && getMarks(0, 1) <= getMarks(1, 0)) {
				cell[currentRow][currentCol]++;
				currentCol++;
				return true;
			}
		}
		
		if (notWall(-1, 0)) {
			if (getMarks(-1, 0) <= getMarks(0, -1) && getMarks(-1, 0) <= getMarks(1, 0) && getMarks(-1, 0) <= getMarks(0, 1)) {
				cell[currentRow][currentCol]++;
				currentRow--;
				return true;
			}
		}
		
		if (notWall(0, -1)) {
			if (getMarks(0, -1) <= getMarks(1, 0) && getMarks(0, -1) <= getMarks(0, 1) && getMarks(0, -1) <= getMarks(-1, 0)) {
				cell[currentRow][currentCol]++;
				currentCol--;
				return true;
			}
		} 
		
		return false;		
	}

	/*****
	 * This method reads in a maze.  If the maze is not valid, it displays the reason and terminates.  If the maze is valid,
	 * it displays the maze using the method mazeToString.
	 */
	private static void readTheMaze() {
		try {
			// Set up the Scanner object to read in the maze.
			Scanner scanner = new Scanner(new File(dataPath));
			String line = scanner.nextLine();
			Scanner s = new Scanner(line);
			
			// Read in the number of rows and columns
			maxCol = s.nextInt();
			maxRow = s.nextInt();
			
			// Create the maze and assume it is valid
			cell = new int[maxCol][maxRow];
			boolean isValidMaze = true;
			
			// Read in the maze, one row per line of input
			for (int r=0;r<maxRow;r++) {
				line = scanner.nextLine();
				char chr;
				
				// For each row, read it in as a sequence of characters, one for each kind of maze element
				for (int c = 0;c<maxCol;c++) {
					chr = line.charAt(c);
					switch (chr) {
					
					// Walls
					case '*': cell[r][c] = WALL; break;

					// Start symbol
					case 'S':
					case 's':  cell[r][c] = START; break;

					// Goal symbol
					case 'G':
					case 'g': cell[r][c] = GOAL; break;

					// Pathways
					case ' ': cell[r][c] = 0; break;
					
					// If a number is present, this is used to initialize a cell to have been visited before.
					case '0': cell[r][c] = 0; break;
					case '1': cell[r][c] = 1; break;
					case '2': cell[r][c] = 2; break;
					case '3': cell[r][c] = 3; break;
					case '4': cell[r][c] = 4; break;
					case '5': cell[r][c] = 5; break;
					case '6': cell[r][c] = 6; break;
					case '7': cell[r][c] = 7; break;
					case '8': cell[r][c] = 8; break;
					case '9': cell[r][c] = 9; break;
					
					// If it is none of these, it is an error
					default: 
						System.out.println(chr+" is not a valid maze character"); 
						isValidMaze = false;
						break;
					}				
				}
			}
			
			// If the maze is valid display it, else give an error message and terminate.
			if (isValidMaze)
				System.out.println(mazeToString());
			else {
				System.out.println("Error! Since there is an invalid character in the input data, the program stops!");
				System.exit(0);
			}
		// If the path to the file does not result in a valid file, given a good error message
		} catch (Exception e) {
			System.out.println("Error! The file <" + dataPath + "> cannot be found!");
			System.exit(0);
		}
	}
	
	/*****
	 * This is the mainline.  It performs the bulk of the work and calls supporting methods from
	 * above to help it do the work.
	 * 
	 * @param args	The program parameters are ignored.
	 */
	public static void main(String[] args) {
		System.out.println("Exercise04");
		
		// Read in the maze from the fixed filename stored in the constant dataPath and display it.
		readTheMaze();

		// Ask the user to specify a location in the maze in order to analyze which direction
		// the maze runner should go if it finds itself at that location.
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter a row and column number for the maze runner!");
	
		// As long as the user inputs something other than an empty line, the program will loop.
		String inputLine = keyboard.nextLine().trim();
		while (inputLine.length() > 0) {
			// Read in the user's response in terms of a row and column number
			Scanner inputScanner = new Scanner(inputLine);
			currentRow = inputScanner.nextInt();
			currentCol = inputScanner.nextInt();
			
			// Check to see if the numbers are valid
			if (currentRow < 1 || currentRow > maxRow-2 ) 
				System.out.println("Error! The row number must be in the range 1 through "+ maxRow + "; it was: " + currentRow);
			else if (currentCol < 1 || currentCol > maxCol-2) 
				System.out.println("Error! The column number must be in the range 1 through "+ maxCol + "; it was: " + currentCol);
			else if (cell[currentRow][currentCol] == -1)
				System.out.println("Error! You have specified the location of a wall.  The maze runner can never get here.");
			else if (cell[currentRow][currentCol] == WALL)
				System.out.println("Error! You have specified the location of a wall.  The maze runner can never get here.");
			else if (cell[currentRow][currentCol] == START)
				System.out.println("Error! You have specified the location of the start symbol.  The maze runner can never get here.");
			else if (cell[currentRow][currentCol] == GOAL)
				System.out.println("Error! You have specified the location of the goal.  The maze runner can never get here.");
			else {
				
				// The row and column numbers are valid

				// Display the portion of the maze of interest before the move
				System.out.println(mazeToString(currentRow-1, currentRow+1, currentCol-1, currentCol+1));
				
				// Compute the next move and return true if the maze runner moves; false if at the goal or not move is possible
				System.out.println("\nThe Maze Runner has moved: " + makeMove() + ".");
				// Display the current position of the maze runner
				System.out.println("The new location is row: " + currentRow +"; column: " + currentCol + "\n");
				
				// Display the new context of the maze of interest after the move
				System.out.println(mazeToString(currentRow-1, currentRow+1, currentCol-1, currentCol+1));

			}
			System.out.println("------------------------------");
			System.out.println(mazeToString());
			System.out.println("Enter a row and column number for the maze runner!");
			inputLine = keyboard.nextLine().trim();
		}
		System.out.println("------------------------------");
		System.out.println("The program ends!");
	}
}
