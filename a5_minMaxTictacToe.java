import java.util.*;

public class a5_minMaxTictacToe {
    static char[][] board = {
        {'_', '_', '_'},
        {'_', '_', '_'},
        {'_', '_', '_'}
    };

    static final char HUMAN = 'X';
    static final char AI = 'O';

    // Print board
    static void printBoard() {
        for (char[] row : board) {
            for (char c : row)
            System.out.print(c + " ");
            System.out.println();
        }
        System.out.println();
    }

    // Check if any moves are left
    static boolean isMovesLeft() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }

    // Evaluate board score
    static int evaluate() {
        // Rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == AI) return +10;
                if (board[i][0] == HUMAN) return -10;
            }
        }
        // Columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                if (board[0][j] == AI) return +10;
                if (board[0][j] == HUMAN) return -10;
            }
        }
        // Diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == AI) return +10;
            if (board[0][0] == HUMAN) return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == AI) return +10;
            if (board[0][2] == HUMAN) return -10;
        }

        return 0; // no winner yet
    }

    // Minimax function
    static int minimax(boolean isAI) {
        int score = evaluate();

        if (score == 10) return score;
        if (score == -10) return score;
        if (!isMovesLeft()) return 0;

        if (isAI) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = AI;
                        best = Math.max(best, minimax(false));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        } else
         {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = HUMAN;
                        best = Math.min(best, minimax(true));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    // Find best move for AI
    static int[] findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = AI;
                    int moveVal = minimax(false);
                    board[i][j] = '_';

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("You are X | Computer is O");
        printBoard();

        while (true) {
            // Human move
            System.out.print("Enter your move (row col): ");
            int r = sc.nextInt();
            int c = sc.nextInt();
            if (r < 0 || r > 2 || c < 0 || c > 2 || board[r][c] != '_') {
                System.out.println("Invalid move, try again.");
                continue;
            }
            board[r][c] = HUMAN;
            printBoard();

            if (evaluate() == -10) {
                System.out.println("You win!");
                break;
            }
            if (!isMovesLeft()) {
                System.out.println(" It's a draw!");
                break;
            }

            // AI move
            int[] move = findBestMove();
            board[move[0]][move[1]] = AI;
            System.out.println("Computer chose: " + move[0] + " " + move[1]);
            printBoard();

            if (evaluate() == 10) {
                System.out.println("Computer wins!");
                break;
            }
            if (!isMovesLeft()) {
                System.out.println("It's a draw!");
                break;
            }
        }

        sc.close();
    }
}
