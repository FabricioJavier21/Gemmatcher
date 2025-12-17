package ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScoreBoard {

    public void show() {
        VBox root = new VBox(10);
        root.getChildren().add(new Label("High Scores (Coming Soon)"));

        Stage stage = new Stage();
        stage.setTitle("High Scores");
        stage.setScene(new Scene(root, 300, 400));
        stage.show();
    }
}
