package app.controllers;

import app.DAOs.LoginDAO;
import app.helpers.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

public class LoginController {
    private final List<String> professors = List.of("professor");
    private final List<String> students = List.of("aluno");

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            Utils.setAlert("ERROR", "Erro de login", "Por favor, insira o email e a senha");
            return;
        }

        if (students.contains(email)) {
            goToStudentScreen(event);
        } else if (professors.contains(email)) {
            goToProfessorScreen(event);
        } else {
            Utils.setAlert("ERROR", "Erro de login", "Email não autorizado");
        }
    }

    private void goToStudentScreen(ActionEvent event) {
        Utils.setScreen(event, "studentScreen");
    }

    private void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }

    @FXML
    private void fillDatabase() {
        LoginDAO loginDAO = new LoginDAO();
        try {
            loginDAO.executeSQLFromFile("/assets/sql/schema.sql");
            Utils.setAlert("CONFIRMATION", "Preenchimento do banco", "Tabelas criadas com sucesso!");
        } catch (Exception e) {
            Utils.setAlert("ERROR", "Preenchimento do banco", "Erro ao criar as tabelas: " + e.getMessage());
        }
        try {
            loginDAO.executeSQLFromFile("/assets/sql/dump.sql");
            Utils.setAlert("CONFIRMATION", "Preenchimento do banco", "As tabelas foram populadas com sucesso!");
        } catch (Exception e) {
            Utils.setAlert("ERROR", "Preenchimento do banco", "Erro ao popular as tabelas: " + e.getMessage());
        }
    }
}
