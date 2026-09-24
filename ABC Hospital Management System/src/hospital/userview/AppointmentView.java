package hospital.userview;

import hospital.models.Appointment;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentView {

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    // =========================================================
    // DISPLAY ALL APPOINTMENTS
    // =========================================================

    public void displayAppointments(List<Appointment> appointments) {

        if (appointments == null || appointments.isEmpty()) {

            System.out.println();
            System.out.println("No appointments found.");
            return;
        }

        System.out.println();

        System.out.println(
                "================================================================================================================================================"
        );

        System.out.printf(
                "%-8s %-25s %-25s %-20s %-20s %-15s %-30s%n",
                "ID",
                "PATIENT",
                "DOCTOR",
                "DATE",
                "REASON",
                "STATUS",
                "NOTES"
        );

        System.out.println(
                "================================================================================================================================================"
        );

        for (Appointment appointment : appointments) {

            String patientName = "N/A";

            if (appointment.getPatient() != null) {

                patientName =
                        appointment.getPatient().getFirstName()
                                + " "
                                + appointment.getPatient().getLastName();
            }


            String doctorName = "N/A";

            if (appointment.getDoctor() != null) {

                doctorName =
                        "Dr. "
                                + appointment.getDoctor().getFirstName()
                                + " "
                                + appointment.getDoctor().getLastName();
            }


            String appointmentDate = "N/A";

            if (appointment.getAppointmentDate() != null) {

                appointmentDate =
                        appointment.getAppointmentDate()
                                .format(dateFormatter);
            }


            String reason =
                    appointment.getReason() != null
                            ? appointment.getReason()
                            : "";

            String status =
                    appointment.getStatus() != null
                            ? appointment.getStatus()
                            : "";

            String notes =
                    appointment.getNotes() != null
                            ? appointment.getNotes()
                            : "";


            System.out.printf(
                    "%-8d %-25s %-25s %-20s %-20s %-15s %-30s%n",
                    appointment.getId(),
                    patientName,
                    doctorName,
                    appointmentDate,
                    reason,
                    status,
                    notes
            );
        }

        System.out.println(
                "================================================================================================================================================"
        );
    }


    // =========================================================
    // DISPLAY SINGLE APPOINTMENT
    // =========================================================

    public void displayAppointment(Appointment appointment) {

        if (appointment == null) {

            System.out.println(
                    "Appointment not found."
            );

            return;
        }


        String patientName = "N/A";

        if (appointment.getPatient() != null) {

            patientName =
                    appointment.getPatient().getFirstName()
                            + " "
                            + appointment.getPatient().getLastName();
        }


        String doctorName = "N/A";

        if (appointment.getDoctor() != null) {

            doctorName =
                    "Dr. "
                            + appointment.getDoctor().getFirstName()
                            + " "
                            + appointment.getDoctor().getLastName();
        }


        System.out.println();
        System.out.println(
                "=============================================="
        );
        System.out.println(
                " APPOINTMENT DETAILS"
        );
        System.out.println(
                "=============================================="
        );

        System.out.println(
                "Appointment ID : " + appointment.getId()
        );

        System.out.println(
                "Patient : " + patientName
        );

        System.out.println(
                "Doctor : " + doctorName
        );

        System.out.println(
                "Specialization : "
                        + (
                        appointment.getDoctor() != null
                                ? appointment.getDoctor().getSpecialization()
                                : "N/A"
                )
        );

        System.out.println(
                "Date : "
                        + (
                        appointment.getAppointmentDate() != null
                                ? appointment.getAppointmentDate()
                                .format(dateFormatter)
                                : "N/A"
                )
        );

        System.out.println(
                "Reason : "
                        + appointment.getReason()
        );

        System.out.println(
                "Status : "
                        + appointment.getStatus()
        );

        System.out.println(
                "Notes : "
                        + appointment.getNotes()
        );

        System.out.println(
                "=============================================="
        );
    }


    // =========================================================
    // DISPLAY APPOINTMENT CREATED MESSAGE
    // =========================================================

    public void displayAppointmentCreated() {

        System.out.println();
        System.out.println(
                "Appointment created successfully."
        );
    }


    // =========================================================
    // DISPLAY UPDATE MESSAGE
    // =========================================================

    public void displayAppointmentUpdated() {

        System.out.println();
        System.out.println(
                "Appointment updated successfully."
        );
    }


    // =========================================================
    // DISPLAY DELETE MESSAGE
    // =========================================================

    public void displayAppointmentDeleted() {

        System.out.println();
        System.out.println(
                "Appointment deleted successfully."
        );
    }
}
