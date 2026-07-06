import java.util.Scanner;

public class EvenOrOdd {

    // Method to check if a number is even
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        char choice;

        do {
            System.out.print("Enter an integer: ");
            int number = input.nextInt();

            if (isEven(number))
                System.out.println(number + " is even.");
            else
                System.out.println(number + " is odd.");

            System.out.print("Do you want to check another number? (y/n): ");
            choice = input.next().charAt(0);

            System.out.println();

        } while (choice == 'y' || choice == 'Y');

        input.close();
    }
}