package app.controllers;

import app.helpers.DatabaseConnection;
import app.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditStudentController implements Initializable {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    ObservableList<UserModel> studentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchStudents();
    }

    private void fetchStudents() {
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount = "SELECT * FROM usuario WHERE ra IS NOT NULL ORDER BY nome";
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer ra = resultSet.getInt("ra");
                String name = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String password = resultSet.getString("senha");
                Integer team_id = resultSet.getInt("id_equipe");

                UserModel user = new UserModel(ra, name, email, password, team_id);
                studentList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    public void voltarProfessorScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/professor/professorScreen.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
