package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User authenticate(String username, String password) {

        String sql = """
            SELECT UserId, Username, PasswordHash, Role, StaffId, IsActive
            FROM Users
            WHERE Username = ?
              AND PasswordHash = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    User user = new User();

                    user.setId(rs.getInt("UserId"));
                    user.setUsername(rs.getString("Username"));
                    user.setPasswordHash(rs.getString("PasswordHash"));
                    user.setRole(rs.getString("Role"));
                    user.setStaff(rs.getString("StaffId"));
                    user.setActive(rs.getBoolean("IsActive"));

                    return user;
                }
            }

        } catch (Exception e) {
            System.out.println("Authentication database error: " + e.getMessage());
        }

        return null;
    }
}