package app.controllers;

import app.DAOs.PeriodDAO;
import app.DAOs.SprintDAO;
import app.DAOs.TeamDAO;
import app.helpers.Utils;
import app.interfaces.ScreenController;
import app.models.PeriodModel;
import app.models.SprintModel;
import app.models.TeamModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.*;

public class ProfessorController implements ScreenController {
    @FXML
    public TableView<TeamModel> teamTable;
    @FXML
    public TableColumn<TeamModel, String> colName;
    @FXML
    public TableColumn<TeamModel, String> colGithub;
    @FXML
    public TableColumn<TeamModel, Void> colVisualize;
    @FXML
    public Label title;
    @FXML
    public Label description;
    @FXML
    public ChoiceBox<String> periodChoiceBox;

    String[] period = Utils.getCurrentSemesterAndYear();
    Integer selectedPeriodId;
    ObservableList<TeamModel> teamList = FXCollections.observableArrayList();
    Map<String, PeriodModel> periodMap = new HashMap<>();

    @Override
    public void initData(Object data) {
        periodChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handlePeriodListSelectionChange(newValue);
        });
        fetchPeriods();
        fetchCurrentSprint();
    }

    private void fetchPeriods() {
        ArrayList<String> periodOptionsList = new ArrayList<>();

        PeriodDAO periodDAO = new PeriodDAO();
        ObservableList<PeriodModel> periodList = periodDAO.selectPeriods();

        for (PeriodModel period : periodList) {
            String periodName = String.format("%dº semestre - %d", period.getSemester(), period.getYear());
            periodOptionsList.add(periodName);
            periodMap.put(periodName, period);
        }

        periodChoiceBox.getItems().addAll(periodOptionsList);
        periodChoiceBox.setValue(period[1] + " - " + period[0]);
    }

    private void fetchCurrentSprint() {
        Utils.getCurrentPeriodAndSprint();
    }

    public void fetchTeams() {
        TeamDAO teamDAO = new TeamDAO();
        teamList = teamDAO.selectTeamsByPeriod(selectedPeriodId);

        if (!teamList.isEmpty()) {
            title.setText("Lista de Equipes");
            description.setText("Equipes cadastradas:");
        }

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGithub.setCellValueFactory(new PropertyValueFactory<>("github"));

        colVisualize.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button btn = new Button("Visualizar");
                    btn.setOnAction((ActionEvent event) -> {
                        TeamModel equipe = getTableView().getItems().get(getIndex());
                        openPopup(equipe.getName(), equipe.getId(), selectedPeriodId);
                    });
                    setGraphic(btn);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });
        teamTable.setItems(teamList);
    }

    private void openPopup(String teamName, int teamId, int periodId) {
        Utils.setPopup("averageScreen", 400, 600, controller -> {
            if (controller instanceof AverageController) {
                ((AverageController) controller).passData(teamName, teamId, periodId);
            }
        });
    }

    private void handlePeriodListSelectionChange(String period) {
        PeriodModel selectedPeriod = periodMap.getOrDefault(period, null);
        selectedPeriodId = (selectedPeriod != null) ? selectedPeriod.getId() : 0;
        teamList.clear();
        fetchTeams();
    }

    @FXML
    public void goToLoginScreen(ActionEvent event) {
        Utils.setScreen(event, "loginScreen");
    }

    @FXML
    public void goToAddStudentScreen(ActionEvent event) {
        Utils.setScreen(event, "addStudentScreen");
    }

    @FXML
    public void goToCriteriaScreen(ActionEvent event) {
        Utils.setScreen(event, "criteriaScreen");
    }

    @FXML
    public void goToEditStudentScreen(ActionEvent event) {
        Utils.setScreen(event, "editStudentScreen");
    }

    @FXML
    public void goToSetScoreScreen(ActionEvent event) {
        SprintDAO sprintDAO = new SprintDAO();
        SprintModel sprint = sprintDAO.selectPastSprint();

        if (sprint == null) {
            Utils.setAlert("WARNING", "Sprints", "Não há nenhuma sprint");
            return;
        }

        Date sprintEndDate = sprint.getEndDate();
        Date setScoreDeadlineDate = Utils.setDate(sprintEndDate, 7);
        Date currentDate = new Date();

        if (currentDate.after(setScoreDeadlineDate)) {
            Utils.setScreen(event, "outOfSetScorePeriodScreen");
        } else {
            Utils.setScreen(event, "setScore");
        }
    }

    @FXML
    public void goToSetSprintDataScreen(ActionEvent event) {
        Utils.setScreen(event, "setSprintDataScreen");
    }
}
