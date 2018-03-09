/**
 * Board method for the pentago game. Inludes win detection, AI characteristics, 
 * AI moves by minimax and alpha/beta pruning algorithm, human moves, move validity checks,
 * and block/quadrant rotations.
 * 
 * The program assumes correct input from the user.
 * 
 * @author Tim Liu
 * @version November 2017
 */

package Assignment2;
import java.util.*;

public class PentagoBoard {
	
	private static final int BLACK_WINNER = 1;
	private static final int WHITE_WINNER = 2;
	private static final int TIE = 3;
	private static final int DRAW = 4;
	private static final int FIVE_IN_A_ROW = 5;
	private static final int FULL_GAME_BOARD = 36;
	
	private int myAlpha = Integer.MIN_VALUE;
	private int myBeta = Integer.MAX_VALUE;

	/* Initialize the blocks in the board as 2D arrays
	 * Quadrant 1 is top-left, Quadrant 2 is top-right, Quadrant 3 is bottom-left, Quadrant 4 is bottom-right .
	 * */
	public String[][] myInitialState = new String[pentagoSize * 2][pentagoSize * 2];
	private String[][] myQuadrantOne = new String[pentagoSize][pentagoSize];
	private String[][] myQuadrantTwo = new String[pentagoSize][pentagoSize];
	private String[][] myQuadrantThree = new String[pentagoSize][pentagoSize];
	private String[][] myQuadrantFour = new String[pentagoSize][pentagoSize];

	private static int pentagoSize = 3;
	final String myAIName = "Computer";
	String myAIColor = new String();
	private int myBoardUtility = 0;
	int myBoardCreator = 0;

	
	/* Constructor for the pentago board class */
	public PentagoBoard(String[][] theInitialBoard, int theCurrentPlayer) {
		for (int i = 0; i < pentagoSize * 2; i++) {
			if (i < 3) {
				
				for (int j = 0; j < pentagoSize * 2; j++) {
					if (j < 3) {
						myQuadrantOne[i][j] = theInitialBoard[i][j];
					
					} else {
						myQuadrantTwo[i][j - 3] = theInitialBoard[i][j];
					}
				}
			
			} else {
				
				for (int j = 0; j < pentagoSize * 2; j++) {
					if (j < 3) {
						myQuadrantThree[i - 3][j] = theInitialBoard[i][j];
					
					} else {
						myQuadrantFour[i - 3][j - 3] = theInitialBoard[i][j];
					}
				}
			}
			this.myBoardCreator = theCurrentPlayer;
		}
	}
	
	public void updateAIColor(String thePlayerOneColor,  PentagoBoard theCurrentBoard) {
		
		theCurrentBoard.myAIColor = thePlayerOneColor;
	}
	
	/* Set AI color opposite of player's color */
	public void setAIColor(String thePlayerOneColor, PentagoBoard theCurrentBoard) {
		if (thePlayerOneColor.toUpperCase().equals("B")) {
			theCurrentBoard.myAIColor = "W";
		} else {
			theCurrentBoard.myAIColor = "B";
		}
	}

	public int detectWinner() {
		int winnerFound = 0;
		int fullBoardCounter = 0;
		int pentagoResize = 0;
		int consecutiveB = 0;
		int consecutiveW = 0;
		int startingI = 0;
		int startingJ = 0;
		String[][] pentagoBoardFull = new String[pentagoSize * 2][pentagoSize * 2];

		for (int i = 0; i < pentagoSize * 2; i++) {
			if (i < 3) {
				
				for (int j = 0; j < pentagoSize * 2; j++) {
					if (j < 3) {
						pentagoBoardFull[i][j] = myQuadrantOne[i][j];
						if (pentagoBoardFull[i][j].equals("b")
								|| pentagoBoardFull[i][j].equals("w")) {
							fullBoardCounter++;
						}
						
					} else {
						pentagoBoardFull[i][j] = myQuadrantTwo[i][j - 3];
						if (pentagoBoardFull[i][j].equals("b")
								|| pentagoBoardFull[i][j].equals("w")) {
							fullBoardCounter++;
						}
					}
				}
				
			} else {
				
				for (int j = 0; j < pentagoSize * 2; j++) {
					if (j < 3) {
						pentagoBoardFull[i][j] = myQuadrantThree[i - 3][j];
						if (pentagoBoardFull[i][j].equals("b")
								|| pentagoBoardFull[i][j].equals("w")) {
							fullBoardCounter++;
						}
						
					} else {
						pentagoBoardFull[i][j] = myQuadrantFour[i - 3][j - 3];
						if (pentagoBoardFull[i][j].equals("b")
								|| pentagoBoardFull[i][j].equals("w")) {
							fullBoardCounter++;
						}
					}
				}
			}
		}

		if (fullBoardCounter == FULL_GAME_BOARD) {
			return DRAW;
		}

		// Checks horizontal victories
		for (int i = 0; i < pentagoSize * 2; i++) {
			for (int j = 0; j < pentagoSize * 2; j++) {
				
				if (pentagoBoardFull[i][j].equals("b")) {
					consecutiveB++;
					consecutiveW = 0;
				
				} else if (pentagoBoardFull[i][j].equals("w")) {
					consecutiveW++;
					consecutiveB = 0;
				
				} else {
					consecutiveB = 0;
					consecutiveW = 0;
				}

				// If there are 5 consecutive B's
				if (consecutiveB == FIVE_IN_A_ROW) {
					// If there are 5 consecutive W's already then it is a draw
					if (winnerFound == WHITE_WINNER) {
						winnerFound = TIE;
					} else if (winnerFound == 0) {
						winnerFound = BLACK_WINNER;
					}
				}
				// If there are 5 consecutive W's
				else if (consecutiveW == FIVE_IN_A_ROW) {
					// If there are 5 consecutive B's already then it is a draw
					if (winnerFound == BLACK_WINNER) {
						winnerFound = TIE;
					} else if (winnerFound == 0) {
						winnerFound = WHITE_WINNER;
					}
				}
			}
			consecutiveB = 0;
			consecutiveW = 0;
		}

		// Checks vertical victories
		for (int j = 0; j < pentagoSize * 2; j++) {
			for (int i = 0; i < pentagoSize * 2; i++) {
				
				if (pentagoBoardFull[i][j].equals("b")) {
					consecutiveB++;
					consecutiveW = 0;
				
				} else if (pentagoBoardFull[i][j].equals("w")) {
					consecutiveW++;
					consecutiveB = 0;
				
				} else {
					consecutiveB = 0;
					consecutiveW = 0;
				}

				if (consecutiveB == FIVE_IN_A_ROW) {
					if (winnerFound == WHITE_WINNER) {
						winnerFound = TIE;
					} else if (winnerFound == 0) {
						winnerFound = BLACK_WINNER;
					}
				}
				else if (consecutiveW == FIVE_IN_A_ROW) {
					if (winnerFound == BLACK_WINNER) {
						winnerFound = TIE;
					} else if (winnerFound == 0) {
						winnerFound = WHITE_WINNER;
					}
				}
			}
			consecutiveB = 0;
			consecutiveW = 0;
		}

		// Checks top left to bottom right diagonal victories, then top right to bottom left diagonal victories
		for (int k = 0; k < pentagoSize * 2; k++) {
			if (k == 0) {
				startingI = 1;
				startingJ = 0;
				pentagoResize = 1;
			
			} else if (k == 1) {
				startingI = 0;
				startingJ = 0;
				pentagoResize = 0;
			
			} else if (k == 2) {
				startingI = 0;
				startingJ = 1;
				pentagoResize = 1;
			
			} else if (k == 3) {
				startingI = 1;
				startingJ = 5;
				pentagoResize = 1;
			
			} else if (k == 4) {
				startingI = 0;
				startingJ = 5;
				pentagoResize = 0;
			
			} else {
				startingI = 0;
				startingJ = 4;
				pentagoResize = 1;
			}

			// Checking top left to bottom right diagonal victories
			if (k < 3) {
				for (int i = 0; i < (pentagoSize * 2) - pentagoResize; i++) {
					if (pentagoBoardFull[startingI + i][startingJ + i].equals("b")) {
						consecutiveB++;
						consecutiveW = 0;
					
					} else if (pentagoBoardFull[startingI + i][startingJ + i].equals("w")) {
						consecutiveW++;
						consecutiveB = 0;
					
					} else {
						consecutiveB = 0;
						consecutiveW = 0;
					}

					// If there are 5 consecutive B's
					if (consecutiveB == FIVE_IN_A_ROW) {
						// If there are 5 consecutive W's already then it is a tie
						if (winnerFound == WHITE_WINNER) {
							winnerFound = TIE;
						
						} else if (winnerFound == 0) {
							winnerFound = BLACK_WINNER;
						}
					}
					// If there are 5 consecutive W's
					else if (consecutiveW == FIVE_IN_A_ROW) {
						// If there are 5 consecutive B's already then it is a tie
						if (winnerFound == BLACK_WINNER) {
							winnerFound = TIE;
						
						} else if (winnerFound == 0) {
							winnerFound = WHITE_WINNER;
						}
					}
				}
				// Checking top right to bottom left diagonal victories
			} else {
				for (int i = 0; i < (pentagoSize * 2) - pentagoResize; i++) {
					if (pentagoBoardFull[startingI + i][startingJ - i].equals("b")) {
						consecutiveB++;
						consecutiveW = 0;
					
					} else if (pentagoBoardFull[startingI + i][startingJ - i]
							.equals("w")) {
						consecutiveW++;
						consecutiveB = 0;
					
					} else {
						consecutiveB = 0;
						consecutiveW = 0;
					}

					// If there are 5 consecutive B's
					if (consecutiveB == FIVE_IN_A_ROW) {
						// If there are 5 consecutive W's already then it is a
						// draw
						if (winnerFound == WHITE_WINNER) {
							winnerFound = TIE;
						} else if (winnerFound == 0) {
							winnerFound = BLACK_WINNER;
						}
					}
					// If there are 5 consecutive W's
					else if (consecutiveW == FIVE_IN_A_ROW) {
						// If there are 5 consecutive B's already then it is a
						// draw
						if (winnerFound == BLACK_WINNER) {
							winnerFound = TIE;
						} else if (winnerFound == 0) {
							winnerFound = WHITE_WINNER;
						}
					}
				}
			}
			consecutiveW = 0;
			consecutiveB = 0;
		}
		return winnerFound;
	}
	
	/* Represent the board as a 2d array */
	private String[][] getBoardStateAsArray() {
		String[][] currentBoard = new String[pentagoSize * 2][pentagoSize * 2];
		
		for (int i = 0; i < pentagoSize * 2; i++) {
			if (i < 3) {
				
				for (int j = 0; j < pentagoSize * 2; j++) {
					if (j < 3) {
						currentBoard[i][j] = myQuadrantOne[i][j];
					
					} else {
						currentBoard[i][j] = myQuadrantTwo[i][j - 3];
					}
				}
			
			} else {
				
				for (int j = 0; j < pentagoSize * 2; j++) {
					if (j < 3) {
						currentBoard[i][j] = myQuadrantThree[i - 3][j];
					
					} else {
						currentBoard[i][j] = myQuadrantFour[i - 3][j - 3];
					}
				}
			}
		}
		return currentBoard;
	}

	public void updatePentagoBoardState(String[][] theCurrentBoard) {
		for (int i = 0; i < pentagoSize * 2; i++) {
			if (i < 3) {
				
				for (int j = 0; j < pentagoSize * 2; j++) {
					if (j < 3) {
						myQuadrantOne[i][j] = theCurrentBoard[i][j];
					
					} else {
						myQuadrantTwo[i][j - 3] = theCurrentBoard[i][j];
					}
				}
			
			} else {
				for (int j = 0; j < pentagoSize * 2; j++) {
					if (j < 3) {
						myQuadrantThree[i - 3][j] = theCurrentBoard[i][j];
					
					} else {
						myQuadrantFour[i - 3][j - 3] = theCurrentBoard[i][j];
					}
				}
			}
		}
	}

	/* Prints the board to the console */
	public void printPentagoBoard() {
		System.out.println("+---+---+");
		
		for (int i = 0; i < pentagoSize * 2; i++) {
			if (i == 3) {
				System.out.println("+---+---+");
			}
			
			if (i < 3) {
				
				for (int j = 0; j < pentagoSize * 2; j++) {
					if (j == 0 || j == 3) {
						System.out.print("|");
					}
					
					if (j < 3) {
						System.out.print(myQuadrantOne[i][j]);
					
					} else {
						System.out.print(myQuadrantTwo[i][j - 3]);
					}
				}
				
				System.out.print("|");
				System.out.println();
				
			} else {
				
				for (int j = 0; j < pentagoSize * 2; j++) {
					if (j == 0 || j == 3) {
						System.out.print("|");
					}
					
					if (j < 3) {
						System.out.print(myQuadrantThree[i - 3][j]);
					} else {
						System.out.print(myQuadrantFour[i - 3][j - 3]);
					}
				}
				System.out.print("|");
				System.out.println();
			}
		}
		System.out.println("+---+---+");
	}

	/* Calculate the utility value for minimax algorithm */
	private int getUtility(int theCurrentPlayer, PentagoBoard theCurrentBoard) {
		int pentagoResize = 0;
		int utilityTotal = 0;
		int consecutiveCounter = 0;
		int startingI = 0;
		int startingJ = 0;
		String currentColor = new String();

		// Get the correct color to search for
		if (theCurrentPlayer == 2) {
			currentColor = theCurrentBoard.myAIColor.toLowerCase();
		} else {
			if (this.myAIColor.equals("B")) {
				currentColor = "w";
			} else {
				currentColor = "b";
			}
		}

		// Load individual boards into a full board for easier traversal
		String[][] pentagoBoardFull = theCurrentBoard.getBoardStateAsArray();

		// Checks horizontal victories
		for (int i = 0; i < pentagoSize * 2; i++) {
			for (int j = 0; j < ((pentagoSize * 2) - 1); j++) {
				if (pentagoBoardFull[i][j].equals(currentColor)
					&& pentagoBoardFull[i][j + 1].equals(currentColor)) {
					
					utilityTotal += consecutiveCounter + 1;
					consecutiveCounter++;
				
				} else {
					consecutiveCounter = 0;
				}
			}
		}

		// Checks vertical victories
		consecutiveCounter = 0;
		for (int j = 0; j < pentagoSize * 2; j++) {
			for (int i = 0; i < ((pentagoSize * 2) - 1); i++) {
				if (pentagoBoardFull[i][j].equals(currentColor.toLowerCase())
					&& pentagoBoardFull[i + 1][j].equals(currentColor.toLowerCase())) {
					
					utilityTotal += consecutiveCounter + 1;
					consecutiveCounter++;
				
				} else {
					consecutiveCounter = 0;
				}
			}
		}

		// Checks top left to bottom right diagonal victories, then top right to
		// bottom left diagonal victories
		for (int k = 0; k < (pentagoSize * 2); k++) {
			// Check to see where the starting I and J values should be, and if
			// it should be 5 or 6 indices
			if (k == 0) {
				startingI = 1;
				startingJ = 0;
				pentagoResize = 1;
			
			} else if (k == 1) {
				startingI = 0;
				startingJ = 0;
				pentagoResize = 0;
			
			} else if (k == 2) {
				startingI = 0;
				startingJ = 1;
				pentagoResize = 1;
			
			} else if (k == 3) {
				consecutiveCounter = 0;
				startingI = 1;
				startingJ = 5;
				pentagoResize = 1;
			
			} else if (k == 4) {
				startingI = 0;
				startingJ = 5;
				pentagoResize = 0;
			
			} else {
				startingI = 0;
				startingJ = 4;
				pentagoResize = 1;
			}

			// Checking top left to bottom right diagonal victories
			if (k < 3) {
				for (int i = 0; i < (((pentagoSize * 2) - pentagoResize) - 1); i++) {
					if (pentagoBoardFull[startingI + i][startingJ + i].equals(currentColor)
						&& pentagoBoardFull[startingI + i + 1][startingJ + i + 1].equals(currentColor)) {
						
						utilityTotal += consecutiveCounter + 1;
						consecutiveCounter++;
					
					} else {
						consecutiveCounter = 0;
					}
				}
			} else {
				for (int i = 0; i < (((pentagoSize * 2) - pentagoResize) - 1); i++) {
					if (pentagoBoardFull[startingI + i][startingJ - i].equals(currentColor)
						&& pentagoBoardFull[startingI + i + 1][startingJ - i - 1].equals(currentColor)) {
						
						utilityTotal += consecutiveCounter + 1;
						consecutiveCounter++;
					
					} else {
						consecutiveCounter = 0;
					}
				}
			}
		}
		theCurrentBoard.myBoardUtility = utilityTotal;
		return utilityTotal;
	}
	
	/* Function the AI uses to determine its best move. Recursively loads in all moves with the current
	 * state of the board, while alternating player turns. It will sum the utility values of each child node
	 * from both human and AI moves, the weight out the best move and plays that move. Uses Alpha-Beta pruning 
	 * to ignore unnecessary nodes to improve performance.
	 */
	public PentagoBoard minimaxAlphaBetaAIMove(PentagoBoard theCurrentBoard,
											   int theCurrentPlayer, 
											   int theDepth, 
											   int theAlpha, 
											   int theBeta) {
		
		PentagoBoard optimalBoard = theCurrentBoard;

		if (theDepth == 0) {
			return theCurrentBoard;
		}
		
		String[][] currentState = this.getBoardStateAsArray();
		LinkedList<PentagoBoard> possibleMoves = new LinkedList<PentagoBoard>();

		for (int i = 0; i < pentagoSize * 2; i++) {
			for (int j = 0; j < pentagoSize * 2; j++) {
				currentState = theCurrentBoard.getBoardStateAsArray();
				
				if (currentState[i][j].equals(".")) {
					if (theCurrentPlayer == 1) {
						if (this.myAIColor.toLowerCase().equals("b")) {
							currentState[i][j] = "w";
						
						} else {
							currentState[i][j] = "b";
						}
						
					} else {
						currentState[i][j] = theCurrentBoard.myAIColor.toLowerCase();
					}

					// Loop 4 times, each time taking the current index and
					// rotating each quadrant left and right, for a total of 8 moves
					for (int k = 1; k <= 4; k++) {

						PentagoBoard newBoardL = new PentagoBoard(currentState, theCurrentPlayer);
						PentagoBoard newBoardR = new PentagoBoard(currentState, theCurrentPlayer);

						newBoardL.rotateQuadrantLeft(newBoardL.getQuadrant(k));
						newBoardR.rotateQuadrantRight(newBoardR.getQuadrant(k));
						
						newBoardL.updateAIColor(theCurrentBoard.myAIColor, newBoardL);
						newBoardR.updateAIColor(theCurrentBoard.myAIColor, newBoardR);

						newBoardL.getUtility(theCurrentPlayer, newBoardL);
						newBoardR.getUtility(theCurrentPlayer, newBoardR);
						
						possibleMoves.add(newBoardL);
						possibleMoves.add(newBoardR);
					}
				}
			}
		}

		// Evaluate every move that was generated by the loops above
		for (int i = 0; i < possibleMoves.size(); i++) {
			if (theCurrentPlayer == 2) {
				
				PentagoBoard boardToEvaluate = possibleMoves.remove();

				// Recursively call the Minimax function with a decreasing
				// depth, and the alternate player for the board
				optimalBoard = minimaxAlphaBetaAIMove(boardToEvaluate, 1, (theDepth - 1), boardToEvaluate.myAlpha, boardToEvaluate.myBeta);

				// If the board that was returned has a better utility than the
				// current alpha, reassign alpha to that utility
				if (optimalBoard.myBoardUtility > theCurrentBoard.myAlpha) {
					optimalBoard.myAlpha = optimalBoard.myBoardUtility;

					// If the depth is lower than the initial board then keep
					// track of the previous move
					if (theDepth < 3) {
						optimalBoard.myInitialState = theCurrentBoard.getBoardStateAsArray();
					}
				}

				if (optimalBoard.myBeta <= optimalBoard.myAlpha) {
					break;
				}
				
			} else {
				PentagoBoard boardToEvaluate = possibleMoves.remove();

				optimalBoard = minimaxAlphaBetaAIMove(boardToEvaluate, 2, (theDepth - 1), boardToEvaluate.myAlpha, boardToEvaluate.myBeta);

				if (optimalBoard.myBoardUtility < theCurrentBoard.myBeta) {
					optimalBoard.myBeta = optimalBoard.myBoardUtility;

					if (theDepth < 3) {
						optimalBoard.myInitialState = theCurrentBoard.getBoardStateAsArray();
					}
				}

				if (optimalBoard.myBeta <= optimalBoard.myAlpha) {
					break;
				}
			}
		}
		return optimalBoard;
	}

	public void humanMove(String theCurrentMove, String thePlayerColor) {

		String[] currentMoveArray = theCurrentMove.split("");
		int quadrantNumber = Integer.parseInt(currentMoveArray[0]);
		int positionNumber = Integer.parseInt(currentMoveArray[2]);
		int quadrantRotation = Integer.parseInt(currentMoveArray[4]);
		String quadrantRotateDirection = currentMoveArray[5];

		int positionCounter = 1;

		// For loop to find the position, and then adds that letter to the
		// corresponding block
		for (int i = 0; i < pentagoSize; i++) {
			for (int j = 0; j < pentagoSize; j++) {
				
				if (positionCounter == positionNumber) {
					if (quadrantNumber == 1) {
						myQuadrantOne[i][j] = thePlayerColor.toLowerCase();
					
					} else if (quadrantNumber == 2) {
						myQuadrantTwo[i][j] = thePlayerColor.toLowerCase();
					
					} else if (quadrantNumber == 3) {
						myQuadrantThree[i][j] = thePlayerColor.toLowerCase();
					
					} else if (quadrantNumber == 4){
						myQuadrantFour[i][j] = thePlayerColor.toLowerCase();
					} else {
						throw new NumberFormatException("Invalid block, enter a quadrant between 1 and 4.");
					}
				}
				positionCounter++;
			}
		}

		if (quadrantRotateDirection.toLowerCase().equals("r")) {
			this.rotateQuadrantRight(this.getQuadrant(quadrantRotation));
		
		} else if (quadrantRotateDirection.toLowerCase().equals("l")) {
			this.rotateQuadrantLeft(this.getQuadrant(quadrantRotation));
		}
	}


	public boolean isMoveValid(String theCurrentMove) {

		String[] currentMoveArray = theCurrentMove.split("");
		int quadrantNumber = Integer.parseInt(currentMoveArray[0]);
		int position = Integer.parseInt(currentMoveArray[2]);

		int positionCounter = 1;

		for (int i = 0; i < pentagoSize; i++) {
			for (int j = 0; j < pentagoSize; j++) {
				
				if (positionCounter == position) {
					if (quadrantNumber == 1) {
						if (myQuadrantOne[i][j].equals("b") || myQuadrantOne[i][j].equals("w")) {
							return false;
						}
						
					} else if (quadrantNumber == 2) {
						if (myQuadrantTwo[i][j].equals("b") || myQuadrantTwo[i][j].equals("w")) {
							return false;
						}
						
					} else if (quadrantNumber == 3) {
						if (myQuadrantThree[i][j].equals("b") || myQuadrantThree[i][j].equals("w")) {
							return false;
						}
						
					} else if (quadrantNumber == 4){
						if (myQuadrantFour[i][j].equals("b") || myQuadrantFour[i][j].equals("w")) {
							return false;
						}
						
					} else {
						return false;
					}
				}
				positionCounter++;
			}
		}
		return true;
	}

	private String[][] getQuadrant(int theCurrentQuadrant) {
		if (theCurrentQuadrant == 1) {
			return myQuadrantOne;
		
		} else if (theCurrentQuadrant == 2) {
			return myQuadrantTwo;
		
		} else if (theCurrentQuadrant == 3) {
			return myQuadrantThree;
		
		} else if (theCurrentQuadrant == 4) {
			return myQuadrantFour;
		
		} else {
			return null;
		}
	}

	private String[][] rotateQuadrantRight(String[][] theCurrentBoard) {
		String tempForRight = "";
		for (int i = 0; i < pentagoSize / 2; i++) {
			for (int j = 0; j < (pentagoSize - 2 * i - 1); j++) {
				
				tempForRight = theCurrentBoard[i][i + j];
				theCurrentBoard[i][i + j] = theCurrentBoard[pentagoSize - i - j - 1][i];
				theCurrentBoard[pentagoSize - i - j - 1][i] = theCurrentBoard[pentagoSize - i - 1][pentagoSize - i - j - 1];
				theCurrentBoard[pentagoSize - i - 1][pentagoSize - i - j - 1] = theCurrentBoard[i + j][pentagoSize - i - 1];
				theCurrentBoard[i + j][pentagoSize - i - 1] = tempForRight;
			}
		}
		return theCurrentBoard;
	}

	private String[][] rotateQuadrantLeft(String[][] theCurrentBoard) {
		String tempForLeft = "";
		for (int i = 0; i < pentagoSize / 2; i++) {
			for (int j = i; j < (pentagoSize - i - 1); j++) {
				
				tempForLeft = theCurrentBoard[i][j];
				theCurrentBoard[i][j] = theCurrentBoard[j][pentagoSize - i - 1];
				theCurrentBoard[j][pentagoSize - i - 1] = theCurrentBoard[pentagoSize - i - 1][pentagoSize - j - 1];
				theCurrentBoard[pentagoSize - i - 1][pentagoSize - j - 1] = theCurrentBoard[pentagoSize - j - 1][i];
				theCurrentBoard[pentagoSize - j - 1][i] = tempForLeft;
			}
		}
		return theCurrentBoard;
	}
}
