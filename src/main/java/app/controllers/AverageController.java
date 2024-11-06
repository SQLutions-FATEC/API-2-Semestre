package app.controllers;

import app.DAOs.CriteriaDAO;
import app.DAOs.SprintDAO;
import app.DAOs.AverageGradeDAO;
import app.helpers.Utils;
import app.models.CriteriaModel;
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

import java.text.SimpleDateFormat;
import java.util.*;

public class AverageController {
    @FXML
    public ChoiceBox<String> sprintChoiceBox;
    @FXML
    public TableView<AverageGradeModel> tableAverageGrades = new TableView<>();

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
        sprintChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
        SprintDAO sprintDAO = new SprintDAO();
        ObservableList<SprintModel> sprintList = sprintDAO.selectSprints(selectedPeriodId);

        for (SprintModel sprint : sprintList) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedStartDate = dateFormat.format(SprintModel.getStartDate());
            String formattedEndDate = dateFormat.format(SprintModel.getEndDate());

            String sprintDescription = sprint.getDescription() + ": (" + formattedStartDate + " - " + formattedEndDate + ")";
            sprintOptionsList.add(sprintDescription);
            sprintIdMap.put(sprintDescription, sprint.getId());
        }

        sprintChoiceBox.getItems().addAll(sprintOptionsList);
        String currentSprint = Utils.getCurrentSprint(sprintOptionsList);
        if (currentSprint != null) {
            sprintChoiceBox.setValue(currentSprint);
        } else {
            sprintChoiceBox.setValue(sprintOptionsList.get(0));
        }
    }

    private void fetchCriterias() {
        CriteriaDAO criteriaDAO = new CriteriaDAO();
        ObservableList<CriteriaModel> criteriaList = criteriaDAO.selectActiveCriteriasByPeriod(selectedPeriodId);

        tableAverageGrades.getColumns().clear();
        ObservableList<TableColumn<AverageGradeModel, Double>> columns = FXCollections.observableArrayList();

        TableColumn<AverageGradeModel, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        int nameColumnWidth = 100;
        nomeColumn.setPrefWidth(nameColumnWidth);
        tableAverageGrades.getColumns().add(nomeColumn);

        for (CriteriaModel criteria : criteriaList) {
            String criteriaName = criteria.getName();
            TableColumn<AverageGradeModel, Double> column = new TableColumn<>(criteriaName);
            columns.add(column);
        }
        tableAverageGrades.getColumns().addAll(columns);

        double tableWidth = 580;
        double widthLeft = tableWidth - nameColumnWidth;
        double widthPerColumn = widthLeft / columns.size();

        for (TableColumn<AverageGradeModel, Double> column : columns) {
            String name = column.getText();
            column.setCellValueFactory(cellData ->
                    new SimpleDoubleProperty(cellData.getValue().getAverage(name)).asObject()
            );
            column.setPrefWidth(widthPerColumn);
        }
    }

    private void fetchGrades() {
        AverageGradeDAO averageGradeDAO = new AverageGradeDAO();
        Map<String, AverageGradeModel> studentsMap = averageGradeDAO.fetchAverages(selectedTeamId, selectedPeriodId, selectedSprintId);
        ObservableList<AverageGradeModel> data = FXCollections.observableArrayList(studentsMap.values());
        tableAverageGrades.setItems(data);
    }
}
