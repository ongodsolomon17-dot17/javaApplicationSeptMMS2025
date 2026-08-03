import java.util.Scanner;

public class Exponentiation {

    // Method to calculate base raised to exponent
    public static int integerPower(int base, int exponent) {
        int result = 1;

        for (int i = 1; i <= exponent; i++) {
            result *= base;
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the base: ");
        int base = input.nextInt();

        System.out.print("Enter the exponent: ");
        int exponent = input.nextInt();

        int answer = integerPower(base, exponent);

        System.out.println(base + " raised to the power of " + exponent + " = " + answer);

        input.close();
    }
}