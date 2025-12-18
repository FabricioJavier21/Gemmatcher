module project.gemclone {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens controller to javafx.fxml;
    opens ui to javafx.fxml;
    opens database to javafx.base;

    exports project.gemclone;
    exports controller;
    exports model;
    exports ui;
    exports database;
    }
