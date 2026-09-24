package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Doctor;
import hospital.models.Patient;
import hospital.models.Prescription;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {

    public boolean addPrescription(Prescription prescription) {

        String sql = """
                INSERT INTO Prescription
                (PatientId, DoctorId, PrescriptionDate)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int doctorDbId =
                    getDoctorIdByStaffId(conn,
                            prescription.getDoctor().getStaffId());

            stmt.setInt(1,
                    prescription.getPatient().getPatientId());

            stmt.setInt(2, doctorDbId);

            stmt.setDate(3,
                    Date.valueOf(prescription.getPrescriptionDate()));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding prescription: " + e.getMessage());
            return false;
        }
    }

    public List<Prescription> findAllPrescriptions() {

        List<Prescription> prescriptions = new ArrayList<>();

        String sql = """
                SELECT PrescriptionId,
                       PatientId,
                       DoctorId,
                       PrescriptionDate
                FROM Prescription
                ORDER BY PrescriptionId
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                prescriptions.add(mapPrescription(conn, rs));
            }

        } catch (SQLException e) {
            System.out.println("Error loading prescriptions: "
                    + e.getMessage());
        }

        return prescriptions;
    }

    public Prescription findPrescriptionById(int id) {

        String sql = """
                SELECT PrescriptionId,
                       PatientId,
                       DoctorId,
                       PrescriptionDate
                FROM Prescription
                WHERE PrescriptionId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapPrescription(conn, rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding prescription: "
                    + e.getMessage());
        }

        return null;
    }

    public List<Prescription> findPrescriptionsByPatient(int patientId) {

        List<Prescription> prescriptions = new ArrayList<>();

        String sql = """
                SELECT PrescriptionId,
                       PatientId,
                       DoctorId,
                       PrescriptionDate
                FROM Prescription
                WHERE PatientId = ?
                ORDER BY PrescriptionDate DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    prescriptions.add(mapPrescription(conn, rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading patient prescriptions: "
                    + e.getMessage());
        }

        return prescriptions;
    }

    public List<Prescription> findPrescriptionsByDoctor(int staffId) {

        List<Prescription> prescriptions = new ArrayList<>();

        String sql = """
                SELECT p.PrescriptionId,
                       p.PatientId,
                       p.DoctorId,
                       p.PrescriptionDate
                FROM Prescription p
                INNER JOIN Doctor d
                    ON p.DoctorId = d.DoctorId
                WHERE d.StaffId = ?
                ORDER BY p.PrescriptionDate DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    prescriptions.add(mapPrescription(conn, rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading doctor prescriptions: "
                    + e.getMessage());
        }

        return prescriptions;
    }

    public boolean updatePrescription(Prescription prescription) {

        String sql = """
                UPDATE Prescription
                SET PatientId = ?,
                    DoctorId = ?,
                    PrescriptionDate = ?
                WHERE PrescriptionId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int doctorDbId =
                    getDoctorIdByStaffId(conn,
                            prescription.getDoctor().getStaffId());

            stmt.setInt(1,
                    prescription.getPatient().getPatientId());

            stmt.setInt(2, doctorDbId);

            stmt.setDate(3,
                    Date.valueOf(prescription.getPrescriptionDate()));

            stmt.setInt(4, prescription.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating prescription: "
                    + e.getMessage());
            return false;
        }
    }

    public boolean deletePrescription(int id) {

        String sql = "DELETE FROM Prescription WHERE PrescriptionId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting prescription: "
                    + e.getMessage());
            return false;
        }
    }

    private Prescription mapPrescription(
            Connection conn,
            ResultSet rs) throws SQLException {

        Prescription prescription = new Prescription();

        prescription.setId(
                rs.getInt("PrescriptionId"));

        Patient patient = new Patient();
        patient.setPatientId(
                rs.getInt("PatientId"));

        Doctor doctor = new Doctor();
        doctor.setStaffId(
                getStaffIdByDoctorId(
                        conn,
                        rs.getInt("DoctorId")));

        prescription.setPatient(patient);
        prescription.setDoctor(doctor);

        Date date = rs.getDate("PrescriptionDate");

        if (date != null) {
            prescription.setPrescriptionDate(
                    date.toLocalDate());
        }

        return prescription;
    }

    private int getDoctorIdByStaffId(
            Connection conn,
            int staffId) throws SQLException {

        String sql =
                "SELECT DoctorId FROM Doctor WHERE StaffId = ?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("DoctorId");
                }
            }
        }

        throw new SQLException(
                "Doctor not found for Staff ID: " + staffId);
    }

    private int getStaffIdByDoctorId(
            Connection conn,
            int doctorId) throws SQLException {

        String sql =
                "SELECT StaffId FROM Doctor WHERE DoctorId = ?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("StaffId");
                }
            }
        }

        throw new SQLException(
                "Staff ID not found for Doctor ID: " + doctorId);
    }
}