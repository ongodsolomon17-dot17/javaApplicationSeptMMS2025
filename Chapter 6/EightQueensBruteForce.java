public class EightQueensBruteForce {

    static int[] board = new int[8];

    static boolean valid() {

        for (int i = 0; i < 8; i++) {

            for (int j = i + 1; j < 8; j++) {

                if (board[i] == board[j] ||
                    Math.abs(board[i] - board[j]) == j - i)
                    return false;
            }
        }

        return true;
    }

    static boolean solve(int row) {

        if (row == 8)
            return valid();

        for (int col = 0; col < 8; col++) {

            board[row] = col;

            if (solve(row + 1))
                return true;
        }

        return false;
    }

    public static void main(String[] args) {

        if (solve(0)) {

            for (int r = 0; r < 8; r++) {

                for (int c = 0; c < 8; c++) {

                    if (board[r] == c)
                        System.out.print("Q ");
                    else
                        System.out.print(". ");
                }

                System.out.println();
            }
        }
    }
}