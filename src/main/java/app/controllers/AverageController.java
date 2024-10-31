package app.controllers;

import app.helpers.DatabaseConnection;
import app.models.CriteriaModel;
import app.models.EquipeModel;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class AverageController implements Initializable {

    @FXML
    public ChoiceBox<String> ChoiceBoxSprint;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String currentSprint;

    private final ObservableList<EquipeModel> studentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChoiceBoxSprint.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleSprintListSelectionChange(newValue);
        });
        fetchSprint();
    }

    private void handleSprintListSelectionChange(String sprint) {
        currentSprint = sprint;
        studentList.clear();
//        fetchGrades();
    }

    private void fetchSprint() {
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount = "SELECT * FROM sprint";
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            ArrayList<String> sprintOptionsList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String descricao = resultSet.getString("descricao");
                Date dataInicio = resultSet.getDate("data_inicio");
                Date dataFim = resultSet.getDate("data_fim");

                new SprintModel(id, descricao, dataInicio, dataFim);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedStartDate = dateFormat.format(SprintModel.getDataInicio());
                String formattedEndDate = dateFormat.format(SprintModel.getDataFim());

                sprintOptionsList.add(descricao + ": (" + formattedStartDate + " - " + formattedEndDate + ")");
            }
            ChoiceBoxSprint.getItems().addAll(sprintOptionsList);
//            ChoiceBoxPeriodo.setValue(period[1] + " - " + period[0]);
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

    public void inicializarDados(String nomeUsuario) {
    }
}
