package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Admission;
import hospital.models.Nurse;
import hospital.models.NurseAssignment;
import hospital.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NurseAssignmentDAO {

    public void addNurseAssignment(NurseAssignment assignment) {

        String sql = """
        INSERT INTO NurseAssignment
        (
            NurseId,
            PatientId,
            AdmissionId,
            AssignmentDate,
            EndDate,
            Shift,
            Status,
            Notes
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS)) {

            // Java model stores StaffId, but SQL NurseAssignment needs NurseId.
            int nurseDbId = getNurseIdByStaffId(
                    conn,
                    assignment.getNurse().getStaffId()
            );

            stmt.setInt(1, nurseDbId);
            stmt.setInt(2, assignment.getPatient().getPatientId());

            if (assignment.getAdmission() != null) {
                stmt.setInt(3, assignment.getAdmission().getId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setTimestamp(
                    4,
                    Timestamp.valueOf(assignment.getAssignmentDate())
            );

            if (assignment.getEndDate() != null) {
                stmt.setTimestamp(
                        5,
                        Timestamp.valueOf(assignment.getEndDate())
                );
            } else {
                stmt.setNull(5, Types.TIMESTAMP);
            }

            stmt.setString(6, assignment.getShift());
            stmt.setString(7, assignment.getStatus());
            stmt.setString(8, assignment.getNotes());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    assignment.setId(rs.getInt(1));
                }
            }

            System.out.println("Nurse assignment added successfully.");

        } catch (SQLException e) {
            System.out.println(
                    "Error adding nurse assignment: " + e.getMessage()
            );
        }
    }

    public List<NurseAssignment> findAllNurseAssignments() {

        List<NurseAssignment> assignments = new ArrayList<>();

        String sql = """
            SELECT
                NurseAssignmentId,
                NurseId,
                PatientId,
                AdmissionId,
                AssignmentDate,
                EndDate,
                Shift,
                Status,
                Notes
            FROM NurseAssignment
            ORDER BY AssignmentDate DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                assignments.add(mapNurseAssignment(conn, rs));
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving nurse assignments: "
                    + e.getMessage()
            );
        }

        return assignments;
    }

    public NurseAssignment findNurseAssignmentById(int id) {

        String sql = """
            SELECT
                NurseAssignmentId,
                NurseId,
                PatientId,
                AdmissionId,
                AssignmentDate,
                EndDate,
                Shift,
                Status,
                Notes
            FROM NurseAssignment
            WHERE NurseAssignmentId = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapNurseAssignment(conn, rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error finding nurse assignment: "
                    + e.getMessage()
            );
        }

        return null;
    }

    public List<NurseAssignment> findNurseAssignmentsByPatient(
            int patientId) {

        List<NurseAssignment> assignments = new ArrayList<>();

        String sql = """
            SELECT
                NurseAssignmentId,
                NurseId,
                PatientId,
                AdmissionId,
                AssignmentDate,
                EndDate,
                Shift,
                Status,
                Notes
            FROM NurseAssignment
            WHERE PatientId = ?
            ORDER BY AssignmentDate DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    assignments.add(mapNurseAssignment(conn, rs));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving patient nurse assignments: "
                    + e.getMessage()
            );
        }

        return assignments;
    }

    public List<NurseAssignment> findNurseAssignmentsByNurse(
            int nurseId) {

        List<NurseAssignment> assignments = new ArrayList<>();

        String sql = """
            SELECT
                NurseAssignmentId,
                NurseId,
                PatientId,
                AdmissionId,
                AssignmentDate,
                EndDate,
                Shift,
                Status,
                Notes
            FROM NurseAssignment
            WHERE NurseId = ?
            ORDER BY AssignmentDate DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nurseId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    assignments.add(mapNurseAssignment(conn, rs));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving nurse assignments: "
                    + e.getMessage()
            );
        }

        return assignments;
    }

    public void updateNurseAssignment(NurseAssignment assignment) {

    String sql = """
        UPDATE NurseAssignment
        SET
            NurseId = ?,
            PatientId = ?,
            AdmissionId = ?,
            AssignmentDate = ?,
            EndDate = ?,
            Shift = ?,
            Status = ?,
            Notes = ?
        WHERE NurseAssignmentId = ?
        """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // Convert Java StaffId to database NurseId.
        int nurseDbId = getNurseIdByStaffId(
                conn,
                assignment.getNurse().getStaffId()
        );

        stmt.setInt(1, nurseDbId);
        stmt.setInt(2, assignment.getPatient().getPatientId());

        if (assignment.getAdmission() != null) {
            stmt.setInt(3, assignment.getAdmission().getId());
        } else {
            stmt.setNull(3, Types.INTEGER);
        }

        stmt.setTimestamp(
                4,
                Timestamp.valueOf(assignment.getAssignmentDate())
        );

        if (assignment.getEndDate() != null) {
            stmt.setTimestamp(
                    5,
                    Timestamp.valueOf(assignment.getEndDate())
            );
        } else {
            stmt.setNull(5, Types.TIMESTAMP);
        }

        stmt.setString(6, assignment.getShift());
        stmt.setString(7, assignment.getStatus());
        stmt.setString(8, assignment.getNotes());
        stmt.setInt(9, assignment.getId());

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            System.out.println(
                    "Nurse assignment updated successfully."
            );
        } else {
            System.out.println(
                    "Nurse assignment not found."
            );
        }

    } catch (SQLException e) {
        System.out.println(
                "Error updating nurse assignment: " + e.getMessage()
        );
    }
}

    public void deleteNurseAssignment(int id) {

        String sql = """
            DELETE FROM NurseAssignment
            WHERE NurseAssignmentId = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println(
                        "Nurse assignment deleted successfully."
                );
            } else {
                System.out.println(
                        "Nurse assignment not found."
                );
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error deleting nurse assignment: "
                    + e.getMessage()
            );
        }
    }

    private NurseAssignment mapNurseAssignment(
            Connection conn,
            ResultSet rs) throws SQLException {

        NurseAssignment assignment = new NurseAssignment();

        assignment.setId(
                rs.getInt("NurseAssignmentId")
        );

        Nurse nurse = getNurseByNurseId(
                conn,
                rs.getInt("NurseId")
        );

        assignment.setNurse(nurse);

        Patient patient = new Patient();
        patient.setPatientId(
                rs.getInt("PatientId")
        );

        assignment.setPatient(patient);

        int admissionId = rs.getInt("AdmissionId");

        if (!rs.wasNull()) {

            Admission admission = new Admission();
            admission.setId(admissionId);

            assignment.setAdmission(admission);
        }

        Timestamp assignmentTimestamp
                = rs.getTimestamp("AssignmentDate");

        if (assignmentTimestamp != null) {

            assignment.setAssignmentDate(
                    assignmentTimestamp.toLocalDateTime()
            );
        }

        Timestamp endTimestamp
                = rs.getTimestamp("EndDate");

        if (endTimestamp != null) {

            assignment.setEndDate(
                    endTimestamp.toLocalDateTime()
            );
        }

        assignment.setShift(
                rs.getString("Shift")
        );

        assignment.setStatus(
                rs.getString("Status")
        );

        assignment.setNotes(
                rs.getString("Notes")
        );

        return assignment;
    }

    private Nurse getNurseByNurseId(
            Connection conn,
            int nurseId) throws SQLException {

        String sql = """
            SELECT StaffId
            FROM Nurse
            WHERE NurseId = ?
            """;

        try (PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setInt(1, nurseId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Nurse nurse = new Nurse();

                    nurse.setStaffId(
                            rs.getInt("StaffId")
                    );

                    return nurse;
                }
            }
        }

        return null;

    }

    private int getNurseIdByStaffId(
            Connection conn,
            int staffId) throws SQLException {

        String sql = """
        SELECT NurseId
        FROM Nurse
        WHERE StaffId = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("NurseId");
                }
            }
        }

        throw new SQLException(
                "No nurse record found for Staff ID: " + staffId
        );
    }
}