package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Medication;
import hospital.models.Prescription;
import hospital.models.PrescriptionItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionItemDAO {

    public boolean addPrescriptionItem(
            PrescriptionItem item) {

        String sql = """
                INSERT INTO PrescriptionItem
                (PrescriptionId, MedicationId, Dosage,
                 Frequency, Duration, DurationUnit, Instructions)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1,
                    item.getPrescription().getId());

            stmt.setInt(2,
                    item.getMedication().getId());

            stmt.setString(3,
                    item.getDosage());

            stmt.setString(4,
                    item.getFrequency());

            stmt.setInt(5,
                    item.getDuration());

            stmt.setString(6,
                    item.getDurationUnit());

            stmt.setString(7,
                    item.getInstructions());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error adding prescription item: "
                    + e.getMessage());

            return false;
        }
    }

    public List<PrescriptionItem> findAllPrescriptionItems() {

        List<PrescriptionItem> items =
                new ArrayList<>();

        String sql = """
                SELECT PrescriptionItemId,
                       PrescriptionId,
                       MedicationId,
                       Dosage,
                       Frequency,
                       Duration,
                       DurationUnit,
                       Instructions
                FROM PrescriptionItem
                ORDER BY PrescriptionItemId
                """;

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                items.add(mapPrescriptionItem(rs));
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error loading prescription items: "
                    + e.getMessage());
        }

        return items;
    }

    public PrescriptionItem findPrescriptionItemById(int id) {

        String sql = """
                SELECT PrescriptionItemId,
                       PrescriptionId,
                       MedicationId,
                       Dosage,
                       Frequency,
                       Duration,
                       DurationUnit,
                       Instructions
                FROM PrescriptionItem
                WHERE PrescriptionItemId = ?
                """;

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapPrescriptionItem(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error finding prescription item: "
                    + e.getMessage());
        }

        return null;
    }

    public List<PrescriptionItem>
    findItemsByPrescription(int prescriptionId) {

        List<PrescriptionItem> items =
                new ArrayList<>();

        String sql = """
                SELECT PrescriptionItemId,
                       PrescriptionId,
                       MedicationId,
                       Dosage,
                       Frequency,
                       Duration,
                       DurationUnit,
                       Instructions
                FROM PrescriptionItem
                WHERE PrescriptionId = ?
                ORDER BY PrescriptionItemId
                """;

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, prescriptionId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    items.add(mapPrescriptionItem(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error loading prescription items: "
                    + e.getMessage());
        }

        return items;
    }

    public boolean updatePrescriptionItem(
            PrescriptionItem item) {

        String sql = """
                UPDATE PrescriptionItem
                SET PrescriptionId = ?,
                    MedicationId = ?,
                    Dosage = ?,
                    Frequency = ?,
                    Duration = ?,
                    DurationUnit = ?,
                    Instructions = ?
                WHERE PrescriptionItemId = ?
                """;

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1,
                    item.getPrescription().getId());

            stmt.setInt(2,
                    item.getMedication().getId());

            stmt.setString(3,
                    item.getDosage());

            stmt.setString(4,
                    item.getFrequency());

            stmt.setInt(5,
                    item.getDuration());

            stmt.setString(6,
                    item.getDurationUnit());

            stmt.setString(7,
                    item.getInstructions());

            stmt.setInt(8,
                    item.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error updating prescription item: "
                    + e.getMessage());

            return false;
        }
    }

    public boolean deletePrescriptionItem(int id) {

        String sql =
                "DELETE FROM PrescriptionItem " +
                "WHERE PrescriptionItemId = ?";

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error deleting prescription item: "
                    + e.getMessage());

            return false;
        }
    }

    private PrescriptionItem mapPrescriptionItem(
            ResultSet rs) throws SQLException {

        PrescriptionItem item =
                new PrescriptionItem();

        item.setId(
                rs.getInt("PrescriptionItemId"));

        Prescription prescription =
                new Prescription();

        prescription.setId(
                rs.getInt("PrescriptionId"));

        Medication medication =
                new Medication();

        medication.setId(
                rs.getInt("MedicationId"));

        item.setPrescription(prescription);
        item.setMedication(medication);

        item.setDosage(
                rs.getString("Dosage"));

        item.setFrequency(
                rs.getString("Frequency"));

        item.setDuration(
                rs.getInt("Duration"));

        item.setDurationUnit(
                rs.getString("DurationUnit"));

        item.setInstructions(
                rs.getString("Instructions"));

        return item;
    }
}