package app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
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

    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    private List<String> professores = Arrays.asList("professor@fatec.sp.gov.br");
    private List<String> alunos = Arrays.asList("aluno@fatec.sp.gov.br");

    @FXML
    private TextField emailField;  // campo de email no FXML

    public void onAlunoButtonClick(ActionEvent event) {
        String email = emailField.getText();
        if (alunos.contains(email)) {
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
        } else {
            showAlert("Acesso negado", "Email não corresponde a um aluno");
        }
    }


    public void onProfessorButtonClick(ActionEvent event) {
        String email = emailField.getText();
        if (professores.contains(email)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/professorScreen.fxml"));  // novo FXML para professor
                root = loader.load();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
    }
}
