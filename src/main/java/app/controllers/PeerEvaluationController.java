package app.controllers;

import app.DAOs.SprintDAO;
import app.helpers.Utils;
import app.models.EvaluationModel;
import app.DAOs.PeerEvaluationDAO;
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

public class PeerEvaluationController {
    @FXML
    private TableView<EvaluationModel> evaluationTable;
    @FXML
    private ChoiceBox<String> previousEvaluationsComboBox;

    private ObservableList<EvaluationModel> evaluationData = FXCollections.observableArrayList();
    public String userEmail = "";

    public void passData(String email) {
        userEmail = email;
        fetchSprint();
//        loadPeerEvaluations(1); // Exemplo: passando o sprintId = 1
    }

    public void fetchSprint() {
        ArrayList<String> sprintOptionsList = new ArrayList<>();

        SprintDAO sprintDAO = new SprintDAO();
        ObservableList<SprintModel> sprintList = sprintDAO.selectSprints(selectedPeriodId);

        for (SprintModel sprint : sprintList) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedStartDate = dateFormat.format(SprintModel.getStartDate());
            String formattedEndDate = dateFormat.format(SprintModel.getEndDate());

            String sprintDescription = sprint.getDescription() + ": (" + formattedStartDate + " - " + formattedEndDate + ")";
            sprintOptionsList.add(sprintDescription);
        }

        previousEvaluationsComboBox.getItems().addAll(sprintOptionsList);
        String currentSprint = Utils.getCurrentSprint(sprintOptionsList);
        if (currentSprint != null) {
            previousEvaluationsComboBox.setValue(currentSprint);
        } else {
            if (!sprintOptionsList.isEmpty()) {
                previousEvaluationsComboBox.setValue(sprintOptionsList.get(0));
            }
        }
    }

    private void loadPeerEvaluations(int sprintId) {
        PeerEvaluationDAO dao = new PeerEvaluationDAO();
        evaluationData.clear();
        evaluationData.addAll(dao.fetchPeerEvaluations(sprintId));
        evaluationTable.setItems(evaluationData);
    }
}
