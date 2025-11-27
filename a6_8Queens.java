// SimpleEightQueens.java
public class a6_8Queens {

    static final int N = 8;
    static int[] board = new int[N]; 
    static int solutionCount = 0;

    // Check if placing a queen at (row, col) is safe
    static boolean isSafe(int col, int row) {
        for (int c = 0; c < col; c++) {
            int r = board[c];
            // Same row OR same diagonal
            if (r == row || Math.abs(r - row) == Math.abs(c - col)) {
                return false;
            }
        }
        return true;
    }

    // Backtracking function to place queens column by column
    static void solve(int col) {
        // All queens placed
        if (col == N) {
            solutionCount++;
            printBoard();
            System.out.println();
            return;
        }

        // Try placing queen at each row in this column
        for (int row = 0; row < N; row++) {
            if (isSafe(col, row)) {
                board[col] = row;   // place queen
                solve(col + 1);     // recurse for next column
                // (No need to "undo" explicitly because board[col] will be overwritten in the next try)
            }
        }
    }

    // Print the chessboard
    static void printBoard() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (board[c] == r) {
                    System.out.print("Q ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        solve(0);
        System.out.println("Total solutions: " + solutionCount);
    }
}
