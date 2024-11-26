package app.controllers;

import app.DAOs.SprintDAO;
import app.helpers.Utils;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;

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
    private ComboBox<Integer> descricaoComboBox;

    @FXML
    private DatePicker dataInicioPicker;

    @FXML
    private DatePicker dataFimPicker;

    private final SprintDAO sprintDAO = new SprintDAO();

    @FXML
    private void initialize() {
        descricaoComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4));

        colSprint.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        loadLast4Sprints();
    }

    private void loadLast4Sprints() {
        ObservableList<SprintModel> sprints = sprintDAO.selectLast8Sprints();
        tableData.setItems(sprints);
    }

    @FXML
    private void createSprint(ActionEvent event) {
        Integer descricaoNumber = descricaoComboBox.getValue();
        Date dataInicio = dataInicioPicker.getValue() != null ? Date.valueOf(dataInicioPicker.getValue()) : null;
        Date dataFim = dataFimPicker.getValue() != null ? Date.valueOf(dataFimPicker.getValue()) : null;

        if (descricaoNumber == null || dataInicio == null || dataFim == null) {
            Utils.setAlert("INFORMATION", "Aviso", "Por favor, preencha todos os campos.");
            return;
        }

        String descricao = "Sprint " + descricaoNumber;

        try {
            boolean success = sprintDAO.createSprint(descricao, dataInicio, dataFim);
            if (success) {
                Utils.setAlert("INFORMATION", "Criação de Sprint", "Sprint criada com sucesso!");
                loadLast4Sprints();
                clearFields();
            } else {
                Utils.setAlert("INFORMATION", "Data inserida já em uso", "Escolha outro intervalo de datas.");
            }
        } catch (SQLException e) {
            Utils.setAlert("INFORMATION", "Erro no sistema", "Erro ao criar sprint: " + e.getMessage());
        }
    }

    private void clearFields() {
        descricaoComboBox.setValue(null);
        dataInicioPicker.setValue(null);
        dataFimPicker.setValue(null);
    }

    @FXML
    private void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }
}
