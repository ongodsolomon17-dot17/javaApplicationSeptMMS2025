package hospital.models;

import java.time.LocalDateTime;

public class LaboratoryTest {

    private int id;
    private Patient patient;
    private LaboratoryTechnician technician;
    private String testName;
    private LocalDateTime testDate;
    private String result;
    private String referenceRange;
    private String status;

    public LaboratoryTest() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LaboratoryTechnician getTechnician() {
        return technician;
    }

    public void setTechnician(LaboratoryTechnician technician) {
        this.technician = technician;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
        this.testDate = testDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReferenceRange() {
        return referenceRange;
    }

    public void setReferenceRange(String referenceRange) {
        this.referenceRange = referenceRange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
