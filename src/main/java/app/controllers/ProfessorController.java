package app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfessorController {

    @FXML
    public void initialize() {
        // Lógica da tela do professor
    }

    public void VoltarPrincipalScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/student/mainScreen.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}