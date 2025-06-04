package net.kettlemc.kessentials.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Handles verification codes used to link Discord accounts with Minecraft players.
 */
public class LinkDAO {

    private final Connection connection;
    private final Random random = new Random();

    public LinkDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Generates a new verification code for the given player.
     * If a code already exists, it will be replaced.
     *
     * @param uuid player uuid
     * @return generated code
     */
    public String createCode(UUID uuid) throws SQLException {
        String code = String.format("%06d", random.nextInt(1_000_000));
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT OR REPLACE INTO link_codes(uuid, code) VALUES(?, ?)")) {
            ps.setString(1, uuid.toString());
            ps.setString(2, code);
            ps.executeUpdate();
        }
        return code;
    }

    /**
     * Verifies a code from Discord.
     *
     * @param code verification code
     * @param discordId id of the discord user
     * @return true if the code was valid and linked
     */
    public boolean verifyCode(String code, String discordId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT uuid FROM link_codes WHERE code = ? AND discord_id IS NULL")) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement ps2 = connection.prepareStatement(
                            "UPDATE link_codes SET discord_id = ? WHERE code = ?")) {
                        ps2.setString(1, discordId);
                        ps2.setString(2, code);
                        ps2.executeUpdate();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the given player already linked their Discord account.
     */
    public boolean isLinked(UUID uuid) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT discord_id FROM link_codes WHERE uuid = ? AND discord_id IS NOT NULL")) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}

