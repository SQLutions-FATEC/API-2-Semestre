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
    ObservableList<PastEvaluationModel> evaluationData = FXCollections.observableArrayList();
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
            System.out.println("Start Date: " + SprintModel.getStartDate());
            System.out.println("End Date: " + SprintModel.getEndDate());


            String sprintDescription = sprint.getDescription() + ": (" + formattedStartDate + " - " + formattedEndDate + ")";
            sprintOptionsList.add(sprintDescription);
            sprintIdMap.put(sprintDescription, sprint.getId());
            System.out.println("Sprint Map: " + sprintIdMap);
            System.out.println("Sprint selecionada: " + sprint);

        }

        sprintChoiceBox.getItems().addAll(sprintOptionsList);
        sprintChoiceBox.setValue(sprintOptionsList.get(0));
        sprintId = sprintIdMap.get(sprintOptionsList.get(0));
        loadPeerEvaluations(sprintId);
        System.out.println("Sprint Map: " + sprintIdMap);
        System.out.println("Sprint selecionada: " + sprintId);

    }

    private void handleSprintListSelectionChange(String sprint) {
        sprintId = sprintIdMap.get(sprint);
        loadPeerEvaluations(sprintId);
        System.out.println("Sprint Map: " + sprintIdMap);
        System.out.println("Sprint selecionada: " + sprint);

    }

    private void createTable() {
        TableColumn<PastEvaluationModel, String> evaluatorColumn = new TableColumn<>("Avaliador");
        TableColumn<PastEvaluationModel, String> criteriaColumn = new TableColumn<>("Critério");
        TableColumn<PastEvaluationModel, String> sprintColumn = new TableColumn<>("Sprint");
        TableColumn<PastEvaluationModel, Integer> evaluatedColumn = new TableColumn<>("Avaliado");
        TableColumn<PastEvaluationModel, Integer> notaColumn = new TableColumn<>("Nota");


        evaluatorColumn.setCellValueFactory(new PropertyValueFactory<>("evaluatorName"));
        criteriaColumn.setCellValueFactory(new PropertyValueFactory<>("criteria"));
        sprintColumn.setCellValueFactory(new PropertyValueFactory<>("sprintDescription"));
        evaluatedColumn.setCellValueFactory(new PropertyValueFactory<>("evaluatedStudentId"));
        notaColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));

        tableEvaluationGrades.getColumns().add(evaluatorColumn);
        tableEvaluationGrades.getColumns().add(criteriaColumn);
        tableEvaluationGrades.getColumns().add(sprintColumn);
        tableEvaluationGrades.getColumns().add(evaluatedColumn);
        tableEvaluationGrades.getColumns().add(notaColumn);
    }

    private void loadPeerEvaluations(int sprintId) {
        evaluationData.clear();
        PastEvaluationDAO peerEvaluationDAO = new PastEvaluationDAO();
        System.out.println("Carregando avaliações para a sprint ID: " + sprintId);

        evaluationData.addAll(peerEvaluationDAO.fetchPeerEvaluations(sprintId));
        System.out.println("Avaliações carregadas: " + evaluationData.size());

        for (PastEvaluationModel evaluation : evaluationData) {
            System.out.println(evaluation.getEvaluatorName() + ", " +
                    evaluation.getEvaluatedStudentId() + ", " +
                    evaluation.getCriteria() + ", " +
                    evaluation.getSprintDescription() + ", " +
                    evaluation.getNota());
        }

        tableEvaluationGrades.setItems(evaluationData);
    }
}
