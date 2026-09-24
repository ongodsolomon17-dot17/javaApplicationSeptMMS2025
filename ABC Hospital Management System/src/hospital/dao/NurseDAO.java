package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Department;
import hospital.models.Nurse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NurseDAO {

    // =========================================================
    // CREATE NURSE
    // =========================================================
    public boolean addNurse(Nurse nurse) {

        String personSql = """
                INSERT INTO Person
                (
                    FirstName,
                    LastName,
                    Gender,
                    DateOfBirth,
                    Phone,
                    Email,
                    Street,
                    City,
                    Country
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        String staffSql = """
                INSERT INTO Staff
                (
                    EmploymentDate,
                    Salary,
                    DepartmentId,
                    PersonId
                )
                VALUES (?, ?, ?, ?)
                """;

        String nurseSql = """
                INSERT INTO Nurse
                (
                    StaffId,
                    NursingLicense,
                    Qualification
                )
                VALUES (?, ?, ?)
                """;

        Connection connection = null;

        try {

            connection
                    = DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            // =====================================================
            // 1. INSERT PERSON
            // =====================================================
            int personId;

            try (
                    PreparedStatement statement
                    = connection.prepareStatement(
                            personSql,
                            Statement.RETURN_GENERATED_KEYS
                    )) {

                        statement.setString(
                                1,
                                nurse.getFirstName()
                        );

                        statement.setString(
                                2,
                                nurse.getLastName()
                        );

                        statement.setString(
                                3,
                                String.valueOf(
                                        nurse.getGender()
                                )
                        );

                        statement.setDate(
                                4,
                                Date.valueOf(
                                        nurse.getDateOfBirth()
                                )
                        );

                        statement.setString(
                                5,
                                nurse.getPhone()
                        );

                        statement.setString(
                                6,
                                nurse.getEmail()
                        );

                        statement.setString(
                                7,
                                nurse.getStreet()
                        );

                        statement.setString(
                                8,
                                nurse.getCity()
                        );

                        statement.setString(
                                9,
                                nurse.getCountry()
                        );

                        statement.executeUpdate();

                        try (
                                ResultSet keys
                                = statement.getGeneratedKeys()) {

                            if (!keys.next()) {

                                throw new SQLException(
                                        "PersonId was not generated."
                                );
                            }

                            personId
                                    = keys.getInt(1);
                        }
                    }

                    // =====================================================
                    // 2. INSERT STAFF
                    // =====================================================
                    int staffId;

                    try (
                            PreparedStatement statement
                            = connection.prepareStatement(
                                    staffSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )) {

                                statement.setDate(
                                        1,
                                        Date.valueOf(
                                                nurse.getEmploymentDate()
                                        )
                                );

                                statement.setDouble(
                                        2,
                                        nurse.getSalary()
                                );

                                statement.setInt(
                                        3,
                                        nurse.getDepartment().getId()
                                );

                                statement.setInt(
                                        4,
                                        personId
                                );

                                statement.executeUpdate();

                                try (
                                        ResultSet keys
                                        = statement.getGeneratedKeys()) {

                                    if (!keys.next()) {

                                        throw new SQLException(
                                                "StaffId was not generated."
                                        );
                                    }

                                    staffId
                                            = keys.getInt(1);
                                }
                            }

                            // =====================================================
                            // 3. INSERT NURSE
                            // =====================================================
                            try (
                                    PreparedStatement statement
                                    = connection.prepareStatement(
                                            nurseSql
                                    )) {

                                        statement.setInt(
                                                1,
                                                staffId
                                        );

                                        statement.setString(
                                                2,
                                                nurse.getNursingLicense()
                                        );

                                        statement.setString(
                                                3,
                                                nurse.getQualification()
                                        );

                                        statement.executeUpdate();
                                    }

                                    connection.commit();

                                    System.out.println(
                                            "Nurse inserted successfully."
                                    );

                                    System.out.println(
                                            "Person ID: " + personId
                                    );

                                    System.out.println(
                                            "Staff ID: " + staffId
                                    );

                                    return true;

        } catch (SQLException e) {

            if (connection != null) {

                try {

                    connection.rollback();

                } catch (SQLException rollbackException) {

                    rollbackException.printStackTrace();
                }
            }

            System.err.println(
                    "Error inserting nurse: "
                    + e.getMessage()
            );

            e.printStackTrace();

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

    // =========================================================
    // READ ALL NURSES
    // =========================================================
    public List<Nurse> findAllNurses() {

        List<Nurse> nurses
                = new ArrayList<>();

        String sql = """
                SELECT
                    n.StaffId,
                    n.NursingLicense,
                    n.Qualification,

                    s.EmploymentDate,
                    s.Salary,
                    s.DepartmentId,
                    s.PersonId,

                    p.FirstName,
                    p.LastName,
                    p.Gender,
                    p.DateOfBirth,
                    p.Phone,
                    p.Email,
                    p.Street,
                    p.City,
                    p.Country

                FROM Nurse n

                INNER JOIN Staff s
                    ON n.StaffId = s.StaffId

                INNER JOIN Person p
                    ON s.PersonId = p.PersonId

                ORDER BY n.StaffId
                """;

        try (
                Connection connection
                = DatabaseConnection.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql); ResultSet resultSet
                = statement.executeQuery()) {

            while (resultSet.next()) {

                Nurse nurse
                        = mapNurse(resultSet);

                nurses.add(nurse);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error retrieving nurses: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }

        return nurses;
    }

    // =========================================================
    // FIND NURSE BY STAFF ID
    // =========================================================
    public Nurse findNurseById(int staffId) {

        String sql = """
                SELECT
                    n.StaffId,
                    n.NursingLicense,
                    n.Qualification,

                    s.EmploymentDate,
                    s.Salary,
                    s.DepartmentId,
                    s.PersonId,

                    p.FirstName,
                    p.LastName,
                    p.Gender,
                    p.DateOfBirth,
                    p.Phone,
                    p.Email,
                    p.Street,
                    p.City,
                    p.Country

                FROM Nurse n

                INNER JOIN Staff s
                    ON n.StaffId = s.StaffId

                INNER JOIN Person p
                    ON s.PersonId = p.PersonId

                WHERE n.StaffId = ?
                """;

        try (
                Connection connection
                = DatabaseConnection.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    staffId
            );

            try (
                    ResultSet resultSet
                    = statement.executeQuery()) {

                if (resultSet.next()) {

                    return mapNurse(
                            resultSet
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error finding nurse: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }

        return null;
    }

    // =========================================================
    // UPDATE NURSE
    // =========================================================
    public boolean update(Nurse nurse) {

        String personSql = """
                UPDATE Person

                SET
                    FirstName = ?,
                    LastName = ?,
                    Gender = ?,
                    DateOfBirth = ?,
                    Phone = ?,
                    Email = ?,
                    Street = ?,
                    City = ?,
                    Country = ?

                WHERE PersonId = ?
                """;

        String staffSql = """
                UPDATE Staff

                SET
                    EmploymentDate = ?,
                    Salary = ?,
                    DepartmentId = ?

                WHERE StaffId = ?
                """;

        String nurseSql = """
                UPDATE Nurse

                SET
                    NursingLicense = ?,
                    Qualification = ?

                WHERE StaffId = ?
                """;

        Connection connection = null;

        try {

            connection
                    = DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            int staffId
                    = nurse.getStaffId();

            // =====================================================
            // GET PERSON ID
            // =====================================================
            int personId
                    = getPersonId(
                            connection,
                            staffId
                    );

            if (personId == -1) {

                connection.rollback();

                return false;
            }

            // =====================================================
            // UPDATE PERSON
            // =====================================================
            try (
                    PreparedStatement statement
                    = connection.prepareStatement(
                            personSql
                    )) {

                        statement.setString(
                                1,
                                nurse.getFirstName()
                        );

                        statement.setString(
                                2,
                                nurse.getLastName()
                        );

                        statement.setString(
                                3,
                                String.valueOf(
                                        nurse.getGender()
                                )
                        );

                        statement.setDate(
                                4,
                                Date.valueOf(
                                        nurse.getDateOfBirth()
                                )
                        );

                        statement.setString(
                                5,
                                nurse.getPhone()
                        );

                        statement.setString(
                                6,
                                nurse.getEmail()
                        );

                        statement.setString(
                                7,
                                nurse.getStreet()
                        );

                        statement.setString(
                                8,
                                nurse.getCity()
                        );

                        statement.setString(
                                9,
                                nurse.getCountry()
                        );

                        statement.setInt(
                                10,
                                personId
                        );

                        statement.executeUpdate();
                    }

                    // =====================================================
                    // UPDATE STAFF
                    // =====================================================
                    try (
                            PreparedStatement statement
                            = connection.prepareStatement(
                                    staffSql
                            )) {

                                statement.setDate(
                                        1,
                                        Date.valueOf(
                                                nurse.getEmploymentDate()
                                        )
                                );

                                statement.setDouble(
                                        2,
                                        nurse.getSalary()
                                );

                                statement.setInt(
                                        3,
                                        nurse.getDepartment().getId()
                                );

                                statement.setInt(
                                        4,
                                        staffId
                                );

                                statement.executeUpdate();
                            }

                            // =====================================================
                            // UPDATE NURSE
                            // =====================================================
                            try (
                                    PreparedStatement statement
                                    = connection.prepareStatement(
                                            nurseSql
                                    )) {

                                        statement.setString(
                                                1,
                                                nurse.getNursingLicense()
                                        );

                                        statement.setString(
                                                2,
                                                nurse.getQualification()
                                        );

                                        statement.setInt(
                                                3,
                                                staffId
                                        );

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

            System.err.println(
                    "Error updating nurse: "
                    + e.getMessage()
            );

            e.printStackTrace();

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

    // =========================================================
    // DELETE NURSE
    // =========================================================
    public boolean delete(int staffId) {

        Connection connection = null;

        try {

            connection
                    = DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            int personId
                    = getPersonId(
                            connection,
                            staffId
                    );

            if (personId == -1) {

                connection.rollback();

                return false;
            }

            // =====================================================
            // DELETE NURSE
            // =====================================================
            String nurseSql = """
                    DELETE FROM Nurse
                    WHERE StaffId = ?
                    """;

            try (
                    PreparedStatement statement
                    = connection.prepareStatement(
                            nurseSql
                    )) {

                        statement.setInt(
                                1,
                                staffId
                        );

                        statement.executeUpdate();
                    }

                    // =====================================================
                    // DELETE STAFF
                    // =====================================================
                    String staffSql = """
                    DELETE FROM Staff
                    WHERE StaffId = ?
                    """;

                    try (
                            PreparedStatement statement
                            = connection.prepareStatement(
                                    staffSql
                            )) {

                                statement.setInt(
                                        1,
                                        staffId
                                );

                                statement.executeUpdate();
                            }

                            // =====================================================
                            // DELETE PERSON
                            // =====================================================
                            String personSql = """
                    DELETE FROM Person
                    WHERE PersonId = ?
                    """;

                            try (
                                    PreparedStatement statement
                                    = connection.prepareStatement(
                                            personSql
                                    )) {

                                        statement.setInt(
                                                1,
                                                personId
                                        );

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

            System.err.println(
                    "Error deleting nurse: "
                    + e.getMessage()
            );

            e.printStackTrace();

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

    // =========================================================
    // GET PERSON ID
    // =========================================================
    private int getPersonId(
            Connection connection,
            int staffId
    ) throws SQLException {

        String sql = """
                SELECT PersonId
                FROM Staff
                WHERE StaffId = ?
                """;

        try (
                PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    staffId
            );

            try (
                    ResultSet resultSet
                    = statement.executeQuery()) {

                if (resultSet.next()) {

                    return resultSet.getInt(
                            "PersonId"
                    );
                }
            }
        }

        return -1;
    }

    // =========================================================
    // MAP RESULT SET TO NURSE
    // =========================================================
    private Nurse mapNurse(
            ResultSet resultSet
    ) throws SQLException {

        Nurse nurse
                = new Nurse();

        // STAFF ID
        nurse.setStaffId(
                resultSet.getInt(
                        "StaffId"
                )
        );

        // NURSE INFORMATION
        nurse.setNursingLicense(
                resultSet.getString(
                        "NursingLicense"
                )
        );

        nurse.setQualification(
                resultSet.getString(
                        "Qualification"
                )
        );

        // STAFF INFORMATION
        Date employmentDate
                = resultSet.getDate(
                        "EmploymentDate"
                );

        if (employmentDate != null) {

            nurse.setEmploymentDate(
                    employmentDate.toLocalDate()
            );
        }

        nurse.setSalary(
                resultSet.getDouble(
                        "Salary"
                )
        );

        // DEPARTMENT
        Department department
                = new Department();

        department.setId(
                resultSet.getInt(
                        "DepartmentId"
                )
        );

        nurse.setDepartment(
                department
        );

        // PERSON INFORMATION
        nurse.setFirstName(
                resultSet.getString(
                        "FirstName"
                )
        );

        nurse.setLastName(
                resultSet.getString(
                        "LastName"
                )
        );

        String gender
                = resultSet.getString(
                        "Gender"
                );

        if (gender != null
                && !gender.isEmpty()) {

            nurse.setGender(
                    gender.charAt(0)
            );
        }

        Date dateOfBirth
                = resultSet.getDate(
                        "DateOfBirth"
                );

        if (dateOfBirth != null) {

            nurse.setDateOfBirth(
                    dateOfBirth.toLocalDate()
            );
        }

        nurse.setPhone(
                resultSet.getString(
                        "Phone"
                )
        );

        nurse.setEmail(
                resultSet.getString(
                        "Email"
                )
        );

        nurse.setStreet(
                resultSet.getString(
                        "Street"
                )
        );

        nurse.setCity(
                resultSet.getString(
                        "City"
                )
        );

        nurse.setCountry(
                resultSet.getString(
                        "Country"
                )
        );

        return nurse;
    }
}
