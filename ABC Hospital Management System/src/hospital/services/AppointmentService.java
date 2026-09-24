package hospital.services;

import hospital.dao.AppointmentDAO;
import hospital.models.Appointment;

import java.util.List;

public class AppointmentService {

    private final AppointmentDAO appointmentDAO;

    public AppointmentService() {
        appointmentDAO = new AppointmentDAO();
    }


    // =========================================================
    // CREATE APPOINTMENT
    // =========================================================

    public boolean addAppointment(Appointment appointment) {

        if (appointment == null) {
            System.out.println("Appointment cannot be null.");
            return false;
        }

        if (appointment.getPatient() == null) {
            System.out.println("Patient is required.");
            return false;
        }

        if (appointment.getDoctor() == null) {
            System.out.println("Doctor is required.");
            return false;
        }

        if (appointment.getAppointmentDate() == null) {
            System.out.println("Appointment date is required.");
            return false;
        }

        if (appointment.getReason() == null ||
                appointment.getReason().trim().isEmpty()) {

            System.out.println("Appointment reason is required.");
            return false;
        }

        if (appointment.getStatus() == null ||
                appointment.getStatus().trim().isEmpty()) {

            appointment.setStatus("Scheduled");
        }

        return appointmentDAO.addAppointment(appointment);
    }


    // =========================================================
    // GET ALL APPOINTMENTS
    // =========================================================

    public List<Appointment> getAllAppointments() {

        return appointmentDAO.getAllAppointments();
    }


    // =========================================================
    // GET APPOINTMENT BY ID
    // =========================================================

    public Appointment getAppointmentById(int appointmentId) {

        if (appointmentId <= 0) {
            System.out.println("Invalid appointment ID.");
            return null;
        }

        return appointmentDAO.getAppointmentById(appointmentId);
    }


    // =========================================================
    // UPDATE APPOINTMENT
    // =========================================================

    public boolean updateAppointment(Appointment appointment) {

        if (appointment == null) {
            System.out.println("Appointment cannot be null.");
            return false;
        }

        if (appointment.getId() <= 0) {
            System.out.println("Invalid appointment ID.");
            return false;
        }

        if (appointment.getPatient() == null) {
            System.out.println("Patient is required.");
            return false;
        }

        if (appointment.getDoctor() == null) {
            System.out.println("Doctor is required.");
            return false;
        }

        if (appointment.getAppointmentDate() == null) {
            System.out.println("Appointment date is required.");
            return false;
        }

        return appointmentDAO.updateAppointment(appointment);
    }


    // =========================================================
    // DELETE APPOINTMENT
    // =========================================================

    public boolean deleteAppointment(int appointmentId) {

        if (appointmentId <= 0) {
            System.out.println("Invalid appointment ID.");
            return false;
        }

        return appointmentDAO.deleteAppointment(appointmentId);
    }


    // =========================================================
    // GET APPOINTMENTS BY PATIENT
    // =========================================================

    public List<Appointment> getAppointmentsByPatient(int patientId) {

        if (patientId <= 0) {
            System.out.println("Invalid patient ID.");
            return List.of();
        }

        return appointmentDAO.getAppointmentsByPatient(patientId);
    }


    // =========================================================
    // GET APPOINTMENTS BY DOCTOR
    // =========================================================

    public List<Appointment> getAppointmentsByDoctor(int doctorId) {

        if (doctorId <= 0) {
            System.out.println("Invalid doctor ID.");
            return List.of();
        }

        return appointmentDAO.getAppointmentsByDoctor(doctorId);
    }
}
