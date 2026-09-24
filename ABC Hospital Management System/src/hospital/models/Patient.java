package hospital.models;

import java.time.LocalDate;

public class Patient extends Person {

    private int patientId;
    private String bloodGroup;
    private String genotype;
    private String allergies;
    private String emergencyContact;
    private String emergencyPhone;

    public Patient() {
    }

    public Patient(int patientId, String bloodGroup, String genotype, String allergies, String emergencyContact, String emergencyPhone, String firstName, String lastName, char gender, LocalDate dateOfBirth, String phone, String email, String street, String city, String Country) {
        super(firstName, lastName, gender, dateOfBirth, phone, email, street, city, Country);
        this.patientId = patientId;
        this.bloodGroup = bloodGroup;
        this.genotype = genotype;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
        this.emergencyPhone = emergencyPhone;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

}
