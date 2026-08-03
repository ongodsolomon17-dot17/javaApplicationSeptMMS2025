//3.20 
import java.util.Scanner;

public class SalaryCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 3; i++) {
            System.out.println("Enter details for Employee " + i);
            System.out.print("Hours worked: ");
            int hours = scanner.nextInt();
            System.out.print("Hourly rate: ");
            double rate = scanner.nextDouble();

            double grossPay;
            if (hours > 40) {
                double basePay = 40 * rate;
                double overtimePay = (hours - 40) * (rate * 1.5);
                grossPay = basePay + overtimePay;
            } else {
                grossPay = hours * rate;
            }

            System.out.printf("Gross pay for Employee %d: $%.2f%n%n", i, grossPay);
        }
        scanner.close();
    }
}