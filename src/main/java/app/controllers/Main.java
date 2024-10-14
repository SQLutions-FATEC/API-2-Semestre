package app.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
<<<<<<< HEAD

    public static void main(String[] args) {
        launch(args);
=======

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/principalScreen.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Tela Principal");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
>>>>>>> 610405d3a499356ead95a722819f393c455ac92e
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/student/principalScreen.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Tela Principal");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}