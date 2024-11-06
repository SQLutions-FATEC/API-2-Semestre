package app.controllers;

import app.DAOs.CriteriaDAO;
import app.DAOs.PeriodDAO;
import app.helpers.Utils;
import app.models.CriteriaModel;
import app.models.PeriodModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class CriteriaController implements Initializable  {
    @FXML
    public ChoiceBox<String> periodChoiceBox;
    @FXML
    public TextField criteriaName;
    @FXML
    public TextArea criteriaDescription;
    @FXML
    public TableView<CriteriaModel> tableCriteria;
    @FXML
    public TableColumn<CriteriaModel, Boolean> colCheckbox;
    @FXML
    public TableColumn<CriteriaModel, String> colName;
    @FXML
    public TableColumn<CriteriaModel, String> colDescription;

    String currentPeriod = null;
    int selectedPeriodId = 0;

    ObservableList<PeriodModel> periodList = FXCollections.observableArrayList();
    Map<String, PeriodModel> periodMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        criteriaName.setPromptText("Adicione um critério");
        criteriaDescription.setPromptText("Descreva o critério");
        periodChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handlePeriodListSelectionChange(newValue);
        });
        fetchPeriods();
    }

    @FXML
    public void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }

    private void fetchPeriods() {
        ArrayList<String> periodOptionsList = new ArrayList<>();
        String[] currentPeriod = Utils.getCurrentSemesterAndYear();

        PeriodDAO periodDAO = new PeriodDAO();
        periodList = periodDAO.selectPeriods();

        for (PeriodModel period : periodList) {
            String periodName = String.format("%dº semestre - %d", period.getSemester(), period.getYear());
            periodOptionsList.add(periodName);
            periodMap.put(periodName, period);
        }

        periodChoiceBox.getItems().addAll(periodOptionsList);
        periodChoiceBox.setValue(currentPeriod[1] + " - " + currentPeriod[0]);
    }

    private void fetchCriterias(int periodId) {
        CriteriaDAO criteriaDAO = new CriteriaDAO();
        ObservableList<CriteriaModel> criteriaList = criteriaDAO.selectCriteriasByPeriod(periodId);

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        colCheckbox.setCellValueFactory(cellData -> cellData.getValue().isDeletedProperty());
        colCheckbox.setCellFactory(column -> new CheckBoxTableCell<CriteriaModel, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item);

                    checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                        CriteriaModel criteriaModel = getTableView().getItems().get(getIndex());
                        if (newValue) {
                            criteriaModel.setDeletedAt(LocalDateTime.now());
                        } else {
                            criteriaModel.clearDeletedAt();
                        }
                    });

                    setGraphic(checkBox);
                }
            }
        });

        tableCriteria.setItems(criteriaList);
    }

    private void handlePeriodListSelectionChange(String period) {
        PeriodModel selectedPeriod = periodMap.getOrDefault(period, null);
        selectedPeriodId = (selectedPeriod != null) ? selectedPeriod.getId() : 0;
        currentPeriod = period;
        fetchCriterias(selectedPeriodId);
    }

    @FXML
    private void addCriteria() {
        String name = criteriaName.getText().trim();
        String description = criteriaDescription.getText().trim();

        if (!Utils.isOnlyLetters(name)) {
            Utils.setAlert("ERROR", "Adição de critério", "O critério deve conter apenas letras");
            return;
        }
        if (!Utils.isOnlyLetters(description)) {
            Utils.setAlert("ERROR", "Adição de critério", "A descrição deve conter apenas letras");
            return;
        }

        CriteriaDAO criteriaDAO = new CriteriaDAO();
        int criteriaId = criteriaDAO.createCriteria(name, description);

        if (criteriaId != 0) {
            Utils.setAlert("CONFIRMATION", "Adição de critério", "O critério foi adicionado com sucesso");
            fetchCriterias(selectedPeriodId);
            criteriaName.clear();
            criteriaDescription.clear();
        } else {
            Utils.setAlert("ERROR", "Adição de critério", "Falha na adição do critério");
        }
    }

    @FXML
    private void addCriteriasToSemester() {
        ObservableList<CriteriaModel> criteriaList = tableCriteria.getItems();
        if (criteriaList.isEmpty()) {
            Utils.setAlert("INFORMATION", "Seleção de critérios", "Nenhum critério disponível na tabela");
            return;
        }

        CriteriaDAO criteriaDAO = new CriteriaDAO();
        criteriaDAO.updateCriteriaToPeriod(selectedPeriodId, criteriaList);

        Utils.setAlert("CONFIRMATION", "Seleção de critérios", "Os critérios foram associados com sucesso");
    }
}
