import java.util.Scanner;

public class GCD {

    public static int gcd(int a, int b) {

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter two integers: ");
        int a = input.nextInt();
        int b = input.nextInt();

        System.out.println("GCD: " + gcd(a, b));

        input.close();
    }
}