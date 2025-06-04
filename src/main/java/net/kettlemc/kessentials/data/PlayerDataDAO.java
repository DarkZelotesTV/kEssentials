package net.kettlemc.kessentials.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Handles CRUD operations for player statistics.
 */
public class PlayerDataDAO {

    private final Connection connection;

    public PlayerDataDAO(Connection connection) {
        this.connection = connection;
    }

    public void incrementKills(UUID uuid) throws SQLException {
        ensureRow(uuid);
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE player_data SET kills = kills + 1 WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        }
    }

    public void incrementDeaths(UUID uuid) throws SQLException {
        ensureRow(uuid);
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE player_data SET deaths = deaths + 1 WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        }
    }

    public int getKills(UUID uuid) throws SQLException {
        ensureRow(uuid);
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT kills FROM player_data WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public int getDeaths(UUID uuid) throws SQLException {
        ensureRow(uuid);
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT deaths FROM player_data WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    /**
     * Returns a list of players with the most kills.
     * @param limit number of players to return
     */
    public java.util.List<java.util.Map.Entry<UUID, Integer>> getTopKills(int limit) throws SQLException {
        java.util.List<java.util.Map.Entry<UUID, Integer>> list = new java.util.ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT uuid, kills FROM player_data ORDER BY kills DESC LIMIT ?")) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new java.util.AbstractMap.SimpleEntry<>(
                            UUID.fromString(rs.getString(1)), rs.getInt(2)));
                }
            }
        }
        return list;
    }

    private void ensureRow(UUID uuid) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT OR IGNORE INTO player_data(uuid) VALUES (?)")) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        }
    }
}
