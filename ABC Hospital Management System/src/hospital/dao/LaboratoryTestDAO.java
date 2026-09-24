package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.LaboratoryTechnician;
import hospital.models.LaboratoryTest;
import hospital.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaboratoryTestDAO {

    // =========================================================
    // ADD LABORATORY TEST
    // =========================================================
    public void addLaboratoryTest(LaboratoryTest test) {
        String sql = """
            INSERT INTO LaboratoryTest
            (PatientId, LaboratoryTechnicianId, TestName, TestDate,
             Result, ReferenceRange, Status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int technicianId = getLaboratoryTechnicianIdByStaffId(
                    conn,
                    test.getTechnician().getStaffId()
            );

            stmt.setInt(1, test.getPatient().getPatientId());
            stmt.setInt(2, technicianId);
            stmt.setString(3, test.getTestName());
            stmt.setTimestamp(4, Timestamp.valueOf(test.getTestDate()));
            stmt.setString(5, test.getResult());
            stmt.setString(6, test.getReferenceRange());
            stmt.setString(7, test.getStatus());

            stmt.executeUpdate();

            System.out.println("Laboratory test added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding laboratory test: " + e.getMessage());
        }
    }

    // =========================================================
    // FIND ALL LABORATORY TESTS
    // =========================================================
    public List<LaboratoryTest> findAllLaboratoryTests() {

        List<LaboratoryTest> tests = new ArrayList<>();

        String sql = """
            SELECT LaboratoryTestId, PatientId, LaboratoryTechnicianId,
                   TestName, TestDate, Result, ReferenceRange, Status
            FROM LaboratoryTest
            ORDER BY LaboratoryTestId
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tests.add(mapLaboratoryTest(conn, rs));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving laboratory tests: "
                    + e.getMessage());
        }

        return tests;
    }

    // =========================================================
    // FIND LABORATORY TEST BY ID
    // =========================================================
    public LaboratoryTest findLaboratoryTestById(int id) {

        String sql = """
            SELECT LaboratoryTestId, PatientId, LaboratoryTechnicianId,
                   TestName, TestDate, Result, ReferenceRange, Status
            FROM LaboratoryTest
            WHERE LaboratoryTestId = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapLaboratoryTest(conn, rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding laboratory test: "
                    + e.getMessage());
        }

        return null;
    }

    // =========================================================
    // FIND TESTS BY PATIENT
    // =========================================================
    public List<LaboratoryTest> findLaboratoryTestsByPatient(int patientId) {

        List<LaboratoryTest> tests = new ArrayList<>();

        String sql = """
            SELECT LaboratoryTestId, PatientId, LaboratoryTechnicianId,
                   TestName, TestDate, Result, ReferenceRange, Status
            FROM LaboratoryTest
            WHERE PatientId = ?
            ORDER BY TestDate DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    tests.add(mapLaboratoryTest(conn, rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding patient's laboratory tests: "
                    + e.getMessage());
        }

        return tests;
    }

    // =========================================================
    // FIND TESTS BY TECHNICIAN
    // =========================================================
    public List<LaboratoryTest> findLaboratoryTestsByTechnician(int staffId) {

        List<LaboratoryTest> tests = new ArrayList<>();

        String sql = """
            SELECT lt.LaboratoryTestId,
                   lt.PatientId,
                   lt.LaboratoryTechnicianId,
                   lt.TestName,
                   lt.TestDate,
                   lt.Result,
                   lt.ReferenceRange,
                   lt.Status
            FROM LaboratoryTest lt
            INNER JOIN LaboratoryTechnician ltech
                ON lt.LaboratoryTechnicianId =
                   ltech.LaboratoryTechnicianId
            WHERE ltech.StaffId = ?
            ORDER BY lt.TestDate DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    tests.add(mapLaboratoryTest(conn, rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding technician's laboratory tests: "
                    + e.getMessage());
        }

        return tests;
    }

    // =========================================================
    // UPDATE LABORATORY TEST
    // =========================================================
    public void updateLaboratoryTest(LaboratoryTest test) {

        String sql = """
            UPDATE LaboratoryTest
            SET PatientId = ?,
                LaboratoryTechnicianId = ?,
                TestName = ?,
                TestDate = ?,
                Result = ?,
                ReferenceRange = ?,
                Status = ?
            WHERE LaboratoryTestId = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int technicianId = getLaboratoryTechnicianIdByStaffId(
                    conn,
                    test.getTechnician().getStaffId()
            );

            stmt.setInt(1, test.getPatient().getPatientId());
            stmt.setInt(2, technicianId);
            stmt.setString(3, test.getTestName());
            stmt.setTimestamp(4, Timestamp.valueOf(test.getTestDate()));
            stmt.setString(5, test.getResult());
            stmt.setString(6, test.getReferenceRange());
            stmt.setString(7, test.getStatus());
            stmt.setInt(8, test.getId());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Laboratory test updated successfully.");
            } else {
                System.out.println("Laboratory test not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating laboratory test: "
                    + e.getMessage());
        }
    }

    // =========================================================
    // DELETE LABORATORY TEST
    // =========================================================
    public void deleteLaboratoryTest(int id) {

        String sql = "DELETE FROM LaboratoryTest WHERE LaboratoryTestId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Laboratory test deleted successfully.");
            } else {
                System.out.println("Laboratory test not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting laboratory test: "
                    + e.getMessage());
        }
    }

    // =========================================================
    // MAP RESULT TO MODEL
    // =========================================================
    private LaboratoryTest mapLaboratoryTest(
            Connection conn,
            ResultSet rs) throws SQLException {

        LaboratoryTest test = new LaboratoryTest();

        test.setId(rs.getInt("LaboratoryTestId"));

        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("PatientId"));
        test.setPatient(patient);

        LaboratoryTechnician technician =
                getTechnicianById(
                        conn,
                        rs.getInt("LaboratoryTechnicianId")
                );

        test.setTechnician(technician);

        test.setTestName(rs.getString("TestName"));

        Timestamp timestamp = rs.getTimestamp("TestDate");
        if (timestamp != null) {
            test.setTestDate(timestamp.toLocalDateTime());
        }

        test.setResult(rs.getString("Result"));
        test.setReferenceRange(rs.getString("ReferenceRange"));
        test.setStatus(rs.getString("Status"));

        return test;
    }

    // =========================================================
    // GET DB LAB TECH ID FROM STAFF ID
    // =========================================================
    private int getLaboratoryTechnicianIdByStaffId(
            Connection conn,
            int staffId) throws SQLException {

        String sql = """
            SELECT LaboratoryTechnicianId
            FROM LaboratoryTechnician
            WHERE StaffId = ?
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("LaboratoryTechnicianId");
                }
            }
        }

        throw new SQLException(
                "Laboratory Technician not found for Staff ID: " + staffId
        );
    }

    // =========================================================
    // GET TECHNICIAN FROM DB ID
    // =========================================================
    private LaboratoryTechnician getTechnicianById(
            Connection conn,
            int technicianId) throws SQLException {

        String sql = """
            SELECT StaffId
            FROM LaboratoryTechnician
            WHERE LaboratoryTechnicianId = ?
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, technicianId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    LaboratoryTechnician technician =
                            new LaboratoryTechnician();

                    technician.setStaffId(rs.getInt("StaffId"));

                    return technician;
                }
            }
        }

        return null;
    }
}