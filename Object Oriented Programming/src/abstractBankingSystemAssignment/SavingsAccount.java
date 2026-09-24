
package abstractBankingSystemAssignment;

public class SavingsAccount extends BankAccount {

    public SavingsAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance);
    }

    @Override
    public void withdraw(double amount) {

        if (amount <= balance) {
            balance -= amount;
            System.out.println("Savings withdrawal successful.");
        } else {
            System.out.println("Insufficient balance.");
        }

    }

    @Override
    public void calculateInterest() {

        double interest = balance * 0.05;

        System.out.println("Savings Interest (5%): ₦" + interest);
    }

}
