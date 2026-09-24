package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Department;
import hospital.models.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // =========================================================
    // CREATE DOCTOR
    // =========================================================

    public boolean addDoctor(Doctor doctor) {

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

        String doctorSql = """
                INSERT INTO Doctor
                (
                    StaffId,
                    Specialization,
                    LicenseNumber
                )
                VALUES (?, ?, ?)
                """;

        Connection connection = null;

        try {

            connection = DatabaseConnection.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            // =================================================
            // STEP 1: INSERT PERSON
            // =================================================

            int personId;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    personSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                statement.setString(
                        1,
                        doctor.getFirstName()
                );

                statement.setString(
                        2,
                        doctor.getLastName()
                );

                statement.setString(
                        3,
                        String.valueOf(doctor.getGender())
                );

                statement.setDate(
                        4,
                        Date.valueOf(doctor.getDateOfBirth())
                );

                statement.setString(
                        5,
                        doctor.getPhone()
                );

                statement.setString(
                        6,
                        doctor.getEmail()
                );

                statement.setString(
                        7,
                        doctor.getStreet()
                );

                statement.setString(
                        8,
                        doctor.getCity()
                );

                statement.setString(
                        9,
                        doctor.getCountry()
                );

                statement.executeUpdate();

                try (
                        ResultSet keys =
                                statement.getGeneratedKeys()
                ) {

                    if (!keys.next()) {
                        throw new SQLException(
                                "PersonId was not generated."
                        );
                    }

                    personId = keys.getInt(1);
                }
            }

            // =================================================
            // STEP 2: INSERT STAFF
            // =================================================

            int staffId;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    staffSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                statement.setDate(
                        1,
                        Date.valueOf(
                                doctor.getEmploymentDate()
                        )
                );

                statement.setDouble(
                        2,
                        doctor.getSalary()
                );

                statement.setInt(
                        3,
                        doctor.getDepartment()
                                .getId()
                );

                statement.setInt(
                        4,
                        personId
                );

                statement.executeUpdate();

                try (
                        ResultSet keys =
                                statement.getGeneratedKeys()
                ) {

                    if (!keys.next()) {

                        throw new SQLException(
                                "StaffId was not generated."
                        );
                    }

                    staffId = keys.getInt(1);
                }
            }

            // =================================================
            // STEP 3: INSERT DOCTOR
            // =================================================

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    doctorSql
                            )
            ) {

                statement.setInt(
                        1,
                        staffId
                );

                statement.setString(
                        2,
                        doctor.getSpecialization()
                );

                statement.setString(
                        3,
                        doctor.getLicenseNumber()
                );

                statement.executeUpdate();
            }

            // =================================================
            // STEP 4: COMMIT
            // =================================================

            connection.commit();

            doctor.setStaffId(staffId);

            System.out.println(
                    "Doctor inserted successfully."
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
                    "Error inserting doctor: "
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
    // READ ALL DOCTORS
    // =========================================================

    public List<Doctor> findAllDoctors() {

        List<Doctor> doctors =
                new ArrayList<>();

        String sql = """
                SELECT
                    s.StaffId,
                    s.PersonId,
                    s.EmploymentDate,
                    s.Salary,
                    s.DepartmentId,

                    d.Specialization,
                    d.LicenseNumber,

                    p.FirstName,
                    p.LastName,
                    p.Gender,
                    p.DateOfBirth,
                    p.Phone,
                    p.Email,
                    p.Street,
                    p.City,
                    p.Country,

                    dep.Name

                FROM Doctor d

                INNER JOIN Staff s
                    ON d.StaffId = s.StaffId

                INNER JOIN Person p
                    ON s.PersonId = p.PersonId

                LEFT JOIN Department dep
                    ON s.DepartmentId = dep.DepartmentId

                ORDER BY s.StaffId
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Doctor doctor =
                        mapDoctor(resultSet);

                doctors.add(doctor);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error retrieving doctors: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return doctors;
    }


    // =========================================================
    // READ DOCTOR BY STAFF ID
    // =========================================================

    public Doctor findDoctorById(int staffId) {

        String sql = """
                SELECT
                    s.StaffId,
                    s.PersonId,
                    s.EmploymentDate,
                    s.Salary,
                    s.DepartmentId,

                    d.Specialization,
                    d.LicenseNumber,

                    p.FirstName,
                    p.LastName,
                    p.Gender,
                    p.DateOfBirth,
                    p.Phone,
                    p.Email,
                    p.Street,
                    p.City,
                    p.Country,

                    dep.Name

                FROM Doctor d

                INNER JOIN Staff s
                    ON d.StaffId = s.StaffId

                INNER JOIN Person p
                    ON s.PersonId = p.PersonId

                LEFT JOIN Department dep
                    ON s.DepartmentId = dep.DepartmentId

                WHERE s.StaffId = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    staffId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return mapDoctor(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error finding doctor: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // UPDATE DOCTOR
    // =========================================================

    public boolean update(Doctor doctor) {

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

        String doctorSql = """
                UPDATE Doctor

                SET
                    Specialization = ?,
                    LicenseNumber = ?

                WHERE StaffId = ?
                """;

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            // =================================================
            // FIND PERSON ID
            // =================================================

            int personId =
                    getPersonId(
                            connection,
                            doctor.getStaffId()
                    );

            if (personId == -1) {

                connection.rollback();

                System.out.println(
                        "Doctor not found."
                );

                return false;
            }


            // =================================================
            // UPDATE PERSON
            // =================================================

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    personSql
                            )
            ) {

                statement.setString(
                        1,
                        doctor.getFirstName()
                );

                statement.setString(
                        2,
                        doctor.getLastName()
                );

                statement.setString(
                        3,
                        String.valueOf(
                                doctor.getGender()
                        )
                );

                statement.setDate(
                        4,
                        Date.valueOf(
                                doctor.getDateOfBirth()
                        )
                );

                statement.setString(
                        5,
                        doctor.getPhone()
                );

                statement.setString(
                        6,
                        doctor.getEmail()
                );

                statement.setString(
                        7,
                        doctor.getStreet()
                );

                statement.setString(
                        8,
                        doctor.getCity()
                );

                statement.setString(
                        9,
                        doctor.getCountry()
                );

                statement.setInt(
                        10,
                        personId
                );

                statement.executeUpdate();
            }


            // =================================================
            // UPDATE STAFF
            // =================================================

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    staffSql
                            )
            ) {

                statement.setDate(
                        1,
                        Date.valueOf(
                                doctor.getEmploymentDate()
                        )
                );

                statement.setDouble(
                        2,
                        doctor.getSalary()
                );

                statement.setInt(
                        3,
                        doctor.getDepartment()
                                .getId()
                );

                statement.setInt(
                        4,
                        doctor.getStaffId()
                );

                statement.executeUpdate();
            }


            // =================================================
            // UPDATE DOCTOR
            // =================================================

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    doctorSql
                            )
            ) {

                statement.setString(
                        1,
                        doctor.getSpecialization()
                );

                statement.setString(
                        2,
                        doctor.getLicenseNumber()
                );

                statement.setInt(
                        3,
                        doctor.getStaffId()
                );

                statement.executeUpdate();
            }


            // =================================================
            // COMMIT
            // =================================================

            connection.commit();

            System.out.println(
                    "Doctor updated successfully."
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
                    "Error updating doctor: "
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
    // DELETE DOCTOR
    // =========================================================

    public boolean delete(int staffId) {

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            // =================================================
            // FIND PERSON ID
            // =================================================

            int personId =
                    getPersonId(
                            connection,
                            staffId
                    );

            if (personId == -1) {

                connection.rollback();

                return false;
            }


            // =================================================
            // DELETE DOCTOR
            // =================================================

            String doctorSql = """
                    DELETE FROM Doctor
                    WHERE StaffId = ?
                    """;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    doctorSql
                            )
            ) {

                statement.setInt(
                        1,
                        staffId
                );

                statement.executeUpdate();
            }


            // =================================================
            // DELETE STAFF
            // =================================================

            String staffSql = """
                    DELETE FROM Staff
                    WHERE StaffId = ?
                    """;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    staffSql
                            )
            ) {

                statement.setInt(
                        1,
                        staffId
                );

                statement.executeUpdate();
            }


            // =================================================
            // DELETE PERSON
            // =================================================

            String personSql = """
                    DELETE FROM Person
                    WHERE PersonId = ?
                    """;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    personSql
                            )
            ) {

                statement.setInt(
                        1,
                        personId
                );

                statement.executeUpdate();
            }


            // =================================================
            // COMMIT
            // =================================================

            connection.commit();

            System.out.println(
                    "Doctor deleted successfully."
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
                    "Error deleting doctor: "
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
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    staffId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

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
    // MAP RESULTSET TO DOCTOR
    // =========================================================

    private Doctor mapDoctor(
            ResultSet resultSet
    ) throws SQLException {

        Doctor doctor =
                new Doctor();

        // =====================================================
        // STAFF ID
        // =====================================================

        doctor.setStaffId(
                resultSet.getInt(
                        "StaffId"
                )
        );


        // =====================================================
        // PERSON INFORMATION
        // =====================================================

        doctor.setFirstName(
                resultSet.getString(
                        "FirstName"
                )
        );

        doctor.setLastName(
                resultSet.getString(
                        "LastName"
                )
        );

        String gender =
                resultSet.getString(
                        "Gender"
                );

        if (gender != null &&
                !gender.isEmpty()) {

            doctor.setGender(
                    gender.charAt(0)
            );
        }

        Date dateOfBirth =
                resultSet.getDate(
                        "DateOfBirth"
                );

        if (dateOfBirth != null) {

            doctor.setDateOfBirth(
                    dateOfBirth.toLocalDate()
            );
        }

        doctor.setPhone(
                resultSet.getString(
                        "Phone"
                )
        );

        doctor.setEmail(
                resultSet.getString(
                        "Email"
                )
        );

        doctor.setStreet(
                resultSet.getString(
                        "Street"
                )
        );

        doctor.setCity(
                resultSet.getString(
                        "City"
                )
        );

        doctor.setCountry(
                resultSet.getString(
                        "Country"
                )
        );


        // =====================================================
        // STAFF INFORMATION
        // =====================================================

        Date employmentDate =
                resultSet.getDate(
                        "EmploymentDate"
                );

        if (employmentDate != null) {

            doctor.setEmploymentDate(
                    employmentDate.toLocalDate()
            );
        }

        doctor.setSalary(
                resultSet.getDouble(
                        "Salary"
                )
        );


        // =====================================================
        // DEPARTMENT
        // =====================================================

        Department department =
                new Department();

        department.setId(
                resultSet.getInt(
                        "DepartmentId"
                )
        );

        department.setName(
                resultSet.getString(
                        "Name"
                )
        );

        doctor.setDepartment(
                department
        );


        // =====================================================
        // DOCTOR INFORMATION
        // =====================================================

        doctor.setSpecialization(
                resultSet.getString(
                        "Specialization"
                )
        );

        doctor.setLicenseNumber(
                resultSet.getString(
                        "LicenseNumber"
                )
        );

        return doctor;
    }
}
