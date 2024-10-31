package app.controllers;

import app.helpers.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class LoginController {
//    private final List<String> professores = List.of("professor@fatec.sp.gov.br");
    private final List<String> professores = List.of("professor");
    private final List<String> alunos = List.of("aluno@fatec.sp.gov.br");
    protected Stage stage;
    protected Parent root;

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
            loadStudentScreen(event);
        } else if (professores.contains(email)) {
            loadProfessorScreen(event);
        } else {
            showAlert("Acesso negado", "Email não autorizado.");
        }
    }

    private void loadStudentScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/studentScreen.fxml"));
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Avaliação de Alunos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProfessorScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/professorScreen.fxml"));
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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

    private void executeSQLFromFile(String filePath) throws SQLException {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DatabaseConnection.getConnection(false);
            statement = connection.createStatement();

            InputStream inputStream = getClass().getResourceAsStream(filePath);

            if (inputStream == null) {
                throw new IllegalArgumentException("Arquivo SQL não encontrado: " + filePath);
            }

            String sql = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));

            String[] commands = sql.split(";");

            for (String command : commands) {
                if (!command.trim().isEmpty()) {
                    statement.execute(command.trim());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new SQLException("Erro ao executar o arquivo SQL", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    @FXML
    private void fillDatabase() {
        try {
            executeSQLFromFile("/assets/sql/schema.sql");
            System.out.println("Tabelas criadas com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
        try {
            executeSQLFromFile("/assets/sql/dump.sql");
            System.out.println("As tabelas foram populadas com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao popular as tabelas: " + e.getMessage());
        }
    }
}
