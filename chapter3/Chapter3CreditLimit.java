import java.util.Scanner;
//3.18
public class Chapter3CreditLimit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int accountNumber;
        int beginningBalance;
        int charges;
        int credits;
        int creditLimit;

        System.out.println("Enter Account Number (or 0 to quit): ");
        accountNumber = scanner.nextInt();

        while (accountNumber != 0) {
            System.out.println("Enter beginning balance: ");
            beginningBalance = scanner.nextInt();
            System.out.println("Enter total charges: ");
            charges = scanner.nextInt();
            System.out.println("Enter total credits applied: ");
            credits = scanner.nextInt();
            System.out.println("Enter allowed credit limit: ");
            creditLimit = scanner.nextInt();

            int newBalance = beginningBalance + charges - credits;

            System.out.printf("New balance for account %d is %d%n", accountNumber, newBalance);

            if (newBalance > creditLimit) {
                System.out.println("Credit limit exceeded");
            }
            System.out.println("\n-----------------------------");
            System.out.println("Enter next Account Number (or 0 to quit): ");
            accountNumber = scanner.nextInt();
        }
        scanner.close();
    }
}