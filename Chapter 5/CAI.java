import java.util.Random;
import java.util.Scanner;

public class CAI {

    static Random rand = new Random();

    public static void question(Scanner input) {

        int a = rand.nextInt(9) + 1;
        int b = rand.nextInt(9) + 1;

        int answer;

        do {
            System.out.print("How much is " + a + " times " + b + "? ");
            answer = input.nextInt();

            if (answer == a * b)
                System.out.println("Very good!");
            else
                System.out.println("No. Please try again.");

        } while (answer != a * b);
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        while (true) {
            question(input);
        }
    }
}