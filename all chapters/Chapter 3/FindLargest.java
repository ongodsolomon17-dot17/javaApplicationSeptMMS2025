import java.util.Scanner;
//3.21
public class FindLargest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        int largest = 0;

        while (counter < 10) {
            System.out.print("Enter integer " + (counter + 1) + ": ");
            int number = scanner.nextInt();

            if (number > largest) {
                largest = number;
            }
            counter++;
        }
        System.out.println("The largest integer entered is: " + largest);
        scanner.close();
    }
}