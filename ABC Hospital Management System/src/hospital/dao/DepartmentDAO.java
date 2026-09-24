package hospital.dao;

import hospital.models.Department;
import hospital.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    // ==============================
    // ADD DEPARTMENT
    // ==============================
    public boolean addDepartment(Department department) {

        String sql = """
                INSERT INTO Department (Name, Description, Location)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, department.getName());
            stmt.setString(2, department.getDescription());
            stmt.setString(3, department.getLocation());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding department: " + e.getMessage());
            return false;
        }
    }


    // ==============================
    // VIEW ALL DEPARTMENTS
    // ==============================
    public List<Department> findAllDepartments() {

        List<Department> departments = new ArrayList<>();

        String sql = """
                SELECT DepartmentId, Name, Description, Location
                FROM Department
                ORDER BY DepartmentId
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Department department = new Department();

                department.setId(rs.getInt("DepartmentId"));
                department.setName(rs.getString("Name"));
                department.setDescription(rs.getString("Description"));
                department.setLocation(rs.getString("Location"));

                departments.add(department);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving departments: " + e.getMessage());
        }

        return departments;
    }


    // ==============================
    // FIND DEPARTMENT BY ID
    // ==============================
    public Department findDepartmentById(int id) {

        String sql = """
                SELECT DepartmentId, Name, Description, Location
                FROM Department
                WHERE DepartmentId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Department department = new Department();

                    department.setId(rs.getInt("DepartmentId"));
                    department.setName(rs.getString("Name"));
                    department.setDescription(rs.getString("Description"));
                    department.setLocation(rs.getString("Location"));

                    return department;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding department: " + e.getMessage());
        }

        return null;
    }


    // ==============================
    // FIND DEPARTMENT BY NAME
    // ==============================
    public Department findDepartmentByName(String name) {

        String sql = """
                SELECT DepartmentId, Name, Description, Location
                FROM Department
                WHERE Name = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Department department = new Department();

                    department.setId(rs.getInt("DepartmentId"));
                    department.setName(rs.getString("Name"));
                    department.setDescription(rs.getString("Description"));
                    department.setLocation(rs.getString("Location"));

                    return department;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding department by name: " + e.getMessage());
        }

        return null;
    }


    // ==============================
    // UPDATE DEPARTMENT
    // ==============================
    public boolean updateDepartment(Department department) {

        String sql = """
                UPDATE Department
                SET Name = ?,
                    Description = ?,
                    Location = ?
                WHERE DepartmentId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, department.getName());
            stmt.setString(2, department.getDescription());
            stmt.setString(3, department.getLocation());
            stmt.setInt(4, department.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating department: " + e.getMessage());
            return false;
        }
    }


    // ==============================
    // DELETE DEPARTMENT
    // ==============================
    public boolean deleteDepartment(int id) {

        String sql = """
                DELETE FROM Department
                WHERE DepartmentId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error deleting department: " + e.getMessage()
            );
            return false;
        }
    }
}