package ui;

import database.DatabaseManager;
import database.ScoreEntry;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScoreBoard {

    private final DatabaseManager db = new DatabaseManager();

    public void show() {
        List<ScoreEntry> scores = db.getHighScores(10);

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("High Scores");
        title.setStyle("-fx-font-size: 20px");
        root.getChildren().add(title);

        if (scores.isEmpty()) {
            root.getChildren().add(new Label("No scores yet."));
        } else {
            int rank = 1;
            for (ScoreEntry s : scores) {
                HBox row = new HBox(15);
                row.setPadding(new Insets(8));
                row.setAlignment(Pos.CENTER_LEFT);
                row.setPrefWidth(340);

                row.setStyle("""
                    -fx-border-color: black;
                    -fx-border-radius: 5;
                    -fx-background-radius: 5;
                    -fx-background-color: #c5aa74;
                """);

                Label rankLbl = new Label(rank + ".");
                rankLbl.setPrefWidth(30);

                Label nameLbl = new Label(s.getPlayerName());
                nameLbl.setPrefWidth(100);

                Label scoreLbl = new Label(String.valueOf(s.getScore()));
                scoreLbl.setPrefWidth(60);

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern("MMM dd, yyyy");
                String dateText = (s.getDatePlayed().format(formatter));

                Label dateLbl = new Label(dateText);
                dateLbl.setPrefWidth(120);

                row.getChildren().addAll(rankLbl, nameLbl, scoreLbl, dateLbl);
                root.getChildren().add(row);

                rank++;
            }
        }



        Scene scene = new Scene(root, 350, 400);
        Stage stage = new Stage();
        stage.setTitle("High Scores");
        stage.setScene(scene);
        stage.show();
    }
}
