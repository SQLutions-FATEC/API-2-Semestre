package app.controllers;

import app.DAOs.SprintDAO;
import app.helpers.DatabaseConnection;
import app.interfaces.ScreenController;
import app.models.AvaliacaoModel;
import app.models.SprintModel;
import app.helpers.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentController implements ScreenController {
    protected Scene scene;
    Integer selectedPeriodId = 1;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    @FXML
    public TableView<AvaliacaoModel> tableView;

    @FXML
    private ComboBox<String> choiceBoxMudarSprint;

    @Override
    public void initData(Object data) {
//        if (data instanceof Map) {
//            int teamId = (int) data.get("teamId");
//            String userEmail = (String) data.get("userEmail");
//        }
        try {
            fetchCriterias();
            fetchSprint();
            fetchAlunos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final ObservableList<AvaliacaoModel> studentList = FXCollections.observableArrayList();

    private final Map<String, Integer> sprintIdMap = new HashMap<>();

    ArrayList<String> sprintOptionsList = new ArrayList<>();

    private void fetchAlunos() throws SQLException {
        try {
            connection = DatabaseConnection.getConnection(true);
            String sql = String.format("SELECT us.nome AS nome FROM usuario us " +
                    " WHERE us.equipe = 1");
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String alunoNome = resultSet.getString("nome");

                AvaliacaoModel student = new AvaliacaoModel(alunoNome, 0);

                studentList.add(student);
            }
            tableView.setItems(studentList);
        } catch (SQLException e) {
            System.out.println("Erro no SQL: " + e.getMessage());
        }
    }

    private void fetchCriterias() {
        try {
            connection = DatabaseConnection.getConnection(true);
            tableView.getColumns().clear();
            String sql = String.format("SELECT * FROM criterio_periodo cp " +
                    "JOIN criterio c ON cp.criterio_id = c.id WHERE cp.periodo_id = 1");
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            ObservableList<TableColumn<AvaliacaoModel, Integer>> columns = FXCollections.observableArrayList();

            TableColumn<AvaliacaoModel, String> nomeColumn = new TableColumn<>("Aluno");
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

            int colunaAlunoWidth = 100;
            nomeColumn.setPrefWidth(colunaAlunoWidth);
            tableView.getColumns().add(nomeColumn);

            while (resultSet.next()) {
                String criterioNome = resultSet.getString("nome");
                TableColumn<AvaliacaoModel, Integer> column = new TableColumn<>(criterioNome);

                column.setCellValueFactory(cellData -> {
                    AvaliacaoModel aluno = cellData.getValue();
                    return new SimpleObjectProperty<>(aluno.getNota(criterioNome));
                });

                column.setCellFactory(ComboBoxTableCell.forTableColumn(0, 1, 2, 3));

                column.setOnEditCommit(event -> {
                    AvaliacaoModel aluno = event.getRowValue();
                    aluno.setNotas(criterioNome, event.getNewValue());
                });

                columns.add(column);
            }

            tableView.getColumns().addAll(columns);
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
        SprintDAO sprintDAO = new SprintDAO();
        ObservableList<SprintModel> sprintList = sprintDAO.selectSprints(selectedPeriodId);


            for (SprintModel sprint : sprintList) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedStartDate = dateFormat.format(SprintModel.getStartDate());
                String formattedEndDate = dateFormat.format(SprintModel.getEndDate());

                String sprintDescription = sprint.getDescription() + ": (" + formattedStartDate + " - " + formattedEndDate + ")";
                sprintOptionsList.add(sprintDescription);
                sprintIdMap.put(sprintDescription, sprint.getId());
            }
            choiceBoxMudarSprint.getItems().addAll(sprintOptionsList);
            String currentSprint = Utils.getCurrentSprint(sprintOptionsList);
            if (currentSprint != null) {
                choiceBoxMudarSprint.setValue(currentSprint);
            } else {
                choiceBoxMudarSprint.setValue(sprintOptionsList.getFirst());
            }
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

    public Integer obterIdDoCriterio(String nomeCriterio) {
        Integer idCriterio = null;
        try {
            connection = DatabaseConnection.getConnection(true);
            String sql = "SELECT id FROM criterio WHERE nome = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nomeCriterio);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                idCriterio = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar o ID do critério: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
        return idCriterio;
    }

    public Integer obterIdDaSprint() {
        String sprintSelecionada = choiceBoxMudarSprint.getValue();
        Integer idSprint = sprintIdMap.get(sprintSelecionada);

        return idSprint;

    }

    public Integer obterIdDoAvaliado(String nomeAluno) {
        Integer idAluno = null;
        try {
            connection = DatabaseConnection.getConnection(true);
            String sql = "SELECT id FROM usuario WHERE nome = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nomeAluno);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                idAluno = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar o ID do aluno: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
        return idAluno;
    }

    @FXML
    public void ConfirmarNotas() {
        Connection connection = null;
        PreparedStatement statementNota = null;

        for (AvaliacaoModel aluno : tableView.getItems()) {
            for (TableColumn<AvaliacaoModel, ?> column : tableView.getColumns()) {
                if (!column.getText().equals("Aluno")) {

                    Integer notaValor = (Integer) column.getCellData(aluno);

                    if (notaValor != null) {
                        String nomeCriterio = column.getText();
                        Integer idCriterio = obterIdDoCriterio(nomeCriterio);

                        String nomeAluno = aluno.getNome();
                        Integer idAvaliado = obterIdDoAvaliado(nomeAluno);

                        Integer idSprint = obterIdDaSprint();

                        try {
                            connection = DatabaseConnection.getConnection(true);
                            String sqlNota = "INSERT INTO nota (valor, avaliador, avaliado, criterio, periodo, sprint) VALUES (?, ?, ?, ?, ?, ?)";
                            statementNota = connection.prepareStatement(sqlNota);

                            statementNota.setInt(1, notaValor);
                            statementNota.setInt(2, 19);
                            statementNota.setInt(3, idAvaliado);
                            statementNota.setInt(4, idCriterio);
                            statementNota.setInt(5, selectedPeriodId);
                            statementNota.setInt(6, idSprint);

                            statementNota.executeUpdate();
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
                }
            }
        }
        System.out.println("Notas registradas no banco de dados com sucesso!");
    }

    @FXML
    public void goToLoginScreen (ActionEvent event){
        Utils.setScreen(event, "loginScreen");
    }
    @FXML
    private void abrirHistoricoAvaliacoes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/HistoricoAvaliacoes.fxml"));
            Parent historicoRoot = loader.load();
            Stage historicoStage = new Stage();
            historicoStage.setScene(new Scene(historicoRoot));
            historicoStage.setTitle("Histórico de Avaliações");
            historicoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirHistoricoAvaliacoes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/HistoricoAvaliacoes.fxml"));
            Parent historicoRoot = loader.load();
            Stage historicoStage = new Stage();
            historicoStage.setScene(new Scene(historicoRoot));
            historicoStage.setTitle("Histórico de Avaliações");
            historicoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

