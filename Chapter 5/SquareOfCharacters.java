import java.util.Scanner;

public class SquareOfCharacters {

    // Method to display square using any character
    public static void squareOfAsterisks(int side, char fillCharacter) {

        for (int i = 1; i <= side; i++) {
            for (int j = 1; j <= side; j++) {
                System.out.print(fillCharacter);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter side length: ");
        int side = input.nextInt();

        System.out.print("Enter fill character: ");
        char fill = input.next().charAt(0);

        squareOfAsterisks(side, fill);

        input.close();
    }
}