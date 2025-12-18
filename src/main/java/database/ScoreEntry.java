package database;

import java.time.LocalDateTime;

public class ScoreEntry {
    private final String playerName;
    private final int score;
    private final LocalDateTime datePlayed;

    public ScoreEntry(String playerName, int score, LocalDateTime datePlayed) {
        this.playerName = playerName;
        this.score = score;
        this.datePlayed = datePlayed;
    }

    public String getPlayerName() { return playerName; }
    public int getScore() { return score; }
    public LocalDateTime getDatePlayed() { return datePlayed; }
}
