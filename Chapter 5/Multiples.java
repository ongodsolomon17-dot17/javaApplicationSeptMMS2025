import java.util.Scanner;

public class Multiples {

    // Method to check if the second number is a multiple of the first
    public static boolean isMultiple(int num1, int num2) {
        return num2 % num1 == 0;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        char choice;

        do {
            System.out.print("Enter the first integer: ");
            int num1 = input.nextInt();

            System.out.print("Enter the second integer: ");
            int num2 = input.nextInt();

            if (isMultiple(num1, num2))
                System.out.println(num2 + " is a multiple of " + num1);
            else
                System.out.println(num2 + " is NOT a multiple of " + num1);

            System.out.print("Do you want to check another pair? (y/n): ");
            choice = input.next().charAt(0);

            System.out.println();

        } while (choice == 'y' || choice == 'Y');

        input.close();
    }
}