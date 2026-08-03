//3.24 .
import java.util.Scanner;

public class InputValidation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int value = 0;

        while (value != 1 && value != 2) {
            System.out.print("Enter a correct value (1 or 2): ");
            value = scanner.nextInt();

            if (value != 1 && value != 2) {
                System.out.println("Invalid input. Please try again.");
            }
        }
        System.out.println("Valid value entered: " + value);
        scanner.close();
    }
}