/**
 * 
 */
package maze;

import java.io.File;
import java.util.Scanner;

public class Exercise02_Full {
	private static final String dataPath = "mazedata-2.txt";
	private static int[][] cell;
	private static int maxRow = 0;		// These are the height and width of the maze
	private static int maxCol = 0;
	private static final int WALL = -1;		// These constants are the encoding used to represent the maze
	private static final int START = -2;	// A zero and positive values are pathways.  A positive value
	private static final int GOAL = -3;		// specifies how many times the MazeRunner has left this position

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Exercise02_Full starting");
		
		try {
			Scanner scanner = new Scanner(new File(dataPath));
			String line = scanner.nextLine();
			Scanner s = new Scanner(line);
			maxCol = s.nextInt();
			maxRow = s.nextInt();
			cell = new int[maxCol][maxRow];
			boolean validation = true;
			
			String [] tempChar = new String [maxRow];
			
			for (int i=0; i<maxRow; i++){
				tempChar[i] = scanner.nextLine();
			}
			
			int number1 = 0, number2 = 0;
			
			for (int i=0; i<maxRow; i++){
				for (int j=0; j<maxCol; j++){
					if (tempChar[i].charAt(j) == '*')
						cell[i][j] = WALL;
					else if (tempChar[i].charAt(j) == 'G')
						cell[i][j] = GOAL;
					else if (tempChar[i].charAt(j) == 'S')
						cell[i][j] = START;
					else if (tempChar[i].charAt(j) == ' ')
						cell[i][j] = 0;
					else{
						validation = false;
						number1 = i;
						number2 = j;
					}
				}
			}
			
			if (!validation){
				System.out.println(mazeToStringValid(number1, number2));
				System.out.println("Sorry, there is non-valid character.");
			} else
				System.out.println(mazeToString());
				
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * Create a String representation of the maze
	 */
	private static String mazeToString(){
		String str = "";
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++)
				str += cell[i][j];
			str += '\n';
		}
		return str;
	}
	
	private static String mazeToStringValid(int num1, int num2){
		String str = "";
		for (int i = 0; i <= num1; i++) {
			for (int j = 0; j < num2-1; j++)
				str += cell[i][j];
			str += '\n';
		}
		for (int i = num1+1; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++)
				str += cell[i][j];
			str += '\n';
		}
		return str;
	}

}
