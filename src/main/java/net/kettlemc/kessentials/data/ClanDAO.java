package net.kettlemc.kessentials.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Basic DAO for clan and membership management.
 */
public class ClanDAO {

    private final Connection connection;

    public ClanDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean clanExists(String name) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT name FROM clans WHERE name = ?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void createClan(String name, UUID leader) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO clans(name) VALUES (?)")) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
        joinClan(leader, name);
    }

    public void joinClan(UUID uuid, String clan) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT OR REPLACE INTO clan_members(uuid, clan) VALUES(?, ?)")) {
            ps.setString(1, uuid.toString());
            ps.setString(2, clan);
            ps.executeUpdate();
        }
    }

    public void leaveClan(UUID uuid) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM clan_members WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        }
    }

    public String getClan(UUID uuid) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT clan FROM clan_members WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString(1) : null;
            }
        }
    }

    public List<UUID> getMembers(String clan) throws SQLException {
        List<UUID> members = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT uuid FROM clan_members WHERE clan = ?")) {
            ps.setString(1, clan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    members.add(UUID.fromString(rs.getString(1)));
                }
            }
        }
        return members;
    }

    /**
     * Returns a list of all clan names.
     */
    public List<String> getAllClans() throws SQLException {
        List<String> names = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT name FROM clans")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    names.add(rs.getString(1));
                }
            }
        }
        return names;
    }

    /**
     * Returns the clans ordered by member count.
     */
    public java.util.List<java.util.Map.Entry<String, Integer>> getTopClans(int limit) throws SQLException {
        java.util.List<java.util.Map.Entry<String, Integer>> list = new java.util.ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT clan, COUNT(*) as cnt FROM clan_members GROUP BY clan ORDER BY cnt DESC LIMIT ?")) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new java.util.AbstractMap.SimpleEntry<>(rs.getString(1), rs.getInt(2)));
                }
            }
        }
        return list;
    }
}
