
package abstractBankingSystemAssignment;

public class Main {

    public static void main(String[] args) {

        SavingsAccount savings =
                new SavingsAccount("SAV101", "Solomon", 50000);

        CurrentAccount current =
                new CurrentAccount("CUR202", "David", 30000);

        System.out.println("========== SAVINGS ACCOUNT ==========");

        savings.deposit(10000);

        savings.withdraw(7000);

        savings.displayBalance();

        savings.calculateInterest();

        System.out.println();

        System.out.println("========== CURRENT ACCOUNT ==========");

        current.deposit(5000);

        current.withdraw(33000);

        current.displayBalance();

        current.calculateInterest();

    }

}