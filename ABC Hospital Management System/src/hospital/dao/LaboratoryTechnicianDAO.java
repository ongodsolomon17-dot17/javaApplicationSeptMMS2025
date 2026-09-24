package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Department;
import hospital.models.LaboratoryTechnician;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaboratoryTechnicianDAO {

    // ============================================================
    // ADD LABORATORY TECHNICIAN
    // ============================================================
    // Inserts the technician into:
    // 1. Person
    // 2. Staff
    // 3. LaboratoryTechnician
    //
    // All operations use one transaction.
    // ============================================================
    public boolean addLaboratoryTechnician(
            LaboratoryTechnician technician) {

        String personSql =
                "INSERT INTO Person " +
                "(FirstName, LastName, Gender, DateOfBirth, Phone, Email, Street, City, Country) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String staffSql =
                "INSERT INTO Staff " +
                "(EmploymentDate, Salary, DepartmentId, PersonId) " +
                "VALUES (?, ?, ?, ?)";

        String technicianSql =
                "INSERT INTO LaboratoryTechnician " +
                "(StaffId, Qualification, LicenseNumber) " +
                "VALUES (?, ?, ?)";

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            int personId;

            // ----------------------------------------------------
            // Insert Person
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 personSql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, technician.getFirstName());
                statement.setString(2, technician.getLastName());
                statement.setString(3,
                        String.valueOf(technician.getGender()));

                statement.setDate(
                        4,
                        technician.getDateOfBirth() == null
                                ? null
                                : Date.valueOf(
                                        technician.getDateOfBirth()));

                statement.setString(5, technician.getPhone());
                statement.setString(6, technician.getEmail());
                statement.setString(7, technician.getStreet());
                statement.setString(8, technician.getCity());
                statement.setString(9, technician.getCountry());

                statement.executeUpdate();

                try (ResultSet keys =
                             statement.getGeneratedKeys()) {

                    if (!keys.next()) {
                        throw new SQLException(
                                "Failed to retrieve generated PersonId.");
                    }

                    personId = keys.getInt(1);
                }
            }

            int staffId;

            // ----------------------------------------------------
            // Insert Staff
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 staffSql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setDate(
                        1,
                        technician.getEmploymentDate() == null
                                ? null
                                : Date.valueOf(
                                        technician.getEmploymentDate()));

                statement.setDouble(2, technician.getSalary());

                if (technician.getDepartment() != null) {
                    statement.setInt(
                            3,
                            technician.getDepartment().getId());
                } else {
                    statement.setNull(3, Types.INTEGER);
                }

                statement.setInt(4, personId);

                statement.executeUpdate();

                try (ResultSet keys =
                             statement.getGeneratedKeys()) {

                    if (!keys.next()) {
                        throw new SQLException(
                                "Failed to retrieve generated StaffId.");
                    }

                    staffId = keys.getInt(1);
                }
            }

            // ----------------------------------------------------
            // Insert Laboratory Technician
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 technicianSql)) {

                statement.setInt(1, staffId);
                statement.setString(
                        2, technician.getQualification());
                statement.setString(
                        3, technician.getLicenseNumber());

                statement.executeUpdate();
            }

            connection.commit();

            technician.setStaffId(staffId);

            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            System.out.println(
                    "Error adding laboratory technician: "
                    + e.getMessage());

            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // ============================================================
    // FIND ALL LABORATORY TECHNICIANS
    // ============================================================
    public List<LaboratoryTechnician>
            findAllLaboratoryTechnicians() {

        List<LaboratoryTechnician> technicians =
                new ArrayList<>();

        String sql =
                "SELECT " +
                "p.PersonId, p.FirstName, p.LastName, p.Gender, " +
                "p.DateOfBirth, p.Phone, p.Email, p.Street, " +
                "p.City, p.Country, " +
                "s.StaffId, s.EmploymentDate, s.Salary, " +
                "s.DepartmentId, d.Name AS DepartmentName, " +
                "lt.LaboratoryTechnicianId, " +
                "lt.Qualification, lt.LicenseNumber, lt.Status " +
                "FROM LaboratoryTechnician lt " +
                "INNER JOIN Staff s ON lt.StaffId = s.StaffId " +
                "INNER JOIN Person p ON s.PersonId = p.PersonId " +
                "LEFT JOIN Department d " +
                "ON s.DepartmentId = d.DepartmentId " +
                "ORDER BY lt.LaboratoryTechnicianId";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs =
                     statement.executeQuery()) {

            while (rs.next()) {
                technicians.add(mapLaboratoryTechnician(rs));
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error finding laboratory technicians: "
                    + e.getMessage());
        }

        return technicians;
    }


    // ============================================================
    // FIND LABORATORY TECHNICIAN BY STAFF ID
    // ============================================================
    public LaboratoryTechnician
            findLaboratoryTechnicianById(int staffId) {

        String sql =
                "SELECT " +
                "p.PersonId, p.FirstName, p.LastName, p.Gender, " +
                "p.DateOfBirth, p.Phone, p.Email, p.Street, " +
                "p.City, p.Country, " +
                "s.StaffId, s.EmploymentDate, s.Salary, " +
                "s.DepartmentId, d.Name AS DepartmentName, " +
                "lt.LaboratoryTechnicianId, " +
                "lt.Qualification, lt.LicenseNumber, lt.Status " +
                "FROM LaboratoryTechnician lt " +
                "INNER JOIN Staff s ON lt.StaffId = s.StaffId " +
                "INNER JOIN Person p ON s.PersonId = p.PersonId " +
                "LEFT JOIN Department d " +
                "ON s.DepartmentId = d.DepartmentId " +
                "WHERE s.StaffId = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, staffId);

            try (ResultSet rs =
                         statement.executeQuery()) {

                if (rs.next()) {
                    return mapLaboratoryTechnician(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error finding laboratory technician: "
                    + e.getMessage());
        }

        return null;
    }


    // ============================================================
    // UPDATE LABORATORY TECHNICIAN
    // ============================================================
    public boolean update(
            LaboratoryTechnician technician) {

        String personSql =
                "UPDATE Person SET " +
                "FirstName = ?, LastName = ?, Gender = ?, " +
                "DateOfBirth = ?, Phone = ?, Email = ?, " +
                "Street = ?, City = ?, Country = ? " +
                "WHERE PersonId = ?";

        String staffSql =
                "UPDATE Staff SET " +
                "EmploymentDate = ?, Salary = ?, DepartmentId = ? " +
                "WHERE StaffId = ?";

        String technicianSql =
                "UPDATE LaboratoryTechnician SET " +
                "Qualification = ?, LicenseNumber = ? " +
                "WHERE StaffId = ?";

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            int personId =
                    getPersonId(
                            connection,
                            technician.getStaffId());

            if (personId == -1) {
                System.out.println(
                        "Laboratory technician not found.");
                connection.rollback();
                return false;
            }

            // ----------------------------------------------------
            // Update Person
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(personSql)) {

                statement.setString(
                        1, technician.getFirstName());
                statement.setString(
                        2, technician.getLastName());
                statement.setString(
                        3, String.valueOf(
                                technician.getGender()));

                statement.setDate(
                        4,
                        technician.getDateOfBirth() == null
                                ? null
                                : Date.valueOf(
                                        technician.getDateOfBirth()));

                statement.setString(
                        5, technician.getPhone());
                statement.setString(
                        6, technician.getEmail());
                statement.setString(
                        7, technician.getStreet());
                statement.setString(
                        8, technician.getCity());
                statement.setString(
                        9, technician.getCountry());
                statement.setInt(10, personId);

                statement.executeUpdate();
            }

            // ----------------------------------------------------
            // Update Staff
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(staffSql)) {

                statement.setDate(
                        1,
                        technician.getEmploymentDate() == null
                                ? null
                                : Date.valueOf(
                                        technician.getEmploymentDate()));

                statement.setDouble(
                        2, technician.getSalary());

                if (technician.getDepartment() != null) {
                    statement.setInt(
                            3,
                            technician.getDepartment().getId());
                } else {
                    statement.setNull(
                            3, Types.INTEGER);
                }

                statement.setInt(
                        4, technician.getStaffId());

                statement.executeUpdate();
            }

            // ----------------------------------------------------
            // Update Laboratory Technician
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 technicianSql)) {

                statement.setString(
                        1, technician.getQualification());
                statement.setString(
                        2, technician.getLicenseNumber());
                statement.setInt(
                        3, technician.getStaffId());

                statement.executeUpdate();
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            System.out.println(
                    "Error updating laboratory technician: "
                    + e.getMessage());

            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // ============================================================
    // DELETE LABORATORY TECHNICIAN
    // ============================================================
    public boolean delete(int staffId) {

        String technicianSql =
                "DELETE FROM LaboratoryTechnician " +
                "WHERE StaffId = ?";

        String staffSql =
                "DELETE FROM Staff WHERE StaffId = ?";

        String personSql =
                "DELETE FROM Person WHERE PersonId = ?";

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            int personId =
                    getPersonId(connection, staffId);

            if (personId == -1) {
                System.out.println(
                        "Laboratory technician not found.");
                connection.rollback();
                return false;
            }

            // ----------------------------------------------------
            // Delete Laboratory Technician
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 technicianSql)) {

                statement.setInt(1, staffId);
                statement.executeUpdate();
            }

            // ----------------------------------------------------
            // Delete Staff
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 staffSql)) {

                statement.setInt(1, staffId);
                statement.executeUpdate();
            }

            // ----------------------------------------------------
            // Delete Person
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 personSql)) {

                statement.setInt(1, personId);
                statement.executeUpdate();
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            System.out.println(
                    "Error deleting laboratory technician: "
                    + e.getMessage());

            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // ============================================================
    // GET PERSON ID
    // ============================================================
    private int getPersonId(
            Connection connection,
            int staffId) throws SQLException {

        String sql =
                "SELECT PersonId FROM Staff " +
                "WHERE StaffId = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, staffId);

            try (ResultSet rs =
                         statement.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("PersonId");
                }
            }
        }

        return -1;
    }


    // ============================================================
    // MAP RESULT SET TO LABORATORY TECHNICIAN
    // ============================================================
    private LaboratoryTechnician
            mapLaboratoryTechnician(ResultSet rs)
            throws SQLException {

        LaboratoryTechnician technician =
                new LaboratoryTechnician();

        // --------------------------------------------------------
        // Person information
        // --------------------------------------------------------
        technician.setFirstName(
                rs.getString("FirstName"));

        technician.setLastName(
                rs.getString("LastName"));

        String gender =
                rs.getString("Gender");

        if (gender != null && !gender.isEmpty()) {
            technician.setGender(
                    gender.charAt(0));
        }

        Date dateOfBirth =
                rs.getDate("DateOfBirth");

        if (dateOfBirth != null) {
            technician.setDateOfBirth(
                    dateOfBirth.toLocalDate());
        }

        technician.setPhone(
                rs.getString("Phone"));

        technician.setEmail(
                rs.getString("Email"));

        technician.setStreet(
                rs.getString("Street"));

        technician.setCity(
                rs.getString("City"));

        technician.setCountry(
                rs.getString("Country"));

        // --------------------------------------------------------
        // Staff information
        // --------------------------------------------------------
        technician.setStaffId(
                rs.getInt("StaffId"));

        Date employmentDate =
                rs.getDate("EmploymentDate");

        if (employmentDate != null) {
            technician.setEmploymentDate(
                    employmentDate.toLocalDate());
        }

        technician.setSalary(
                rs.getDouble("Salary"));

        // --------------------------------------------------------
        // Department
        // --------------------------------------------------------
        int departmentId =
                rs.getInt("DepartmentId");

        if (!rs.wasNull()) {

            Department department =
                    new Department();

            department.setId(departmentId);

            String departmentName =
                    rs.getString("DepartmentName");

            if (departmentName != null) {
                department.setName(departmentName);
            }

            technician.setDepartment(department);
        }

        // --------------------------------------------------------
        // Laboratory Technician information
        // --------------------------------------------------------
        technician.setQualification(
                rs.getString("Qualification"));

        technician.setLicenseNumber(
                rs.getString("LicenseNumber"));

        return technician;
    }
}