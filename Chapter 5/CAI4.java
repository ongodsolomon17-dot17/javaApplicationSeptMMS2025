import java.util.Random;
import java.util.Scanner;

public class CAI4 {

    static Random rand = new Random();

    public static int generate(int level) {
        return rand.nextInt((int) Math.pow(10, level - 1), (int) Math.pow(10, level));
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter difficulty level: ");
        int level = input.nextInt();

        while (true) {

            int a = generate(level);
            int b = generate(level);

            System.out.print("How much is " + a + " times " + b + "? ");
            int ans = input.nextInt();

            if (ans == a * b)
                System.out.println("Correct!");
            else
                System.out.println("Try again.");
        }
    }
}