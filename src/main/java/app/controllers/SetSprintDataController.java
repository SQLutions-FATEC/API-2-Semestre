package app.controllers;

import app.DAOs.SprintDAO;
import app.helpers.Utils;
import app.models.SprintModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

public class SetSprintDataController {

    @FXML
    private TableView<SprintModel> tableData;

    @FXML
    private TableColumn<SprintModel, String> colSprint;

    @FXML
    private TableColumn<SprintModel, Date> colStartDate;

    @FXML
    private TableColumn<SprintModel, Date> colEndDate;

    private SprintDAO sprintDAO = new SprintDAO();

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
    private void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }
}
