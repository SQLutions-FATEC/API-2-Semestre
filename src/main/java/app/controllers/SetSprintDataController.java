package app.controllers;

import app.DAOs.SprintDAO;
import app.helpers.Utils;
import app.models.SprintModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class SetSprintDataController {

    @FXML
    private TableView<SprintModel> tableData;

    @FXML
    private TableColumn<SprintModel, String> colSprint;

    @FXML
    private TableColumn<SprintModel, Date> colStartDate;

    @FXML
    private TableColumn<SprintModel, Date> colEndDate;

    @FXML
    private TextField descricaoField;

    @FXML
    private DatePicker dataInicioPicker;

    @FXML
    private DatePicker dataFimPicker;

    private final SprintDAO sprintDAO = new SprintDAO();

    @FXML
    private void initialize() {
        colSprint.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        loadLast4Sprints();
    }

    private void loadLast4Sprints() {
        ObservableList<SprintModel> sprints = sprintDAO.selectLast4Sprints();
        tableData.setItems(sprints);
    }

    @FXML
    private void createSprint(ActionEvent event) {
        String descricao = descricaoField.getText();
        Date dataInicio = dataInicioPicker.getValue() != null ? Date.valueOf(dataInicioPicker.getValue()) : null;
        Date dataFim = dataFimPicker.getValue() != null ? Date.valueOf(dataFimPicker.getValue()) : null;
        if (descricao == null || descricao.isEmpty() || dataInicio == null || dataFim == null) {
            Utils.setAlert("Erro", "Por favor, preencha todos os campos.", "");
            return;
        }
        boolean success = sprintDAO.createSprint(descricao, dataInicio, dataFim);
        if (success) {
            Utils.setAlert("INFORMATION", "Criação de Sprint", "Sprint criada com sucesso!");
            loadLast4Sprints();
            clearFields();
        } else {
            Utils.setAlert("DANGER", "Erro", "Erro ao criar a sprint.");
        }
    }

    private void clearFields() {
        descricaoField.clear();
        dataInicioPicker.setValue(null);
        dataFimPicker.setValue(null);
    }

    @FXML
    private void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }
}
