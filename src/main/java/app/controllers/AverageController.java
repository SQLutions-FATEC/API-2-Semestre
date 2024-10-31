package app.controllers;

import app.helpers.DatabaseConnection;
import app.helpers.AverageGradeDAO;
import app.helpers.Utils;
import app.models.EquipeModel;
import app.models.AverageGradeModel;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AverageController {
    @FXML
    public ChoiceBox<String> ChoiceBoxSprint;
    @FXML
    public TableView<AverageGradeModel> tableAverageGrades = new TableView<>();

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String currentSprint;
    Integer selectedSprintId;
    Integer selectedPeriodId;
    Integer selectedTeamId;

    private final ObservableList<EquipeModel> studentList = FXCollections.observableArrayList();
    private final Map<String, Integer> sprintIdMap = new HashMap<>();

    public void passData(int teamId, int periodId) {
        selectedPeriodId = periodId;
        selectedTeamId = teamId;
        ChoiceBoxSprint.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleSprintListSelectionChange(newValue);
        });
        fetchSprint();
    }

    private void handleSprintListSelectionChange(String sprint) {
        currentSprint = sprint;
        selectedSprintId = sprintIdMap.get(sprint);
        studentList.clear();
        fetchCriterias();
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
                String description = resultSet.getString("descricao");
                Date dataInicio = resultSet.getDate("data_inicio");
                Date dataFim = resultSet.getDate("data_fim");

                new SprintModel(id, description, dataInicio, dataFim);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedStartDate = dateFormat.format(SprintModel.getDataInicio());
                String formattedEndDate = dateFormat.format(SprintModel.getDataFim());

                String sprintDescription = description + ": (" + formattedStartDate + " - " + formattedEndDate + ")";
                sprintOptionsList.add(sprintDescription);
                sprintIdMap.put(sprintDescription, id);
            }
            ChoiceBoxSprint.getItems().addAll(sprintOptionsList);
            String currentSprint = Utils.getCurrentSprint(sprintOptionsList);
            if (currentSprint != null) {
                ChoiceBoxSprint.setValue(currentSprint);
            } else {
                sprintOptionsList.add("Todos");
                ChoiceBoxSprint.setValue("Todos");
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

    private void fetchCriterias() {
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount = String.format("SELECT c.nome as nome FROM criterio_periodo cp join criterio c on cp.criterio_id = c.id where cp.periodo_id = '%d'", selectedPeriodId);
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            ObservableList<TableColumn<AverageGradeModel, Double>> columns = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String criterioNome = resultSet.getString("nome");

                TableColumn<AverageGradeModel, Double> column = new TableColumn<>(criterioNome);
                column.setCellValueFactory(new PropertyValueFactory<>(criterioNome));
                columns.add(column);
            }
            tableAverageGrades.getColumns().addAll(columns);
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

    private void fetchGrades() {
        AverageGradeDAO averageGradeDAO = new AverageGradeDAO();
        List<AverageGradeModel> notas = averageGradeDAO.fetchAverages(selectedTeamId, selectedPeriodId, selectedSprintId);
        ObservableList<AverageGradeModel> observableNotas = FXCollections.observableArrayList(notas);
        tableAverageGrades.setItems(observableNotas);
    }
}
