import java.util.Scanner;
//3.19
public class SalesCommission {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double totalSales = 0.0;
        int itemNumber = 1;

        System.out.println("Enter value of item 1 (or -1 to end input): ");
        double itemValue = scanner.nextDouble();

        while (itemValue != -1) {
            totalSales += itemValue;
            itemNumber++;
            System.out.println("Enter value of item " + itemNumber + " (or -1 to end input): ");
            itemValue = scanner.nextDouble();
        }

        double earnings = 200.00 + (totalSales * 0.09);

        System.out.printf("Total sales for the week: $%.2f%n", totalSales);
        System.out.printf("Total earnings for the week: $%.2f%n", earnings);

        scanner.close();
    }
}