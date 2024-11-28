package app.controllers;

import app.DAOs.SprintDAO;
import app.helpers.Utils;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SetSprintDataController implements Initializable {

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


    public void initialize(URL url, ResourceBundle resourceBundle) {

        descricaoComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8));
        descricaoComboBox.setPromptText("Selecione uma sprint:");

        colSprint.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colStartDate.setCellFactory(column -> new TableCell<SprintModel, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(Utils.formatDate(item.toLocalDate()));
                }
            }
        });

        colEndDate.setCellFactory(column -> new TableCell<SprintModel, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(Utils.formatDate(item.toLocalDate()));
                }
            }
        });

        loadLast8Sprints();
    }

    private void loadLast8Sprints() {
        ObservableList<SprintModel> sprints = sprintDAO.selectLast8Sprints();
        tableData.setItems(sprints);
    }

    @FXML
    private void createSprint(ActionEvent event) {
        Integer descricaoNumber = descricaoComboBox.getValue();
        Date dataInicio = dataInicioPicker.getValue() != null ? Date.valueOf(dataInicioPicker.getValue()) : null;
        Date dataFim = dataFimPicker.getValue() != null ? Date.valueOf(dataFimPicker.getValue()) : null;

        if (descricaoNumber == null || dataInicio == null || dataFim == null) {
            Utils.setAlert("WARNING", "AVISO", "Por favor, preencha todos os campos.");
            return;
        }

        String descricao = "Sprint " + descricaoNumber;

        try {
            boolean success = sprintDAO.createSprint(descricao, dataInicio, dataFim);
            if (success) {
                Utils.setAlert("INFORMATION", "Criação de Sprint", "Sprint criada com sucesso!");
                loadLast8Sprints();
                clearFields();
            } else {
                Utils.setAlert("WARNING", "Data inserida já em uso", "Escolha outro intervalo de datas.");
            }
        } catch (SQLException e) {
            Utils.setAlert("ERROR", "Erro no sistema", "Erro ao criar sprint: " + e.getMessage());
        }
    }

    private void clearFields() {
        descricaoComboBox.setValue(null);
        dataInicioPicker.setValue(null);
        dataFimPicker.setValue(null);
    }

    @FXML
    private void deleteSprint(){




                ObservableList<SprintModel> selectedItems = tableData.getSelectionModel().getSelectedItems();
            for (SprintModel sprint : selectedItems) {
                System.out.println(sprint);
            }

    }


/*    public String toString(){

        String informacoes =
        return informacoes;
    };*/

    @FXML
    private void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }
}
