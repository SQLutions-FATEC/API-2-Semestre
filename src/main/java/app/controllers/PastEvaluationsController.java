package app.controllers;

import app.DAOs.PeriodDAO;
import app.DAOs.SprintDAO;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PastEvaluationsController {
    @FXML
    public ChoiceBox<String> sprintChoiceBox;

    int teamId = 0;
    int periodId = 0;
    ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();
    ArrayList<String> sprintOptionsList = new ArrayList<>();

    public void passData(int userTeamId) {
        teamId = userTeamId;

        fetchPeriod();
        fetchSprint();
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
        }

        sprintChoiceBox.getItems().addAll(sprintOptionsList);
        sprintChoiceBox.setValue(sprintOptionsList.get(0));
    }
}
