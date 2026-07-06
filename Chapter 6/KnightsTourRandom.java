import java.util.Random;

public class KnightsTourRandom {

    static Random rand = new Random();

    static int[] horizontal = {2,1,-1,-2,-2,-1,1,2};
    static int[] vertical = {-1,-2,-2,-1,1,2,2,1};

    public static void main(String[] args) {

        int[][] board = new int[8][8];

        int row = 0;
        int col = 0;

        board[row][col] = 1;

        int moves = 1;

        while (true) {

            int move = rand.nextInt(8);

            int newRow = row + vertical[move];
            int newCol = col + horizontal[move];

            if (newRow >= 0 &&
                newRow < 8 &&
                newCol >= 0 &&
                newCol < 8 &&
                board[newRow][newCol] == 0) {

                row = newRow;
                col = newCol;

                board[row][col] = ++moves;
            }

            boolean possible = false;

            for (int i = 0; i < 8; i++) {

                int r = row + vertical[i];
                int c = col + horizontal[i];

                if (r >= 0 &&
                    r < 8 &&
                    c >= 0 &&
                    c < 8 &&
                    board[r][c] == 0)

                    possible = true;
            }

            if (!possible)
                break;
        }

        System.out.println("Moves completed = " + moves);
    }
}