/* 
 * TCSS 435 Autumn 2017
 * 
 * Assignment #1 - Solving the 15-Puzzle
 */

/**
 * Class for the game board.
 * 
 * @author Tim Liu
 * @version October 2017
 */

import java.util.Arrays;

public final class GameBoard {
	
	public class BlankPosition {
		public int x;
		public int y;
		
		public BlankPosition(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private static final int GAME_BOARD_SIZE = 4;
	public int depth;
	private String[][]gameBoard = new String[GAME_BOARD_SIZE][GAME_BOARD_SIZE];
	BlankPosition currentBlankSpot = new BlankPosition(0,0);
	
	private String[][]gameBoardSolved1 = new String[GAME_BOARD_SIZE][GAME_BOARD_SIZE];
	private String[][]gameBoardSolved2 = new String[GAME_BOARD_SIZE][GAME_BOARD_SIZE];

	String[] myGoalStateOne = {"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"," "};
	String[] myGoalStateTwo = {"1","2","3","4","5","6","7","8","9","A","B","C","D","F","E"," "};
	
	
	public GameBoard(String[] inputState, int inputDepth) {
		int k = 0;
		depth = inputDepth;
		for (int i = 0; i < GAME_BOARD_SIZE; i++) {
			for (int j = 0; j < GAME_BOARD_SIZE; j++) {
				gameBoard[i][j] = inputState[k];
				if (inputState[k].equals(" ")) {
					currentBlankSpot.x = i;
					currentBlankSpot.y = j;
				}
				gameBoardSolved1[i][j] = myGoalStateOne[k];
				gameBoardSolved2[i][j] = myGoalStateTwo[k];
				k++;
			}
		}
	}
	
	public String[] getCurrentState() {
		String[] currentState = {"0","0","0","0",
								 "0","0","0","0",
								 "0","0","0","0",
								 "0","0","0","0"};
		int k = 0;
		
		for (int i = 0; i < GAME_BOARD_SIZE; i++) {
			for (int j = 0; j < GAME_BOARD_SIZE; j++) {
				currentState[k] = this.gameBoard[i][j];
				k++;
			}
		}
		return currentState;
	}
	
	public int getMismatchedTiles() {
		int mismatched = 0;
		for (int i = 0; i < GAME_BOARD_SIZE; i++) {
			for (int j = 0; j < GAME_BOARD_SIZE; j++) {
				if (!gameBoard[i][j].equals(gameBoardSolved1[i][j])) {
					mismatched++;
				}
			}
		}
		return mismatched;
	}
	
	public int calcManhattanSum() {
		int manhattanDistanceSum = 0;
		int value = 0;
		
		for (int i = 0; i < GAME_BOARD_SIZE; i++) {
			for (int j = 0; j < GAME_BOARD_SIZE; j++) {
				if (this.gameBoard[i][j].equals(" ")) {
					value = 0;
				
				} else if (this.gameBoard[i][j].equals("A")) { value = 10; }
				  else if (this.gameBoard[i][j].equals("B")) { value = 11; }
				  else if (this.gameBoard[i][j].equals("C")) { value = 12; }
				  else if (this.gameBoard[i][j].equals("D")) { value = 13; }
				  else if (this.gameBoard[i][j].equals("E")) { value = 14; }
				  else if (this.gameBoard[i][j].equals("F")) { value = 15; }
				  else {
					value = Integer.parseInt(this.gameBoard[i][j]);
				}
				
				if (value != 0) {
					int goalX = (value - 1) / GAME_BOARD_SIZE;
					int goalY = (value - 1) % GAME_BOARD_SIZE;
					int dx = i - goalX;
					int dy = j - goalY;
					manhattanDistanceSum += Math.abs(dx) + Math.abs(dy);
				}
			}
		}
		return manhattanDistanceSum;
	}
	
	public boolean isSolved() {
		for (int i = 0; i < GAME_BOARD_SIZE; i++) {
			for (int j = 0; j < GAME_BOARD_SIZE; j++) {
				if ((!gameBoard[i][j].equals(gameBoardSolved1[i][j])) &&
					   (!gameBoard[i][j].equals(gameBoardSolved2[i][j]))) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean equals(Object theObject) {
		if (this == theObject) { return true; }
		if (theObject == null) { return false; }
		if (getClass() != theObject.getClass()) { return false; }
		GameBoard other = (GameBoard) theObject;
		if (!Arrays.deepEquals(gameBoard, other.gameBoard)) { return false; }
		
		return true;
	}
	
	public void printGameBoard() {
		System.out.println("Current game board: ");
		for (int i = 0; i < GAME_BOARD_SIZE; i++) {
			for (int j = 0; j < GAME_BOARD_SIZE; j++) {
				System.out.print(this.gameBoard[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printBlankPosition() {
		System.out.println("X: ");
		System.out.println(this.currentBlankSpot.x);
		System.out.println(", Y: ");
		System.out.println(this.currentBlankSpot.y);
	}
	
	public GameBoard moveLeft() {
		GameBoard updatedBoard = this;
		int xVal = updatedBoard.currentBlankSpot.x;
		int yVal = updatedBoard.currentBlankSpot.y;
		
		if ((yVal - 1) >= 0) {
			String blankPiece = updatedBoard.gameBoard[xVal][yVal];
			String swapPiece = updatedBoard.gameBoard[xVal][yVal - 1];
			updatedBoard.gameBoard[xVal][yVal] = swapPiece;
			updatedBoard.gameBoard[xVal][yVal - 1] = blankPiece;
			updatedBoard.currentBlankSpot.y = yVal - 1;
			
			return updatedBoard;
			
		} else {
			return null;
		}
	}
	
	public GameBoard moveRight() {
		GameBoard updatedBoard = this;
		int xVal = updatedBoard.currentBlankSpot.x;
		int yVal = updatedBoard.currentBlankSpot.y;
		
		if ((yVal + 1) <= (GAME_BOARD_SIZE - 1)) {
			String blankPiece = updatedBoard.gameBoard[xVal][yVal];
			String swapPiece = updatedBoard.gameBoard[xVal][yVal + 1];
			updatedBoard.gameBoard[xVal][yVal] = swapPiece;
			updatedBoard.gameBoard[xVal][yVal + 1] = blankPiece;
			updatedBoard.currentBlankSpot.y = yVal + 1;
			
			return updatedBoard;
			
		} else {
			return null;
		}
	}
	
	public GameBoard moveUp() {
		GameBoard updatedBoard = this;
		int xVal = updatedBoard.currentBlankSpot.x;
		int yVal = updatedBoard.currentBlankSpot.y;
		
		if ((xVal - 1) >= 0) {
			String blankPiece = updatedBoard.gameBoard[xVal][yVal];
			String swapPiece = updatedBoard.gameBoard[xVal - 1][yVal];
			updatedBoard.gameBoard[xVal][yVal] = swapPiece;
			updatedBoard.gameBoard[xVal - 1][yVal] = blankPiece;
			updatedBoard.currentBlankSpot.x = xVal - 1;
			
			return updatedBoard;
			
		} else {
			return null;
		}
	}
	
	public GameBoard moveDown() {
		GameBoard updatedBoard = this;
		int xVal = updatedBoard.currentBlankSpot.x;
		int yVal = updatedBoard.currentBlankSpot.y;
		
		if ((xVal + 1) <= (GAME_BOARD_SIZE - 1)) {
			String blankPiece = updatedBoard.gameBoard[xVal][yVal];
			String swapPiece = updatedBoard.gameBoard[xVal + 1][yVal];
			updatedBoard.gameBoard[xVal][yVal] = swapPiece;
			updatedBoard.gameBoard[xVal + 1][yVal] = blankPiece;
			updatedBoard.currentBlankSpot.x = xVal + 1;
			
			return updatedBoard;
			
		} else {
			return null;
		}	
	}
}
