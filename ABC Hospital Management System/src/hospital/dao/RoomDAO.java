package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Room;
import hospital.models.Ward;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public boolean addRoom(Room room) {

        String sql = """
            INSERT INTO Room
                (RoomNumber, WardId, RoomType, Capacity)
            VALUES
                (?, ?, ?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, room.getRoomNumber());
            statement.setInt(2, room.getWard().getId());
            statement.setString(3, room.getRoomType());
            statement.setInt(4, room.getCapacity());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    room.setId(generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error adding room: " + e.getMessage());
            return false;
        }
    }

    public List<Room> findAllRooms() {

        List<Room> rooms = new ArrayList<>();

        String sql = """
            SELECT
                r.RoomId,
                r.RoomNumber,
                r.WardId,
                r.RoomType,
                r.Capacity,
                w.Name AS WardName,
                w.WardType,
                w.Capacity AS WardCapacity
            FROM Room r
            INNER JOIN Ward w
                ON r.WardId = w.WardId
            ORDER BY r.RoomId
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                rooms.add(mapRoom(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving rooms: " + e.getMessage());
        }

        return rooms;
    }

    public Room findRoomById(int roomId) {

        String sql = """
            SELECT
                r.RoomId,
                r.RoomNumber,
                r.WardId,
                r.RoomType,
                r.Capacity,
                w.Name AS WardName,
                w.WardType,
                w.Capacity AS WardCapacity
            FROM Room r
            INNER JOIN Ward w
                ON r.WardId = w.WardId
            WHERE r.RoomId = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roomId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapRoom(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding room: " + e.getMessage());
        }

        return null;
    }

    public List<Room> findRoomsByWard(int wardId) {

        List<Room> rooms = new ArrayList<>();

        String sql = """
            SELECT
                r.RoomId,
                r.RoomNumber,
                r.WardId,
                r.RoomType,
                r.Capacity,
                w.Name AS WardName,
                w.WardType,
                w.Capacity AS WardCapacity
            FROM Room r
            INNER JOIN Ward w
                ON r.WardId = w.WardId
            WHERE r.WardId = ?
            ORDER BY r.RoomId
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, wardId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    rooms.add(mapRoom(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding rooms by ward: " + e.getMessage());
        }

        return rooms;
    }

    public boolean updateRoom(Room room) {

        String sql = """
            UPDATE Room
            SET
                RoomNumber = ?,
                WardId = ?,
                RoomType = ?,
                Capacity = ?
            WHERE RoomId = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, room.getRoomNumber());
            statement.setInt(2, room.getWard().getId());
            statement.setString(3, room.getRoomType());
            statement.setInt(4, room.getCapacity());
            statement.setInt(5, room.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRoom(int roomId) {

        String sql = """
            DELETE FROM Room
            WHERE RoomId = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roomId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting room: " + e.getMessage());
            return false;
        }
    }

    private Room mapRoom(ResultSet resultSet) throws SQLException {

        Room room = new Room();

        room.setId(resultSet.getInt("RoomId"));
        room.setRoomNumber(resultSet.getString("RoomNumber"));
        room.setRoomType(resultSet.getString("RoomType"));
        room.setCapacity(resultSet.getInt("Capacity"));

        Ward ward = new Ward();

        ward.setId(resultSet.getInt("WardId"));
        ward.setName(resultSet.getString("WardName"));
        ward.setWardType(resultSet.getString("WardType"));
        ward.setCapacity(resultSet.getInt("WardCapacity"));

        room.setWard(ward);

        return room;
    }
}