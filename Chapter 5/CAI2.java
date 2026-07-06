import java.util.Random;
import java.util.Scanner;

public class CAI2 {

    static Random rand = new Random();

    public static void correct() {
        switch (rand.nextInt(4) + 1) {
            case 1 -> System.out.println("Very good!");
            case 2 -> System.out.println("Excellent!");
            case 3 -> System.out.println("Nice work!");
            case 4 -> System.out.println("Keep up the good work!");
        }
    }

    public static void wrong() {
        switch (rand.nextInt(4) + 1) {
            case 1 -> System.out.println("No. Please try again.");
            case 2 -> System.out.println("Wrong. Try once more.");
            case 3 -> System.out.println("Don't give up!");
            case 4 -> System.out.println("Keep trying.");
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        while (true) {

            int a = rand.nextInt(9) + 1;
            int b = rand.nextInt(9) + 1;

            int answer;

            do {
                System.out.print("How much is " + a + " times " + b + "? ");
                answer = input.nextInt();

                if (answer == a * b)
                    correct();
                else
                    wrong();

            } while (answer != a * b);
        }
    }
}