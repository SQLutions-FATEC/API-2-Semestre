package app.controllers;

import app.models.EvaluationModel;
import app.DAOs.PeerEvaluationDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PeerEvaluationController {
    @FXML
    private TableView<EvaluationModel> evaluationTable;

    private ObservableList<EvaluationModel> evaluationData = FXCollections.observableArrayList();
    public String userEmail = "";

    public void passData(String email) {
        userEmail = email;
        loadPeerEvaluations(1); // Exemplo: passando o sprintId = 1
    }

    private void loadPeerEvaluations(int sprintId) {
        PeerEvaluationDAO dao = new PeerEvaluationDAO();
        evaluationData.clear();
        evaluationData.addAll(dao.fetchPeerEvaluations(sprintId));
        evaluationTable.setItems(evaluationData);
    }
}
