package database;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/gemmatcherdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Password123";


    public void saveScore(String playerName, int score) {
        String sql = "INSERT INTO highscores (player_name, score) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerName);
            stmt.setInt(2, score);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<ScoreEntry> getHighScores(int limit) {
        String sql = """
                SELECT player_name, score, date_played
                FROM highscores
                ORDER BY score DESC
                LIMIT ?
                """;

        List<ScoreEntry> scores = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("player_name");
                    int score = rs.getInt("score");

                    Timestamp ts = rs.getTimestamp("date_played");
                    LocalDateTime played = (ts != null) ? ts.toLocalDateTime() : null;

                    scores.add(new ScoreEntry(name, score, played));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            }

        return scores;
    }
}
