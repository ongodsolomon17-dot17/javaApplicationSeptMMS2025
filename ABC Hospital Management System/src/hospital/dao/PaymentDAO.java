package hospital.dao;

import hospital.models.Invoice;
import hospital.models.Payment;
import hospital.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // =========================================================
    // ADD PAYMENT
    // =========================================================
    public boolean addPayment(Payment payment) {

        String sql = """
                INSERT INTO Payment
                (InvoiceId, Amount, PaymentDate, PaymentMethod)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, payment.getInvoice().getId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setDate(3, Date.valueOf(payment.getPaymentDate()));
            stmt.setString(4, payment.getPaymentMethod());

            int rows = stmt.executeUpdate();

            if (rows > 0) {

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        payment.setId(rs.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error adding payment: "
                    + e.getMessage());
        }

        return false;
    }

    // =========================================================
    // VIEW ALL PAYMENTS
    // =========================================================
    public List<Payment> findAllPayments() {

        List<Payment> payments = new ArrayList<>();

        String sql = """
                SELECT
                    PaymentId,
                    InvoiceId,
                    Amount,
                    PaymentDate,
                    PaymentMethod
                FROM Payment
                ORDER BY PaymentId
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                payments.add(mapPayment(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving payments: "
                    + e.getMessage());
        }

        return payments;
    }

    // =========================================================
    // FIND PAYMENT BY ID
    // =========================================================
    public Payment findPaymentById(int id) {

        String sql = """
                SELECT
                    PaymentId,
                    InvoiceId,
                    Amount,
                    PaymentDate,
                    PaymentMethod
                FROM Payment
                WHERE PaymentId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapPayment(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding payment: "
                    + e.getMessage());
        }

        return null;
    }

    // =========================================================
    // FIND PAYMENTS BY INVOICE
    // =========================================================
    public List<Payment> findPaymentsByInvoice(int invoiceId) {

        List<Payment> payments = new ArrayList<>();

        String sql = """
                SELECT
                    PaymentId,
                    InvoiceId,
                    Amount,
                    PaymentDate,
                    PaymentMethod
                FROM Payment
                WHERE InvoiceId = ?
                ORDER BY PaymentId
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    payments.add(mapPayment(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding invoice payments: "
                    + e.getMessage());
        }

        return payments;
    }

    // =========================================================
    // UPDATE PAYMENT
    // =========================================================
    public boolean updatePayment(Payment payment) {

        String sql = """
                UPDATE Payment
                SET InvoiceId = ?,
                    Amount = ?,
                    PaymentDate = ?,
                    PaymentMethod = ?
                WHERE PaymentId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payment.getInvoice().getId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setDate(3, Date.valueOf(payment.getPaymentDate()));
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setInt(5, payment.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating payment: "
                    + e.getMessage());
        }

        return false;
    }

    // =========================================================
    // DELETE PAYMENT
    // =========================================================
    public boolean deletePayment(int id) {

        String sql = "DELETE FROM Payment WHERE PaymentId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting payment: "
                    + e.getMessage());
        }

        return false;
    }

    // =========================================================
    // MAP RESULT TO MODEL
    // =========================================================
    private Payment mapPayment(ResultSet rs)
            throws SQLException {

        Payment payment = new Payment();

        payment.setId(rs.getInt("PaymentId"));

        Invoice invoice = new Invoice();
        invoice.setId(rs.getInt("InvoiceId"));
        payment.setInvoice(invoice);

        payment.setAmount(rs.getDouble("Amount"));

        Date paymentDate = rs.getDate("PaymentDate");
        if (paymentDate != null) {
            payment.setPaymentDate(paymentDate.toLocalDate());
        }

        payment.setPaymentMethod(rs.getString("PaymentMethod"));

        return payment;
    }
}