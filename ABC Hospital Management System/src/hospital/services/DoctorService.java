package hospital.services;

import hospital.dao.DoctorDAO;
import hospital.models.Doctor;

import java.util.List;

public class DoctorService {

    private final DoctorDAO doctorDAO;

    public DoctorService() {

        doctorDAO = new DoctorDAO();
    }


    // =========================================================
    // REGISTER DOCTOR
    // =========================================================

    public boolean registerDoctor(
            Doctor doctor
    ) {

        if (doctor == null) {

            System.out.println(
                    "Doctor information is required."
            );

            return false;
        }

        if (doctor.getFirstName() == null ||
                doctor.getFirstName().isBlank()) {

            System.out.println(
                    "First name is required."
            );

            return false;
        }

        if (doctor.getLastName() == null ||
                doctor.getLastName().isBlank()) {

            System.out.println(
                    "Last name is required."
            );

            return false;
        }

        if (doctor.getDateOfBirth() == null) {

            System.out.println(
                    "Date of birth is required."
            );

            return false;
        }

        if (doctor.getEmploymentDate() == null) {

            System.out.println(
                    "Employment date is required."
            );

            return false;
        }

        if (doctor.getDepartment() == null) {

            System.out.println(
                    "Department is required."
            );

            return false;
        }

        if (doctor.getSpecialization() == null ||
                doctor.getSpecialization().isBlank()) {

            System.out.println(
                    "Specialization is required."
            );

            return false;
        }

        if (doctor.getLicenseNumber() == null ||
                doctor.getLicenseNumber().isBlank()) {

            System.out.println(
                    "License number is required."
            );

            return false;
        }

        if (doctor.getSalary() < 0) {

            System.out.println(
                    "Salary cannot be negative."
            );

            return false;
        }

        return doctorDAO.addDoctor(
                doctor
        );
    }


    // =========================================================
    // GET ALL DOCTORS
    // =========================================================

    public List<Doctor> getAllDoctors() {

        return doctorDAO.findAllDoctors();
    }


    // =========================================================
    // GET DOCTOR BY ID
    // =========================================================

    public Doctor getDoctorById(
            int id
    ) {

        if (id <= 0) {

            System.out.println(
                    "Invalid doctor ID."
            );

            return null;
        }

        return doctorDAO.findDoctorById(
                id
        );
    }


    // =========================================================
    // UPDATE DOCTOR
    // =========================================================

    public boolean updateDoctor(
            Doctor doctor
    ) {

        if (doctor == null) {

            System.out.println(
                    "Doctor information is required."
            );

            return false;
        }

        if (doctor.getStaffId() <= 0) {

            System.out.println(
                    "Invalid doctor ID."
            );

            return false;
        }

        if (doctor.getFirstName() == null ||
                doctor.getFirstName().isBlank()) {

            System.out.println(
                    "First name is required."
            );

            return false;
        }

        if (doctor.getLastName() == null ||
                doctor.getLastName().isBlank()) {

            System.out.println(
                    "Last name is required."
            );

            return false;
        }

        if (doctor.getDateOfBirth() == null) {

            System.out.println(
                    "Date of birth is required."
            );

            return false;
        }

        if (doctor.getDepartment() == null) {

            System.out.println(
                    "Department is required."
            );

            return false;
        }

        if (doctor.getSpecialization() == null ||
                doctor.getSpecialization().isBlank()) {

            System.out.println(
                    "Specialization is required."
            );

            return false;
        }

        if (doctor.getLicenseNumber() == null ||
                doctor.getLicenseNumber().isBlank()) {

            System.out.println(
                    "License number is required."
            );

            return false;
        }

        return doctorDAO.update(
                doctor
        );
    }


    // =========================================================
    // DELETE DOCTOR
    // =========================================================

    public boolean deleteDoctor(
            int id
    ) {

        if (id <= 0) {

            System.out.println(
                    "Invalid doctor ID."
            );

            return false;
        }

        return doctorDAO.delete(
                id
        );
    }
}
