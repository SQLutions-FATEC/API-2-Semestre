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
    private final List<String> professores = List.of("professor");
    private final List<String> alunos = List.of("aluno");

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erro de login", "Por favor, insira o email e a senha.");
            return;
        }

        if (alunos.contains(email)) {
            goToStudentScreen(event);
        } else if (professores.contains(email)) {
            goToProfessorScreen(event);
        } else {
            showAlert("Acesso negado", "Email não autorizado.");
        }
    }

    private void goToStudentScreen(ActionEvent event) {
        Utils.setScreen(event, "studentScreen");
    }

    private void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void fillDatabase() {
        LoginDAO loginDAO = new LoginDAO();
        try {
            loginDAO.executeSQLFromFile("/assets/sql/schema.sql");
            System.out.println("Tabelas criadas com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
        try {
            loginDAO.executeSQLFromFile("/assets/sql/dump.sql");
            System.out.println("As tabelas foram populadas com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao popular as tabelas: " + e.getMessage());
        }
    }
}
