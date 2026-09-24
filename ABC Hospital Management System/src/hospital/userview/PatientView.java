package hospital.userview;

import hospital.models.Patient;

import java.util.List;

public class PatientView {

    // Display all patients
 public void displayPatients(List<Patient> patients) {

    if (patients == null || patients.isEmpty()) {
        System.out.println("No patients found.");
        return;
    }

    System.out.println();

    System.out.println(
            "==========================================================================================================================================="
    );

    System.out.printf(
            "%-4s %-12s %-12s %-6s %-10s %-12s %-25s %-15s %-11s %-9s %-20s%n",
            "ID",
            "First Name",
            "Last Name",
            "Gender",
            "DOB",
            "Phone",
            "Email",
            "City",
            "Blood Grp",
            "Genotype",
            "Allergies"
    );

    System.out.println(
            "-------------------------------------------------------------------------------------------------------------------------------------------"
    );

    for (Patient patient : patients) {

        System.out.printf(
                "%-4d %-12s %-12s %-6s %-10s %-12s %-25s %-15s %-11s %-9s %-20s%n",
                patient.getPatientId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getGender(),
                patient.getDateOfBirth(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getCity(),
                patient.getBloodGroup(),
                patient.getGenotype(),
                patient.getAllergies()
        );
    }

    System.out.println(
            "==========================================================================================================================================="
    );
}

    // Display one patient by ID
    public void displayPatient(Patient patient) {

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println();
        System.out.println("==============================================");
        System.out.println("              PATIENT DETAILS");
        System.out.println("==============================================");

        System.out.printf("%-20s : %s%n", "Patient ID", patient.getPatientId());
        System.out.printf("%-20s : %s%n", "First Name", patient.getFirstName());
        System.out.printf("%-20s : %s%n", "Last Name", patient.getLastName());
        System.out.printf("%-20s : %s%n", "Gender", patient.getGender());
        System.out.printf("%-20s : %s%n", "Date of Birth", patient.getDateOfBirth());
        System.out.printf("%-20s : %s%n", "Phone", patient.getPhone());
        System.out.printf("%-20s : %s%n", "Email", patient.getEmail());
        System.out.printf("%-20s : %s%n", "Blood Group", patient.getBloodGroup());
        System.out.printf("%-20s : %s%n", "Genotype", patient.getGenotype());
        System.out.printf("%-20s : %s%n", "Allergies", patient.getAllergies());
        System.out.printf(
                "%-20s : %s%n",
                "Emergency Contact",
                patient.getEmergencyContact()
        );
        System.out.printf(
                "%-20s : %s%n",
                "Emergency Phone",
                patient.getEmergencyPhone()
        );

        System.out.println("==============================================");
    }
}

