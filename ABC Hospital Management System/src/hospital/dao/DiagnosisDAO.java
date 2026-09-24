
package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Diagnosis;
import hospital.models.Doctor;
import hospital.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosisDAO {

    // =========================================================
    // ADD DIAGNOSIS
    // =========================================================
    public boolean addDiagnosis(Diagnosis diagnosis) {

        String doctorSql =
                "SELECT DoctorId FROM Doctor WHERE StaffId = ?";

        String insertSql =
                "INSERT INTO Diagnose " +
                "(PatientId, DoctorId, DiagnosisDate, Condition, Description, Notes) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            // -------------------------------------------------
            // Get DoctorId from StaffId
            // -------------------------------------------------
            int doctorId;

            try (PreparedStatement stmt = conn.prepareStatement(doctorSql)) {

                stmt.setInt(1, diagnosis.getDoctor().getStaffId());

                try (ResultSet rs = stmt.executeQuery()) {

                    if (!rs.next()) {
                        System.out.println("Doctor not found.");
                        return false;
                    }

                    doctorId = rs.getInt("DoctorId");
                }
            }

            // -------------------------------------------------
            // Insert Diagnosis
            // -------------------------------------------------
            try (PreparedStatement stmt =
                         conn.prepareStatement(insertSql)) {

                stmt.setInt(
                        1,
                        diagnosis.getPatient().getPatientId()
                );

                stmt.setInt(2, doctorId);

                stmt.setDate(
                        3,
                        Date.valueOf(diagnosis.getDiagnosisDate())
                );

                stmt.setString(
                        4,
                        diagnosis.getCondition()
                );

                stmt.setString(
                        5,
                        diagnosis.getDescription()
                );

                stmt.setString(
                        6,
                        diagnosis.getNotes()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error adding diagnosis: " + e.getMessage()
            );

            return false;
        }
    }


    // =========================================================
    // FIND ALL DIAGNOSES
    // =========================================================
    public List<Diagnosis> findAllDiagnoses() {

        List<Diagnosis> diagnoses = new ArrayList<>();

        String sql =
                "SELECT " +
                "d.DiagnosisId, " +
                "d.PatientId, " +
                "d.DoctorId, " +
                "d.DiagnosisDate, " +
                "d.Condition, " +
                "d.Description, " +
                "d.Notes " +
                "FROM Diagnose d " +
                "ORDER BY d.DiagnosisDate DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Diagnosis diagnosis =
                        mapDiagnosis(conn, rs);

                diagnoses.add(diagnosis);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving diagnoses: "
                    + e.getMessage()
            );
        }

        return diagnoses;
    }


    // =========================================================
    // FIND DIAGNOSIS BY ID
    // =========================================================
    public Diagnosis findDiagnosisById(int diagnosisId) {

        String sql =
                "SELECT " +
                "DiagnosisId, " +
                "PatientId, " +
                "DoctorId, " +
                "DiagnosisDate, " +
                "Condition, " +
                "Description, " +
                "Notes " +
                "FROM Diagnose " +
                "WHERE DiagnosisId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, diagnosisId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return mapDiagnosis(conn, rs);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding diagnosis: "
                    + e.getMessage()
            );
        }

        return null;
    }


    // =========================================================
    // FIND DIAGNOSES BY PATIENT
    // =========================================================
    public List<Diagnosis> findDiagnosesByPatient(int patientId) {

        List<Diagnosis> diagnoses = new ArrayList<>();

        String sql =
                "SELECT " +
                "DiagnosisId, " +
                "PatientId, " +
                "DoctorId, " +
                "DiagnosisDate, " +
                "Condition, " +
                "Description, " +
                "Notes " +
                "FROM Diagnose " +
                "WHERE PatientId = ? " +
                "ORDER BY DiagnosisDate DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    diagnoses.add(
                            mapDiagnosis(conn, rs)
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding patient diagnoses: "
                    + e.getMessage()
            );
        }

        return diagnoses;
    }


    // =========================================================
    // UPDATE DIAGNOSIS
    // =========================================================
    public boolean update(Diagnosis diagnosis) {

        String doctorSql =
                "SELECT DoctorId FROM Doctor WHERE StaffId = ?";

        String updateSql =
                "UPDATE Diagnose SET " +
                "PatientId = ?, " +
                "DoctorId = ?, " +
                "DiagnosisDate = ?, " +
                "Condition = ?, " +
                "Description = ?, " +
                "Notes = ? " +
                "WHERE DiagnosisId = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            // -------------------------------------------------
            // Get DoctorId from StaffId
            // -------------------------------------------------
            int doctorId;

            try (PreparedStatement stmt =
                         conn.prepareStatement(doctorSql)) {

                stmt.setInt(
                        1,
                        diagnosis.getDoctor().getStaffId()
                );

                try (ResultSet rs = stmt.executeQuery()) {

                    if (!rs.next()) {

                        System.out.println("Doctor not found.");

                        return false;
                    }

                    doctorId = rs.getInt("DoctorId");
                }
            }

            // -------------------------------------------------
            // Update Diagnosis
            // -------------------------------------------------
            try (PreparedStatement stmt =
                         conn.prepareStatement(updateSql)) {

                stmt.setInt(
                        1,
                        diagnosis.getPatient().getPatientId()
                );

                stmt.setInt(2, doctorId);

                stmt.setDate(
                        3,
                        Date.valueOf(
                                diagnosis.getDiagnosisDate()
                        )
                );

                stmt.setString(
                        4,
                        diagnosis.getCondition()
                );

                stmt.setString(
                        5,
                        diagnosis.getDescription()
                );

                stmt.setString(
                        6,
                        diagnosis.getNotes()
                );

                stmt.setInt(
                        7,
                        diagnosis.getId()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error updating diagnosis: "
                    + e.getMessage()
            );

            return false;
        }
    }


    // =========================================================
    // DELETE DIAGNOSIS
    // =========================================================
    public boolean delete(int diagnosisId) {

        String sql =
                "DELETE FROM Diagnose " +
                "WHERE DiagnosisId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, diagnosisId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting diagnosis: "
                    + e.getMessage()
            );

            return false;
        }
    }


    // =========================================================
    // MAP DATABASE RECORD TO DIAGNOSIS OBJECT
    // =========================================================
    private Diagnosis mapDiagnosis(
            Connection conn,
            ResultSet rs) throws SQLException {

        Diagnosis diagnosis = new Diagnosis();

        diagnosis.setId(
                rs.getInt("DiagnosisId")
        );

        diagnosis.setDiagnosisDate(
                rs.getDate("DiagnosisDate").toLocalDate()
        );

        diagnosis.setCondition(
                rs.getString("Condition")
        );

        diagnosis.setDescription(
                rs.getString("Description")
        );

        diagnosis.setNotes(
                rs.getString("Notes")
        );

        // -----------------------------------------------------
        // Patient
        // -----------------------------------------------------
        Patient patient = new Patient();

        patient.setPatientId(
                rs.getInt("PatientId")
        );

        diagnosis.setPatient(patient);


        // -----------------------------------------------------
        // Doctor
        // -----------------------------------------------------
        Doctor doctor =
                getDoctorByDoctorId(
                        conn,
                        rs.getInt("DoctorId")
                );

        diagnosis.setDoctor(doctor);

        return diagnosis;
    }


    // =========================================================
    // GET DOCTOR USING DATABASE DoctorId
    // =========================================================
    private Doctor getDoctorByDoctorId(
            Connection conn,
            int doctorId) throws SQLException {

        String sql =
                "SELECT DoctorId, StaffId " +
                "FROM Doctor " +
                "WHERE DoctorId = ?";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Doctor doctor = new Doctor();

                    doctor.setStaffId(
                            rs.getInt("StaffId")
                    );

                    return doctor;
                }
            }
        }

        return null;
    }
}
