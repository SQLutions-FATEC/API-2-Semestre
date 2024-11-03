package app.controllers;

import app.DAOs.CriteriaDAO;
import app.DAOs.PeriodDAO;
import app.helpers.DatabaseConnection;
import app.helpers.Utils;
import app.models.CriteriaModel;
import app.models.PeriodModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class CriteriaController implements Initializable  {
    protected Scene scene;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

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

        PeriodDAO periodDAO = new PeriodDAO();
        periodList = periodDAO.selectPeriods();

        for (PeriodModel period : periodList) {
            String periodName = String.format("%dº semestre - %d", period.getSemester(), period.getYear());
            periodOptionsList.add(periodName);
            periodMap.put(periodName, period);
        }

        periodChoiceBox.getItems().addAll(periodOptionsList);
        periodChoiceBox.setValue(String.format("%dº semestre - %d", periodList.getFirst().getSemester(), periodList.getFirst().getYear()));
    }

    private void fetchCriterias(int periodId) {
        CriteriaDAO criteriaDAO = new CriteriaDAO();
        ObservableList<CriteriaModel> criteriaList = criteriaDAO.selectPeriodCriterias(periodId);

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
        int selectedPeriodId = (selectedPeriod != null) ? selectedPeriod.getId() : 0;
        currentPeriod = period;
        fetchCriterias(selectedPeriodId);
    }

    @FXML
    private void addCriteria() {
        String name = criteriaName.getText().trim();
        String description = criteriaDescription.getText().trim();

        if (!Utils.isOnlyLetters(name)) {
            System.out.println("Erro: O critério deve conter apenas letras.");
            return;
        }
        if (!Utils.isOnlyLetters(description)) {
            System.out.println("Erro: O critério deve conter apenas letras.");
            return;
        }

        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlInsert = "INSERT INTO criterio (nome, descricao) VALUES (?, ?)";
            statement = connection.prepareStatement(sqlInsert);
            statement.setString(1, name);
            statement.setString(2, description);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Critério adicionado com sucesso!");
                fetchCriterias(selectedPeriodId);
                criteriaName.clear();
                criteriaDescription.clear();
            } else {
                System.out.println("Falha ao adicionar o critério.");
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL: " + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
        Utils.setAlert("INFORMATION", "Adição de critério", "O critério foi adicionado com sucesso");
    }

    @FXML
    private void addCriteriasToSemester() {
        ObservableList<CriteriaModel> criteriaList = tableCriteria.getItems();
        if (criteriaList.isEmpty()) {
            System.out.println("Nenhum critério disponível na tabela.");
            return;
        }
        int[] curPeriod;
        try {
            curPeriod = Utils.getPeriodFromFilter(currentPeriod);
        } catch (IllegalArgumentException error) {
            throw error;
        }
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlSelectPeriodoId = String.format("SELECT id FROM periodo WHERE semestre = '%d' AND ano = '%d'", curPeriod[0], curPeriod[1]);
            statement = connection.prepareStatement(sqlSelectPeriodoId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int periodId = resultSet.getInt("id");
                String sqlCheckExistence = "SELECT COUNT(*) FROM criterio_periodo WHERE criterio_id = ? AND periodo_id = ?";
                String sqlInsertOrUpdate = "INSERT INTO criterio_periodo (criterio_id, periodo_id, deleted_at) VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE deleted_at = ?";

                for (CriteriaModel criteria : criteriaList) {
                    int criteriaId = criteria.getId();
                    boolean isDeleted = criteria.getDeletedAt() == null;

                    PreparedStatement checkStatement = connection.prepareStatement(sqlCheckExistence);
                    checkStatement.setInt(1, criteriaId);
                    checkStatement.setInt(2, periodId);
                    ResultSet checkResultSet = checkStatement.executeQuery();

                    if (checkResultSet.next() && checkResultSet.getInt(1) > 0) {
                        PreparedStatement updateStatement = connection.prepareStatement(sqlInsertOrUpdate);
                        updateStatement.setInt(1, criteriaId);
                        updateStatement.setInt(2, periodId);
                        updateStatement.setObject(3, isDeleted ? LocalDateTime.now() : null);
                        updateStatement.setObject(4, isDeleted ? LocalDateTime.now() : null);
                        updateStatement.executeUpdate();
                        updateStatement.close();
                    } else {
                        PreparedStatement insertStatement = connection.prepareStatement(sqlInsertOrUpdate);
                        insertStatement.setInt(1, criteriaId);
                        insertStatement.setInt(2, periodId);
                        insertStatement.setObject(3, isDeleted ? LocalDateTime.now() : null);
                        insertStatement.setObject(4, isDeleted ? LocalDateTime.now() : null);
                        insertStatement.executeUpdate();
                        insertStatement.close();
                    }

                    checkStatement.close();
                }

                System.out.println("Critérios associados ao período com sucesso.");
            } else {
                System.out.println("Período não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao associar critérios ao período: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
        Utils.setAlert("INFORMATION", "Seleção de critérios", "Os critérios foram associados com sucesso");
    }

}
