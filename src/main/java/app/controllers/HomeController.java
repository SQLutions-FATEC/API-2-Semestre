package app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;  //campo para senha
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

public class HomeController {
    private List<String> professores = Arrays.asList("professor@fatec.sp.gov.br");
    private List<String> alunos = Arrays.asList("aluno@fatec.sp.gov.br");

    @FXML
    private TextField emailField;  //campo de email no FXML

    @FXML
    private PasswordField passwordField;  //campo de senha no FXML

    @FXML
    public void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erro de login", "Por favor, insira o email e a senha.");
            return;
        }

        // Verificação básica de email para alunos e professores
        if (alunos.contains(email)) {
            loadStudentScreen();
        } else if (professores.contains(email)) {
            loadProfessorScreen();
        } else {
            showAlert("Acesso negado", "Email não autorizado.");
        }
    }

    private void loadStudentScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/studentScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Avaliação de Alunos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProfessorScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/professorScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela do Professor");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
