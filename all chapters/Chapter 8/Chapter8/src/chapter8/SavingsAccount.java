
package chapter8;

public class SavingsAccount {
    // Static variable shared by ALL instances to store the annual interest rate
    private static double annualInterestRate = 0.0;

    // Private instance variable unique to each account object
    private double savingsBalance;

    // Constructor to initialize savings balance
    public SavingsAccount(double balance) {
        if (balance < 0.0) {
            throw new IllegalArgumentException("Initial balance must be non-negative.");
        }
        this.savingsBalance = balance;
    }

    // Calculates monthly interest and adds it to savingsBalance
    public void calculateMonthlyInterest() {
        double monthlyInterest = (this.savingsBalance * annualInterestRate) / 12.0;
        this.savingsBalance += monthlyInterest;
    }

    // Static method to modify the annual interest rate for all account holders
    public static void modifyInterestRate(double newRate) {
        if (newRate < 0.0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        annualInterestRate = newRate;
    }

    // Getter for savings balance
    public double getSavingsBalance() {
        return this.savingsBalance;
    }
}