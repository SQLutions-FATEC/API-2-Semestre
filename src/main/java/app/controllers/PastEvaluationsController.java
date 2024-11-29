package app.controllers;

import app.DAOs.PastEvaluationDAO;
import app.DAOs.PeriodDAO;
import app.DAOs.SprintDAO;
import app.models.CriteriaModel;
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
    @FXML
    public TableColumn<PastEvaluationModel, String> evaluator;
    @FXML
    public TableColumn<PastEvaluationModel, String> evaluated;
    @FXML
    public TableColumn<PastEvaluationModel, String> criteria;
    @FXML
    public TableColumn<PastEvaluationModel, String> description;
    @FXML
    public TableColumn<PastEvaluationModel, Integer> grade;

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
            String formattedStartDate = dateFormat.format(sprint.getStartDate());
            String formattedEndDate = dateFormat.format(sprint.getEndDate());

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
        evaluator.setCellValueFactory(new PropertyValueFactory<>("evaluatorStudentName"));
        evaluated.setCellValueFactory(new PropertyValueFactory<>("evaluatedStudentName"));
        criteria.setCellValueFactory(new PropertyValueFactory<>("criteriaName"));
        description.setCellValueFactory(new PropertyValueFactory<>("sprintDescription"));
        grade.setCellValueFactory(new PropertyValueFactory<>("nota"));
    }

    private void loadPeerEvaluations(int sprintId) {
        PastEvaluationDAO pastEvaluationDAO = new PastEvaluationDAO();
        ObservableList<PastEvaluationModel> evaluationData = pastEvaluationDAO.selectPastEvaluations(teamId, sprintId);

        tableEvaluationGrades.setItems(evaluationData);
    }
}
