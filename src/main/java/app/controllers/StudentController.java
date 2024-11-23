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
import javafx.scene.control.*;
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
    @FXML
    public Button sendButton;
    @FXML
    public Label pointsInfo;

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
            LimitePontos();
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
            String sql = "SELECT us.nome AS nome FROM usuario us WHERE us.equipe = 1 AND us.deleted_at IS NULL";
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
            String sql = "SELECT * FROM criterio_periodo cp JOIN criterio c ON cp.criterio_id = c.id WHERE cp.periodo_id = 1";
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
                    LimitePontos();
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
        return sprintIdMap.get(sprintSelecionada);
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
    public void LimitePontos() {
        Connection connection = null;
        PreparedStatement statementLimite = null;
        ResultSet rsLimite;
        int totalUsado = 0;
        int totalLimite = 0;
        Integer idSprint = obterIdDaSprint();

        try {
            connection = DatabaseConnection.getConnection(true);
            String sqlLimitePontos = "SELECT valor FROM pontuacao WHERE sprint = ? AND equipe = 1;";
            statementLimite = connection.prepareStatement(sqlLimitePontos);

            statementLimite.setInt(1,idSprint);

            rsLimite = statementLimite.executeQuery();

            while (rsLimite.next()) {
                totalLimite = rsLimite.getInt("valor");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao preparar a declaração SQL: " + e.getMessage());
        } finally {
            try {
                if (statementLimite != null) {
                    statementLimite.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        System.out.println("Total de pontuacoes: " + totalLimite);

        for (AvaliacaoModel aluno : tableView.getItems()) {
            for (TableColumn<AvaliacaoModel, ?> column : tableView.getColumns()) {
                if (!column.getText().equals("Aluno")) {
                    Integer valorCell = (Integer) column.getCellData(aluno);
                    if (valorCell != null) {
                        totalUsado += valorCell;
                    }
                }
            }
        }

        if (totalUsado > totalLimite) {
            sendButton.setDisable(true);
            sendButton.setStyle("-fx-background-color: #FF0000;");
        } else {
            sendButton.setDisable(false);
            sendButton.setStyle("-fx-background-color: #00FF00;");
        }

        pointsInfo.setText(String.format("Pontos destinados: %s (Limite: %s)", totalUsado, totalLimite));
    }

    @FXML
    public void goToLoginScreen (ActionEvent event){
        Utils.setScreen(event, "loginScreen");
    }
}

