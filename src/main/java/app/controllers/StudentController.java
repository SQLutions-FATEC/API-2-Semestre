package app.controllers;

import app.DAOs.*;
import app.helpers.DatabaseConnection;
import app.interfaces.ScreenController;
import app.models.*;
import app.helpers.Utils;
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
    int periodId = 0;
    String userEmail;
    int evaluatorId = 0;
    int sprintId = 0;
    int teamId = 0;
    ActionEvent actionEvent;
    private final ObservableList<EvaluationModel> studentList = FXCollections.observableArrayList();
    Map<String, CriteriaModel> criteriaMap = new HashMap<>();
    Map<String, UserModel> studentNameMap = new HashMap<>();


    @Override
    public void initData(Object data) {
        if (data instanceof Map) {
            teamId = (int) ((Map<?, ?>) data).get("teamId");
            userEmail = (String) ((Map<?, ?>) data).get("userEmail");
        }
        try {
            fetchPeriod();
            fetchSprint();
            fetchCriterias();
            fetchStudents();
            LimitePontos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchPeriod() {
        PeriodDAO periodDAO = new PeriodDAO();
        periodId = periodDAO.selectCurrentPeriodId();
    }

    private void fetchSprint() {
        SprintDAO sprintDAO = new SprintDAO();
        SprintModel sprint = sprintDAO.selectPastSprint();
        sprintId = sprint.getId();
    }

    private void fetchStudents() throws SQLException {
        UserDAO userDAO = new UserDAO();
        ObservableList<UserModel> students = userDAO.selectStudentsByTeamId(teamId);

        for (UserModel student : students) {
            String studentName = student.getNome();
            String studentEmail = student.getEmail();
            if (Objects.equals(studentEmail, userEmail)) evaluatorId = student.getId();

            EvaluationModel currentStudent = new EvaluationModel(studentName, 0);

            studentList.add(currentStudent);
            studentNameMap.put(studentName, student);
        }
        tableView.setItems(studentList);
    }

    private void fetchCriterias() {
        CriteriaDAO criteriaDAO = new CriteriaDAO();
        ObservableList<CriteriaModel> criterias = criteriaDAO.selectActiveCriteriasByPeriod(periodId);

        ObservableList<TableColumn<EvaluationModel, Integer>> columns = FXCollections.observableArrayList();

        TableColumn<EvaluationModel, String> nomeColumn = new TableColumn<>("Aluno");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        int studentColumnWidth = 100;
        nomeColumn.setPrefWidth(studentColumnWidth);
        tableView.getColumns().add(nomeColumn);

        for (CriteriaModel criteria : criterias) {
            String criteriaName = criteria.getName();
            TableColumn<EvaluationModel, Integer> column = new TableColumn<>(criteriaName);

            column.setCellValueFactory(cellData -> {
                EvaluationModel student = cellData.getValue();
                return new SimpleObjectProperty<>(student.getNota(criteriaName));
            });

            column.setCellFactory(ComboBoxTableCell.forTableColumn(0, 1, 2, 3));

            column.setOnEditCommit(event -> {
                EvaluationModel student = event.getRowValue();
                student.setNotas(criteriaName, event.getNewValue());
                LimitePontos();
            });
            columns.add(column);
            criteriaMap.put(criteriaName, criteria);
        }
        tableView.getColumns().addAll(columns);
    }

    @FXML
    public void setGrades() {
        Utils.setAlert("WARNING", "Definição de notas", "Tem certeza que deseja enviar as notas? A ação não poderá ser desfeita",() -> {
            for (EvaluationModel student : tableView.getItems()) {
                for (TableColumn<EvaluationModel, ?> column : tableView.getColumns()) {
                    if (!column.getText().equals("Aluno")) {

                        Integer grade = (Integer) column.getCellData(student);

                        if (grade != null) {
                            String criteriaName = column.getText();
                            CriteriaModel criteria = criteriaMap.getOrDefault(criteriaName, null);
                            int criteriaId = (criteria != null) ? criteria.getId() : 0;

                            String evaluatedStudentName = student.getNome();
                            UserModel evaluatedStudent = studentNameMap.getOrDefault(evaluatedStudentName, null);
                            int evaluatedId = (evaluatedStudent != null) ? evaluatedStudent.getId() : 0;

                            GradeDAO gradeDAO = new GradeDAO();
                            gradeDAO.createGrade(grade, evaluatorId, evaluatedId, criteriaId, periodId, sprintId);
                        }
                    }
                }
            }
            Utils.setAlert("CONFIRMATION", "Definição de notas", "AS notas foram registradas com sucesso!", () -> {
                goToLoginScreen(actionEvent);
            });
        });
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

            statementLimite.setInt(1, sprintId);
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
        actionEvent = event;
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
