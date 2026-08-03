
package chapter8;

public class SavingsAccountTest {
    public static void main(String[] args) {
        // Instantiate two SavingsAccount objects
        SavingsAccount saver1 = new SavingsAccount(2000.00);
        SavingsAccount saver2 = new SavingsAccount(3000.00);

        // Set annual interest rate to 4% (0.04)
        SavingsAccount.modifyInterestRate(0.04);

        System.out.println("--- 12-Month Simulation at 4% Annual Interest Rate ---");
        System.out.printf("%-10s %-15s %-15s%n", "Month", "Saver 1 Balance", "Saver 2 Balance");
        System.out.println("-----------------------------------------------------");

        // Calculate and print monthly balances for 12 months
        for (int month = 1; month <= 12; month++) {
            saver1.calculateMonthlyInterest();
            saver2.calculateMonthlyInterest();

            System.out.printf("Month %-4d $%-14.2f $%-14.2f%n", 
                month, saver1.getSavingsBalance(), saver2.getSavingsBalance());
        }

        // Set annual interest rate to 5% (0.05)
        SavingsAccount.modifyInterestRate(0.05);

        // Calculate interest for the 13th month
        saver1.calculateMonthlyInterest();
        saver2.calculateMonthlyInterest();

        System.out.println("\n--- Month 13 Balance at 5% Annual Interest Rate ---");
        System.out.printf("Saver 1: $%.2f%n", saver1.getSavingsBalance());
        System.out.printf("Saver 2: $%.2f%n", saver2.getSavingsBalance());
    }
}
