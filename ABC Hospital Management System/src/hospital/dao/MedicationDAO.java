package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Medication;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationDAO {

    public boolean addMedication(Medication medication) {
        String sql = """
                INSERT INTO Medication
                (Name, Description, DosageForm, Price, QuantityInStock)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medication.getName());
            stmt.setString(2, medication.getDescription());
            stmt.setString(3, medication.getDosageForm());
            stmt.setDouble(4, medication.getPrice());
            stmt.setInt(5, medication.getQuantityInStock());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding medication: " + e.getMessage());
            return false;
        }
    }

    public List<Medication> findAllMedications() {
        List<Medication> medications = new ArrayList<>();

        String sql = """
                SELECT MedicationId, Name, Description,
                       DosageForm, Price, QuantityInStock
                FROM Medication
                ORDER BY MedicationId
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                medications.add(mapMedication(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error loading medications: " + e.getMessage());
        }

        return medications;
    }

    public Medication findMedicationById(int id) {
        String sql = """
                SELECT MedicationId, Name, Description,
                       DosageForm, Price, QuantityInStock
                FROM Medication
                WHERE MedicationId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapMedication(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding medication: " + e.getMessage());
        }

        return null;
    }

    public boolean updateMedication(Medication medication) {
        String sql = """
                UPDATE Medication
                SET Name = ?,
                    Description = ?,
                    DosageForm = ?,
                    Price = ?,
                    QuantityInStock = ?
                WHERE MedicationId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medication.getName());
            stmt.setString(2, medication.getDescription());
            stmt.setString(3, medication.getDosageForm());
            stmt.setDouble(4, medication.getPrice());
            stmt.setInt(5, medication.getQuantityInStock());
            stmt.setInt(6, medication.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating medication: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMedication(int id) {
        String sql = "DELETE FROM Medication WHERE MedicationId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting medication: " + e.getMessage());
            return false;
        }
    }

    private Medication mapMedication(ResultSet rs) throws SQLException {
        Medication medication = new Medication();

        medication.setId(rs.getInt("MedicationId"));
        medication.setName(rs.getString("Name"));
        medication.setDescription(rs.getString("Description"));
        medication.setDosageForm(rs.getString("DosageForm"));
        medication.setPrice(rs.getDouble("Price"));
        medication.setQuantityInStock(rs.getInt("QuantityInStock"));

        return medication;
    }
}