package hospital.services;

import hospital.dao.NurseDAO;
import hospital.models.Nurse;

import java.util.List;

public class NurseService {

    private final NurseDAO nurseDAO;

    public NurseService() {
        nurseDAO = new NurseDAO();
    }

    // =========================================================
    // REGISTER NURSE
    // =========================================================
    public boolean registerNurse(Nurse nurse) {

        if (nurse == null) {

            System.out.println(
                    "Nurse information is required."
            );

            return false;
        }

        if (nurse.getFirstName() == null
                || nurse.getFirstName().isBlank()) {

            System.out.println(
                    "First name is required."
            );

            return false;
        }

        if (nurse.getLastName() == null
                || nurse.getLastName().isBlank()) {

            System.out.println(
                    "Last name is required."
            );

            return false;
        }

        if (nurse.getDateOfBirth() == null) {

            System.out.println(
                    "Date of birth is required."
            );

            return false;
        }

        if (nurse.getEmploymentDate() == null) {

            System.out.println(
                    "Employment date is required."
            );

            return false;
        }

        if (nurse.getSalary() < 0) {

            System.out.println(
                    "Salary cannot be negative."
            );

            return false;
        }

        if (nurse.getDepartment() == null) {

            System.out.println(
                    "Department is required."
            );

            return false;
        }

        if (nurse.getNursingLicense() == null
                || nurse.getNursingLicense().isBlank()) {

            System.out.println(
                    "Nursing license is required."
            );

            return false;
        }

        if (nurse.getQualification() == null
                || nurse.getQualification().isBlank()) {

            System.out.println(
                    "Qualification is required."
            );

            return false;
        }

        return nurseDAO.addNurse(nurse);
    }

    // =========================================================
    // GET ALL NURSES
    // =========================================================
    public List<Nurse> getAllNurses() {

        return nurseDAO.findAllNurses();
    }

    // =========================================================
    // GET NURSE BY STAFF ID
    // =========================================================
    public Nurse getNurseById(int staffId) {

        if (staffId <= 0) {

            System.out.println(
                    "Invalid staff ID."
            );

            return null;
        }

        return nurseDAO.findNurseById(staffId);
    }

    // =========================================================
    // UPDATE NURSE
    // =========================================================
    public boolean updateNurse(Nurse nurse) {

        if (nurse == null) {

            System.out.println(
                    "Nurse information is required."
            );

            return false;
        }

        // Nurse uses StaffId as its identifier
        if (nurse.getStaffId() <= 0) {

            System.out.println(
                    "Invalid staff ID."
            );

            return false;
        }

        if (nurse.getFirstName() == null
                || nurse.getFirstName().isBlank()) {

            System.out.println(
                    "First name is required."
            );

            return false;
        }

        if (nurse.getLastName() == null
                || nurse.getLastName().isBlank()) {

            System.out.println(
                    "Last name is required."
            );

            return false;
        }

        if (nurse.getDateOfBirth() == null) {

            System.out.println(
                    "Date of birth is required."
            );

            return false;
        }

        if (nurse.getEmploymentDate() == null) {

            System.out.println(
                    "Employment date is required."
            );

            return false;
        }

        if (nurse.getSalary() < 0) {

            System.out.println(
                    "Salary cannot be negative."
            );

            return false;
        }

        if (nurse.getDepartment() == null) {

            System.out.println(
                    "Department is required."
            );

            return false;
        }

        if (nurse.getNursingLicense() == null
                || nurse.getNursingLicense().isBlank()) {

            System.out.println(
                    "Nursing license is required."
            );

            return false;
        }

        if (nurse.getQualification() == null
                || nurse.getQualification().isBlank()) {

            System.out.println(
                    "Qualification is required."
            );

            return false;
        }

        return nurseDAO.update(nurse);
    }

    // =========================================================
    // DELETE NURSE
    // =========================================================
    public boolean deleteNurse(int staffId) {

        if (staffId <= 0) {

            System.out.println(
                    "Invalid staff ID."
            );

            return false;
        }

        return nurseDAO.delete(staffId);
    }
}
