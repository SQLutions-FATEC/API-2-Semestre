package app.controllers;

import app.DAOs.PastEvaluationDAO;
import app.DAOs.PeriodDAO;
import app.DAOs.SprintDAO;
import app.models.PastEvaluationModel;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PastEvaluationsController {
    @FXML
    public ChoiceBox<String> sprintChoiceBox;
    @FXML
    TableView<PastEvaluationModel> tableEvaluationGrades;

    int teamId = 0;
    int periodId = 0;
    int sprintId = 0;
    ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();
    ArrayList<String> sprintOptionsList = new ArrayList<>();
    Map<String, Integer> sprintIdMap = new HashMap<>();

    public void passData(int userTeamId) {
        teamId = userTeamId;

        sprintChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleSprintListSelectionChange(newValue);
        });
        fetchPeriod();
        fetchSprint();

        createTable();
    }

    private void fetchPeriod() {
        PeriodDAO periodDAO = new PeriodDAO();
        periodId = periodDAO.selectCurrentPeriodId();
    }

    private void fetchSprint() {
        SprintDAO sprintDAO = new SprintDAO();
        sprintList = sprintDAO.selectSprints(periodId);

        for (SprintModel sprint : sprintList) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedStartDate = dateFormat.format(SprintModel.getStartDate());
            String formattedEndDate = dateFormat.format(SprintModel.getEndDate());

            String sprintDescription = sprint.getDescription() + ": (" + formattedStartDate + " - " + formattedEndDate + ")";
            sprintOptionsList.add(sprintDescription);
            sprintIdMap.put(sprintDescription, sprint.getId());
        }

        sprintChoiceBox.getItems().addAll(sprintOptionsList);
        sprintChoiceBox.setValue(sprintOptionsList.get(0));
        sprintId = sprintIdMap.get(sprintOptionsList.get(0));
        loadPeerEvaluations(sprintId);
    }

    private void handleSprintListSelectionChange(String sprint) {
        sprintId = sprintIdMap.get(sprint);
        loadPeerEvaluations(sprintId);
    }

    private void createTable() {
        TableColumn<PastEvaluationModel, String> evaluatorColumn = new TableColumn<>("Avaliador");
        TableColumn<PastEvaluationModel, String> evaluatedColumn = new TableColumn<>("Avaliado");
        TableColumn<PastEvaluationModel, String> criteriaColumn = new TableColumn<>("Critério");
        TableColumn<PastEvaluationModel, String> sprintColumn = new TableColumn<>("Sprint");
        TableColumn<PastEvaluationModel, Integer> notaColumn = new TableColumn<>("Nota");

        evaluatorColumn.setCellValueFactory(new PropertyValueFactory<>("evaluatorStudentName"));
        evaluatedColumn.setCellValueFactory(new PropertyValueFactory<>("evaluatedStudentName"));
        criteriaColumn.setCellValueFactory(new PropertyValueFactory<>("criteriaName"));
        sprintColumn.setCellValueFactory(new PropertyValueFactory<>("sprintDescription"));
        notaColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));

        tableEvaluationGrades.getColumns().add(evaluatorColumn);
        tableEvaluationGrades.getColumns().add(evaluatedColumn);
        tableEvaluationGrades.getColumns().add(criteriaColumn);
        tableEvaluationGrades.getColumns().add(sprintColumn);
        tableEvaluationGrades.getColumns().add(notaColumn);
    }

    private void loadPeerEvaluations(int sprintId) {
        PastEvaluationDAO pastEvaluationDAO = new PastEvaluationDAO();
        ObservableList<PastEvaluationModel> evaluationData = pastEvaluationDAO.selectPastEvaluations(teamId, sprintId);

        tableEvaluationGrades.setItems(evaluationData);
    }
}
