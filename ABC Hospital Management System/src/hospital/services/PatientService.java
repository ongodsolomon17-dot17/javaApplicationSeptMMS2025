package hospital.services;

import hospital.dao.PatientDAO;
import hospital.models.Patient;

import java.util.List;

public class PatientService {

    private final PatientDAO patientDAO;

    public PatientService() {
        patientDAO = new PatientDAO();
    }

    public boolean registerPatient(Patient patient) {

        if (patient.getFirstName() == null ||
                patient.getFirstName().isBlank()) {

            System.out.println(
                    "First name is required."
            );

            return false;
        }

        if (patient.getLastName() == null ||
                patient.getLastName().isBlank()) {

            System.out.println(
                    "Last name is required."
            );

            return false;
        }

        if (patient.getDateOfBirth() == null) {

            System.out.println(
                    "Date of birth is required."
            );

            return false;
        }

        return patientDAO.addPatient(patient);
    }

    public List<Patient> getAllPatients() {

        return patientDAO.findAllPatient();
    }

    public Patient getPatientById(int id) {

        return patientDAO.findPatientById(id);
    }

    public boolean updatePatient(Patient patient) {

        return patientDAO.update(patient);
    }

    public boolean deletePatient(int id) {

        return patientDAO.delete(id);
    }
}

