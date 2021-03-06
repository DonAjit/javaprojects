package com.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

enum WhoseTurn {
	PLAYER, COMPUTER;
}

/**
 * @author Ajit Shikalgar
 *
 */
public class TicTacToe {

	// Main 3 * 3 board
	private char[][] board;

	// this will keep track of the available moves
	// if the cell value is true then it is filled else empty
	private boolean moves[];

	private static int rows = 3;
	private static int columns = 3;
	private static int tmove = 9;

	WhoseTurn whoseTurn = WhoseTurn.PLAYER;

	Random random = new Random();

	/**
	 * @param args
	 *            Driver method to implement TicTacToe
	 */
	public static void main(String args[]) {

		Scanner scanner  = new Scanner(System.in);
		int decision;
		boolean choice = true;
		while(choice) {
		TicTacToe ticTacToe = new TicTacToe(rows, columns, tmove);
		ticTacToe.play();
		System.out.println("1. Try Again");
		System.out.println("2. Quit");
		decision = scanner.nextInt();
		if(decision == 1)
			choice = true;
		else choice = false;
		
		}
		scanner.close();
	}

	public TicTacToe(int rows, int columns, int nMoves) {
		board = new char[rows][columns];

		moves = new boolean[nMoves];

		// initialize the board with empty character
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				board[i][j] = ' '; // initially empty board
			}
		}

	}

	/**
	 * Displaying the play board Row by Row
	 */
	private void showBoard() {
		System.out.printf("\t %c \t | \t %c \t | \t %c \n\n ", board[0][0], board[0][1], board[0][2]);

		System.out.printf("\t %c \t | \t %c \t | \t %c  \n\n", board[1][0], board[1][1], board[1][2]);

		System.out.printf("\t %c \t | \t %c \t | \t %c  \n \n \n", board[2][0], board[2][1], board[2][2]);
	}

	/**
	 * the sample board with instructions
	 */
	private void showInstructions() {
		System.out.println("\t\t\t  Tic-Tac-Toe\n\n");
		System.out.println("Choose a cell numbered from 1 to 9 as below" + " and play\n\n");
		System.out.println("\t 1 \t | \t 2 \t | \t 3  \n");
		System.out.println("\n");
		System.out.println("\t 4 \t | \t 5 \t | \t 6  \n");
		System.out.println("\n");
		System.out.println("\t 7 \t | \t 8 \t | \t 9  \n");

	}

	/**
	 * function to check who's the winner
	 */
	private void declareWinner() {

		// we use reverse logic since if we have winning condition true then the
		// player who played the last turn has won
		if (whoseTurn == WhoseTurn.COMPUTER) {
			System.out.println("PLAYER HAS WON");
		} else
			System.out.println("COMPUTER HAS WON");

	}

	/**
	 * function to check whether rows are crossed
	 * 
	 * @return
	 */
	private boolean rowCrossed() {
		for (int i = 0; i < rows; i++) {
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ')
				return (true);
		}

		return (false);
	}

	
	/**
	 * similar logic for columns
	 * @return
	 */
	private boolean columnCrossed() {
		for (int i = 0; i < columns; i++) {
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ')
				return (true);
		}

		return (false);
	}

	/**
	 * check diagonal crossed
	 * @return
	 */
	private boolean diagonalCrossed() {
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ')
			return (true);

		if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ')
			return (true);

		return (false);
	}

	/**
	 * if rowCrossed or columnCrossed or diagonalCrossed
	 * @return
	 */
	private boolean gameOver() {
		return (rowCrossed() || columnCrossed() || diagonalCrossed());
	}

	/**
	 * initialize game
	 */
	private void play() {

		int movesPlayed = 0;

		// move index from 1 to 9
		int moveIndex = 0;

		// for computing row and column
		int x = 0;
		int y = 0;

		// showBoard first
		showBoard();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			// keep playing till game is over or it's a draw
			while (gameOver() == false && movesPlayed != tmove) {

				// check who's turn it is and play accordingly
				if (whoseTurn == WhoseTurn.COMPUTER) {
					boolean correctCell = false;
					// keep trying using random no to fill a cell with O
					// simulating a computer

					while (correctCell == false) {
						moveIndex = random.nextInt(9);

						if (moves[moveIndex] == false) {

							// compute the cells row and column no
							x = (moveIndex) / rows; // can use column also
							y = (moveIndex) % rows;

							board[x][y] = 'O';
							System.out.println("Computer has placed O in cell " + moveIndex);

							correctCell = true;

							// increase the moves played
							movesPlayed++;

							// change the turn to Computer
							whoseTurn = WhoseTurn.PLAYER;

							// change the cell value to filled
							moves[moveIndex] = true;

							// showBoard
							showBoard();
						}
					}

				}

				else if (whoseTurn == WhoseTurn.PLAYER) {

					// showInstructions
					showInstructions();

					boolean correctCell = false;

					// keep asking the user to enter correct cell if already
					// filled
					while (correctCell == false) {

						// ask the player for placing his move as X
						moveIndex = Integer.parseInt(br.readLine()); // - 1
																		// since
																		// output
																		// cells
																		// start
																		// with
																		// 1

						// add validation if given cell no is not within the
						// specified range

						// add check whether the cell is already filled
						if (moves[moveIndex - 1] == true) {
							System.out.println("This cell is already filled please enter another cell no");
						}

						else {
							// compute the cells row and column no
							x = (moveIndex - 1) / rows; // can use column also
							y = (moveIndex - 1) % rows;

							board[x][y] = 'X';
							System.out.println("Player has placed X in cell " + moveIndex);

							correctCell = true;

							// increase the moves played
							movesPlayed++;

							// change the cell value to filled
							moves[moveIndex - 1] = true;

							// change the turn to Computer
							whoseTurn = WhoseTurn.COMPUTER;

							// showBoard
							showBoard();
						}

					}
				}

				// after each move check if the game is tied or anyone has won
				// the game
				if (gameOver() == false && movesPlayed == 9) {
					System.out.println("It's a draw!");
				}
				declareWinner();

				if (gameOver() == true) {

					declareWinner();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}