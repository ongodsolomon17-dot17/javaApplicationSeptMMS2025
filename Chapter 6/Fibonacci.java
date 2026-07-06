import java.util.Scanner;

public class Fibonacci {

    public static long fibonacci(int n) {

        if (n == 0)
            return 0;

        if (n == 1)
            return 1;

        long a = 0;
        long b = 1;

        for (int i = 2; i <= n; i++) {

            long temp = a + b;
            a = b;
            b = temp;
        }

        return b;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter n: ");
        int n = input.nextInt();

        System.out.println("Fibonacci = " + fibonacci(n));

        input.close();
    }
}