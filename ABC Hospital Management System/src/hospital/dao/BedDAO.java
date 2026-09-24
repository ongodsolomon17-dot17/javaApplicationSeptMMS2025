package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Bed;
import hospital.models.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BedDAO {

    public boolean addBed(Bed bed) {

        String sql = """
            INSERT INTO Bed
                (BedNumber, RoomId, Occupied)
            VALUES
                (?, ?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, bed.getBedNumber());
            statement.setInt(2, bed.getRoom().getId());
            statement.setBoolean(3, bed.isOccupied());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    bed.setId(generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error adding bed: " + e.getMessage());
            return false;
        }
    }

    public List<Bed> findAllBeds() {

        List<Bed> beds = new ArrayList<>();

        String sql = """
            SELECT
                b.BedId,
                b.BedNumber,
                b.RoomId,
                b.Occupied,
                r.RoomNumber
            FROM Bed b
            INNER JOIN Room r
                ON b.RoomId = r.RoomId
            ORDER BY b.BedId
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                beds.add(mapBed(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving beds: " + e.getMessage());
        }

        return beds;
    }

    public Bed findBedById(int bedId) {

        String sql = """
            SELECT
                b.BedId,
                b.BedNumber,
                b.RoomId,
                b.Occupied,
                r.RoomNumber
            FROM Bed b
            INNER JOIN Room r
                ON b.RoomId = r.RoomId
            WHERE b.BedId = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bedId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapBed(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding bed: " + e.getMessage());
        }

        return null;
    }

    public List<Bed> findBedsByRoom(int roomId) {

        List<Bed> beds = new ArrayList<>();

        String sql = """
            SELECT
                b.BedId,
                b.BedNumber,
                b.RoomId,
                b.Occupied,
                r.RoomNumber
            FROM Bed b
            INNER JOIN Room r
                ON b.RoomId = r.RoomId
            WHERE b.RoomId = ?
            ORDER BY b.BedId
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roomId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    beds.add(mapBed(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding beds by room: " + e.getMessage());
        }

        return beds;
    }

    public List<Bed> findAvailableBeds() {

        List<Bed> beds = new ArrayList<>();

        String sql = """
            SELECT
                b.BedId,
                b.BedNumber,
                b.RoomId,
                b.Occupied,
                r.RoomNumber
            FROM Bed b
            INNER JOIN Room r
                ON b.RoomId = r.RoomId
            WHERE b.Occupied = 0
            ORDER BY b.BedId
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                beds.add(mapBed(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving available beds: " + e.getMessage());
        }

        return beds;
    }

    public List<Bed> findOccupiedBeds() {

        List<Bed> beds = new ArrayList<>();

        String sql = """
            SELECT
                b.BedId,
                b.BedNumber,
                b.RoomId,
                b.Occupied,
                r.RoomNumber
            FROM Bed b
            INNER JOIN Room r
                ON b.RoomId = r.RoomId
            WHERE b.Occupied = 1
            ORDER BY b.BedId
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                beds.add(mapBed(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving occupied beds: " + e.getMessage());
        }

        return beds;
    }

    public boolean updateBed(Bed bed) {

        String sql = """
            UPDATE Bed
            SET
                BedNumber = ?,
                RoomId = ?,
                Occupied = ?
            WHERE BedId = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, bed.getBedNumber());
            statement.setInt(2, bed.getRoom().getId());
            statement.setBoolean(3, bed.isOccupied());
            statement.setInt(4, bed.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating bed: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBedOccupancy(int bedId, boolean occupied) {

        String sql = """
            UPDATE Bed
            SET Occupied = ?
            WHERE BedId = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, occupied);
            statement.setInt(2, bedId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating bed occupancy: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBed(int bedId) {

        String sql = """
            DELETE FROM Bed
            WHERE BedId = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bedId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting bed: " + e.getMessage());
            return false;
        }
    }

    private Bed mapBed(ResultSet resultSet) throws SQLException {

        Bed bed = new Bed();

        bed.setId(resultSet.getInt("BedId"));
        bed.setBedNumber(resultSet.getString("BedNumber"));
        bed.setOccupied(resultSet.getBoolean("Occupied"));

        Room room = new Room();

        room.setId(resultSet.getInt("RoomId"));
        room.setRoomNumber(resultSet.getString("RoomNumber"));

        bed.setRoom(room);

        return bed;
    }
}