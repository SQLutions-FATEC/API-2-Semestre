package app.controllers;

import app.DAOs.PeriodDAO;
import app.DAOs.SprintDAO;
import app.DAOs.TeamDAO;
import app.helpers.Utils;
import app.models.PeriodModel;
import app.models.SprintModel;
import app.models.TeamModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SetScoreController implements Initializable {
    @FXML
    public ChoiceBox<String> periodChoiceBox;
    @FXML
    public ChoiceBox<String> teamChoiceBox;
    @FXML
    public ChoiceBox<String> sprintChoiceBox;
    @FXML
    public TextField maxScore;

    String[] period = Utils.getCurrentSemesterAndYear();
    Integer selectedPeriodId;
    Integer selectedSprintId;
    Map<String, PeriodModel> periodMap = new HashMap<>();
    private final Map<String, Integer> sprintIdMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maxScore.setPromptText("Adicione um valor");
        periodChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handlePeriodListSelectionChange(newValue);
        });
        sprintChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleSprintListSelectionChange(newValue);
        });
        fetchPeriods();
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
        if (!periodOptionsList.isEmpty()) {
            periodChoiceBox.setValue(period[1] + " - " + period[0]);
        }
    }

    private void handlePeriodListSelectionChange(String period) {
        PeriodModel selectedPeriod = periodMap.getOrDefault(period, null);
        selectedPeriodId = (selectedPeriod != null) ? selectedPeriod.getId() : 0;
        fetchSprint();
    }

    private void handleSprintListSelectionChange(String sprint) {
        if (sprint != null) {
            selectedSprintId = sprintIdMap.get(sprint);
            fetchTeams();
        }
    }

    private void fetchSprint() {
        sprintChoiceBox.getItems().clear();

        ArrayList<String> sprintOptionsList = new ArrayList<>();

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
            if (!sprintOptionsList.isEmpty()) {
                sprintChoiceBox.setValue(sprintOptionsList.getFirst());
            }
        }
    }

    public void fetchTeams() {
        teamChoiceBox.getItems().clear();

        ArrayList<String> teamsOptionsList = new ArrayList<>();

        TeamDAO teamDAO = new TeamDAO();
        ObservableList<TeamModel> teamList = teamDAO.selectTeamsWithoutScoreByPeriod(selectedPeriodId, selectedSprintId);

        if (teamList.isEmpty()) {
            Utils.setAlert("INFORMATION", "Listagem de equipes", "Todas as equipes já possuem pontuação para a sprint selecionada");
        }

        for (TeamModel team : teamList) {
            String teamName = team.getName();
            teamsOptionsList.add(teamName);
        }

        teamChoiceBox.getItems().addAll(teamsOptionsList);
        if (!teamsOptionsList.isEmpty()) {
            teamChoiceBox.setValue(teamsOptionsList.getFirst());
        }
    }

    @FXML
    private void addMaxScore() {
        int score = maxScore.getText().trim().isEmpty() ? 0 : Integer.parseInt(maxScore.getText().trim());
        String selectedTeam = teamChoiceBox.getValue();

        if (selectedTeam.isEmpty()) {
            Utils.setAlert("ERROR", "Adição de pontuação", "Nenhuma equipe foi selecionada");
            return;
        }
        if (score == 0) {
            Utils.setAlert("ERROR", "Adição de pontuação", "A pontuação deve ser maior do que zero");
            return;
        }

//        CriteriaDAO criteriaDAO = new CriteriaDAO();
//        int criteriaId = criteriaDAO.createCriteria(name, description);
//
//        if (criteriaId != 0) {
//            Utils.setAlert("CONFIRMATION", "Adição de critério", "O critério foi adicionado com sucesso");
//            fetchCriterias(selectedPeriodId);
//            criteriaName.clear();
//            criteriaDescription.clear();
//        } else {
//            Utils.setAlert("ERROR", "Adição de critério", "Falha na adição do critério");
//        }
    }

    @FXML
    public void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }
}
