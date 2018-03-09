/* 
 * TCSS 435 Autumn 2017
 * 
 * Assignment #1 - Solving the 15-Puzzle
 */

/**
 * Main method, including search algorithms to solve the 15-puzzle.
 * 
 * @author Tim Liu
 * @version October 2017
 */

import java.util.*;

public final class FifteenPuzzle {
	
	private static GameBoard myBoard;
	private static int myNumCreated = 0;
	private static int myMaxFringe = 0;
	private static int myNumExpanded = 0;


	public static void main(String[] args) {
		String[] initialState = args[0].split("");
		String searchAlgo = args[1];
		String options = new String();
		
		if (args.length > 2) {
			options = args[2];
		}
		
		myBoard = new GameBoard(initialState, 0);
		
		if (searchAlgo.equals("BFS")) {
			BFS();
			
		} else if (searchAlgo.equals("DFS")){
			DFS();
			
		} else if (searchAlgo.equals("DLS")) {
			int depthLimit = Integer.parseInt(options);
			DLS(depthLimit);
			
		} else if (searchAlgo.equals("GBFS")) {
			if(options.equals("h1")){
				GBFS_h1();
				
			} else {
				GBFS_h2();
			}
			
		} else if (searchAlgo.equals("AStar")) {
			if(options.equals("h1")) {
				AStar_h1();
				
			} else {
				AStar_h2();
			}
		}
	}
	/* Function that moves tiles in order in the Priority Queue variety */
	
	private static void placeTilesInOrderPrQueue(GameBoard currentMove, 
												String[] currentState,
												PriorityQueue<GameBoard> queuedMoves,
												LinkedList<GameBoard> visitedMoves) {
		//Right
		currentState = currentMove.getCurrentState();
		GameBoard moveRightState = new GameBoard(currentState, currentMove.depth + 1);
		moveRightState = moveRightState.moveRight();
		if (moveRightState != null) {
			moveRightState.printGameBoard();
			queuedMoves.add(moveRightState);
			myNumCreated++;
		
		} else {
			System.out.println("Invalid move. \n");
		}
		
		//Down
		currentState = currentMove.getCurrentState();
		GameBoard moveDownState = new GameBoard(currentState, currentMove.depth + 1);
		moveDownState = moveDownState.moveDown();
		if (moveDownState != null) {
			moveDownState.printGameBoard();
			queuedMoves.add(moveDownState);
			myNumCreated++;
		
		} else {
			System.out.println("Invalid move. \n");
		}
		
		//Left
		currentState = currentMove.getCurrentState();
		GameBoard moveLeftState = new GameBoard(currentState, currentMove.depth + 1);
		moveLeftState = moveLeftState.moveLeft();
		if (moveLeftState != null) {
			moveLeftState.printGameBoard();
			queuedMoves.add(moveLeftState);
			myNumCreated++;
		
		} else {
			System.out.println("Invalid move. \n");
		}
		
		//Up
		currentState = currentMove.getCurrentState();
		GameBoard moveUpState = new GameBoard(currentState, currentMove.depth + 1);
		moveUpState = moveUpState.moveUp();
		if (moveUpState != null) {
			moveUpState.printGameBoard();
			queuedMoves.add(moveUpState);
			myNumCreated++;
		
		} else {
			System.out.println("Invalid move. \n");
		}
	}
	
	/* BFS algorithm used to solve the puzzle */
	private static void BFS() {
		
		String[] currentState = null;
		
		System.out.println("Initial for BFS: ");
		myBoard.printGameBoard();
		
		Queue<GameBoard> queuedMoves = new LinkedList<GameBoard>();
		LinkedList<GameBoard> visitedMoves = new LinkedList<GameBoard>();
		
		queuedMoves.add(myBoard);
		myMaxFringe = queuedMoves.size();
		
		while(queuedMoves.size() > 0) {
			GameBoard currentMove = queuedMoves.remove();
			if (!visitedMoves.contains(currentMove)) {
				myNumExpanded++;
				
				if (queuedMoves.size() > myMaxFringe) {
					myMaxFringe = queuedMoves.size();
				}
				
				visitedMoves.add(currentMove);
				System.out.println("Current board for BFS: ");
				currentMove.printGameBoard();
				
				if (currentMove.isSolved()) {
					System.out.println("BFS: ");
					System.out.print("Depth: " + currentMove.depth);
					System.out.print(", Number created: " + myNumCreated);
					System.out.print(", Number expanded: " + myNumExpanded);
					System.out.print(", Max fringe: " + myMaxFringe);
					
					return;
				}
				
				//placeTilesInOrderQueue(currentMove, currentState, queuedMoves, visitedMoves);
				
				//Right
				currentState = currentMove.getCurrentState();
				GameBoard moveRightState = new GameBoard(currentState, currentMove.depth + 1);
				moveRightState = moveRightState.moveRight();
				if (moveRightState != null) {
					moveRightState.printGameBoard();
					queuedMoves.add(moveRightState);
					myNumCreated++;
				
				} else {
					System.out.println("Move is invalid.\n");
				}
				
				//Down
				currentState = currentMove.getCurrentState();
				GameBoard moveDownState = new GameBoard(currentState, currentMove.depth + 1);
				moveDownState = moveDownState.moveDown();
				if (moveDownState != null) {
					moveDownState.printGameBoard();
					queuedMoves.add(moveDownState);
					myNumCreated++;
				
				} else {
					System.out.println("Move is invalid.\n");
				}
				
				//Left
				currentState = currentMove.getCurrentState();
				GameBoard moveLeftState = new GameBoard(currentState, currentMove.depth + 1);
				moveLeftState = moveLeftState.moveLeft();
				if (moveLeftState != null) {
					moveLeftState.printGameBoard();
					queuedMoves.add(moveLeftState);
					myNumCreated++;
				
				} else {
					System.out.println("Move is invalid.\n");
				}
				
				//Up
				currentState = currentMove.getCurrentState();
				GameBoard moveUpState = new GameBoard(currentState, currentMove.depth + 1);
				moveUpState = moveUpState.moveUp();
				if (moveUpState != null) {
					moveUpState.printGameBoard();
					queuedMoves.add(moveUpState);
					myNumCreated++;
				
				} else {
					System.out.println("Move is invalid.\n");
				}
				
			} else {
				System.out.println("Visited");
			}
		}
		System.out.println("Failed to find solution.");
	}
	
	/* DFS algorithm used to solve the puzzle */
	private static void DFS() {
		String[] currentState = null;
		
		myBoard.printGameBoard();
		
		Stack<GameBoard> queuedMoves = new Stack<GameBoard>();
		LinkedList<GameBoard> visitedMoves = new LinkedList<GameBoard>();
		
		queuedMoves.push(myBoard);
		myMaxFringe = queuedMoves.size();
		
		while(queuedMoves.size() > 0) {
			GameBoard currentMove = queuedMoves.pop();
			
			if (!visitedMoves.contains(currentMove)) {
				myNumExpanded++;
				
				if(queuedMoves.size() > myMaxFringe) {
					myMaxFringe = queuedMoves.size();
				}
				
				visitedMoves.add(currentMove);
				currentMove.printGameBoard();
				
				if(currentMove.isSolved()) {
					System.out.println("DFS: ");
					System.out.print("Depth: " + currentMove.depth);
					System.out.print(", Numbers created: " + myNumCreated);
					System.out.print(", Numbers expanded: " + myNumExpanded);
					System.out.print(", Max fringe: " + myMaxFringe);
					return;
				}
				
				//Up
				currentState = currentMove.getCurrentState();
				GameBoard moveUpState = new GameBoard(currentState,currentMove.depth + 1); 
				moveUpState = moveUpState.moveUp();
				if (moveUpState != null) {
					moveUpState.printGameBoard();
					if (!queuedMoves.contains(moveUpState) && !visitedMoves.contains(moveUpState)) {
						queuedMoves.push(moveUpState);
						myNumCreated++;
					}
				} else {
					System.out.println("Move is invalid.\n");
				}
				
				//Left
				currentState = currentMove.getCurrentState();
				GameBoard moveLeftState = new GameBoard(currentState,currentMove.depth + 1); 
				moveLeftState = moveLeftState.moveLeft();
				
				System.out.println("Moved left");
				if(moveLeftState != null) {
					moveLeftState.printGameBoard();
					
					if(!queuedMoves.contains(moveLeftState) && !visitedMoves.contains(moveLeftState)) {
						queuedMoves.push(moveLeftState);
						myNumCreated++;
					}
				} else {
					System.out.println("Move is invalid.\n");
				}
				
				//Right
				currentState = currentMove.getCurrentState();
				GameBoard moveRightState = new GameBoard(currentState,currentMove.depth + 1); 
				moveRightState = moveRightState.moveRight();
				System.out.println("RIGHT");
				if (moveRightState != null) {
					moveRightState.printGameBoard();
					if (!queuedMoves.contains(moveRightState) && !visitedMoves.contains(moveRightState)) {
						queuedMoves.push(moveRightState);
						myNumCreated++;
					}
					
				} else {
					System.out.println("Move is invalid.\n");
				}
				
				//Down
				currentState = currentMove.getCurrentState();
				GameBoard moveDownState = new GameBoard(currentState,currentMove.depth + 1); 
				moveDownState = moveDownState.moveDown();
				
				if (moveDownState != null) {
					moveDownState.printGameBoard();
					
					if (!queuedMoves.contains(moveDownState) && !visitedMoves.contains(moveDownState)) {
						queuedMoves.push(moveDownState);
						myNumCreated++;
					}
					
				} else {
					System.out.println("Move is invalid.\n");
				}
				
				
			} else {
				System.out.println("VISITED");
			}
		}
		System.out.println("Solution could not be found.");
	}
	
	private static void GBFS_h1() {
		String[] currentState = null;
		System.out.println("Initial for GBFS with h1: ");
		myBoard.printGameBoard();
		
		Comparator<GameBoard> comparator = new Comparator<GameBoard>() {
			@Override
			public int compare(GameBoard boardObjectOne, GameBoard boardObjectTwo) {
				return (boardObjectOne.getMismatchedTiles() - boardObjectTwo.getMismatchedTiles());
			}
		};
		
		PriorityQueue<GameBoard> queuedMoves = new PriorityQueue<GameBoard>(10, comparator);
		LinkedList<GameBoard> visitedMoves = new LinkedList<GameBoard>();
		
		queuedMoves.add(myBoard);
		myMaxFringe = queuedMoves.size();
		
		while(queuedMoves.size() > 0) {
			GameBoard currentMove = queuedMoves.remove();
			if (!visitedMoves.contains(currentMove)) {
				myNumExpanded++;
				
				if(queuedMoves.size() > myMaxFringe) {
					myMaxFringe = queuedMoves.size();
				}
				
				visitedMoves.add(currentMove);
				currentMove.printGameBoard();
				
				if (currentMove.isSolved()) {
					System.out.println("GBFS h1: ");
					System.out.print("Depth: " + currentMove.depth);
					System.out.print(", Number created: " + myNumCreated);
					System.out.print(", Number expanded: " + myNumExpanded);
					System.out.print(", Max fringe: " + myMaxFringe);
					
					return;
				}
				
				placeTilesInOrderPrQueue(currentMove, currentState, queuedMoves, visitedMoves);
				
			} else {
				System.out.println("Visited");
			}
		}
		
		System.out.println("Failed to find solution.");
	}
	
	private static void GBFS_h2() {
		String[] currentState = null;
		System.out.println("Initial for GBFS with h2: ");
		myBoard.printGameBoard();
		
		Comparator<GameBoard> comparator = new Comparator<GameBoard>() {
			@Override
			public int compare(GameBoard boardObjectOne, GameBoard boardObjectTwo) {
				return (boardObjectOne.calcManhattanSum() - boardObjectTwo.calcManhattanSum());
			}
		};
		
		PriorityQueue<GameBoard> queuedMoves = new PriorityQueue<GameBoard>(10, comparator);
		LinkedList<GameBoard> visitedMoves = new LinkedList<GameBoard>();
		
		queuedMoves.add(myBoard);
		myMaxFringe = queuedMoves.size();
		
		while(queuedMoves.size() > 0) {
			GameBoard currentMove = queuedMoves.remove();
			if (!visitedMoves.contains(currentMove)) {
				myNumExpanded++;
				
				if(queuedMoves.size() > myMaxFringe) {
					myMaxFringe = queuedMoves.size();
				}
				
				visitedMoves.add(currentMove);
				currentMove.printGameBoard();
				
				if (currentMove.isSolved()) {
					System.out.println("GBFS h2: ");
					System.out.print("Depth: " + currentMove.depth);
					System.out.print(", Number created: " + myNumCreated);
					System.out.print(", Number expanded: " + myNumExpanded);
					System.out.print(", Max fringe: " + myMaxFringe);
					
					return;
				}
				
				placeTilesInOrderPrQueue(currentMove, currentState, queuedMoves, visitedMoves);
				
			} else {
				System.out.println("Visited");
			}
		}
		
		System.out.println("Failed to find solution.");
	}
	
	private static void AStar_h1() {
		String[] currentState = null;
		System.out.println("Initial for Astar with h1: ");
		myBoard.printGameBoard();
		
		Comparator<GameBoard> comparator = new Comparator<GameBoard>() {
			@Override
			public int compare(GameBoard boardObjectOne, GameBoard boardObjectTwo) {
				return ((boardObjectOne.getMismatchedTiles() + boardObjectOne.depth) - 
						(boardObjectTwo.getMismatchedTiles() + boardObjectTwo.depth));
			}
		};
		
		PriorityQueue<GameBoard> queuedMoves = new PriorityQueue<GameBoard>(10, comparator);
		LinkedList<GameBoard> visitedMoves = new LinkedList<GameBoard>();
		
		queuedMoves.add(myBoard);
		myMaxFringe = queuedMoves.size();
		
		while(queuedMoves.size() > 0) {
			GameBoard currentMove = queuedMoves.remove();
			if (!visitedMoves.contains(currentMove)) {
				myNumExpanded++;
				
				if(queuedMoves.size() > myMaxFringe) {
					myMaxFringe = queuedMoves.size();
				}
				
				visitedMoves.add(currentMove);
				currentMove.printGameBoard();
				
				if (currentMove.isSolved()) {
					System.out.println("Astar h1: ");
					System.out.print("Depth: " + currentMove.depth);
					System.out.print(", Number created: " + myNumCreated);
					System.out.print(", Number expanded: " + myNumExpanded);
					System.out.print(", Max fringe: " + myMaxFringe);
					
					return;
				}
				
				placeTilesInOrderPrQueue(currentMove, currentState, queuedMoves, visitedMoves);
			
			} else {
				System.out.println("Visited");
			}
		}
		
		System.out.println("Failed to find solution.");
	}
	
	private static void AStar_h2() {
		String[] currentState = null;
		System.out.println("Initial for AStar with h2: ");
		myBoard.printGameBoard();
		
		Comparator<GameBoard> comparator = new Comparator<GameBoard>() {
			@Override
			public int compare(GameBoard boardObjectOne, GameBoard boardObjectTwo) {
				return ((boardObjectOne.calcManhattanSum() + boardObjectOne.depth) - 
						(boardObjectTwo.calcManhattanSum() + boardObjectTwo.depth));
			}
		};
		
		PriorityQueue<GameBoard> queuedMoves = new PriorityQueue<GameBoard>(10, comparator);
		LinkedList<GameBoard> visitedMoves = new LinkedList<GameBoard>();
		
		queuedMoves.add(myBoard);
		myMaxFringe = queuedMoves.size();
		
		while(queuedMoves.size() > 0) {
			GameBoard currentMove = queuedMoves.remove();
			if (!visitedMoves.contains(currentMove)) {
				myNumExpanded++;
				
				if(queuedMoves.size() > myMaxFringe) {
					myMaxFringe = queuedMoves.size();
				}
				
				visitedMoves.add(currentMove);
				currentMove.printGameBoard();
				
				if (currentMove.isSolved()) {
					System.out.println("Astar h2: ");
					System.out.print("Depth: " + currentMove.depth);
					System.out.print(", Number created: " + myNumCreated);
					System.out.print(", Number expanded: " + myNumExpanded);
					System.out.print(", Max fringe: " + myMaxFringe);
					
					return;
				}
				
				placeTilesInOrderPrQueue(currentMove, currentState, queuedMoves, visitedMoves);
				
			} else {
				System.out.println("Visited");
			}
		}
		
		System.out.println("Failed to find solution.");
	}
	
	/* Depth-limited search algorithm used to solve the puzzle. Takes in a depth integer as parameter. */
	
	private static void DLS(int depthLimit) {
		String[] currentState = null;
		
		System.out.println("Initial for DLS with depth " + depthLimit + ":");
		myBoard.printGameBoard();
		
		Stack<GameBoard> queuedMoves = new Stack<GameBoard>();
		LinkedList<GameBoard> visitedMoves = new LinkedList<GameBoard>();
		
		queuedMoves.push(myBoard);
		myMaxFringe = queuedMoves.size();
		
		while (queuedMoves.size() > 0) {
			GameBoard currentMove = queuedMoves.pop();
			
			if (currentMove.depth >= depthLimit) {
				System.out.println("Depth limit reached.");
			
			} else if (!visitedMoves.contains(currentMove)) {
				myNumExpanded++;
				
				if(queuedMoves.size() > myMaxFringe) {
					myMaxFringe = queuedMoves.size();
				}
				
				visitedMoves.add(currentMove);
				currentMove.printGameBoard();
				
				if (currentMove.isSolved()) {
					System.out.println("DLS of depth limit " + depthLimit + ":");
					System.out.print("Depth: " + currentMove.depth);
					System.out.print(", Number created: " + myNumCreated);
					System.out.print(", Number expanded: " + myNumExpanded);
					System.out.print(", Max fringe: " + myMaxFringe);
					
					return;
				}
			
					
				//Right
				currentState = currentMove.getCurrentState();
				GameBoard moveRightState = new GameBoard(currentState, currentMove.depth + 1);
				moveRightState = moveRightState.moveRight();
				
				if (moveRightState != null) {
					moveRightState.printGameBoard();
					
					if (!visitedMoves.contains(moveRightState) && moveRightState.depth <= depthLimit) {
						queuedMoves.push(moveRightState);
						myNumCreated++;
					}
					
				} else {
					System.out.println("MOVE IS INVALID\n");
				}
				
				//Down
				currentState = currentMove.getCurrentState();
				GameBoard moveDownState = new GameBoard(currentState, currentMove.depth + 1);
				moveDownState = moveDownState.moveDown();
				if (moveDownState != null) {
					moveDownState.printGameBoard();
					
					if (!visitedMoves.contains(moveDownState) && moveDownState.depth <= depthLimit) {
						queuedMoves.push(moveDownState);
						myNumCreated++;
					}
				
				} else {
					System.out.println("MOVE IS INVALID\n");
				}
				
				//Left
				currentState = currentMove.getCurrentState();
				GameBoard moveLeftState = new GameBoard(currentState, currentMove.depth + 1);
				moveLeftState = moveLeftState.moveLeft();
				if (moveLeftState != null) {
					moveLeftState.printGameBoard();
					
					if (!visitedMoves.contains(moveLeftState) && moveLeftState.depth <= depthLimit) {
						queuedMoves.push(moveLeftState);
						myNumCreated++;
					}
				
				} else {
					System.out.println("MOVE IS INVALID\n");
				}
				
				//Up
				currentState = currentMove.getCurrentState();
				GameBoard moveUpState = new GameBoard(currentState, currentMove.depth + 1);
				moveUpState = moveUpState.moveUp();
				if (moveUpState != null) {
					moveUpState.printGameBoard();
					
					if (!visitedMoves.contains(moveUpState) && moveUpState.depth <= depthLimit) {
						queuedMoves.push(moveUpState);
						myNumCreated++;
					}
				} else {
					System.out.println("MOVE IS INVALID\n");
				}
				

			} else {
				System.out.println("Visited");
			}
		}
		
		System.out.println("Failed to find a solution.");
	}
}
