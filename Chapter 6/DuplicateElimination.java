import java.util.Scanner;

public class DuplicateElimination {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int[] numbers = new int[5];
        int count = 0;

        while (count < 5) {

            System.out.print("Enter a number (10-100): ");
            int value = input.nextInt();

            if (value < 10 || value > 100) {
                System.out.println("Invalid number.");
                continue;
            }

            boolean duplicate = false;

            for (int i = 0; i < count; i++) {
                if (numbers[i] == value) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                numbers[count] = value;
                count++;
            }

            System.out.print("Unique values: ");

            for (int i = 0; i < count; i++)
                System.out.print(numbers[i] + " ");

            System.out.println();
        }

        input.close();
    }
}