import java.util.Random;
import java.util.Scanner;

public class CAI5 {

    static Random rand = new Random();

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Choose type (1=+,2=-,3=*,4=/,5=random): ");
        int type = input.nextInt();

        while (true) {

            int a = rand.nextInt(10) + 1;
            int b = rand.nextInt(10) + 1;

            if (type == 5) type = rand.nextInt(4) + 1;

            double correct = 0;

            switch (type) {
                case 1 -> {
                    System.out.print(a + " + " + b + " = ");
                    correct = a + b;
                }
                case 2 -> {
                    System.out.print(a + " - " + b + " = ");
                    correct = a - b;
                }
                case 3 -> {
                    System.out.print(a + " * " + b + " = ");
                    correct = a * b;
                }
                case 4 -> {
                    System.out.print(a + " / " + b + " = ");
                    correct = (double) a / b;
                }
            }

            double ans = input.nextDouble();

            if (Math.abs(ans - correct) < 0.0001)
                System.out.println("Correct!");
            else
                System.out.println("Wrong. Correct answer: " + correct);
        }
    }
}