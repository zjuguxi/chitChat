package com.chitchat.domain.DHT;

import java.sql.*;

public class DHTUtils {
    private static final String URL = "jdbc:sqlite:path_to_your_database.db"; // 修改为实际的数据库路径

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS nodes (" +
                             "nodeId TEXT PRIMARY KEY, " +
                             "host TEXT NOT NULL, " +
                             "port INTEGER NOT NULL)";
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void storeNode(String nodeId, String host, int port) {
        String sql = "INSERT INTO nodes(nodeId, host, port) VALUES(?, ?, ?) ON CONFLICT(nodeId) DO UPDATE SET " +
                     "host = excluded.host, port = excluded.port;";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nodeId);
            pstmt.setString(2, host);
            pstmt.setInt(3, port);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error storing node: " + e.getMessage());
        }
    }

    public static String findNode(String nodeId) {
        String sql = "SELECT host, port FROM nodes WHERE nodeId = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nodeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("host") + ":" + rs.getInt("port");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding node: " + e.getMessage());
        }
        return null;
    }
}
