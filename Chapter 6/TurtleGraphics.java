public class TurtleGraphics {

    static int[][] floor = new int[20][20];

    static int row = 0;
    static int col = 0;

    static boolean penDown = false;

    // 0=Right, 1=Down, 2=Left, 3=Up
    static int direction = 0;

    public static void move(int steps) {

        for (int i = 0; i < steps; i++) {

            if (penDown)
                floor[row][col] = 1;

            switch (direction) {

                case 0:
                    if (col < 19)
                        col++;
                    break;

                case 1:
                    if (row < 19)
                        row++;
                    break;

                case 2:
                    if (col > 0)
                        col--;
                    break;

                case 3:
                    if (row > 0)
                        row--;
                    break;
            }

            if (penDown)
                floor[row][col] = 1;
        }
    }

    public static void displayFloor() {

        for (int i = 0; i < 20; i++) {

            for (int j = 0; j < 20; j++) {

                if (floor[i][j] == 1)
                    System.out.print("* ");
                else
                    System.out.print("  ");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {

        int[][] commands = {
                {2},
                {5,12},
                {3},
                {5,12},
                {3},
                {5,12},
                {3},
                {5,12},
                {1},
                {6},
                {9}
        };

        for (int[] command : commands) {

            switch (command[0]) {

                case 1:
                    penDown = false;
                    break;

                case 2:
                    penDown = true;
                    break;

                case 3:
                    direction = (direction + 1) % 4;
                    break;

                case 4:
                    direction = (direction + 3) % 4;
                    break;

                case 5:
                    move(command[1]);
                    break;

                case 6:
                    displayFloor();
                    break;

                case 9:
                    return;
            }
        }
    }
}