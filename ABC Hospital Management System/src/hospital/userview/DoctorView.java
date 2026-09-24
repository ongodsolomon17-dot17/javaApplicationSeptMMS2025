package hospital.userview;

import hospital.models.Doctor;

import java.util.List;

public class DoctorView {

    // =========================================================
    // DISPLAY ALL DOCTORS
    // =========================================================

    public void displayDoctors(
            List<Doctor> doctors
    ) {

        if (doctors == null ||
                doctors.isEmpty()) {

            System.out.println(
                    "No doctors found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "=========================================================================================================================================================================="
        );

        System.out.printf(
                "%-5s %-12s %-12s %-7s %-12s %-15s %-22s %-18s %-18s %-18s %-12s%n",
                "Doctor ID",
                "First Name",
                "Last Name",
                "Gender",
                "Phone",
                "City",
                "Department",
                "Specialization",
                "License No.",
                "Employment Date",
                "Salary"
        );

        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
        );

        for (Doctor doctor : doctors) {

            String departmentName =
                    "N/A";

            if (doctor.getDepartment() != null) {

                departmentName =
                        doctor.getDepartment()
                                .getName();
            }

            System.out.printf(
                    "%-5d %-12s %-12s %-7s %-12s %-15s %-22s %-18s %-18s %-18s %-12.2f%n",

                    doctor.getStaffId(),
                    doctor.getFirstName(),
                    doctor.getLastName(),
                    doctor.getGender(),
                    doctor.getPhone(),
                    doctor.getCity(),
                    departmentName,
                    doctor.getSpecialization(),
                    doctor.getLicenseNumber(),
                    doctor.getEmploymentDate(),
                    doctor.getSalary()
            );
        }

        System.out.println(
                "=========================================================================================================================================================================="
        );
    }


    // =========================================================
    // DISPLAY ONE DOCTOR
    // =========================================================

    public void displayDoctor(
            Doctor doctor
    ) {

        if (doctor == null) {

            System.out.println(
                    "Doctor not found."
            );

            return;
        }

        String departmentName =
                "N/A";

        int departmentId = 0;

        if (doctor.getDepartment() != null) {

            departmentId =
                    doctor.getDepartment()
                            .getId();

            departmentName =
                    doctor.getDepartment()
                            .getName();
        }


        System.out.println();

        System.out.println(
                "======================================================"
        );

        System.out.println(
                " DOCTOR DETAILS"
        );

        System.out.println(
                "======================================================"
        );

        System.out.printf(
                "%-20s : %s%n",
                "Staff ID",
                doctor.getStaffId()
        );

        System.out.printf(
                "%-20s : %s%n",
                "First Name",
                doctor.getFirstName()
        );

        System.out.printf(
                "%-20s : %s%n",
                "Last Name",
                doctor.getLastName()
        );

        System.out.printf(
                "%-20s : %s%n",
                "Gender",
                doctor.getGender()
        );

        System.out.printf(
                "%-20s : %s%n",
                "Date of Birth",
                doctor.getDateOfBirth()
        );

        System.out.printf(
                "%-20s : %s%n",
                "Phone",
                doctor.getPhone()
        );

        System.out.printf(
                "%-20s : %s%n",
                "Email",
                doctor.getEmail()
        );

        System.out.printf(
                "%-20s : %s%n",
                "Street",
                doctor.getStreet()
        );

        System.out.printf(
                "%-20s : %s%n",
                "City",
                doctor.getCity()
        );

        System.out.printf(
                "%-20s : %s%n",
                "Country",
                doctor.getCountry()
        );

        System.out.println(
                "------------------------------------------------------"
        );

        System.out.printf(
                "%-20s : %s%n",
                "Staff ID",
                doctor.getStaffId()
        );

        System.out.printf(
                "%-20s : %s%n",
                "Employment Date",
                doctor.getEmploymentDate()
        );

        System.out.printf(
                "%-20s : %.2f%n",
                "Salary",
                doctor.getSalary()
        );

        System.out.printf(
                "%-20s : %s%n",
                "Department ID",
                departmentId
        );

        System.out.printf(
                "%-20s : %s%n",
                "Department",
                departmentName
        );

        System.out.println(
                "------------------------------------------------------"
        );

        System.out.printf(
                "%-20s : %s%n",
                "Specialization",
                doctor.getSpecialization()
        );

        System.out.printf(
                "%-20s : %s%n",
                "License Number",
                doctor.getLicenseNumber()
        );

        System.out.println(
                "======================================================"
        );
    }
}
