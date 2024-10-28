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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CriteriaController implements Initializable  {
    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

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
        fetchPeriods();
        fetchCriterias();
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
        periodList.setValue("Selecione um período");

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
            periodList.setValue("Todos");
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

    private void fetchCriterias() {
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount = "SELECT nome, descricao FROM criterio ORDER BY nome";
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            ObservableList<CriteriaModel> criteriaList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");

                CriteriaModel criteria = new CriteriaModel(nome, descricao);
                criteriaList.add(criteria);
            }

            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

            colCheckbox.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
            colCheckbox.setCellFactory(CheckBoxTableCell.forTableColumn(colCheckbox));

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
                fetchCriterias();
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
}
