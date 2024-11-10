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
    @FXML
    private TableColumn<EvaluationModel, String> evaluatorColumn;
    @FXML
    private TableColumn<EvaluationModel, Integer> evaluatedColumn;
    @FXML
    private TableColumn<EvaluationModel, String> criteriaColumn;
    @FXML
    private TableColumn<EvaluationModel, String> sprintDescriptionColumn;

    private ObservableList<EvaluationModel> evaluationData = FXCollections.observableArrayList();

    public void initialize() {
        // Configuração das colunas
        evaluatorColumn.setCellValueFactory(new PropertyValueFactory<>("evaluatorName"));
        evaluatedColumn.setCellValueFactory(new PropertyValueFactory<>("evaluatedStudentId"));
        criteriaColumn.setCellValueFactory(new PropertyValueFactory<>("criteria"));
        sprintDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("sprintDescription"));

        // Carregar dados do banco de dados
        loadPeerEvaluations(1); // Exemplo: passando o sprintId = 1
    }

    private void loadPeerEvaluations(int sprintId) {
        PeerEvaluationDAO dao = new PeerEvaluationDAO();
        evaluationData.clear();
        evaluationData.addAll(dao.fetchPeerEvaluations(sprintId));
        evaluationTable.setItems(evaluationData);
    }
}
