package app.controllers;

import app.helpers.DatabaseConnection;
import app.models.Aluno;
import app.models.NotaModel;
import app.models.SprintModel;
import app.helpers.Utils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentController implements Initializable {
    protected Stage stage;
    protected Parent root;
    protected Scene scene;
    String currentSprint;
    Integer selectedSprintId;
    Integer selectedPeriodId;
    Integer selectedTeamId;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    @FXML
    public TableView<Aluno> tableView;

    @FXML
    private ComboBox<String> choiceBoxMudarSprint;

    @FXML
    private Label LabelNumeroSprint;

    @FXML
    private Label LabelDataSprint;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchSprint();
        fetchCriterias();
    }

    public void passData(int teamId, int periodId) {
        selectedPeriodId = periodId;
        selectedTeamId = teamId;
        choiceBoxMudarSprint.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleSprintListSelectionChange(newValue));
        fetchSprint();
    }

    private final ObservableList<Aluno> studentList = FXCollections.observableArrayList();
    private final Map<String, Integer> sprintIdMap = new HashMap<>();
    ArrayList<String> sprintOptionsList = new ArrayList<>();

    private void handleSprintListSelectionChange(String sprint) {
        currentSprint = sprint;
        selectedSprintId = sprintIdMap.get(sprint);
        studentList.clear();
        fetchCriterias();
    }

    private void fetchCriterias() {
        try {
            connection = DatabaseConnection.getConnection(true);

            tableView.getColumns().clear();

            String sqlCount = String.format(
                    "SELECT c.nome AS nome FROM criterio_periodo cp " +
                            "JOIN criterio c ON cp.criterio_id = c.id WHERE cp.periodo_id = 1");
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            ObservableList<TableColumn<Aluno, Integer>> columns = FXCollections.observableArrayList();

            TableColumn<Aluno, String> nomeColumn = new TableColumn<>("Aluno");
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            int colunaAlunoWidth = 100;
            nomeColumn.setPrefWidth(colunaAlunoWidth);
            tableView.getColumns().add(nomeColumn);

            while (resultSet.next()) {
                String criterioNome = resultSet.getString("nome");

                TableColumn<Aluno, Integer> column = new TableColumn<>(criterioNome);
                columns.add(column);
            }
            tableView.getColumns().addAll(columns);

            double larguraTabela = 580;
            double larguraRestante = larguraTabela - colunaAlunoWidth;
            double larguraPorColuna = larguraRestante / columns.size();

            for (TableColumn<Aluno, Integer> column : columns) {
                String criterioNome = column.getText();
                column.setCellValueFactory(cellData ->
                        new SimpleIntegerProperty(cellData.getValue().getAverage(criterioNome)).asObject()
                );
                column.setPrefWidth(larguraPorColuna);
            }
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

    private void fetchSprint() {
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount = String.format("SELECT * FROM sprint s WHERE s.periodo = 1 ORDER BY s.data_inicio");
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("descricao");
                Date dataInicio = resultSet.getDate("data_inicio");
                Date dataFim = resultSet.getDate("data_fim");

                new SprintModel(id, description, dataInicio, dataFim);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedStartDate = dateFormat.format(SprintModel.getDataInicio());
                String formattedEndDate = dateFormat.format(SprintModel.getDataFim());

                String sprintDescription = description + ": (" + formattedStartDate + " - " + formattedEndDate + ")";
                sprintOptionsList.add(sprintDescription);
                sprintIdMap.put(sprintDescription, id);
                LabelDataSprint.setText(sprintDescription);

            }
            choiceBoxMudarSprint.getItems().addAll(sprintOptionsList);
            String currentSprint = Utils.getCurrentSprint(sprintOptionsList);
            if (currentSprint != null) {
                choiceBoxMudarSprint.setValue(currentSprint);
            } else {
                choiceBoxMudarSprint.setValue(sprintOptionsList.getFirst());
            }
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

    public void ConfirmarNotas() {
        Connection connection = null;
        PreparedStatement statementNota = null;

        try {
            connection = DatabaseConnection.getConnection(true);
            String sqlNota = "INSERT INTO nota (valor, avaliador, avaliado, criterio, periodo, sprint) VALUES (?, ?, ?, ?, ?, ?)";
            statementNota = connection.prepareStatement(sqlNota);

            for (NotaModel nota : tableView.getItems()) {
                statementNota.setInt(1, nota.getValor());
                statementNota.setInt(2, nota.getAvaliador());
                statementNota.setInt(3, nota.getAvaliado());
                statementNota.setInt(4, nota.getCriterio());
                statementNota.setInt(5, nota.getPeriodo());
                statementNota.setInt(6, nota.getSprint());

                try {
                    statementNota.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Erro: "+ e.getMessage());
                }
            }

            System.out.println("Notas registradas no banco de dados com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao preparar a declaração SQL: " + e.getMessage());
        } finally {
            try {
                if (statementNota != null) {
                    statementNota.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }



    public void voltarPrincipalScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/loginScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

}
