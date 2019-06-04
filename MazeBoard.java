package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * <p>
 * Title: WindowManger Class - A component of the MazeRunner system
 * </p>
 *
 * <p>
 * Description: A controller object class that implements the animated window
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2012
 * </p>
 * @version 1.00
 */
public class MazeBoard extends JPanel {
	/*
	 * The maze is a two dimensional array of integer values.  Negative values are walls (-1) and the start (-2)
	 * and stop (-3) doors.  Zeros are maze squares that have never been visited.  Positive values represent
	 * cells that have been visited before.  The number of the cell indicates how many times it has been visited.
	 * 
	 */

	private int [][] cell=null;			// The maze is encoded as a two dimensional array of integer values
	private int numRows = 0;			// To make it easy to read, we use these values to record the number
	private int numCols = 0;			// of row and columns

	private int cellSize = 30;			// The size of a cell in terms of pixels
	private int currentRow = -1;		// These pair of values keep track of the location of the MazeRunner
	private int currentCol = -1;		// as it is solving the maze
	private final int WALL = -1;		// These constants are the encoding used to represent the maze
	private final int START = -2;		// A zero and positive values are pathways.  A positive value
	private final int STOP = -3;		// specifies how many times the MazeRunner has left this position


	private final int MARGIN = 15;		// The number of pixels in the margin

	private Rectangle boardRect = null;	// Hard coded bounding rectangle for the board

	/*****
	 * Constructor to create a display board to get things started, but do not actually allocate the storage 
	 * for the maze.  That is done once a valid file name has been found and the actual dimensions of the real 
	 * maze have been established. We need to start with an empty maze, so the rest of the user interface can 
	 * be shown and used to establish the path to the data for the real maze. 
	 * 
	 */
	public MazeBoard() {
		cell = null;		// Pointer to the real maze.  Will be specified once we know its actual size.
		boardRect = null;
	}

	/*****
	 * This is the second half of the "constructor".  This method is called once the details of the actual
	 * maze is known.  The rest of the window has been established, so this routine resizes that portion of
	 * the window dedicated to the maze and places it in the upper left portion of the whole window that
	 * includes the GUI elements for specifying the maze and the start and stop buttons.
	 * 
	 * @param rowSize	- The actual number of rows
	 * @param colSize	- The actual number of columns
	 * @param cSize		- The actual cell size
	 */
	public void resetBoard(int rowSize, int colSize, int cSize) {
		numRows=rowSize;	// Record the number of rows
		numCols=colSize;	// Record the number of columns
		cellSize=cSize;		// Record the number of pixels per cell
		cell = new int[rowSize][colSize];

		// Compute the rectangle (square) that is the drawing space for the game boards
		boardRect = new Rectangle(MARGIN, MARGIN, numCols * cellSize + MARGIN/4, numRows * cellSize + MARGIN/4);
	}

	/*****
	 * This method is used to help load up a maze into the two dimensional array that has been allocated
	 * to hold it.  The WindowManager is used to interact with the user and calls this method with a 
	 * sequence of row and column values, with a character for each cell in the mazes.  This routine
	 * encodes the maze and places the encoding into the maze.
	 * 
	 * There are three kinds of walls, a regular wall which is represented by the character * in the
	 * input file, the starting point, represented by a "B" or a "b", and the ending point, which is
	 * represented by a "E" or an "e".  A blank represents a pathway.  Any other character is treated
	 * as a well
	 * 
	 * @param r		- the row number for this definition
	 * @param c		- the column number for this definition
	 * @param v		- the character value to be used to establish the maze value at the specified place
	 */
	public void set(int r, int c, char v){
		if (r<0 || r >= numRows || c < 0 || c > numCols) return;
		switch (v) {
		// Walls
		case '*': cell[r][c] = WALL; break;
		
		// Start symbol
		case 'S':
		case 's':  cell[r][c] = START; break;
		
		// Goal symbol
		case 'G':
		case 'g':  cell[r][c] = STOP; break;
		
		// Pathways
		case ' ':  cell[r][c] = 0; break;
		case '0':  cell[r][c] = 0; break;
		case '1':  cell[r][c] = 1; break;
		case '2':  cell[r][c] = 2; break;
		case '3':  cell[r][c] = 3; break;
		case '4':  cell[r][c] = 4; break;
		case '5':  cell[r][c] = 5; break;
		case '6':  cell[r][c] = 6; break;
		case '7':  cell[r][c] = 7; break;
		case '8':  cell[r][c] = 8; break;
		case '9':  cell[r][c] = 9; break;
		default:   cell[r][c] = WALL; break;
		}
	}


	/*****
	 * This method forces the painting of the current game board
	 */
	public void drawMaze(){
		this.repaint();
	}

	/*****
	 * Draw the board and all of the alive cells on the boards
	 */
	public void paint(Graphics g){
		if (cell == null) return;
		g.setColor(Color.WHITE);
		g.fillRect(boardRect.x, boardRect.y, boardRect.width, boardRect.height);
		g.setColor(Color.BLACK);
		g.drawRect(boardRect.x-1,boardRect.y-1 , boardRect.width+1, boardRect.height+1);

		/*
		 * Drawing the maze is done in two passes, first the horizontal lines and then the vertical lines.  A horizontal
		 * line is drawn when two or more cells are wall values.  If the previous cell (to the left) is an edge or a cell 
		 * that does not contain a wall value, and this cell and the one to it's right, is a wall, then a line segment is
		 * going to be drawn, but we do not draw the line until we find the end of the line.  Until that point, we just
		 * keep adding to the line length.  When the end of a line is found, then the whole line is drawn at once.
		 */
		
		// Draw the horizontal lines
		for (int row = 0; row<numRows; row++){
			boolean previousWasWall = true;	// The design of the mazes requires walls for the outer most cells
			int wallLength=0;				// We count the length of a wall in number of cells
			for (int col = 1; col<numCols; col++){
				// If this is a WALL element and the previous was a WALL, then we are continuing a wall
				if (cell[row][col] == WALL && previousWasWall) wallLength++;
				else if (cell[row][col] == -1){
					// If this is a WALL and the previous was not a WALL, we are starting a wall
					previousWasWall = true;
					wallLength=0;
				}
				// If this is not a WALL, the previous was a wall, and that wall length was greater than zero
				// then we must draw a wall on the display based on out current location and the length of the wall.
				else if (previousWasWall && wallLength > 0 ) {
					g.drawLine(MARGIN + (col-wallLength)*cellSize - cellSize/2, MARGIN + row*cellSize + cellSize/2, 
							MARGIN + (col-wallLength)*cellSize - cellSize/2 + wallLength*cellSize, MARGIN + row*cellSize + cellSize/2);
					wallLength = 0;
					previousWasWall = false;
				}
				// If this is not a WALL, the previous was a not wall, we are on a path, so reset everything
				else {
					wallLength = 0;
					previousWasWall = false;
				}
			}
			// When we are done with a row, if the length is positive, we have a pending wall to draw, so this draws it
			if (wallLength > 0) {
				g.drawLine(MARGIN + (numCols-wallLength)*cellSize - cellSize/2, MARGIN + row*cellSize + cellSize/2, 
						MARGIN + (numCols-wallLength)*cellSize - cellSize/2 + wallLength*cellSize, MARGIN + row*cellSize + cellSize/2);
			}
		}

		// Draw the vertical lines.  This is exactly the same algorithm as above, but now we are going down a column as
		// opposed to across a row.
		for (int col = 0; col<numCols; col++){
			boolean previousWasWall = true;
			int wallLength=0;
			for (int row = 1; row<numRows; row++){
				// If this is a WALL element and the previous was a WALL, then we are continuing a wall
				if (cell[row][col] == -1 && previousWasWall) wallLength++;
				else if (cell[row][col] == WALL){
					// If this is a WALL and the previous was not a WALL, we are starting a wall
					previousWasWall = true;
					wallLength=0;
				}
				// If this is not a WALL, the previous was a wall, and that wall length was greater than zero
				// then we must draw a wall on the display based on out current location and the length of the wall.
				else if (previousWasWall && wallLength > 0 ) {
					g.drawLine(MARGIN + col*cellSize + cellSize/2, MARGIN + (row-wallLength)*cellSize - cellSize/2, 
							MARGIN + col*cellSize + cellSize/2, MARGIN + (row-wallLength)*cellSize - cellSize/2 + wallLength*cellSize);
					wallLength = 0;
					previousWasWall = false;
				}
				else {
					// If this is not a WALL, the previous was a not wall, we are on a path, so reset everything
					wallLength = 0;
					previousWasWall = false;
				}
			}
			// When we are done with a column, if the length is positive, we have a pending wall to draw, so this draws it
			if (wallLength > 0) {
				g.drawLine(MARGIN + col*cellSize + cellSize/2, MARGIN + (numRows-wallLength)*cellSize - cellSize/2, 
						MARGIN + col*cellSize + cellSize/2, MARGIN + (numRows)*cellSize - cellSize/2);
				
			}
		}

		// Draw the contents (The start symbol, the stop symbol, the MazeRunner, and the counts for how many time
		// the various path elements have been left.
		for (int row = 0; row<numRows; row++){
			for (int col = 1; col<numCols; col++){
				Integer i = cell[row][col];
				if (cell[row][col] == START)
					// Display the start symbol
					g.drawString("Start", MARGIN + col*cellSize + 3, MARGIN + row*cellSize + cellSize/2 + 5);
				else if (cell[row][col] == STOP)
					// Display the stop symbol
					g.drawString("Stop", MARGIN + col*cellSize + 4, MARGIN + row*cellSize + cellSize/2 + 5);
				else if (currentRow == row && currentCol == col)
					// Display the MazeRunner
					g.drawString("XXX", MARGIN + col*cellSize + 3, MARGIN + row*cellSize + cellSize/2 + 5);
				else if (cell[row][col] > 0)
					// Display the count for how many times this cell have been left
					g.drawString(i.toString(), MARGIN + col*cellSize + 12, MARGIN + row*cellSize + cellSize/2 + 5);
				// If non of these conditions are true, the item is a WALL or a path element that has not yet been left
				// so we do not need to draw anything there.
			}
		}
	}

	/*****
	 * This method is used to set the start location for the maze.  We assume it is on an external edge of
	 * the maze.
	 */
	public void findStart(){
		// Scan every cell in the table until the START value is found.  Record that location and return

		for (int i=0; i<numRows; i++){
			for (int j=0; j<numCols; j++){
				if (cell[i][j] == START){
					currentRow = i-1;
					currentCol = j;
				}
			}
		}
		// Place the code here to find the start symbol and then set currentRow and currentCol so the Maze
		// Runner is next to it on the board.
	}

	/****
	 * This method returns false if the specified location is outside of the maze or the cell is a WALL, a START, or
	 * a STOP.  The method works on a delta from the current location of the MazeRunner (e.g., -1, 0, or +1)
	 * 
	 * @param rDelta	- row offset
	 * @param cDelta	- column offset
	 * @return			- true if this is not a wall, false if this is a a WALL, a START, or a STOP
	 */
	private boolean notWall(int rDelta, int cDelta) {
		if (currentRow+rDelta < 0 || currentRow+rDelta >= numRows || currentCol+cDelta < 0 || currentCol+cDelta >= numCols)
			return false;
		return cell[currentRow+rDelta][currentCol+cDelta] >= 0;
	}

	/*****
	 * This method returns the integer value for a path that indicates how many times this cell has been left.  If the
	 * indicated cell is a WALL, a START, or a STOP, the method returns a very large positive value.   The method works 
	 * on a delta from the current location of the MazeRunner (e.g., -1, 0, or +1)
	 * 
	 * @param rDelta	- row offset
	 * @param cDelta	- column offset
	 * @return			- the integer value of the number of marks for this cell.
	 */
	private int getMarks(int rDelta, int cDelta) {
		if (currentRow+rDelta < 0 || currentRow+rDelta >= numRows || currentCol+cDelta < 0 || currentCol+cDelta >= numCols) return 9999999;
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
	private int getContents(int rDelta, int cDelta) {
		if (currentRow+rDelta < 0 || currentRow+rDelta >= numRows || currentCol+cDelta < 0 || currentCol+cDelta >= numCols)
			return WALL;
		return cell[currentRow+rDelta][currentCol+cDelta];
	}

	/*****
	 * This is the method you need to write.  The above methods can help make this method much easier to write.
	 */
	public boolean makeMove(){
		
		if (getContents(1, 0) == STOP || getContents(0, 1) == STOP || getContents(-1, 0) == STOP || getContents(0, -1) == STOP)
			return false;
		
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
				
		/********************/ 
		// Place the code here to see if your are next to the goal symbol.  If so, return false.  Otherwise, assess the current 
		// context, determine which move to make, make the move, if one is possible, and return true, else return false.
		
		return false;
	}
}
