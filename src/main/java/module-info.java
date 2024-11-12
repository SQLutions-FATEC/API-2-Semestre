module app.controllers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires dotenv.java;

    opens student to javafx.fxml;
    opens app.controllers to javafx.fxml;
    exports app.controllers;
    exports app.models;
    opens app.models to javafx.fxml;
    exports app.helpers;
    opens app.helpers to javafx.fxml;
    exports app.DAOs;
    opens app.DAOs to javafx.fxml;
    exports app.Launcher;
    opens app.Launcher to javafx.fxml;
}
