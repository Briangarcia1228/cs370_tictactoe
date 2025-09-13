import java.util.Scanner;

public class TicTacToe {
    // this is a constant for the boardsize
    public static final int BOARDSIZE = 3;

    // this will hold the possible status that the game can be in
    public enum Status { WIN, DRAW, CONTINUE }

    public char[][] board;   // the game board
    public boolean firstPlayer; // track if it’s player X’s turn
    public boolean gameOver;    // track if the game has ended

    //  constructor initializes the board and game state
    public TicTacToe() {
        board = new char[BOARDSIZE][BOARDSIZE]; // create the board
        firstPlayer = true;  // player X starts first
        gameOver = false;    // game is not over at the start

        // fill the board with empty spaces
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                board[row][col] = ' ';
            }
        }
    }

    //entry point of the program
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe(); // create a new game
        game.play(); // start the game
    }

    // main game loop: handles turns and checks for win/draw
    public void play() {
        Scanner sc = new Scanner(System.in);
        int currentPlayer = 1; // 1 = X, 2 = O

        while (!gameOver) {
            printBoard();           // show the board
            printStatus(currentPlayer); // show whose turn it is

            // ask user for input
            System.out.print("Enter row (1-3): ");
            int row = sc.nextInt();
            System.out.print("Enter column (1-3): ");
            int col = sc.nextInt();

            // determine the current player’s symbol
            char symbol = (currentPlayer == 1) ? 'X' : 'O';

            // if move is valid, place the symbol
            if (validMove(col, row)) {
                placeSymbol(col, row, symbol);

                // check the status after the move
                Status status = gameStatus();
                if (status == Status.WIN) {
                    printBoard();
                    System.out.println("Player " + (currentPlayer == 1 ? "X" : "O") + " wins!");
                    gameOver = true;
                } else if (status == Status.DRAW) {
                    printBoard();
                    System.out.println("It's a draw!");
                    gameOver = true;
                } else {
                    // switch players if the game continues
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                }
            }
        }
        sc.close(); // close the scanner when the game is done
    }

    // show whose turn it is
    public void printStatus(int player) {
        System.out.println(player == 1 ? "Player X's turn" : "Player O's turn");
    }

    // check if the game is won, drawn, or still continuing
    public Status gameStatus() {
        // check rows
        for (int row = 0; row < BOARDSIZE; row++) {
            if (board[row][0] != ' ' && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                return Status.WIN;
            }
        }

        // check columns
        for (int col = 0; col < BOARDSIZE; col++) {
            if (board[0][col] != ' ' && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                return Status.WIN;
            }
        }

        // check diagonals
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return Status.WIN;
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return Status.WIN;
        }

        // check for empty spaces
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                if (board[row][col] == ' ') {
                    return Status.CONTINUE; // still playable
                }
            }
        }

        // if no empty spaces and no win, it’s a draw
        return Status.DRAW;
    }

    // display the board
    public void printBoard() {
        for (int row = 0; row < BOARDSIZE; row++) {
            System.out.println("-------------------------"); // row border
            System.out.print('|');
            for (int col = 0; col < BOARDSIZE; col++) {
                System.out.print("\t" + board[row][col] + "\t|"); // print each cell
            }
            System.out.println(); // move to next row
        }
        System.out.println("-------------------------"); // bottom border
    }

    // place a symbol on the board
    public void placeSymbol(int col, int row, char val) {
        if (validMove(col, row)) { // only if the move is valid
            board[row - 1][col - 1] = val; // adjust for 0-based index
        }
    }

    // check if a move is valid (inside board and empty space)
    public boolean validMove(int col, int row) {
        if (col >= 1 && col <= BOARDSIZE && row >= 1 && row <= BOARDSIZE) {
            if (board[row - 1][col - 1] == ' ') {
                return true; // move is valid
            }
        }
        System.out.println("Invalid move, please try again");
        return false; // invalid otherwise
    }
}


