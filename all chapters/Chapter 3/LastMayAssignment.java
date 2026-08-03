import java.util.Scanner;

public class LastMayAssignment {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char option;

        do {
            int[] num = new int[10];

            System.out.println("Enter 10 numbers:");

            for (int i = 0; i < 10; i++) {
                System.out.print("Number " + (i + 1) + ": ");
                num[i] = scan.nextInt();
            }

            int firstPart = num[0] + num[4] + num[9];   // 1st, 5th, 10th
            int secondPart = num[2] + num[7] + num[1];  // 3rd, 8th, 2nd

            int multiplicationResult = firstPart * secondPart;

            int thirdPart = num[3] + num[6] + num[5] + num[8]; // 4th, 7th, 6th, 9th

            int finalResult = thirdPart - multiplicationResult;

            System.out.println("Final Result = " + finalResult);

            if (finalResult >= 100) {
                System.out.println("Hurray I did it");
            } else {
                System.out.println("I still need to learn more in Java");
            }

            System.out.print("Do you want to run the program again (Y/N): ");
            option = scan.next().charAt(0);

        } while (option == 'Y' || option == 'y');

        System.out.println("Good bye.....................................");
    }
}