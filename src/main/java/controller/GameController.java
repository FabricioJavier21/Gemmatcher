package controller;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import model.Board;
import model.Gem;
import database.DatabaseManager;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class GameController {

    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static final int TILE_SIZE = 60;

    private final Board board;
    private final GridPane grid;
    private final Label scoreLabel;
    private int score = 0;

    private int selectedRow = -1;
    private int selectedCol = -1;

    public GameController() {
        board = new Board(ROWS, COLS);
        grid = new GridPane();
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font(18));

        grid.setAlignment(Pos.CENTER);
        drawBoard();
    }

    private void drawBoard() {
        grid.getChildren().clear();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                StackPane cell = createGemCell(r, c);
                grid.add(cell, c, r);
            }
        }
    }

    private StackPane createGemCell(int row, int col) {
        Gem gem = board.getGem(row, col);

        ImageView imageView = new ImageView(gem.getImage());
        imageView.setFitWidth(TILE_SIZE - 10);
        imageView.setFitHeight(TILE_SIZE - 10);
        imageView.setPreserveRatio(true);

        StackPane cell = new StackPane(imageView);
        cell.setMinSize(TILE_SIZE, TILE_SIZE);
        cell.setAlignment(Pos.CENTER);

        boolean isSelected = (row == selectedRow && col == selectedCol);
        cell.setStyle(
                "-fx-border-color: black;" +
                        "-fx-background-color: " + (isSelected ? "gold" : "lightgray") + ";"
        );

        cell.setOnMouseClicked(e -> handleClick(row, col));
        return cell;
    }

    private void handleClick(int row, int col) {
        if (selectedRow == -1) {
            selectedRow = row;
            selectedCol = col;
            drawBoard();
            return;
        }

        if (board.isAdjacent(selectedRow, selectedCol, row, col)) {
            board.swap(selectedRow, selectedCol, row, col);

            // Optional: only keep swap if it creates a match
            var matches = board.findMatches();
            if (matches.isEmpty()) {
                board.swap(selectedRow, selectedCol, row, col); // swap back
            } else {
                processBoard(); // clears, collapses, scores
            }
        }

        selectedRow = -1;
        selectedCol = -1;
        drawBoard();
    }

    private void processBoard() {
        boolean foundMatch;
        do {
            var matches = board.findMatches();
            foundMatch = !matches.isEmpty();

            if (foundMatch) {
                score += matches.size() * 10;
                scoreLabel.setText("Score: " + score);

                board.removeMatches(matches);
                board.collapse();
            }
        } while (foundMatch);
    }

    public int getScore() {
        return score;
    }

    public Parent getRoot() {
        BorderPane root = new BorderPane();
        root.setTop(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.CENTER);
        root.setCenter(grid);

        Button saveBtn = new Button("Save Score");
        saveBtn.setOnAction(e -> saveScoreToDb());
        BorderPane.setAlignment(saveBtn, Pos.CENTER);
        root.setBottom(saveBtn);

        return root;
    }

    private void saveScoreToDb() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Score");
        dialog.setHeaderText("Enter your name to save your score:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty() || result.get().trim().isEmpty()) return;

        String name = result.get().trim();
        new DatabaseManager().saveScore(name, score);
    }

}
