package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Ward;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WardDAO {

    // =========================================================
    // ADD WARD
    // =========================================================

    public boolean addWard(Ward ward) {

        String sql = """
                INSERT INTO Ward
                (
                    Name,
                    WardType,
                    Capacity
                )
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            statement.setString(1, ward.getName());
            statement.setString(2, ward.getWardType());
            statement.setInt(3, ward.getCapacity());

            int rows = statement.executeUpdate();

            if (rows == 0) {
                return false;
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {

                if (keys.next()) {
                    // Ward model currently has no setId(),
                    // so there is nothing to assign here.
                }
            }

            return true;

        } catch (SQLException e) {

            System.err.println("Error adding ward: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // FIND ALL WARDS
    // =========================================================

    public List<Ward> findAllWards() {

        List<Ward> wards = new ArrayList<>();

        String sql = """
                SELECT
                    WardId,
                    Name,
                    WardType,
                    Capacity
                FROM Ward
                ORDER BY WardId
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Ward ward = mapWard(resultSet);
                wards.add(ward);
            }

        } catch (SQLException e) {

            System.err.println("Error retrieving wards: " + e.getMessage());
            e.printStackTrace();
        }

        return wards;
    }


    // =========================================================
    // FIND WARD BY ID
    // =========================================================

    public Ward findWardById(int wardId) {

        String sql = """
                SELECT
                    WardId,
                    Name,
                    WardType,
                    Capacity
                FROM Ward
                WHERE WardId = ?
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, wardId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapWard(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println("Error finding ward: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // UPDATE WARD
    // =========================================================

    public boolean updateWard(Ward ward) {

        String sql = """
                UPDATE Ward
                SET
                    Name = ?,
                    WardType = ?,
                    Capacity = ?
                WHERE WardId = ?
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, ward.getName());
            statement.setString(2, ward.getWardType());
            statement.setInt(3, ward.getCapacity());

            statement.setInt(4, ward.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Error updating ward: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // DELETE WARD
    // =========================================================

    public boolean deleteWard(int wardId) {

        String sql = """
                DELETE FROM Ward
                WHERE WardId = ?
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, wardId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Error deleting ward: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // MAP RESULT SET TO WARD
    // =========================================================

   private Ward mapWard(ResultSet resultSet) throws SQLException {

    Ward ward = new Ward();

    ward.setId(resultSet.getInt("WardId"));
    ward.setName(resultSet.getString("Name"));
    ward.setWardType(resultSet.getString("WardType"));
    ward.setCapacity(resultSet.getInt("Capacity"));

    return ward;
}    }
