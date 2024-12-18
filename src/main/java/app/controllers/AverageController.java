package app.controllers;

import app.DAOs.CriteriaDAO;
import app.DAOs.SprintDAO;
import app.DAOs.GradeDAO;
import app.helpers.Utils;
import app.models.CriteriaModel;
import app.models.EquipeModel;
import app.models.AverageGradeModel;
import app.models.SprintModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AverageController {
    @FXML
    public ChoiceBox<String> sprintChoiceBox;
    @FXML
    public TableView<AverageGradeModel> tableAverageGrades = new TableView<>();
    @FXML
    public Button reportButton;

    String currentSprint;
    Integer selectedSprintId;
    String selectedSprintDescription;
    Integer selectedPeriodId;
    Integer selectedTeamId;
    String selectedTeamName;

    private final ObservableList<EquipeModel> studentList = FXCollections.observableArrayList();
    private final Map<String, Integer> sprintIdMap = new HashMap<>();
    ArrayList<String> sprintOptionsList = new ArrayList<>();

    public void passData(String teamName, int teamId, int periodId) {
        selectedPeriodId = periodId;
        selectedTeamId = teamId;
        selectedTeamName = teamName;
        sprintChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleSprintListSelectionChange(newValue);
        });
        fetchSprint();
    }

    private void handleSprintListSelectionChange(String sprint) {
        currentSprint = sprint;
        selectedSprintId = sprintIdMap.get(sprint);
        selectedSprintDescription = sprint;
        studentList.clear();
        fetchCriterias();
        fetchGrades();
    }

    private void fetchSprint() {
        SprintDAO sprintDAO = new SprintDAO();
        ObservableList<SprintModel> sprintList = sprintDAO.selectSprints(selectedPeriodId);

        if (sprintList.isEmpty()) {
            reportButton.setDisable(true);
            return;
        }

        for (SprintModel sprint : sprintList) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedStartDate = dateFormat.format(sprint.getStartDate());
            String formattedEndDate = dateFormat.format(sprint.getEndDate());

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
        ObservableList<TableColumn<AverageGradeModel, Integer>> columns = FXCollections.observableArrayList();

        TableColumn<AverageGradeModel, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        int nameColumnWidth = 100;
        nomeColumn.setPrefWidth(nameColumnWidth);
        tableAverageGrades.getColumns().add(nomeColumn);

        for (CriteriaModel criteria : criteriaList) {
            String criteriaName = criteria.getName();
            TableColumn<AverageGradeModel, Integer> column = new TableColumn<>(criteriaName);
            columns.add(column);
        }
        tableAverageGrades.getColumns().addAll(columns);

        double tableWidth = 580;
        double widthLeft = tableWidth - nameColumnWidth;
        double widthPerColumn = widthLeft / columns.size();

        for (TableColumn<AverageGradeModel, Integer> column : columns) {
            String name = column.getText();
            column.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getAverage(name)).asObject()
            );
            column.setPrefWidth(widthPerColumn);
        }
    }

    private void fetchGrades() {
        GradeDAO averageGradeDAO = new GradeDAO();
        Map<String, AverageGradeModel> studentsMap = averageGradeDAO.selectAverages(selectedTeamId, selectedPeriodId, selectedSprintId);
        ObservableList<AverageGradeModel> data = FXCollections.observableArrayList(studentsMap.values());
        if (data.isEmpty()) {
            reportButton.setDisable(true);
        } else {
            reportButton.setDisable(false);
        }
        tableAverageGrades.setItems(data);
    }

    @FXML
    public void gerarCsvButton() {
        String caminhoArquivo = Utils.getDownloadsPath() + "\\relatorio.csv";

        try {
            Utils.createCsv(caminhoArquivo, selectedTeamName, selectedTeamId, selectedPeriodId, selectedSprintDescription, selectedSprintId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
