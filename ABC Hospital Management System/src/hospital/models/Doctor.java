package hospital.models;

import java.time.LocalDate;


public class Doctor extends Staff{
    private String specialization;
    private String licenseNumber;
   
    
    public Doctor(){
        
    }

    public Doctor(String firstName,
            String lastName, char gender, 
            LocalDate dateOfBirth, String phone,
            String email, String street, 
            String city, String country,int staffId,
            LocalDate employmentDate,
            double salary,Department department,
            String specialization,
            String licenseNumber
             ) {
        
        super(
                firstName, lastName, gender, 
                dateOfBirth, phone, email, 
                street, city, country,staffId,
                employmentDate, salary,department );
        
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
       
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
     

}