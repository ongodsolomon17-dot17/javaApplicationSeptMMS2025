package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.MedicationDispensing;
import hospital.models.Patient;
import hospital.models.Pharmacist;
import hospital.models.Prescription;
import hospital.models.PrescriptionItem;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicationDispensingDAO {

    public boolean addDispensing(
            MedicationDispensing dispensing) {

        String insertSql = """
                INSERT INTO MedicationDispensing
                (PrescriptionId,
                 PrescriptionItemId,
                 PharmacistId,
                 PatientId,
                 DispensingDate,
                 Quantity,
                 Status,
                 Notes)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        String stockSql = """
                UPDATE Medication
                SET QuantityInStock =
                    QuantityInStock - ?
                WHERE MedicationId = ?
                  AND QuantityInStock >= ?
                """;

        try (Connection conn
                = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false);

            try {

                int medicationId
                        = getMedicationIdFromPrescriptionItem(
                                conn,
                                dispensing.getPrescriptionItem()
                                        .getId());

                int pharmacistDbId
                        = getPharmacistIdByStaffId(
                                conn,
                                dispensing.getPharmacist()
                                        .getStaffId());

                try (PreparedStatement stockStmt
                        = conn.prepareStatement(stockSql)) {

                    int quantity
                            = dispensing.getQuantity();

                    stockStmt.setInt(1, quantity);
                    stockStmt.setInt(2, medicationId);
                    stockStmt.setInt(3, quantity);

                    int updated
                            = stockStmt.executeUpdate();

                    if (updated == 0) {
                        conn.rollback();

                        System.out.println(
                                "Insufficient medication stock.");

                        return false;
                    }
                }

                try (PreparedStatement stmt
                        = conn.prepareStatement(insertSql)) {

                    stmt.setInt(
                            1,
                            dispensing.getPrescription()
                                    .getId());

                    stmt.setInt(
                            2,
                            dispensing.getPrescriptionItem()
                                    .getId());

                    stmt.setInt(
                            3,
                            pharmacistDbId);

                    stmt.setInt(
                            4,
                            dispensing.getPatient()
                                    .getPatientId());

                    stmt.setTimestamp(
                            5,
                            Timestamp.valueOf(
                                    dispensing.getDispensingDate().atStartOfDay()
                            )
                    );

                    stmt.setInt(
                            6,
                            dispensing.getQuantity());

                    stmt.setString(
                            7,
                            dispensing.getStatus());

                    stmt.setString(
                            8,
                            dispensing.getNotes());

                    stmt.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {

                conn.rollback();
                throw e;

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error adding dispensing: "
                    + e.getMessage());

            return false;
        }
    }

    public List<MedicationDispensing>
            findAllDispensings() {

        List<MedicationDispensing> dispensings
                = new ArrayList<>();

        String sql = """
                SELECT MedicationDispensingId,
                       PrescriptionId,
                       PrescriptionItemId,
                       PharmacistId,
                       PatientId,
                       DispensingDate,
                       Quantity,
                       Status,
                       Notes
                FROM MedicationDispensing
                ORDER BY MedicationDispensingId
                """;

        try (Connection conn
                = DatabaseConnection.getConnection(); PreparedStatement stmt
                = conn.prepareStatement(sql); ResultSet rs
                = stmt.executeQuery()) {

            while (rs.next()) {
                dispensings.add(
                        mapDispensing(conn, rs));
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading dispensings: "
                    + e.getMessage());
        }

        return dispensings;
    }

    public MedicationDispensing
            findDispensingById(int id) {

        String sql = """
                SELECT MedicationDispensingId,
                       PrescriptionId,
                       PrescriptionItemId,
                       PharmacistId,
                       PatientId,
                       DispensingDate,
                       Quantity,
                       Status,
                       Notes
                FROM MedicationDispensing
                WHERE MedicationDispensingId = ?
                """;

        try (Connection conn
                = DatabaseConnection.getConnection(); PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs
                    = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapDispensing(conn, rs);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding dispensing: "
                    + e.getMessage());
        }

        return null;
    }

    public List<MedicationDispensing>
            findDispensingsByPatient(int patientId) {

        List<MedicationDispensing> dispensings
                = new ArrayList<>();

        String sql = """
                SELECT MedicationDispensingId,
                       PrescriptionId,
                       PrescriptionItemId,
                       PharmacistId,
                       PatientId,
                       DispensingDate,
                       Quantity,
                       Status,
                       Notes
                FROM MedicationDispensing
                WHERE PatientId = ?
                ORDER BY DispensingDate DESC
                """;

        try (Connection conn
                = DatabaseConnection.getConnection(); PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs
                    = stmt.executeQuery()) {

                while (rs.next()) {
                    dispensings.add(
                            mapDispensing(conn, rs));
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading patient dispensings: "
                    + e.getMessage());
        }

        return dispensings;
    }

    public boolean deleteDispensing(int id) {

        String findSql = """
                SELECT PrescriptionItemId, Quantity
                FROM MedicationDispensing
                WHERE MedicationDispensingId = ?
                """;

        String deleteSql = """
                DELETE FROM MedicationDispensing
                WHERE MedicationDispensingId = ?
                """;

        String stockSql = """
                UPDATE Medication
                SET QuantityInStock =
                    QuantityInStock + ?
                WHERE MedicationId = ?
                """;

        try (Connection conn
                = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false);

            try {

                int prescriptionItemId;
                int quantity;

                try (PreparedStatement stmt
                        = conn.prepareStatement(findSql)) {

                    stmt.setInt(1, id);

                    try (ResultSet rs
                            = stmt.executeQuery()) {

                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }

                        prescriptionItemId
                                = rs.getInt("PrescriptionItemId");

                        quantity
                                = rs.getInt("Quantity");
                    }
                }

                int medicationId
                        = getMedicationIdFromPrescriptionItem(
                                conn,
                                prescriptionItemId);

                try (PreparedStatement stmt
                        = conn.prepareStatement(deleteSql)) {

                    stmt.setInt(1, id);

                    if (stmt.executeUpdate() == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement stmt
                        = conn.prepareStatement(stockSql)) {

                    stmt.setInt(1, quantity);
                    stmt.setInt(2, medicationId);
                    stmt.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {

                conn.rollback();
                throw e;

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting dispensing: "
                    + e.getMessage());

            return false;
        }
    }

    private MedicationDispensing mapDispensing(
            Connection conn,
            ResultSet rs) throws SQLException {

        MedicationDispensing dispensing
                = new MedicationDispensing();

        dispensing.setId(
                rs.getInt(
                        "MedicationDispensingId"));

        Prescription prescription
                = new Prescription();

        prescription.setId(
                rs.getInt("PrescriptionId"));

        PrescriptionItem item
                = new PrescriptionItem();

        item.setId(
                rs.getInt("PrescriptionItemId"));

        Pharmacist pharmacist
                = new Pharmacist();

        pharmacist.setStaffId(
                getStaffIdByPharmacistId(
                        conn,
                        rs.getInt("PharmacistId")));

        Patient patient
                = new Patient();

        patient.setPatientId(
                rs.getInt("PatientId"));

        dispensing.setPrescription(prescription);
        dispensing.setPrescriptionItem(item);
        dispensing.setPharmacist(pharmacist);
        dispensing.setPatient(patient);

        Timestamp timestamp
                = rs.getTimestamp("DispensingDate");

        if (timestamp != null) {
            dispensing.setDispensingDate(
                    timestamp.toLocalDateTime().toLocalDate()
            );
        }

        dispensing.setQuantity(
                rs.getInt("Quantity"));

        dispensing.setStatus(
                rs.getString("Status"));

        dispensing.setNotes(
                rs.getString("Notes"));

        return dispensing;
    }

    private int getMedicationIdFromPrescriptionItem(
            Connection conn,
            int prescriptionItemId)
            throws SQLException {

        String sql = """
                SELECT MedicationId
                FROM PrescriptionItem
                WHERE PrescriptionItemId = ?
                """;

        try (PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setInt(1, prescriptionItemId);

            try (ResultSet rs
                    = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("MedicationId");
                }
            }
        }

        throw new SQLException(
                "Prescription item not found: "
                + prescriptionItemId);
    }

    private int getPharmacistIdByStaffId(
            Connection conn,
            int staffId) throws SQLException {

        String sql
                = "SELECT PharmacistId "
                + "FROM Pharmacist "
                + "WHERE StaffId = ?";

        try (PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);

            try (ResultSet rs
                    = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("PharmacistId");
                }
            }
        }

        throw new SQLException(
                "Pharmacist not found for Staff ID: "
                + staffId);
    }

    private int getStaffIdByPharmacistId(
            Connection conn,
            int pharmacistId) throws SQLException {

        String sql
                = "SELECT StaffId "
                + "FROM Pharmacist "
                + "WHERE PharmacistId = ?";

        try (PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setInt(1, pharmacistId);

            try (ResultSet rs
                    = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("StaffId");
                }
            }
        }

        throw new SQLException(
                "Staff ID not found for Pharmacist ID: "
                + pharmacistId);
    }
}