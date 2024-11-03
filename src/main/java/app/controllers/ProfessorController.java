package app.controllers;

import app.DAOs.PeriodDAO;
import app.helpers.DatabaseConnection;
import app.helpers.Utils;
import app.models.EquipeModel;
import app.models.PeriodModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProfessorController implements Initializable {
    protected Scene scene;

    @FXML
    public TableView<EquipeModel> teamTable;
    @FXML
    public TableColumn<EquipeModel, String> colName;
    @FXML
    public TableColumn<EquipeModel, String> colGithub;
    @FXML
    public TableColumn<EquipeModel, Void> colVisualize;
    @FXML
    public Label title;
    @FXML
    public Label description;
    @FXML
    public ChoiceBox<String> periodChoiceBox;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String[] period = Utils.getCurrentSemesterAndYear();
    String currentPeriod;
    Integer selectedPeriodId;

    private final ObservableList<EquipeModel> teamList = FXCollections.observableArrayList();
    private final Map<String, Integer> periodIdMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        periodChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handlePeriodListSelectionChange(newValue);
        });
        fetchPeriods();
    }

    private void fetchTeams() {
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount = "SELECT COUNT(*) FROM equipe";
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                carregarDadosEquipe();
                title.setText("Lista de Equipes");
                description.setText("Segue a lista das equipes cadastradas:");
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

    private void fetchPeriods() {
        ArrayList<String> periodOptionsList = new ArrayList<>();

        PeriodDAO periodDAO = new PeriodDAO();
        ObservableList<PeriodModel> periodList = periodDAO.selectPeriods();

        for (PeriodModel period : periodList) {
            String periodName = String.format("%dº semestre - %d", period.getSemester(), period.getYear());
            periodOptionsList.add(periodName);
        }

        periodChoiceBox.getItems().addAll(periodOptionsList);
        periodChoiceBox.setValue(period[1] + " - " + period[0]);
    }

    public void carregarDadosEquipe() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection(true);
            int[] curPeriod = Utils.getPeriodFromFilter(currentPeriod);
            String sql = String.format("SELECT e.id,e.nome,e.github from equipe_periodo ep join periodo p on ep.periodo_id = p.id join equipe e on ep.equipe_id = e.id where p.semestre = '%d' and p.ano = '%d'", curPeriod[0], curPeriod[1] );
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String github = resultSet.getString("github");

                EquipeModel equipe = new EquipeModel(id, nome, github);
                teamList.add(equipe);
            }

            colName.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colGithub.setCellValueFactory(new PropertyValueFactory<>("github"));

            colVisualize.setCellFactory(column -> new TableCell<>() {
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Button btn = new Button("Visualizar");
                        btn.setOnAction((ActionEvent event) -> {
                            EquipeModel equipe = getTableView().getItems().get(getIndex());
                            openPopup(equipe.getId(), selectedPeriodId);
                        });
                        setGraphic(btn);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            });
            teamTable.setItems(teamList);

        } catch (SQLException e) {
            System.out.println("Erro ao carregar os dados da equipe: " + e.getMessage());
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

    private void openPopup(int teamId, int periodId) {
        Utils.setPopup("averageScreen", 400, 600, controller -> {
            if (controller instanceof AverageController) {
                ((AverageController) controller).passData(teamId, periodId);
            }
        });
    }

    private void handlePeriodListSelectionChange(String period) {
        currentPeriod = period;
        selectedPeriodId = periodIdMap.get(period);
        teamList.clear();
        carregarDadosEquipe();
    }

    @FXML
    public void goToLoginScreen(ActionEvent event) {
        Utils.setScreen(event, "loginScreen");
    }

    @FXML
    public void goToAddStudentScreen(ActionEvent event) {
        Utils.setScreen(event, "addStudentScreen");
    }

    @FXML
    public void goToCriteriaScreen(ActionEvent event) {
        Utils.setScreen(event, "criteriaScreen");
    }

    @FXML
    public void goToEditStudentScreen(ActionEvent event) {
        Utils.setScreen(event, "editStudentScreen");
    }

    @FXML
    public void goToSetScoreScreen(ActionEvent event) {
        Utils.setScreen(event, "setScore");
    }
}
