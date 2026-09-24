package hospital.models;

import java.time.LocalDate;

public abstract class Staff extends Person {

    private int staffId;
    private LocalDate employmentDate;
    private double Salary;
    private Department department;

    public Staff() {
    }

    public Staff(String firstName,
            String lastName, char gender,
            LocalDate dateOfBirth, String phone, String email,
            String street, String city,
            String Country, int staffId,
            LocalDate employmentDate,
            double Salary, Department department) {

        super(firstName, lastName, gender,
                dateOfBirth, phone, email,
                street, city, Country);

        this.staffId = staffId;
        this.employmentDate = employmentDate;
        this.Salary = Salary;
        this.department = department;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    
    public int getStaffId() {
        return staffId;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double Salary) {
        this.Salary = Salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
