package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // =========================================================
    // CREATE
    // =========================================================

    public boolean addPatient(Patient patient) {

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

        String patientSql = """
                INSERT INTO Patient
                (
                    BloodGroup,
                    Genotype,
                    Allergies,
                    EmergencyContact,
                    EmergencyPhone,
                    PersonId
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        Connection connection = null;

        try {

            connection = DatabaseConnection.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            // =================================================
            // STEP 1
            // INSERT INTO PERSON
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
                        patient.getFirstName()
                );

                statement.setString(
                        2,
                        patient.getLastName()
                );

                statement.setString(
                        3,
                        String.valueOf(
                                patient.getGender()
                        )
                );

                statement.setDate(
                        4,
                        Date.valueOf(
                                patient.getDateOfBirth()
                        )
                );

                statement.setString(
                        5,
                        patient.getPhone()
                );

                statement.setString(
                        6,
                        patient.getEmail()
                );

                statement.setString(
                        7,
                        patient.getStreet()
                );

                statement.setString(
                        8,
                        patient.getCity()
                );

                statement.setString(
                        9,
                        patient.getCountry()
                );

                statement.executeUpdate();

                // Get generated PersonId
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
            // STEP 2
            // INSERT INTO PATIENT
            // =================================================

            int patientId;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    patientSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                statement.setString(
                        1,
                        patient.getBloodGroup()
                );

                statement.setString(
                        2,
                        patient.getGenotype()
                );

                statement.setString(
                        3,
                        patient.getAllergies()
                );

                statement.setString(
                        4,
                        patient.getEmergencyContact()
                );

                statement.setString(
                        5,
                        patient.getEmergencyPhone()
                );

                // Connect Patient to Person
                statement.setInt(
                        6,
                        personId
                );

                statement.executeUpdate();

                // Get generated PatientId
                try (
                        ResultSet keys =
                                statement.getGeneratedKeys()
                ) {

                    if (!keys.next()) {
                        throw new SQLException(
                                "PatientId was not generated."
                        );
                    }

                    patientId = keys.getInt(1);
                }
            }

            // =================================================
            // STEP 3
            // COMMIT
            // =================================================

            connection.commit();

            System.out.println(
                    "Patient inserted successfully."
            );

            System.out.println(
                    "Person ID: " + personId
            );

            System.out.println(
                    "Patient ID: " + patientId
            );

            return true;

        } catch (SQLException e) {

            // =================================================
            // ROLLBACK
            // =================================================

            if (connection != null) {

                try {
                    connection.rollback();

                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            System.err.println(
                    "Error inserting patient: "
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
    // READ ALL
    // =========================================================

    public List<Patient> findAllPatient() {

        List<Patient> patients = new ArrayList<>();

        String sql = """
                SELECT
                    pt.PatientId,
                    pt.PersonId,
                    pt.BloodGroup,
                    pt.Genotype,
                    pt.Allergies,
                    pt.EmergencyContact,
                    pt.EmergencyPhone,

                    p.FirstName,
                    p.LastName,
                    p.Gender,
                    p.DateOfBirth,
                    p.Phone,
                    p.Email,
                    p.Street,
                    p.City,
                    p.Country

                FROM Patient pt

                INNER JOIN Person p
                    ON pt.PersonId = p.PersonId

                ORDER BY pt.PatientId
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

                Patient patient =
                        mapPatient(resultSet);

                patients.add(patient);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error retrieving patients: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return patients;
    }


    // =========================================================
    // READ BY PATIENT ID
    // =========================================================

    public Patient findPatientById(int patientId) {

        String sql = """
                SELECT
                    pt.PatientId,
                    pt.PersonId,
                    pt.BloodGroup,
                    pt.Genotype,
                    pt.Allergies,
                    pt.EmergencyContact,
                    pt.EmergencyPhone,

                    p.FirstName,
                    p.LastName,
                    p.Gender,
                    p.DateOfBirth,
                    p.Phone,
                    p.Email,
                    p.Street,
                    p.City,
                    p.Country

                FROM Patient pt

                INNER JOIN Person p
                    ON pt.PersonId = p.PersonId

                WHERE pt.PatientId = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    patientId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return mapPatient(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error finding patient: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // UPDATE
    // =========================================================

    public boolean update(Patient patient) {

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

        String patientSql = """
                UPDATE Patient

                SET
                    BloodGroup = ?,
                    Genotype = ?,
                    Allergies = ?,
                    EmergencyContact = ?,
                    EmergencyPhone = ?

                WHERE PatientId = ?
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
                            patient.getPatientId()
                    );

            if (personId == -1) {

                connection.rollback();

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
                        patient.getFirstName()
                );

                statement.setString(
                        2,
                        patient.getLastName()
                );

                statement.setString(
                        3,
                        String.valueOf(
                                patient.getGender()
                        )
                );

                statement.setDate(
                        4,
                        Date.valueOf(
                                patient.getDateOfBirth()
                        )
                );

                statement.setString(
                        5,
                        patient.getPhone()
                );

                statement.setString(
                        6,
                        patient.getEmail()
                );

                statement.setString(
                        7,
                        patient.getStreet()
                );

                statement.setString(
                        8,
                        patient.getCity()
                );

                statement.setString(
                        9,
                        patient.getCountry()
                );

                statement.setInt(
                        10,
                        personId
                );

                statement.executeUpdate();
            }


            // =================================================
            // UPDATE PATIENT
            // =================================================

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    patientSql
                            )
            ) {

                statement.setString(
                        1,
                        patient.getBloodGroup()
                );

                statement.setString(
                        2,
                        patient.getGenotype()
                );

                statement.setString(
                        3,
                        patient.getAllergies()
                );

                statement.setString(
                        4,
                        patient.getEmergencyContact()
                );

                statement.setString(
                        5,
                        patient.getEmergencyPhone()
                );

                statement.setInt(
                        6,
                        patient.getPatientId()
                );

                statement.executeUpdate();
            }


            // =================================================
            // COMMIT
            // =================================================

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
    // DELETE
    // =========================================================

    public boolean delete(int patientId) {

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
                            patientId
                    );

            if (personId == -1) {

                connection.rollback();

                return false;
            }


            // =================================================
            // DELETE PATIENT
            // =================================================

            String patientSql = """
                    DELETE FROM Patient
                    WHERE PatientId = ?
                    """;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    patientSql
                            )
            ) {

                statement.setInt(
                        1,
                        patientId
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

            return true;

        } catch (SQLException e) {

            if (connection != null) {

                try {
                    connection.rollback();

                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

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
            int patientId
    ) throws SQLException {

        String sql = """
                SELECT PersonId
                FROM Patient
                WHERE PatientId = ?
                """;

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    patientId
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
    // MAP RESULTSET TO PATIENT
    // =========================================================

    private Patient mapPatient(
        ResultSet resultSet
) throws SQLException {

    Patient patient =
            new Patient();

    // =========================================================
    // PATIENT ID
    // =========================================================

    patient.setPatientId(
            resultSet.getInt("PatientId")
    );

    // =========================================================
    // PERSON INFORMATION
    // =========================================================

    patient.setFirstName(
            resultSet.getString("FirstName")
    );

    patient.setLastName(
            resultSet.getString("LastName")
    );

    String gender =
            resultSet.getString("Gender");

    if (gender != null &&
            !gender.isEmpty()) {

        patient.setGender(
                gender.charAt(0)
        );
    }

    Date dateOfBirth =
            resultSet.getDate("DateOfBirth");

    if (dateOfBirth != null) {

        patient.setDateOfBirth(
                dateOfBirth.toLocalDate()
        );
    }

    patient.setPhone(
            resultSet.getString("Phone")
    );

    patient.setEmail(
            resultSet.getString("Email")
    );

    patient.setStreet(
            resultSet.getString("Street")
    );

    patient.setCity(
            resultSet.getString("City")
    );

    patient.setCountry(
            resultSet.getString("Country")
    );

    // =========================================================
    // PATIENT MEDICAL INFORMATION
    // =========================================================

    patient.setBloodGroup(
            resultSet.getString("BloodGroup")
    );

    patient.setGenotype(
            resultSet.getString("Genotype")
    );

    patient.setAllergies(
            resultSet.getString("Allergies")
    );

    patient.setEmergencyContact(
            resultSet.getString(
                    "EmergencyContact"
            )
    );

    patient.setEmergencyPhone(
            resultSet.getString(
                    "EmergencyPhone"
            )
    );

    return patient;
  }
}

