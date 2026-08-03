public class KnightsTour {

    static int[][] board = new int[8][8];

    static int[] horizontal = {2,1,-1,-2,-2,-1,1,2};
    static int[] vertical   = {-1,-2,-2,-1,1,2,2,1};

    public static void main(String[] args) {

        int row = 0;
        int col = 0;

        board[row][col] = 1;

        for (int move = 2; move <= 64; move++) {

            boolean moved = false;

            for (int i = 0; i < 8; i++) {

                int newRow = row + vertical[i];
                int newCol = col + horizontal[i];

                if (newRow >= 0 &&
                    newRow < 8 &&
                    newCol >= 0 &&
                    newCol < 8 &&
                    board[newRow][newCol] == 0) {

                    row = newRow;
                    col = newCol;
                    board[row][col] = move;

                    moved = true;
                    break;
                }
            }

            if (!moved)
                break;
        }

        for (int[] r : board) {

            for (int value : r)
                System.out.printf("%3d", value);

            System.out.println();
        }
    }
}