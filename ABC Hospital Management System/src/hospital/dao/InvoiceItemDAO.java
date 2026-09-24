package hospital.dao;

import hospital.models.Invoice;
import hospital.models.InvoiceItem;
import hospital.models.Medication;
import hospital.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceItemDAO {

    // =========================================================
    // ADD INVOICE ITEM
    // =========================================================
    public boolean addInvoiceItem(InvoiceItem item) {

        String sql = """
                INSERT INTO InvoiceItem
                (InvoiceId, Description, Quantity, UnitPrice)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, item.getInvoice().getId());
            stmt.setString(2, item.getDescription());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitPrice());

            int rows = stmt.executeUpdate();

            if (rows > 0) {

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        item.setId(rs.getInt(1));
                    }
                }

                // Recalculate invoice total
                updateInvoiceTotal(conn, item.getInvoice().getId());

                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error adding invoice item: "
                    + e.getMessage());
        }

        return false;
    }

    // =========================================================
    // VIEW ALL ITEMS
    // =========================================================
    public List<InvoiceItem> findAllInvoiceItems() {

        List<InvoiceItem> items = new ArrayList<>();

        String sql = """
                SELECT
                    InvoiceItemId,
                    InvoiceId,
                    Description,
                    Quantity,
                    UnitPrice,
                    Amount
                FROM InvoiceItem
                ORDER BY InvoiceItemId
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                items.add(mapInvoiceItem(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving invoice items: "
                    + e.getMessage());
        }

        return items;
    }

    // =========================================================
    // FIND ITEM BY ID
    // =========================================================
    public InvoiceItem findInvoiceItemById(int id) {

        String sql = """
                SELECT
                    InvoiceItemId,
                    InvoiceId,
                    Description,
                    Quantity,
                    UnitPrice,
                    Amount
                FROM InvoiceItem
                WHERE InvoiceItemId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapInvoiceItem(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding invoice item: "
                    + e.getMessage());
        }

        return null;
    }

    // =========================================================
    // FIND ITEMS BY INVOICE
    // =========================================================
    public List<InvoiceItem> findItemsByInvoice(int invoiceId) {

        List<InvoiceItem> items = new ArrayList<>();

        String sql = """
                SELECT
                    InvoiceItemId,
                    InvoiceId,
                    Description,
                    Quantity,
                    UnitPrice,
                    Amount
                FROM InvoiceItem
                WHERE InvoiceId = ?
                ORDER BY InvoiceItemId
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    items.add(mapInvoiceItem(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding invoice items: "
                    + e.getMessage());
        }

        return items;
    }

    // =========================================================
    // UPDATE ITEM
    // =========================================================
    public boolean updateInvoiceItem(InvoiceItem item) {

        String sql = """
                UPDATE InvoiceItem
                SET Description = ?,
                    Quantity = ?,
                    UnitPrice = ?
                WHERE InvoiceItemId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getDescription());
            stmt.setInt(2, item.getQuantity());
            stmt.setDouble(3, item.getUnitPrice());
            stmt.setInt(4, item.getId());

            boolean success = stmt.executeUpdate() > 0;

            if (success && item.getInvoice() != null) {
                updateInvoiceTotal(
                        conn,
                        item.getInvoice().getId()
                );
            }

            return success;

        } catch (SQLException e) {
            System.out.println("Error updating invoice item: "
                    + e.getMessage());
        }

        return false;
    }

    // =========================================================
    // DELETE ITEM
    // =========================================================
    public boolean deleteInvoiceItem(int id) {

        String findSql =
                "SELECT InvoiceId FROM InvoiceItem WHERE InvoiceItemId = ?";

        String deleteSql =
                "DELETE FROM InvoiceItem WHERE InvoiceItemId = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            int invoiceId = 0;

            try (PreparedStatement findStmt =
                         conn.prepareStatement(findSql)) {

                findStmt.setInt(1, id);

                try (ResultSet rs = findStmt.executeQuery()) {
                    if (rs.next()) {
                        invoiceId = rs.getInt("InvoiceId");
                    }
                }
            }

            try (PreparedStatement deleteStmt =
                         conn.prepareStatement(deleteSql)) {

                deleteStmt.setInt(1, id);

                boolean success = deleteStmt.executeUpdate() > 0;

                if (success && invoiceId > 0) {
                    updateInvoiceTotal(conn, invoiceId);
                }

                return success;
            }

        } catch (SQLException e) {
            System.out.println("Error deleting invoice item: "
                    + e.getMessage());
        }

        return false;
    }

    // =========================================================
    // UPDATE INVOICE TOTAL
    // =========================================================
    private void updateInvoiceTotal(
            Connection conn,
            int invoiceId) throws SQLException {

        String sql = """
                UPDATE Invoice
                SET TotalAmount =
                    ISNULL(
                        (
                            SELECT SUM(Amount)
                            FROM InvoiceItem
                            WHERE InvoiceId = ?
                        ),
                        0
                    )
                WHERE InvoiceId = ?
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);
            stmt.setInt(2, invoiceId);

            stmt.executeUpdate();
        }
    }

    // =========================================================
    // MAP RESULT TO MODEL
    // =========================================================
    private InvoiceItem mapInvoiceItem(ResultSet rs)
            throws SQLException {

        InvoiceItem item = new InvoiceItem();

        item.setId(rs.getInt("InvoiceItemId"));

        Invoice invoice = new Invoice();
        invoice.setId(rs.getInt("InvoiceId"));
        item.setInvoice(invoice);

        item.setDescription(rs.getString("Description"));
        item.setQuantity(rs.getInt("Quantity"));
        item.setUnitPrice(rs.getDouble("UnitPrice"));
        item.setAmount(rs.getDouble("Amount"));

        return item;
    }
}