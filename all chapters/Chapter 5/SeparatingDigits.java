import java.util.Scanner;

public class SeparatingDigits {

    // quotient
    public static int quotient(int a, int b) {
        return a / b;
    }

    // remainder
    public static int remainder(int a, int b) {
        return a % b;
    }

    // display digits separated by tabs
    public static void displayDigits(int number) {

        int divisor = 10000;
        boolean started = false;

        while (divisor > 0) {
            int digit = quotient(number, divisor);
            number = remainder(number, divisor);

            if (digit != 0 || started || divisor == 1) {
                System.out.print(digit + "\t");
                started = true;
            }

            divisor /= 10;
        }

        System.out.println();
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter number (1 to 99999): ");
        int num = input.nextInt();

        displayDigits(num);

        input.close();
    }
}