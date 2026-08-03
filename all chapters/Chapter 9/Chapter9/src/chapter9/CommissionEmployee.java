package chapter9;

public class CommissionEmployee extends Employee {

    private double grossSales;
    private double commissionRate;

    public CommissionEmployee(String firstName,
                              String lastName,
                              String socialSecurityNumber,
                              double grossSales,
                              double commissionRate) {

        super(firstName, lastName, socialSecurityNumber);

        setGrossSales(grossSales);
        setCommissionRate(commissionRate);
    }


    public void setGrossSales(double grossSales) {
        if (grossSales >= 0)
            this.grossSales = grossSales;
    }

    public double getGrossSales() {
        return grossSales;
    }


    public void setCommissionRate(double commissionRate) {
        if (commissionRate > 0 && commissionRate < 1)
            this.commissionRate = commissionRate;
    }

    public double getCommissionRate() {
        return commissionRate;
    }


    public double earnings() {
        return getCommissionRate() * getGrossSales();
    }


    @Override
    public String toString() {
        return String.format("%s%nGross sales: %.2f%nCommission rate: %.2f",
                super.toString(),
                getGrossSales(),
                getCommissionRate());
    }
}