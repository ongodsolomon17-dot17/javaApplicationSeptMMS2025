package chapter10;

public class CommissionEmployee extends EmployeePayable { // [cite: 25]
    private double grossSales;
    private double commissionRate;

    public CommissionEmployee(String firstName, String lastName, String socialSecurityNumber, 
                              double grossSales, double commissionRate) {
        super(firstName, lastName, socialSecurityNumber);
        this.grossSales = grossSales;
        this.commissionRate = commissionRate;
    }

    public double getGrossSales() { return grossSales; }
    public double getCommissionRate() { return commissionRate; }

    @Override
    public double earnings() {
        return getCommissionRate() * getGrossSales();
    }

    @Override
    public String toString() {
        return String.format("Commission Employee: %s%nGross Sales: $%,.2f, Rate: %.2f", 
            super.toString(), getGrossSales(), getCommissionRate());
    }
}