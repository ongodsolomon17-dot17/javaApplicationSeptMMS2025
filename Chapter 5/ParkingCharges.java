import java.util.Scanner;

public class ParkingCharges {

    // Method to calculate parking charges
    public static double calculateCharges(double hours) {
        double charge = 2.00;

        if (hours > 3.0) {
            // Charge $0.50 for each additional hour or part thereof
            charge += Math.ceil(hours - 3.0) * 0.50;
        }

        // Maximum charge for 24 hours
        if (charge > 10.00) {
            charge = 10.00;
        }

        return charge;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double totalReceipts = 0.0;

        System.out.print("Enter the number of customers: ");
        int customers = input.nextInt();

        System.out.printf("%-10s%-15s%-15s%n", "Customer", "Hours", "Charge");

        for (int i = 1; i <= customers; i++) {
            System.out.print("Enter hours parked for customer " + i + ": ");
            double hours = input.nextDouble();

            double charge = calculateCharges(hours);
            totalReceipts += charge;

            System.out.printf("%-10d%-15.2f$%-14.2f%n", i, hours, charge);
            System.out.printf("Running Total: $%.2f%n%n", totalReceipts);
        }

        System.out.printf("Yesterday's Total Receipts: $%.2f%n", totalReceipts);

        input.close();
    }
}