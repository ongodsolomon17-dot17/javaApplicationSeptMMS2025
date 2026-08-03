public class EightQueens {

    static final int SIZE = 8;
    static int[] queens = new int[SIZE];

    public static boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col ||
                Math.abs(queens[i] - col) == Math.abs(i - row))
                return false;
        }
        return true;
    }

    public static boolean solve(int row) {

        if (row == SIZE)
            return true;

        for (int col = 0; col < SIZE; col++) {

            if (isSafe(row, col)) {

                queens[row] = col;

                if (solve(row + 1))
                    return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        if (solve(0)) {

            for (int i = 0; i < SIZE; i++) {

                for (int j = 0; j < SIZE; j++) {

                    if (queens[i] == j)
                        System.out.print(" Q ");
                    else
                        System.out.print(" . ");
                }

                System.out.println();
            }
        }
    }
}