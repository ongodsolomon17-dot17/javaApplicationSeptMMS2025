package hospital.userview;

import hospital.models.Nurse;

import java.util.List;

public class NurseView {

    // =========================================================
    // DISPLAY ALL NURSES
    // =========================================================
    public void displayNurses(
            List<Nurse> nurses
    ) {

        if (nurses == null
                || nurses.isEmpty()) {

            System.out.println(
                    "No nurses found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "==============================================================================================================================="
        );

        System.out.printf(
                "%-10s %-15s %-15s %-8s %-15s %-12s %-15s %-18s %-20s%n",
                "Staff ID",
                "First Name",
                "Last Name",
                "Gender",
                "Phone",
                "Salary",
                "Department",
                "License",
                "Qualification"
        );

        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------"
        );

        for (Nurse nurse : nurses) {

            String department = "N/A";

            if (nurse.getDepartment() != null) {

                department
                        = String.valueOf(
                                nurse.getDepartment()
                                        .getId()
                        );
            }

            System.out.printf(
                    "%-10d %-15s %-15s %-8s %-15s %-12.2f %-15s %-18s %-20s%n",
                    nurse.getStaffId(),
                    nurse.getFirstName(),
                    nurse.getLastName(),
                    nurse.getGender(),
                    nurse.getPhone(),
                    nurse.getSalary(),
                    department,
                    nurse.getNursingLicense(),
                    nurse.getQualification()
            );
        }

        System.out.println(
                "==============================================================================================================================="
        );
    }

    // =========================================================
    // DISPLAY ONE NURSE
    // =========================================================
    public void displayNurse(
            Nurse nurse
    ) {

        if (nurse == null) {

            System.out.println(
                    "Nurse not found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "=================================================="
        );

        System.out.println(
                " NURSE DETAILS"
        );

        System.out.println(
                "=================================================="
        );

        System.out.printf(
                "%-22s : %s%n",
                "Staff ID",
                nurse.getStaffId()
        );

        System.out.printf(
                "%-22s : %s%n",
                "First Name",
                nurse.getFirstName()
        );

        System.out.printf(
                "%-22s : %s%n",
                "Last Name",
                nurse.getLastName()
        );

        System.out.printf(
                "%-22s : %s%n",
                "Gender",
                nurse.getGender()
        );

        System.out.printf(
                "%-22s : %s%n",
                "Date of Birth",
                nurse.getDateOfBirth()
        );

        System.out.printf(
                "%-22s : %s%n",
                "Phone",
                nurse.getPhone()
        );

        System.out.printf(
                "%-22s : %s%n",
                "Email",
                nurse.getEmail()
        );

        System.out.printf(
                "%-22s : %s%n",
                "Street",
                nurse.getStreet()
        );

        System.out.printf(
                "%-22s : %s%n",
                "City",
                nurse.getCity()
        );

        System.out.printf(
                "%-22s : %s%n",
                "Country",
                nurse.getCountry()
        );

        System.out.println(
                "--------------------------------------------------"
        );

        System.out.printf(
                "%-22s : %s%n",
                "Employment Date",
                nurse.getEmploymentDate()
        );

        System.out.printf(
                "%-22s : %.2f%n",
                "Salary",
                nurse.getSalary()
        );

        if (nurse.getDepartment() != null) {

            System.out.printf(
                    "%-22s : %s%n",
                    "Department ID",
                    nurse.getDepartment()
                            .getId()
            );

        } else {

            System.out.printf(
                    "%-22s : %s%n",
                    "Department",
                    "N/A"
            );
        }

        System.out.println(
                "--------------------------------------------------"
        );

        System.out.printf(
                "%-22s : %s%n",
                "Nursing License",
                nurse.getNursingLicense()
        );

        System.out.printf(
                "%-22s : %s%n",
                "Qualification",
                nurse.getQualification()
        );

        System.out.println(
                "=================================================="
        );
    }
}
