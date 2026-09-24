package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Diagnosis;
import hospital.models.Doctor;
import hospital.models.Patient;
import hospital.models.Treatment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO {

    // =========================================================
    // ADD TREATMENT
    // =========================================================
    public boolean addTreatment(Treatment treatment) {

        String doctorSql =
                "SELECT DoctorId FROM Doctor WHERE StaffId = ?";

        String insertSql =
                "INSERT INTO Treatment " +
                "(PatientId, DoctorId, DiagnosisId, TreatmentDate, " +
                "TreatmentName, Description, Notes, Status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn =
                     DatabaseConnection.getConnection()) {

            // -------------------------------------------------
            // Get DoctorId from StaffId
            // -------------------------------------------------
            int doctorId;

            try (PreparedStatement stmt =
                         conn.prepareStatement(doctorSql)) {

                stmt.setInt(
                        1,
                        treatment.getDoctor().getStaffId()
                );

                try (ResultSet rs =
                             stmt.executeQuery()) {

                    if (!rs.next()) {

                        System.out.println(
                                "Doctor not found."
                        );

                        return false;
                    }

                    doctorId =
                            rs.getInt("DoctorId");
                }
            }

            // -------------------------------------------------
            // Insert Treatment
            // -------------------------------------------------
            try (PreparedStatement stmt =
                         conn.prepareStatement(insertSql)) {

                stmt.setInt(
                        1,
                        treatment.getPatient().getPatientId()
                );

                stmt.setInt(
                        2,
                        doctorId
                );

                // Diagnosis is optional
                if (treatment.getDiagnosis() != null) {

                    stmt.setInt(
                            3,
                            treatment.getDiagnosis().getId()
                    );

                } else {

                    stmt.setNull(
                            3,
                            Types.INTEGER
                    );
                }

                stmt.setDate(
                        4,
                        Date.valueOf(
                                treatment.getTreatmentDate()
                        )
                );

                stmt.setString(
                        5,
                        treatment.getTreatmentName()
                );

                stmt.setString(
                        6,
                        treatment.getDescription()
                );

                stmt.setString(
                        7,
                        treatment.getNotes()
                );

                stmt.setString(
                        8,
                        treatment.getStatus()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error adding treatment: "
                    + e.getMessage()
            );

            return false;
        }
    }


    // =========================================================
    // FIND ALL TREATMENTS
    // =========================================================
    public List<Treatment> findAllTreatments() {

        List<Treatment> treatments =
                new ArrayList<>();

        String sql =
                "SELECT " +
                "TreatmentId, " +
                "PatientId, " +
                "DoctorId, " +
                "DiagnosisId, " +
                "TreatmentDate, " +
                "TreatmentName, " +
                "Description, " +
                "Notes, " +
                "Status " +
                "FROM Treatment " +
                "ORDER BY TreatmentDate DESC";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql);
             ResultSet rs =
                     stmt.executeQuery()) {

            while (rs.next()) {

                Treatment treatment =
                        mapTreatment(conn, rs);

                treatments.add(treatment);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving treatments: "
                    + e.getMessage()
            );
        }

        return treatments;
    }


    // =========================================================
    // FIND TREATMENT BY ID
    // =========================================================
    public Treatment findTreatmentById(
            int treatmentId) {

        String sql =
                "SELECT " +
                "TreatmentId, " +
                "PatientId, " +
                "DoctorId, " +
                "DiagnosisId, " +
                "TreatmentDate, " +
                "TreatmentName, " +
                "Description, " +
                "Notes, " +
                "Status " +
                "FROM Treatment " +
                "WHERE TreatmentId = ?";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    treatmentId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return mapTreatment(
                            conn,
                            rs
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding treatment: "
                    + e.getMessage()
            );
        }

        return null;
    }


    // =========================================================
    // FIND TREATMENTS BY PATIENT
    // =========================================================
    public List<Treatment> findTreatmentsByPatient(
            int patientId) {

        List<Treatment> treatments =
                new ArrayList<>();

        String sql =
                "SELECT " +
                "TreatmentId, " +
                "PatientId, " +
                "DoctorId, " +
                "DiagnosisId, " +
                "TreatmentDate, " +
                "TreatmentName, " +
                "Description, " +
                "Notes, " +
                "Status " +
                "FROM Treatment " +
                "WHERE PatientId = ? " +
                "ORDER BY TreatmentDate DESC";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    patientId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                while (rs.next()) {

                    treatments.add(
                            mapTreatment(
                                    conn,
                                    rs
                            )
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding patient treatments: "
                    + e.getMessage()
            );
        }

        return treatments;
    }


    // =========================================================
    // UPDATE TREATMENT
    // =========================================================
    public boolean updateTreatment(
            Treatment treatment) {

        String doctorSql =
                "SELECT DoctorId FROM Doctor WHERE StaffId = ?";

        String updateSql =
                "UPDATE Treatment SET " +
                "PatientId = ?, " +
                "DoctorId = ?, " +
                "DiagnosisId = ?, " +
                "TreatmentDate = ?, " +
                "TreatmentName = ?, " +
                "Description = ?, " +
                "Notes = ?, " +
                "Status = ? " +
                "WHERE TreatmentId = ?";

        try (Connection conn =
                     DatabaseConnection.getConnection()) {

            // -------------------------------------------------
            // Get DoctorId from StaffId
            // -------------------------------------------------
            int doctorId;

            try (PreparedStatement stmt =
                         conn.prepareStatement(doctorSql)) {

                stmt.setInt(
                        1,
                        treatment.getDoctor().getStaffId()
                );

                try (ResultSet rs =
                             stmt.executeQuery()) {

                    if (!rs.next()) {

                        System.out.println(
                                "Doctor not found."
                        );

                        return false;
                    }

                    doctorId =
                            rs.getInt("DoctorId");
                }
            }

            // -------------------------------------------------
            // Update Treatment
            // -------------------------------------------------
            try (PreparedStatement stmt =
                         conn.prepareStatement(updateSql)) {

                stmt.setInt(
                        1,
                        treatment.getPatient().getPatientId()
                );

                stmt.setInt(
                        2,
                        doctorId
                );

                // Diagnosis is optional
                if (treatment.getDiagnosis() != null) {

                    stmt.setInt(
                            3,
                            treatment.getDiagnosis().getId()
                    );

                } else {

                    stmt.setNull(
                            3,
                            Types.INTEGER
                    );
                }

                stmt.setDate(
                        4,
                        Date.valueOf(
                                treatment.getTreatmentDate()
                        )
                );

                stmt.setString(
                        5,
                        treatment.getTreatmentName()
                );

                stmt.setString(
                        6,
                        treatment.getDescription()
                );

                stmt.setString(
                        7,
                        treatment.getNotes()
                );

                stmt.setString(
                        8,
                        treatment.getStatus()
                );

                stmt.setInt(
                        9,
                        treatment.getId()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error updating treatment: "
                    + e.getMessage()
            );

            return false;
        }
    }


    // =========================================================
    // DELETE TREATMENT
    // =========================================================
    public boolean deleteTreatment(
            int treatmentId) {

        String sql =
                "DELETE FROM Treatment " +
                "WHERE TreatmentId = ?";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    treatmentId
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting treatment: "
                    + e.getMessage()
            );

            return false;
        }
    }


    // =========================================================
    // MAP DATABASE RECORD TO TREATMENT OBJECT
    // =========================================================
    private Treatment mapTreatment(
            Connection conn,
            ResultSet rs) throws SQLException {

        Treatment treatment =
                new Treatment();

        // -----------------------------------------------------
        // Treatment information
        // -----------------------------------------------------
        treatment.setId(
                rs.getInt("TreatmentId")
        );

        treatment.setTreatmentDate(
                rs.getDate("TreatmentDate")
                        .toLocalDate()
        );

        treatment.setTreatmentName(
                rs.getString("TreatmentName")
        );

        treatment.setDescription(
                rs.getString("Description")
        );

        treatment.setNotes(
                rs.getString("Notes")
        );

        treatment.setStatus(
                rs.getString("Status")
        );


        // -----------------------------------------------------
        // Patient
        // -----------------------------------------------------
        Patient patient =
                new Patient();

        patient.setPatientId(
                rs.getInt("PatientId")
        );

        treatment.setPatient(
                patient
        );


        // -----------------------------------------------------
        // Doctor
        // -----------------------------------------------------
        Doctor doctor =
                getDoctorByDoctorId(
                        conn,
                        rs.getInt("DoctorId")
                );

        treatment.setDoctor(
                doctor
        );


        // -----------------------------------------------------
        // Diagnosis
        // -----------------------------------------------------
        int diagnosisId =
                rs.getInt("DiagnosisId");

        if (!rs.wasNull()) {

            Diagnosis diagnosis =
                    new Diagnosis();

            diagnosis.setId(
                    diagnosisId
            );

            treatment.setDiagnosis(
                    diagnosis
            );
        }

        return treatment;
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

            stmt.setInt(
                    1,
                    doctorId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    Doctor doctor =
                            new Doctor();

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