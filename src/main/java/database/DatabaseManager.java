package database;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.PreparedStatement;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/gemmatcherdb";
    private static final String USER = "root";
    private static final String PASSWORD = "password123";

    public void saveScore(String playerName, int score) {
        String sql = "INSERT INTO highscores (player_name, score) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerName);
            stmt.setInt(2, score);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save score to database.");
        }
    }
}
