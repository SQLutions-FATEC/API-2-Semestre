module app.controllers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires dotenv.java;

    opens student to javafx.fxml;
    opens app.controllers to javafx.fxml;
    exports app.controllers;
}
