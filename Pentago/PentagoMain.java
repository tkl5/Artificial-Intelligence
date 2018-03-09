/* 
 * TCSS 435 Autumn 2017
 * 
 * Assignment #2 - Pentago
 */

/**
 * Main method for the pentago game. Includes user input necessary
 * to play the game and creates an initial, empty board.
 * 
 * The program assumes correct input from the user.
 * 
 * @author Tim Liu
 * @version November 2017
 */

package Assignment2;
import java.util.*;

public final class PentagoMain {
	
	/* Private constructor to prevent construction of instances */
	private PentagoMain() {
        throw new IllegalStateException();
	}
	
	/* Global variables 
	 * Winners are denoted by an integer return value */
	private static final int BLACK_WINNER = 1;
	private static final int WHITE_WINNER = 2;
	private static final int TIE = 3;
	
	/* Size of game board is set to 3 */
	private static int pentagoSize = 3;
	private static int winnerFound = 0;
	
	/* Create default gameboard object of size 6x6 */
	private static String[][] myInitialBoard = new String[pentagoSize * 2][pentagoSize * 2];
	
	/* Human player name and color */
	private static String myPlayerOneName;
	private static String myPlayerOneColor;

	public static void main(String[] args) {		
		/* First, we initialize the pentago board to all "." */
		initializeEmptyBoard(myInitialBoard);
		
		PentagoBoard theGameBoard = new PentagoBoard(myInitialBoard, 0);

		Scanner theScan = new Scanner(System.in);
		
		/* Take user input */
		try {
			
			System.out.print("Enter name for player 1: ");
			myPlayerOneName = theScan.next();
			System.out.print("Enter name for player 2: ");
			System.out.println(theGameBoard.myAIName);
	
			System.out.print(myPlayerOneName + ", choose your token color (B or W): ");
			myPlayerOneColor = theScan.next().toUpperCase();

			System.out.print(theGameBoard.myAIName + ", choose your token color (B or W): ");
			theGameBoard.setAIColor(myPlayerOneColor, theGameBoard);
			System.out.println(theGameBoard.myAIColor);
	
			System.out.print("Which player will start first? (1 or 2): ");
			int playerTurn = theScan.nextInt();
			theScan.nextLine();
			
			System.out.println("\n----- G A M E  S T A R T -----");
	
			theGameBoard.myBoardCreator = playerTurn;
		 
			/* Run the game until a winner is found, a tie, or a draw happens. */
			while (winnerFound == 0) {
				
				if (playerTurn == 1) {
					System.out.println();
					System.out.print(myPlayerOneName + ", enter your move (B/P BD): ");
					String playerOneMove = theScan.nextLine();
	
					// Loop while that move is invalid
					while (!theGameBoard.isMoveValid(playerOneMove)) {
						System.out.println("INVALID MOVE");
						System.out.print(myPlayerOneName + ", enter your move (B/P BD): ");
						playerOneMove = theScan.nextLine();
					}
					theGameBoard.humanMove(playerOneMove, myPlayerOneColor);
	
					System.out.println("\nCURRENT BOARD");
					theGameBoard.printPentagoBoard();
	
					winnerFound = theGameBoard.detectWinner();
	
					playerTurn = 2;
				
				/* AI's turn */
				} else {
					System.out.println();
					System.out.println(theGameBoard.myAIName + ", enter your move (B/P BD): ");
					PentagoBoard bestBoard = theGameBoard.minimaxAlphaBetaAIMove(theGameBoard, 2, 3, 
																				 Integer.MIN_VALUE, Integer.MAX_VALUE);
	
					theGameBoard.updatePentagoBoardState(bestBoard.myInitialState);
	
					System.out.println("\nCURRENT BOARD");
					theGameBoard.printPentagoBoard();
	
					winnerFound = theGameBoard.detectWinner();
	
					playerTurn = 1;
				}
			}
			
		} catch (InputMismatchException | NumberFormatException theException) {
			theException.printStackTrace();
		}

		if (winnerFound == BLACK_WINNER) {
			if (myPlayerOneColor.equals("B")) {
				System.out.println(myPlayerOneName + " wins the game!");
			
			} else {
				System.out.println(theGameBoard.myAIName + " wins the game!");
			}
		}

		else if (winnerFound == WHITE_WINNER) {
			if (myPlayerOneColor.equals("B")) {
				System.out.println(theGameBoard.myAIName + " wins the game!");
			
			} else {
				System.out.println(myPlayerOneName+ " wins the game!");
			}
		}
		
		else if (winnerFound == TIE) {
			System.out.println("\nThe game ends in a tie!");
		
		} else {
			System.out.print("\nBoard full! Game ends in a draw.");
		}

		theScan.close();
	}
	
	/* Helper method to initialize game board to all "."*/
	private static void initializeEmptyBoard(String[][] theInitialBoard) {
		for (int i = 0; i < pentagoSize * 2; i++) {
			for (int j = 0; j < pentagoSize * 2; j++) {
				theInitialBoard[i][j] = ".";
			}
		}
	}
}

