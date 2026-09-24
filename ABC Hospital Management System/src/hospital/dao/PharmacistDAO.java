package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Department;
import hospital.models.Pharmacist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PharmacistDAO {

    // ============================================================
    // ADD PHARMACIST
    // ============================================================
    // A pharmacist is created in three related tables:
    // 1. Person
    // 2. Staff
    // 3. Pharmacist
    //
    // All three inserts are handled inside one transaction.
    // If one step fails, everything is rolled back.
    // ============================================================
    public boolean addPharmacist(Pharmacist pharmacist) {

        String personSql =
                "INSERT INTO Person " +
                "(FirstName, LastName, Gender, DateOfBirth, Phone, Email, Street, City, Country) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String staffSql =
                "INSERT INTO Staff " +
                "(EmploymentDate, Salary, DepartmentId, PersonId) " +
                "VALUES (?, ?, ?, ?)";

        String pharmacistSql =
                "INSERT INTO Pharmacist " +
                "(StaffId, Qualification, LicenseNumber) " +
                "VALUES (?, ?, ?)";

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            // ----------------------------------------------------
            // STEP 1: Insert Person
            // ----------------------------------------------------
            int personId;

            try (PreparedStatement personStatement =
                         connection.prepareStatement(
                                 personSql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                personStatement.setString(1, pharmacist.getFirstName());
                personStatement.setString(2, pharmacist.getLastName());

                // Staff model uses char for gender
                personStatement.setString(3, String.valueOf(pharmacist.getGender()));

                personStatement.setDate(
                        4,
                        pharmacist.getDateOfBirth() == null
                                ? null
                                : Date.valueOf(pharmacist.getDateOfBirth())
                );

                personStatement.setString(5, pharmacist.getPhone());
                personStatement.setString(6, pharmacist.getEmail());
                personStatement.setString(7, pharmacist.getStreet());
                personStatement.setString(8, pharmacist.getCity());
                personStatement.setString(9, pharmacist.getCountry());

                personStatement.executeUpdate();

                try (ResultSet keys = personStatement.getGeneratedKeys()) {

                    if (!keys.next()) {
                        throw new SQLException("Failed to retrieve generated PersonId.");
                    }

                    personId = keys.getInt(1);
                }
            }

            // ----------------------------------------------------
            // STEP 2: Insert Staff
            // ----------------------------------------------------
            int staffId;

            try (PreparedStatement staffStatement =
                         connection.prepareStatement(
                                 staffSql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                staffStatement.setDate(
                        1,
                        pharmacist.getEmploymentDate() == null
                                ? null
                                : Date.valueOf(pharmacist.getEmploymentDate())
                );

                staffStatement.setDouble(2, pharmacist.getSalary());

                if (pharmacist.getDepartment() != null) {
                    staffStatement.setInt(
                            3,
                            pharmacist.getDepartment().getId()
                    );
                } else {
                    staffStatement.setNull(3, Types.INTEGER);
                }

                staffStatement.setInt(4, personId);

                staffStatement.executeUpdate();

                try (ResultSet keys = staffStatement.getGeneratedKeys()) {

                    if (!keys.next()) {
                        throw new SQLException("Failed to retrieve generated StaffId.");
                    }

                    staffId = keys.getInt(1);
                }
            }

            // ----------------------------------------------------
            // STEP 3: Insert Pharmacist
            // ----------------------------------------------------
            try (PreparedStatement pharmacistStatement =
                         connection.prepareStatement(pharmacistSql)) {

                pharmacistStatement.setInt(1, staffId);
                pharmacistStatement.setString(2, pharmacist.getQualification());
                pharmacistStatement.setString(3, pharmacist.getLicenseNumber());

                pharmacistStatement.executeUpdate();
            }

            // Everything succeeded
            connection.commit();

            // Store generated StaffId in the model
            pharmacist.setStaffId(staffId);

            return true;

        } catch (SQLException e) {

            // Roll back everything if any step fails
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            System.out.println("Error adding pharmacist: " + e.getMessage());
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
    // FIND ALL PHARMACISTS
    // ============================================================
    public List<Pharmacist> findAllPharmacists() {

        List<Pharmacist> pharmacists = new ArrayList<>();

        String sql =
                "SELECT " +
                "p.PersonId, " +
                "p.FirstName, " +
                "p.LastName, " +
                "p.Gender, " +
                "p.DateOfBirth, " +
                "p.Phone, " +
                "p.Email, " +
                "p.Street, " +
                "p.City, " +
                "p.Country, " +
                "s.StaffId, " +
                "s.EmploymentDate, " +
                "s.Salary, " +
                "s.DepartmentId, " +
                "d.Name AS DepartmentName, " +
                "ph.PharmacistId, " +
                "ph.Qualification, " +
                "ph.LicenseNumber, " +
                "ph.Status " +
                "FROM Pharmacist ph " +
                "INNER JOIN Staff s ON ph.StaffId = s.StaffId " +
                "INNER JOIN Person p ON s.PersonId = p.PersonId " +
                "LEFT JOIN Department d ON s.DepartmentId = d.DepartmentId " +
                "ORDER BY ph.PharmacistId";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                pharmacists.add(mapPharmacist(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error finding pharmacists: " + e.getMessage());
        }

        return pharmacists;
    }


    // ============================================================
    // FIND PHARMACIST BY STAFF ID
    // ============================================================
    public Pharmacist findPharmacistById(int staffId) {

        String sql =
                "SELECT " +
                "p.PersonId, " +
                "p.FirstName, " +
                "p.LastName, " +
                "p.Gender, " +
                "p.DateOfBirth, " +
                "p.Phone, " +
                "p.Email, " +
                "p.Street, " +
                "p.City, " +
                "p.Country, " +
                "s.StaffId, " +
                "s.EmploymentDate, " +
                "s.Salary, " +
                "s.DepartmentId, " +
                "d.Name AS DepartmentName, " +
                "ph.PharmacistId, " +
                "ph.Qualification, " +
                "ph.LicenseNumber, " +
                "ph.Status " +
                "FROM Pharmacist ph " +
                "INNER JOIN Staff s ON ph.StaffId = s.StaffId " +
                "INNER JOIN Person p ON s.PersonId = p.PersonId " +
                "LEFT JOIN Department d ON s.DepartmentId = d.DepartmentId " +
                "WHERE s.StaffId = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, staffId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapPharmacist(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding pharmacist: " + e.getMessage());
        }

        return null;
    }


    // ============================================================
    // UPDATE PHARMACIST
    // ============================================================
    // Updates:
    // 1. Person information
    // 2. Staff information
    // 3. Pharmacist information
    // ============================================================
    public boolean update(Pharmacist pharmacist) {

        String personSql =
                "UPDATE Person SET " +
                "FirstName = ?, " +
                "LastName = ?, " +
                "Gender = ?, " +
                "DateOfBirth = ?, " +
                "Phone = ?, " +
                "Email = ?, " +
                "Street = ?, " +
                "City = ?, " +
                "Country = ? " +
                "WHERE PersonId = ?";

        String staffSql =
                "UPDATE Staff SET " +
                "EmploymentDate = ?, " +
                "Salary = ?, " +
                "DepartmentId = ? " +
                "WHERE StaffId = ?";

        String pharmacistSql =
                "UPDATE Pharmacist SET " +
                "Qualification = ?, " +
                "LicenseNumber = ?, " +
                "Status = ? " +
                "WHERE StaffId = ?";

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            int personId = getPersonId(connection, pharmacist.getStaffId());

            if (personId == -1) {
                System.out.println("Pharmacist not found.");
                connection.rollback();
                return false;
            }

            // ----------------------------------------------------
            // STEP 1: Update Person
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(personSql)) {

                statement.setString(1, pharmacist.getFirstName());
                statement.setString(2, pharmacist.getLastName());
                statement.setString(3, String.valueOf(pharmacist.getGender()));

                statement.setDate(
                        4,
                        pharmacist.getDateOfBirth() == null
                                ? null
                                : Date.valueOf(pharmacist.getDateOfBirth())
                );

                statement.setString(5, pharmacist.getPhone());
                statement.setString(6, pharmacist.getEmail());
                statement.setString(7, pharmacist.getStreet());
                statement.setString(8, pharmacist.getCity());
                statement.setString(9, pharmacist.getCountry());
                statement.setInt(10, personId);

                statement.executeUpdate();
            }

            // ----------------------------------------------------
            // STEP 2: Update Staff
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(staffSql)) {

                statement.setDate(
                        1,
                        pharmacist.getEmploymentDate() == null
                                ? null
                                : Date.valueOf(pharmacist.getEmploymentDate())
                );

                statement.setDouble(2, pharmacist.getSalary());

                if (pharmacist.getDepartment() != null) {
                    statement.setInt(
                            3,
                            pharmacist.getDepartment().getId()
                    );
                } else {
                    statement.setNull(3, Types.INTEGER);
                }

                statement.setInt(4, pharmacist.getStaffId());

                statement.executeUpdate();
            }

            // ----------------------------------------------------
            // STEP 3: Update Pharmacist
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(pharmacistSql)) {

                statement.setString(1, pharmacist.getQualification());
                statement.setString(2, pharmacist.getLicenseNumber());

                // Keep existing status if the model does not contain it.
                statement.setString(3, "Active");

                statement.setInt(4, pharmacist.getStaffId());

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

            System.out.println("Error updating pharmacist: " + e.getMessage());
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
    // DELETE PHARMACIST
    // ============================================================
    // Delete order:
    // 1. Pharmacist
    // 2. Staff
    // 3. Person
    //
    // This prevents foreign-key constraint errors.
    // ============================================================
    public boolean delete(int staffId) {

        String pharmacistSql =
                "DELETE FROM Pharmacist WHERE StaffId = ?";

        String staffSql =
                "DELETE FROM Staff WHERE StaffId = ?";

        String personSql =
                "DELETE FROM Person WHERE PersonId = ?";

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            int personId = getPersonId(connection, staffId);

            if (personId == -1) {
                System.out.println("Pharmacist not found.");
                connection.rollback();
                return false;
            }

            // ----------------------------------------------------
            // STEP 1: Delete Pharmacist
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(pharmacistSql)) {

                statement.setInt(1, staffId);
                statement.executeUpdate();
            }

            // ----------------------------------------------------
            // STEP 2: Delete Staff
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(staffSql)) {

                statement.setInt(1, staffId);
                statement.executeUpdate();
            }

            // ----------------------------------------------------
            // STEP 3: Delete Person
            // ----------------------------------------------------
            try (PreparedStatement statement =
                         connection.prepareStatement(personSql)) {

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

            System.out.println("Error deleting pharmacist: " + e.getMessage());
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
    // The Staff table connects StaffId to PersonId.
    // We need this when updating or deleting the Person record.
    // ============================================================
    private int getPersonId(Connection connection, int staffId)
            throws SQLException {

        String sql =
                "SELECT PersonId " +
                "FROM Staff " +
                "WHERE StaffId = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, staffId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("PersonId");
                }
            }
        }

        return -1;
    }


    // ============================================================
    // MAP RESULTSET TO PHARMACIST OBJECT
    // ============================================================
    private Pharmacist mapPharmacist(ResultSet rs)
            throws SQLException {

        Pharmacist pharmacist = new Pharmacist();

        // --------------------------------------------------------
        // Person information
        // --------------------------------------------------------
        pharmacist.setFirstName(rs.getString("FirstName"));
        pharmacist.setLastName(rs.getString("LastName"));

        String gender = rs.getString("Gender");

        if (gender != null && !gender.isEmpty()) {
            pharmacist.setGender(gender.charAt(0));
        }

        Date dateOfBirth = rs.getDate("DateOfBirth");

        if (dateOfBirth != null) {
            pharmacist.setDateOfBirth(
                    dateOfBirth.toLocalDate()
            );
        }

        pharmacist.setPhone(rs.getString("Phone"));
        pharmacist.setEmail(rs.getString("Email"));
        pharmacist.setStreet(rs.getString("Street"));
        pharmacist.setCity(rs.getString("City"));
        pharmacist.setCountry(rs.getString("Country"));

        // --------------------------------------------------------
        // Staff information
        // --------------------------------------------------------
        pharmacist.setStaffId(
                rs.getInt("StaffId")
        );

        Date employmentDate = rs.getDate("EmploymentDate");

        if (employmentDate != null) {
            pharmacist.setEmploymentDate(
                    employmentDate.toLocalDate()
            );
        }

        pharmacist.setSalary(
                rs.getDouble("Salary")
        );

        // --------------------------------------------------------
        // Department
        // --------------------------------------------------------
        int departmentId = rs.getInt("DepartmentId");

        if (!rs.wasNull()) {

            Department department = new Department();

            department.setId(departmentId);

            String departmentName =
                    rs.getString("DepartmentName");

            if (departmentName != null) {
                department.setName(departmentName);
            }

            pharmacist.setDepartment(department);
        }

        // --------------------------------------------------------
        // Pharmacist-specific information
        // --------------------------------------------------------
        pharmacist.setQualification(
                rs.getString("Qualification")
        );

        pharmacist.setLicenseNumber(
                rs.getString("LicenseNumber")
        );

        return pharmacist;
    }
}