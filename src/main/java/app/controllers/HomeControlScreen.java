package app.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        }
    }

    public void onProfessorButtonClick() {

    }
}
