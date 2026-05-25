public class OperatorPart1 {
    public static void main(String[] args) {
        // Assignment Operator (=)
        int num = 200;
        System.out.printf("The value of num is %d%n", num);

        System.out.println();
        System.out.println("=================");

        // Arithmetic Operators (+, -, /, *, %)
        int num1 = 80;
        int num2 = 100;

        int addition = num1 + num2;
        int subtraction = num1 - num2;
        double division = (double) num1 / num2;
        int multiplication = num1 * num2;
        int remainder = num1 % num2;

        System.out.printf(" %d + %d = %d%n", num1, num2, addition);
        System.out.printf(" %d - %d = %d%n", num1, num2, subtraction);
        System.out.printf(" %d / %d = %.1f%n", num1, num2, division);
        System.out.printf(" %d * %d = %d%n", num1, num2, multiplication);
        System.out.printf(" %d %% %d = %d%n", num1, num2, remainder);

        System.out.println();
        System.out.println("===========================================");

        // Compound assignment operator (+=, -=, *=, /=, %=)
        num1 += num2;
        System.out.printf("The value of num1 has been updated to %d%n", num1);

        num1 -= num2;
        System.out.printf("The value of num1 has been updated to %d%n", num1);

        num1 *= num2;
        System.out.printf("The value of num1 has been updated to %d%n", num1);

        num1 /= num2;
        System.out.printf("The value of num1 has been updated to %d%n", num1);

        num1 %= num2;
        System.out.printf("The value of num1 has been updated to %d%n", num1);

        System.out.println();
        System.out.println("===========================================");

        int number1 = 15;
        int number2 = 30;

        boolean isGreater = number1 > number2;
        boolean isLessThan = number1 < number2;
        boolean isGreaterOrEqualTo = number1 >= number2;
        boolean isLessThanOrEqualTo = number1 <= number2;
        boolean isEqualTo = number1 == number2;
        boolean isNotEqualTo = number1 != number2;

        System.out.printf("Is %d > %d = %b%n", number1, number2, isGreater);
        System.out.printf("Is %d < %d = %b%n", number1, number2, isLessThan);
        System.out.printf("Is %d >= %d = %b%n", number1, number2, isGreaterOrEqualTo);
        System.out.printf("Is %d <= %d = %b%n", number1, number2, isLessThanOrEqualTo);
        System.out.printf("Is %d == %d = %b%n", number1, number2, isEqualTo);
        System.out.printf("Is %d != %d = %b%n", number1, number2, isNotEqualTo);
    }
}
