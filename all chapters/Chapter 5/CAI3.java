import java.util.Random;
import java.util.Scanner;

public class CAI3 {

    static Random rand = new Random();

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        while (true) {

            int correct = 0;
            int wrong = 0;

            for (int i = 0; i < 10; i++) {

                int a = rand.nextInt(9) + 1;
                int b = rand.nextInt(9) + 1;

                System.out.print("How much is " + a + " times " + b + "? ");
                int ans = input.nextInt();

                if (ans == a * b) {
                    correct++;
                } else {
                    wrong++;
                }
            }

            double percent = correct * 10.0;

            if (percent < 75)
                System.out.println("Please ask your teacher for extra help.");
            else
                System.out.println("Congratulations, you are ready to go to the next level!");
        }
    }
}