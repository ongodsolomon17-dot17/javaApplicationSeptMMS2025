import java.util.Scanner;

public class RoundingNumbers2 {

    // Round to the nearest integer
    public static double roundToInteger(double number) {
        return Math.floor(number + 0.5);
    }

    // Round to the nearest tenth
    public static double roundToTenths(double number) {
        return Math.floor(number * 10 + 0.5) / 10;
    }

    // Round to the nearest hundredth
    public static double roundToHundredths(double number) {
        return Math.floor(number * 100 + 0.5) / 100;
    }

    // Round to the nearest thousandth
    public static double roundToThousandths(double number) {
        return Math.floor(number * 1000 + 0.5) / 1000;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("How many numbers do you want to round? ");
        int n = input.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.print("\nEnter number " + i + ": ");
            double number = input.nextDouble();

            System.out.printf("Original Number        : %.6f%n", number);
            System.out.printf("Rounded to Integer     : %.0f%n", roundToInteger(number));
            System.out.printf("Rounded to Tenths      : %.1f%n", roundToTenths(number));
            System.out.printf("Rounded to Hundredths  : %.2f%n", roundToHundredths(number));
            System.out.printf("Rounded to Thousandths : %.3f%n", roundToThousandths(number));
        }

        input.close();
    }
}