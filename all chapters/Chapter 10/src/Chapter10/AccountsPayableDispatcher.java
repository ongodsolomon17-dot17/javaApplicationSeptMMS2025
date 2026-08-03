package chapter10;

public class AccountsPayableDispatcher {
    public static void main(String[] args) {
        Payable[] payableObjects = new Payable[3]; // [cite: 30]

        payableObjects[0] = new Invoice("01234", "Seat Belt", 2, 375.00); // [cite: 29]
        payableObjects[1] = new CommissionEmployee("Sue", "Jones", "333-33-3333", 10000.00, 0.06); // [cite: 29]
        payableObjects[2] = new BasePlusCommissionEmployee("Bob", "Lewis", "444-44-4444", 5000.00, 0.04, 300.00); // [cite: 29]

        System.out.println("--- Accounts Payable Processing ---\n");

        for (Payable currentPayable : payableObjects) { // [cite: 30]
            if (currentPayable instanceof BasePlusCommissionEmployee) { // [cite: 28, 31]
                BasePlusCommissionEmployee employee = (BasePlusCommissionEmployee) currentPayable;
                double oldSalary = employee.getBaseSalary();
                employee.setBaseSalary(oldSalary * 1.10); // Increase base salary by 10% [cite: 28, 31]
                System.out.printf("** Base salary adjusted by +10%% to: $%,.2f **%n", employee.getBaseSalary());
            }

            System.out.println(currentPayable.toString());
            System.out.printf("Payment Due: $%,.2f%n%n", currentPayable.getPaymentAmount()); // [cite: 31]
        }
    }
}