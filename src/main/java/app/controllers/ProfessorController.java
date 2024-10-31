package app.controllers;

import app.helpers.DatabaseConnection;
import app.helpers.Utils;
import app.models.EquipeModel;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProfessorController implements Initializable {
    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    @FXML
    public TableView<EquipeModel> tableEquipe;
    @FXML
    public TableColumn<EquipeModel, String> colNome;
    @FXML
    public TableColumn<EquipeModel, String> colGithub;
    @FXML
    public TableColumn<EquipeModel, Void> colVisualizar;
    @FXML
    public Label labelAvisoEquipe;
    @FXML
    public Label labelAvisoDesc;
    @FXML
    public ChoiceBox<String> ChoiceBoxPeriodo;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String[] period = Utils.obterSemestreEAnoAtual();
    String currentPeriod;
    Integer selectedPeriodId;

    private final ObservableList<EquipeModel> equipeList = FXCollections.observableArrayList();
    private final Map<String, Integer> periodIdMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChoiceBoxPeriodo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
                labelAvisoEquipe.setText("Lista de Equipes");
                labelAvisoDesc.setText("Segue a lista das equipes cadastradas:");
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
        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount = "SELECT * FROM periodo ORDER BY ano";
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            ArrayList<String> periodOptionsList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String semester = resultSet.getString("semestre");
                String year = resultSet.getString("ano");

                String periodDescription = semester + "º semestre - " + year;
                periodOptionsList.add(periodDescription);
                periodIdMap.put(periodDescription, id);
            }
            ChoiceBoxPeriodo.getItems().addAll(periodOptionsList);
            ChoiceBoxPeriodo.setValue(period[1] + " - " + period[0]);
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
                equipeList.add(equipe);
            }

            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colGithub.setCellValueFactory(new PropertyValueFactory<>("github"));

            colVisualizar.setCellFactory(column -> new TableCell<>() {
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Button btn = new Button("Visualizar");
                        btn.setOnAction((ActionEvent event) -> {
                            EquipeModel equipe = getTableView().getItems().get(getIndex());
                            abrirNovaTela(equipe.getId(), selectedPeriodId);
                        });
                        setGraphic(btn);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            });

            tableEquipe.setItems(equipeList);

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

    private void abrirNovaTela(int teamId, int periodId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/averageScreen.fxml"));
            Parent root = loader.load();

            AverageController averageController = loader.getController();
            averageController.passData(teamId, periodId);

            stage = new Stage();
            scene = new Scene(root, 800, 600);

            stage.setScene(scene);
            stage.setTitle("Médias");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePeriodListSelectionChange(String period) {
        currentPeriod = period;
        selectedPeriodId = periodIdMap.get(period);
        equipeList.clear();
        carregarDadosEquipe();
    }

    public void voltarPrincipalScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/loginScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public void trocarCSVScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/professor/csvScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Insira um arquivo CSV");
        stage.show();
    }

    public void definirCriteriosCSVScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/professor/criteriaScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public void editarAluno(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/professor/editStudentScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Editar aluno");
        stage.show();
    }
    public void definirPontuacao(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/professor/setScore.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
