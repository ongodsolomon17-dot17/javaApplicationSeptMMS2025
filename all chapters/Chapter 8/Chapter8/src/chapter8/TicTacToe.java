
package chapter8;

import java.util.Scanner;

public class TicTacToe {
    private final CellState[][] board;
    private CellState currentPlayer;

    // Constructor initializes 3x3 board to EMPTY and sets Player X to start
    public TicTacToe() {
        board = new CellState[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = CellState.EMPTY;
            }
        }
        currentPlayer = CellState.X;
    }

    // Play loop for two human players
    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameEnded = false;

        System.out.println("=== Welcome to Tic-Tac-Toe ===");
        printBoard();

        while (!gameEnded) {
            System.out.printf("%nPlayer %s's turn.%n", currentPlayer);
            int row = getValidCoordinate(scanner, "row (0, 1, or 2): ");
            int col = getValidCoordinate(scanner, "column (0, 1, or 2): ");

            if (makeMove(row, col)) {
                printBoard();

                if (checkWin(currentPlayer)) {
                    System.out.printf("%n🎉 Player %s WINS! 🎉%n", currentPlayer);
                    gameEnded = true;
                } else if (isBoardFull()) {
                    System.out.println("%n🤝 The game is a DRAW!");
                    gameEnded = true;
                } else {
                    switchPlayer();
                }
            } else {
                System.out.println("❌ Invalid move! That square is already taken. Try again.");
            }
        }
    }

    // Attempt to make a move at (row, col)
    private boolean makeMove(int row, int col) {
        if (board[row][col] == CellState.EMPTY) {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    // Prompt user for valid integer (0-2)
    private int getValidCoordinate(Scanner scanner, String prompt) {
        int val = -1;
        while (val < 0 || val > 2) {
            System.out.print("Enter " + prompt);
            if (scanner.hasNextInt()) {
                val = scanner.nextInt();
                if (val < 0 || val > 2) {
                    System.out.println("Value must be between 0 and 2.");
                }
            } else {
                System.out.println("Invalid input! Please enter an integer (0, 1, or 2).");
                scanner.next(); // Clear invalid token
            }
        }
        return val;
    }

    // Switch active player
    private void switchPlayer() {
        currentPlayer = (currentPlayer == CellState.X) ? CellState.O : CellState.X;
    }

    // Check all rows, columns, and diagonals for 3 matching symbols
    private boolean checkWin(CellState p) {
        // Check Rows & Columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == p && board[i][1] == p && board[i][2] == p) ||
                (board[0][i] == p && board[1][i] == p && board[2][i] == p)) {
                return true;
            }
        }

        // Check Diagonals
        return (board[0][0] == p && board[1][1] == p && board[2][2] == p) ||
               (board[0][2] == p && board[1][1] == p && board[2][0] == p);
    }

    // Check if board is completely filled
    private boolean isBoardFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == CellState.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // Display the 3x3 board
    public void printBoard() {
        System.out.println("\n  0   1   2 ");
        for (int r = 0; r < 3; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < 3; c++) {
                char symbol = switch (board[r][c]) {
                    case X -> 'X';
                    case O -> 'O';
                    case EMPTY -> ' ';
                };
                System.out.print(" " + symbol + " ");
                if (c < 2) System.out.print("|");
            }
            System.out.println();
            if (r < 2) System.out.println("  ---+---+---");
        }
    }
}