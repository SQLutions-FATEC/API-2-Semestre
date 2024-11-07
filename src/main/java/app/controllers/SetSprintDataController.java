package app.controllers;

import app.DAOs.SprintDAO;
import app.helpers.Utils;
import app.models.SprintModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SetSprintDataController {

    @FXML
    private TableView<SprintModel> tableData;
    @FXML
    private TableColumn<SprintModel, String> colSprint;
    @FXML
    private TableColumn<SprintModel, String> colStartDate;
    @FXML
    private TableColumn<SprintModel, String> colEndDate;

    private SprintDAO sprintDAO;

    public void initialize() {
        sprintDAO = new SprintDAO();
        configureTableColumns();
        loadSprints();
    }

    private void configureTableColumns() {
        colSprint.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }

    private void loadSprints() {
        int selectedPeriodId = 1; // Ajuste conforme o período que deseja consultar
        ObservableList<SprintModel> sprints = sprintDAO.selectSprints(selectedPeriodId);
        tableData.setItems(sprints);
    }

    @FXML
    void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }
}
