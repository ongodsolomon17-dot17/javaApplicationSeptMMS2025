
package hospital.models;

import java.time.LocalDate;


public class Pharmacist extends Staff {
    private String qualification;
    private String licenseNumber;
   
    public Pharmacist(){
       
    }

    public Pharmacist(String firstName, String lastName, char gender, LocalDate dateOfBirth, String phone,
            String email, String street, String city, String country, int staffID, LocalDate employmentDate,
            double salary, Department department, String qualification, String licenseNumber) {
        super(firstName, lastName, gender, dateOfBirth, phone, email, street, city, country, staffID, employmentDate, salary, department);
        this.qualification = qualification;
        this.licenseNumber = licenseNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }


   
}