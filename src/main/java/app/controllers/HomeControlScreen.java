package app.controllers;

<<<<<<< HEAD
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
=======
>>>>>>> 610405d3a499356ead95a722819f393c455ac92e
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
<<<<<<< HEAD
import java.util.List;
import java.util.Arrays;

public class HomeControlScreen {

    // Emails simulados para a validação
    private List<String> professores = Arrays.asList("professor@fatec.sp.gov.br");
    private List<String> alunos = Arrays.asList("aluno@fatec.sp.gov.br");

    @FXML
    private TextField emailField;  // campo de email no FXML

    public void onAlunoButtonClick() {
        String email = emailField.getText();
        if (alunos.contains(email)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/studentEvaluator.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Avaliação de Alunos");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Acesso negado", "Email não corresponde a um aluno");
=======

public class HomeControlScreen {

    public void onAlunoButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/studentEvaluator.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Avaliaçao de Alunos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
>>>>>>> 610405d3a499356ead95a722819f393c455ac92e
        }
    }

    public void onProfessorButtonClick() {
<<<<<<< HEAD
        String email = emailField.getText();
        if (professores.contains(email)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/professorScreen.fxml"));  // novo FXML para professor
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Tela do Professor");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Acesso negado", "Email não corresponde a um professor");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
=======

>>>>>>> 610405d3a499356ead95a722819f393c455ac92e
    }
}
