package app.controllers;

import app.DAOs.PeriodDAO;
import app.DAOs.ScoreDAO;
import app.DAOs.SprintDAO;
import app.DAOs.TeamDAO;
import app.helpers.Utils;
import app.models.SprintModel;
import app.models.TeamModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SetScoreController implements Initializable {
    @FXML
    public ChoiceBox<String> teamChoiceBox;
    @FXML
    public TextField maxScore;

    Integer selectedTeamId;
    Integer sprintId;
    Integer periodId;
    private final Map<String, Integer> teamIdMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maxScore.setPromptText("Adicione um valor");
        teamChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleTeamListSelectionChange(newValue);
        });
        fetchPeriod();
        fetchSprint();
        fetchTeams();
    }

    private void handleTeamListSelectionChange(String team) {
        if (team != null) {
            selectedTeamId = teamIdMap.get(team);
        }
    }

    public void fetchPeriod() {
        PeriodDAO periodDAO = new PeriodDAO();
        periodId = periodDAO.selectCurrentPeriodId();
    }

    public void fetchSprint() {
        SprintDAO sprintDAO = new SprintDAO();
        SprintModel sprint = sprintDAO.selectPastSprint();
        sprintId = sprint.getId();
    }

    public void fetchTeams() {
        teamChoiceBox.getItems().clear();

        ArrayList<String> teamsOptionsList = new ArrayList<>();

        TeamDAO teamDAO = new TeamDAO();
        ObservableList<TeamModel> teamList = teamDAO.selectTeamsWithoutScoreByPeriod(periodId, sprintId);

        if (teamList.isEmpty()) {
            Utils.setAlert("INFORMATION", "Listagem de equipes", "Todas as equipes já possuem pontuação para a sprint selecionada");
        }

        for (TeamModel team : teamList) {
            String teamName = team.getName();
            teamsOptionsList.add(teamName);
            teamIdMap.put(teamName, team.getId());
        }

        teamChoiceBox.getItems().addAll(teamsOptionsList);
        if (!teamsOptionsList.isEmpty()) {
            teamChoiceBox.setValue(teamsOptionsList.get(0));
        }
    }

    @FXML
    private void addMaxScore() {
        int score = maxScore.getText().trim().isEmpty() ? 0 : Integer.parseInt(maxScore.getText().trim());
        String selectedTeam = teamChoiceBox.getValue();

        if (selectedTeam == null || selectedTeam.isEmpty()) {
            Utils.setAlert("ERROR", "Adição de pontuação", "Nenhuma equipe foi selecionada");
            return;
        }
        if (score == 0) {
            Utils.setAlert("ERROR", "Adição de pontuação", "A pontuação deve ser maior do que zero");
            return;
        }

        ScoreDAO setScoreDAO = new ScoreDAO();
        int scoreId = setScoreDAO.createScore(score, sprintId, selectedTeamId);

        if (scoreId != 0) {
            Utils.setAlert("CONFIRMATION", "Adição de pontuação", "A pontuação foi adicionada com sucesso");
            maxScore.clear();
            fetchTeams();
        } else {
            Utils.setAlert("ERROR", "Adição de pontuação", "Falha na adição de pontuação");
        }
    }

    @FXML
    public void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }
}