//3.23
import java.util.Scanner;

public class FindTwoLargest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        int largest1 = Integer.MIN_VALUE;
        int largest2 = Integer.MIN_VALUE;

        while (counter < 10) {
            System.out.print("Enter integer " + (counter + 1) + ": ");
            int number = scanner.nextInt();

            if (number > largest1) {
                largest2 = largest1; // Shift previous largest down
                largest1 = number;   // Assign new largest
            } else if (number > largest2) {
                largest2 = number;   // Update second largest only
            }
            counter++;
        }

        System.out.println("The largest number is: " + largest1);
        System.out.println("The second largest number is: " + largest2);
        scanner.close();
    }
}