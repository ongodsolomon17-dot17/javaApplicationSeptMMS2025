
package chapter9;

public class BasePlusCommissionEmployee {

    private CommissionEmployee employee;
    private double baseSalary;

    public BasePlusCommissionEmployee(
            String firstName,
            String lastName,
            String socialSecurityNumber,
            double grossSales,
            double commissionRate,
            double baseSalary) {

        employee = new CommissionEmployee(
            firstName, lastName,
            socialSecurityNumber,
            grossSales,
            commissionRate);

        this.baseSalary = baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        if (baseSalary >= 0.0)
            this.baseSalary = baseSalary;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public double earnings() {
        return baseSalary + employee.earnings();
    }

    @Override
    public String toString() {
        return String.format(
            "Base-Salaried %s%nBase Salary: %.2f",
            employee.toString(),
            baseSalary);
    }
}
