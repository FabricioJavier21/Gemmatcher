module project.gemclone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens project.gemclone to javafx.fxml;
    exports project.gemclone;
    }
