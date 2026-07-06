import java.util.Scanner;

public class ReverseDigits {

    public static int reverse(int number) {

        int reversed = 0;

        while (number != 0) {
            reversed = reversed * 10 + number % 10;
            number /= 10;
        }

        return reversed;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter integer: ");
        int num = input.nextInt();

        System.out.println("Reversed: " + reverse(num));

        input.close();
    }
}