package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Admission;
import hospital.models.Bed;
import hospital.models.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdmissionDAO {

    // =========================================================
    // CREATE ADMISSION
    // =========================================================

    public boolean addAdmission(Admission admission) {

        String sql = """
                INSERT INTO Admission
                (
                    PatientId,
                    AdmissionDate,
                    DischargeDate,
                    BedNumber,
                    Reason,
                    Status
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setInt(
                    1,
                    admission.getPatient().getPatientId()
            );

            statement.setDate(
                    2,
                    Date.valueOf(
                            admission.getAdmissionDate()
                    )
            );

            if (admission.getDischargeDate() != null) {

                statement.setDate(
                        3,
                        Date.valueOf(
                                admission.getDischargeDate()
                        )
                );

            } else {

                statement.setNull(
                        3,
                        Types.DATE
                );
            }

            String bedNumber = null;

            if (admission.getBed() != null) {

                bedNumber =
                        admission.getBed().getBedNumber();
            }

            statement.setString(
                    4,
                    bedNumber
            );

            statement.setString(
                    5,
                    admission.getReason()
            );

            statement.setString(
                    6,
                    admission.getStatus()
            );

            int rows =
                    statement.executeUpdate();

            if (rows == 0) {
                return false;
            }

            try (
                    ResultSet keys =
                            statement.getGeneratedKeys()
            ) {

                if (keys.next()) {

                    admission.setId(
                            keys.getInt(1)
                    );
                }
            }

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error adding admission: "
                            + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // FIND ALL ADMISSIONS
    // =========================================================

    public List<Admission> findAllAdmissions() {

        List<Admission> admissions =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.AdmissionId,
                    a.PatientId,
                    a.AdmissionDate,
                    a.DischargeDate,
                    a.BedNumber,
                    a.Reason,
                    a.Status,

                    p.FirstName,
                    p.LastName

                FROM Admission a

                INNER JOIN Patient pt
                    ON a.PatientId = pt.PatientId

                INNER JOIN Person p
                    ON pt.PersonId = p.PersonId

                ORDER BY a.AdmissionId
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

                admissions.add(
                        mapAdmission(resultSet)
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error retrieving admissions: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return admissions;
    }


    // =========================================================
    // FIND ADMISSION BY ID
    // =========================================================

    public Admission findAdmissionById(
            int admissionId
    ) {

        String sql = """
                SELECT
                    a.AdmissionId,
                    a.PatientId,
                    a.AdmissionDate,
                    a.DischargeDate,
                    a.BedNumber,
                    a.Reason,
                    a.Status,

                    p.FirstName,
                    p.LastName

                FROM Admission a

                INNER JOIN Patient pt
                    ON a.PatientId = pt.PatientId

                INNER JOIN Person p
                    ON pt.PersonId = p.PersonId

                WHERE a.AdmissionId = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    admissionId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return mapAdmission(
                            resultSet
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error finding admission: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // FIND ADMISSIONS BY PATIENT
    // =========================================================

    public List<Admission> findAdmissionsByPatient(
            int patientId
    ) {

        List<Admission> admissions =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.AdmissionId,
                    a.PatientId,
                    a.AdmissionDate,
                    a.DischargeDate,
                    a.BedNumber,
                    a.Reason,
                    a.Status,

                    p.FirstName,
                    p.LastName

                FROM Admission a

                INNER JOIN Patient pt
                    ON a.PatientId = pt.PatientId

                INNER JOIN Person p
                    ON pt.PersonId = p.PersonId

                WHERE a.PatientId = ?

                ORDER BY a.AdmissionDate DESC
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

                while (resultSet.next()) {

                    admissions.add(
                            mapAdmission(
                                    resultSet
                            )
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error finding patient admissions: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return admissions;
    }


    // =========================================================
    // UPDATE ADMISSION
    // =========================================================

    public boolean updateAdmission(
            Admission admission
    ) {

        String sql = """
                UPDATE Admission

                SET
                    PatientId = ?,
                    AdmissionDate = ?,
                    DischargeDate = ?,
                    BedNumber = ?,
                    Reason = ?,
                    Status = ?

                WHERE AdmissionId = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    admission.getPatient().getPatientId()
            );

            statement.setDate(
                    2,
                    Date.valueOf(
                            admission.getAdmissionDate()
                    )
            );

            if (admission.getDischargeDate() != null) {

                statement.setDate(
                        3,
                        Date.valueOf(
                                admission.getDischargeDate()
                        )
                );

            } else {

                statement.setNull(
                        3,
                        Types.DATE
                );
            }

            String bedNumber = null;

            if (admission.getBed() != null) {

                bedNumber =
                        admission.getBed().getBedNumber();
            }

            statement.setString(
                    4,
                    bedNumber
            );

            statement.setString(
                    5,
                    admission.getReason()
            );

            statement.setString(
                    6,
                    admission.getStatus()
            );

            statement.setInt(
                    7,
                    admission.getId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error updating admission: "
                            + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // DISCHARGE PATIENT
    // =========================================================

    public boolean dischargePatient(
            int admissionId,
            LocalDate dischargeDate
    ) {

        String sql = """
                UPDATE Admission

                SET
                    DischargeDate = ?,
                    Status = 'Discharged'

                WHERE AdmissionId = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setDate(
                    1,
                    Date.valueOf(
                            dischargeDate
                    )
            );

            statement.setInt(
                    2,
                    admissionId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error discharging patient: "
                            + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // DELETE ADMISSION
    // =========================================================

    public boolean deleteAdmission(
            int admissionId
    ) {

        String sql = """
                DELETE FROM Admission
                WHERE AdmissionId = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    admissionId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error deleting admission: "
                            + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // MAP RESULTSET TO ADMISSION
    // =========================================================

    private Admission mapAdmission(
            ResultSet resultSet
    ) throws SQLException {

        Admission admission =
                new Admission();

        // =====================================================
        // ADMISSION ID
        // =====================================================

        admission.setId(
                resultSet.getInt(
                        "AdmissionId"
                )
        );

        // =====================================================
        // PATIENT
        // =====================================================

        Patient patient =
                new Patient();

        patient.setPatientId(
                resultSet.getInt(
                        "PatientId"
                )
        );

        patient.setFirstName(
                resultSet.getString(
                        "FirstName"
                )
        );

        patient.setLastName(
                resultSet.getString(
                        "LastName"
                )
        );

        admission.setPatient(
                patient
        );

        // =====================================================
        // BED
        // =====================================================

        String bedNumber =
                resultSet.getString(
                        "BedNumber"
                );

        if (bedNumber != null) {

            Bed bed =
                    new Bed();

            bed.setBedNumber(
                    bedNumber
            );

            admission.setBed(
                    bed
            );
        }

        // =====================================================
        // DATES
        // =====================================================

        Date admissionDate =
                resultSet.getDate(
                        "AdmissionDate"
                );

        if (admissionDate != null) {

            admission.setAdmissionDate(
                    admissionDate.toLocalDate()
            );
        }

        Date dischargeDate =
                resultSet.getDate(
                        "DischargeDate"
                );

        if (dischargeDate != null) {

            admission.setDischargeDate(
                    dischargeDate.toLocalDate()
            );
        }

        // =====================================================
        // OTHER INFORMATION
        // =====================================================

        admission.setReason(
                resultSet.getString(
                        "Reason"
                )
        );

        admission.setStatus(
                resultSet.getString(
                        "Status"
                )
        );

        return admission;
    }
}