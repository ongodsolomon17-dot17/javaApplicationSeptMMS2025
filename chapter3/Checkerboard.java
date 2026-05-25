//3.25 
public class Checkerboard {
    public static void main(String[] args) {
        int rows = 8;
        int columns = 8;

        for (int row = 1; row <= rows; row++) {
            // Indents every alternate row
            if (row % 2 == 0) {
                System.out.print(" ");
            }

            for (int col = 1; col <= columns; col++) {
                System.out.print("* ");
            }
            
            System.out.println();
        }
    }
}