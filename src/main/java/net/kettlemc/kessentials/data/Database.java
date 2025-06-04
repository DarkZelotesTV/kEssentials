package net.kettlemc.kessentials.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple SQLite database wrapper used for storing player statistics and team data.
 */
public class Database {

    private final File file;
    private Connection connection;

    public Database(File file) {
        this.file = file;
    }

    /**
     * Opens the SQLite connection and creates default tables if they do not exist.
     */
    public void open() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            return;
        }
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
        try (Statement stmt = this.connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS player_data (" +
                    "uuid TEXT PRIMARY KEY," +
                    "kills INTEGER DEFAULT 0," +
                    "deaths INTEGER DEFAULT 0" +
                    ")");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS clans (" +
                    "name TEXT PRIMARY KEY" +
                    ")");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS clan_members (" +
                    "uuid TEXT," +
                    "clan TEXT," +
                    "PRIMARY KEY(uuid)," +
                    "FOREIGN KEY(clan) REFERENCES clans(name)" +
                    ")");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS link_codes (" +
                    "uuid TEXT PRIMARY KEY," +
                    "code TEXT," +
                    "discord_id TEXT" +
                    ")");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
