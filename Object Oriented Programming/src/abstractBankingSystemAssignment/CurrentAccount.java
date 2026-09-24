
package abstractBankingSystemAssignment;

public class CurrentAccount extends BankAccount {

    public CurrentAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance);
    }

    @Override
    public void withdraw(double amount) {

        double overdraftLimit = 5000;

        if (amount <= balance + overdraftLimit) {

            balance -= amount;
            System.out.println("Current account withdrawal successful.");

        } else {

            System.out.println("Withdrawal exceeds overdraft limit.");

        }

    }

    @Override
    public void calculateInterest() {

        double interest = balance * 0.02;

        System.out.println("Current Account Interest (2%): ₦" + interest);

    }

}