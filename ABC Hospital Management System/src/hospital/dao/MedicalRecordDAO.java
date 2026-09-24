package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.MedicalRecord;
import hospital.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAO {

    public void addMedicalRecord(MedicalRecord record) {

        String sql = """
            INSERT INTO MedicalRecord
            (
                PatientId,
                CreatedDate
            )
            VALUES (?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, record.getPatient().getPatientId());
            stmt.setDate(2, Date.valueOf(record.getCreatedDate()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    record.setId(rs.getInt(1));
                }
            }

            System.out.println("Medical record added successfully.");

        } catch (SQLException e) {
            System.out.println(
                    "Error adding medical record: " + e.getMessage()
            );
        }
    }

    public List<MedicalRecord> findAllMedicalRecords() {

        List<MedicalRecord> records = new ArrayList<>();

        String sql = """
            SELECT
                MedicalRecordId,
                PatientId,
                CreatedDate
            FROM MedicalRecord
            ORDER BY CreatedDate DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                records.add(mapMedicalRecord(rs));
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving medical records: "
                    + e.getMessage()
            );
        }

        return records;
    }

    public MedicalRecord findMedicalRecordById(int id) {

        String sql = """
            SELECT
                MedicalRecordId,
                PatientId,
                CreatedDate
            FROM MedicalRecord
            WHERE MedicalRecordId = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapMedicalRecord(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error finding medical record: "
                    + e.getMessage()
            );
        }

        return null;
    }

    public MedicalRecord findMedicalRecordByPatient(int patientId) {

        String sql = """
            SELECT
                MedicalRecordId,
                PatientId,
                CreatedDate
            FROM MedicalRecord
            WHERE PatientId = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapMedicalRecord(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error finding patient's medical record: "
                    + e.getMessage()
            );
        }

        return null;
    }

    public void updateMedicalRecord(MedicalRecord record) {

        String sql = """
            UPDATE MedicalRecord
            SET
                PatientId = ?,
                CreatedDate = ?
            WHERE MedicalRecordId = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, record.getPatient().getPatientId());
            stmt.setDate(2, Date.valueOf(record.getCreatedDate()));
            stmt.setInt(3, record.getId());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println(
                        "Medical record updated successfully."
                );
            } else {
                System.out.println(
                        "Medical record not found."
                );
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error updating medical record: "
                    + e.getMessage()
            );
        }
    }

    public void deleteMedicalRecord(int id) {

        String sql = """
            DELETE FROM MedicalRecord
            WHERE MedicalRecordId = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println(
                        "Medical record deleted successfully."
                );
            } else {
                System.out.println(
                        "Medical record not found."
                );
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error deleting medical record: "
                    + e.getMessage()
            );
        }
    }

    private MedicalRecord mapMedicalRecord(ResultSet rs)
            throws SQLException {

        MedicalRecord record = new MedicalRecord();

        record.setId(
                rs.getInt("MedicalRecordId")
        );

        Patient patient = new Patient();

        patient.setPatientId(
                rs.getInt("PatientId")
        );

        record.setPatient(patient);

        Date createdDate = rs.getDate("CreatedDate");

        if (createdDate != null) {
            record.setCreatedDate(
                    createdDate.toLocalDate()
            );
        }

        return record;
    }
}