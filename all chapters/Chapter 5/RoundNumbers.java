import java.util.Scanner;

public class RoundNumbers {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a double value: ");
        double x = input.nextDouble();

        double y = Math.floor(x + 0.5);

        System.out.printf("Original number: %.2f%n", x);
        System.out.printf("Rounded number: %.0f%n", y);

        input.close();
    }
}