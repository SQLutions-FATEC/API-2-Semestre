package app.controllers;

import app.helpers.DatabaseConnection;
import app.DAOs.AverageGradeDAO;
import app.helpers.Utils;
import app.models.EquipeModel;
import app.models.AverageGradeModel;
import app.models.SprintModel;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    ArrayList<String> sprintOptionsList = new ArrayList<>();

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
        fetchGrades();
    }

    private void fetchSprint() {
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount = String.format("SELECT * FROM sprint s WHERE s.periodo = '%d' ORDER BY s.data_inicio", selectedPeriodId);
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

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
                ChoiceBoxSprint.setValue(sprintOptionsList.get(0));
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

            tableAverageGrades.getColumns().clear();

            String sqlCount = String.format(
                    "SELECT c.nome AS nome FROM criterio_periodo cp " +
                    "JOIN criterio c ON cp.criterio_id = c.id WHERE cp.periodo_id = '%d'", selectedPeriodId);
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            ObservableList<TableColumn<AverageGradeModel, Double>> columns = FXCollections.observableArrayList();

            TableColumn<AverageGradeModel, String> nomeColumn = new TableColumn<>("Nome");
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            int nomeColumnWidth = 100;
            nomeColumn.setPrefWidth(nomeColumnWidth);
            tableAverageGrades.getColumns().add(nomeColumn);

            while (resultSet.next()) {
                String criterioNome = resultSet.getString("nome");

                TableColumn<AverageGradeModel, Double> column = new TableColumn<>(criterioNome);
                columns.add(column);
            }
            tableAverageGrades.getColumns().addAll(columns);

            double larguraTabela = 580;
            double larguraRestante = larguraTabela - nomeColumnWidth;
            double larguraPorColuna = larguraRestante / columns.size();

            for (TableColumn<AverageGradeModel, Double> column : columns) {
                String criterioNome = column.getText();
                column.setCellValueFactory(cellData ->
                        new SimpleDoubleProperty(cellData.getValue().getAverage(criterioNome)).asObject()
                );
                column.setPrefWidth(larguraPorColuna);
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

    private void fetchGrades() {
        AverageGradeDAO averageGradeDAO = new AverageGradeDAO();
        Map<String, AverageGradeModel> studentsMap = averageGradeDAO.fetchAverages(selectedTeamId, selectedPeriodId, selectedSprintId);
        ObservableList<AverageGradeModel> data = FXCollections.observableArrayList(studentsMap.values());
        tableAverageGrades.setItems(data);
    }
}
