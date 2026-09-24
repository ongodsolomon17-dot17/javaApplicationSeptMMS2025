package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Invoice;
import hospital.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    // =========================================================
    // ADD INVOICE
    // =========================================================
    public void addInvoice(Invoice invoice) {

        String sql =
                "INSERT INTO Invoice " +
                "(PatientId, InvoiceDate, TotalAmount, Status) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, invoice.getPatient().getPatientId());
            stmt.setDate(2, Date.valueOf(invoice.getInvoiceDate()));
            stmt.setDouble(3, invoice.getTotalAmount());
            stmt.setString(4, invoice.getStatus());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    invoice.setId(rs.getInt(1));
                }
            }

            System.out.println("Invoice added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding invoice: " + e.getMessage());
        }
    }


    // =========================================================
    // FIND ALL INVOICES
    // =========================================================
    public List<Invoice> findAllInvoices() {

        List<Invoice> invoices = new ArrayList<>();

        String sql =
                "SELECT i.InvoiceId, i.PatientId, " +
                "p.FirstName, p.LastName, " +
                "i.InvoiceDate, i.TotalAmount, i.Status " +
                "FROM Invoice i " +
                "JOIN Patient pt ON i.PatientId = pt.PatientId " +
                "JOIN Person p ON pt.PersonId = p.PersonId " +
                "ORDER BY i.InvoiceId";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                invoices.add(mapInvoice(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving invoices: "
                    + e.getMessage());
        }

        return invoices;
    }


    // =========================================================
    // FIND INVOICE BY ID
    // =========================================================
    public Invoice findInvoiceById(int invoiceId) {

        String sql =
                "SELECT i.InvoiceId, i.PatientId, " +
                "p.FirstName, p.LastName, " +
                "i.InvoiceDate, i.TotalAmount, i.Status " +
                "FROM Invoice i " +
                "JOIN Patient pt ON i.PatientId = pt.PatientId " +
                "JOIN Person p ON pt.PersonId = p.PersonId " +
                "WHERE i.InvoiceId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapInvoice(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving invoice: "
                    + e.getMessage());
        }

        return null;
    }


    // =========================================================
    // FIND INVOICES BY PATIENT
    // =========================================================
    public List<Invoice> findInvoicesByPatient(int patientId) {

        List<Invoice> invoices = new ArrayList<>();

        String sql =
                "SELECT i.InvoiceId, i.PatientId, " +
                "p.FirstName, p.LastName, " +
                "i.InvoiceDate, i.TotalAmount, i.Status " +
                "FROM Invoice i " +
                "JOIN Patient pt ON i.PatientId = pt.PatientId " +
                "JOIN Person p ON pt.PersonId = p.PersonId " +
                "WHERE i.PatientId = ? " +
                "ORDER BY i.InvoiceDate DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    invoices.add(mapInvoice(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving patient invoices: "
                    + e.getMessage());
        }

        return invoices;
    }


    // =========================================================
    // UPDATE INVOICE
    // =========================================================
    public void updateInvoice(Invoice invoice) {

        String sql =
                "UPDATE Invoice " +
                "SET PatientId = ?, " +
                "InvoiceDate = ?, " +
                "TotalAmount = ?, " +
                "Status = ? " +
                "WHERE InvoiceId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoice.getPatient().getPatientId());
            stmt.setDate(2, Date.valueOf(invoice.getInvoiceDate()));
            stmt.setDouble(3, invoice.getTotalAmount());
            stmt.setString(4, invoice.getStatus());
            stmt.setInt(5, invoice.getId());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Invoice updated successfully.");
            } else {
                System.out.println("Invoice not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating invoice: "
                    + e.getMessage());
        }
    }


    // =========================================================
    // DELETE INVOICE
    // =========================================================
    public void deleteInvoice(int invoiceId) {

        String sql =
                "DELETE FROM Invoice " +
                "WHERE InvoiceId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Invoice deleted successfully.");
            } else {
                System.out.println("Invoice not found.");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error deleting invoice: " + e.getMessage()
            );
        }
    }


    // =========================================================
    // MAP RESULTSET TO INVOICE
    // =========================================================
    private Invoice mapInvoice(ResultSet rs) throws SQLException {

        Invoice invoice = new Invoice();

        invoice.setId(rs.getInt("InvoiceId"));

        Patient patient = new Patient();

        patient.setPatientId(rs.getInt("PatientId"));

        patient.setFirstName(rs.getString("FirstName"));
        patient.setLastName(rs.getString("LastName"));

        invoice.setPatient(patient);

        invoice.setInvoiceDate(
                rs.getDate("InvoiceDate").toLocalDate()
        );

        invoice.setTotalAmount(
                rs.getDouble("TotalAmount")
        );

        invoice.setStatus(
                rs.getString("Status")
        );

        return invoice;
    }
}