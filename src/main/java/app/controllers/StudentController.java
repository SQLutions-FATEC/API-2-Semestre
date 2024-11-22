package app.controllers;

import app.DAOs.SprintDAO;
import app.DAOs.UserDAO;
import app.helpers.DatabaseConnection;
import app.interfaces.ScreenController;
import app.models.EvaluationModel;
import app.helpers.Utils;
import app.models.SprintModel;
import app.models.UserModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentController implements ScreenController {
    @FXML
    public TableView<EvaluationModel> tableView;
    @FXML
    public Button sendButton;
    @FXML
    public Label pointsInfo;

    protected Scene scene;
    int periodId = 1;
    String userEmail;
    int idSprint = 0;
    int teamId = 0;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    private final ObservableList<EvaluationModel> studentList = FXCollections.observableArrayList();

    @Override
    public void initData(Object data) {
        if (data instanceof Map) {
            teamId = (int) ((Map<?, ?>) data).get("teamId");
            userEmail = (String) ((Map<?, ?>) data).get("userEmail");
        }
        try {
            fetchSprint();
            fetchCriterias();
            fetchStudents();
            LimitePontos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchSprint() {
        SprintDAO sprintDAO = new SprintDAO();
        SprintModel sprint = sprintDAO.selectPastSprint();
        idSprint = sprint.getId();
    }

    private void fetchStudents() throws SQLException {
        UserDAO userDAO = new UserDAO();
        ObservableList<UserModel> students = userDAO.selectStudentsByTeamId(teamId);

        for (UserModel student : students) {
            String studentName = student.getNome();
            EvaluationModel currentStudent = new EvaluationModel(studentName, 0);

            studentList.add(currentStudent);
        }
        tableView.setItems(studentList);
    }

    private void fetchCriterias() {
        try {
            connection = DatabaseConnection.getConnection(true);
            tableView.getColumns().clear();
            String sql = "SELECT * FROM criterio_periodo cp JOIN criterio c ON cp.criterio_id = c.id WHERE cp.periodo_id = ?";
            statement = connection.prepareStatement(sql);

            statement.setInt(1, periodId);

            resultSet = statement.executeQuery();

            ObservableList<TableColumn<EvaluationModel, Integer>> columns = FXCollections.observableArrayList();

            TableColumn<EvaluationModel, String> nomeColumn = new TableColumn<>("Aluno");
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

            int colunaAlunoWidth = 100;
            nomeColumn.setPrefWidth(colunaAlunoWidth);
            tableView.getColumns().add(nomeColumn);

            while (resultSet.next()) {
                String criterioNome = resultSet.getString("nome");
                TableColumn<EvaluationModel, Integer> column = new TableColumn<>(criterioNome);

                column.setCellValueFactory(cellData -> {
                    EvaluationModel aluno = cellData.getValue();
                    return new SimpleObjectProperty<>(aluno.getNota(criterioNome));
                });

                column.setCellFactory(ComboBoxTableCell.forTableColumn(0, 1, 2, 3));

                column.setOnEditCommit(event -> {
                    EvaluationModel aluno = event.getRowValue();
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

        for (EvaluationModel aluno : tableView.getItems()) {
            for (TableColumn<EvaluationModel, ?> column : tableView.getColumns()) {
                if (!column.getText().equals("Aluno")) {

                    Integer notaValor = (Integer) column.getCellData(aluno);

                    if (notaValor != null) {
                        String nomeCriterio = column.getText();
                        Integer idCriterio = obterIdDoCriterio(nomeCriterio);

                        String nomeAluno = aluno.getNome();
                        Integer idAvaliado = obterIdDoAvaliado(nomeAluno);


                        try {
                            connection = DatabaseConnection.getConnection(true);
                            String sqlNota = "INSERT INTO nota (valor, avaliador, avaliado, criterio, periodo, sprint) VALUES (?, ?, ?, ?, ?, ?)";
                            statementNota = connection.prepareStatement(sqlNota);

                            statementNota.setInt(1, notaValor);
                            statementNota.setInt(2, 19);
                            statementNota.setInt(3, idAvaliado);
                            statementNota.setInt(4, idCriterio);
                            statementNota.setInt(5, periodId);
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

        try {
            connection = DatabaseConnection.getConnection(true);
            String sqlLimitePontos = "SELECT valor FROM pontuacao WHERE sprint = ? AND equipe = ?;";
            statementLimite = connection.prepareStatement(sqlLimitePontos);

            statementLimite.setInt(1, idSprint);
            statementLimite.setInt(2, teamId);

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

        for (EvaluationModel aluno : tableView.getItems()) {
            for (TableColumn<EvaluationModel, ?> column : tableView.getColumns()) {
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
        }

        pointsInfo.setText(String.format("Pontos destinados: %s (Limite: %s)", totalUsado, totalLimite));
    }

    @FXML
    public void goToLoginScreen (ActionEvent event){
        Utils.setScreen(event, "loginScreen");
    }

    @FXML
    public void goToPastEvaluationsScreen (ActionEvent event){
        Utils.setPopup("pastEvaluationsScreen", 400, 600, controller -> {
            if (controller instanceof PastEvaluationsController) {
                ((PastEvaluationsController) controller).passData(teamId);
            }
        });
    }
}
