package chapter10;

public abstract class EmployeePayable implements Payable { // [cite: 36]
    private String firstName;
    private String lastName;
    private String socialSecurityNumber;

    public EmployeePayable(String firstName, String lastName, String socialSecurityNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getSocialSecurityNumber() { return socialSecurityNumber; }

    @Override
    public String toString() {
        return String.format("%s %s%nSSN: %s", getFirstName(), getLastName(), getSocialSecurityNumber());
    }

    public abstract double earnings();

    @Override
    public double getPaymentAmount() {
        return earnings(); // Polymorphically invokes earnings() 
    }
}