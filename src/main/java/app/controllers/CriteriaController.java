package app.controllers;

import app.helpers.DatabaseConnection;
import app.helpers.Utils;
import app.models.CriteriaModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class CriteriaController implements Initializable  {
    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String currentPeriod = "Todos";

    @FXML
    public ChoiceBox<String> periodList;
    @FXML
    public TextField criteriaName;
    @FXML
    public TextArea criteriaDescription;
    @FXML
    public TableView<CriteriaModel> tableCriteria;
    @FXML
    public TableColumn<CriteriaModel, Boolean> colCheckbox;
    @FXML
    public TableColumn<CriteriaModel, String> colNome;
    @FXML
    public TableColumn<CriteriaModel, String> colDescricao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        criteriaName.setPromptText("Adicione um critério");
        criteriaDescription.setPromptText("Descreva o critério");
        periodList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handlePeriodListSelectionChange(newValue);
        });
        fetchPeriods();
    }

    public void voltarTelaProfessor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/professorScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("Tela do Professor");
        stage.show();
    }

    private void fetchPeriods() {
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount = "SELECT * FROM periodo ORDER BY ano";
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            ArrayList<String> periodOptionsList = new ArrayList<>();
            periodOptionsList.add("Todos");

            while (resultSet.next()) {
                String semester = resultSet.getString("semestre");
                String year = resultSet.getString("ano");

                periodOptionsList.add(semester + "º semestre - " + year);
            }
            periodList.getItems().addAll(periodOptionsList);
            periodList.setValue(currentPeriod);
        } catch (SQLException e) {
            System.out.println("Erro no SQL: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    private void fetchCriterias(String period) {
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount;
            boolean isAllPeriods = Objects.equals(period, "Todos");
            int[] curPeriod;
            try {
                curPeriod = isAllPeriods ? null : Utils.getPeriodFromFilter(period);
            } catch (IllegalArgumentException error) {
                throw error;
            }
            sqlCount = "SELECT id, nome, descricao FROM criterio ORDER BY nome";
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            ObservableList<CriteriaModel> criteriaList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");

                CriteriaModel criteria = new CriteriaModel(id, nome, descricao);

                if (!isAllPeriods) {
                    String checkAssociationSql = "SELECT COUNT(*) FROM criterio_periodo cp " +
                            "JOIN periodo p ON cp.periodo_id = p.id " +
                            "WHERE cp.criterio_id = ? AND p.semestre = ? AND p.ano = ? AND cp.deleted_at IS NULL";
                    try (PreparedStatement checkStmt = connection.prepareStatement(checkAssociationSql)) {
                        checkStmt.setInt(1, id);
                        checkStmt.setInt(2, curPeriod[0]);
                        checkStmt.setInt(3, curPeriod[1]);

                        ResultSet checkResult = checkStmt.executeQuery();
                        if (checkResult.next() && checkResult.getInt(1) > 0) {
                            criteria.setDeletedAt(LocalDateTime.now());
                        }
                    }
                }

                criteriaList.add(criteria);
            }

            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

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
        } catch (SQLException e) {
            System.out.println("Erro no SQL: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
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
                fetchCriterias(currentPeriod);
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
    }

    private void handlePeriodListSelectionChange(String period) {
        currentPeriod = period;
        fetchCriterias(period);
    }

    @FXML
    private void addCriteriasToSemester() {
        if (currentPeriod.equals("Todos")) {
            System.out.println("Selecione um período válido.");
            return;
        }
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
    }

}
