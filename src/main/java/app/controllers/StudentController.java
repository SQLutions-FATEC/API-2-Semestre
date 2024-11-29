package app.controllers;

import app.DAOs.*;
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
            actionEvent = (ActionEvent) ((Map<?, ?>) data).get("event");
        }
        try {
            fetchPeriod();
            fetchSprint();
            fetchCriterias();
            fetchStudents();
            setScoreLimit();
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
                setScoreLimit();
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
            Utils.setAlert("CONFIRMATION", "Definição de notas", "As notas foram registradas com sucesso!");
            Utils.setScreen(actionEvent, "alreadyEvaluatedScreen");
        });
    }

    @FXML
    public void setScoreLimit() {
        ScoreDAO scoreDAO = new ScoreDAO();
        ScoreModel score = scoreDAO.selectScoreBySprintId(teamId, sprintId);
        int scoreValue = 1000;

        if (score != null) {
            scoreValue = score.getValue();
        }

        int total = 0;

        for (EvaluationModel student : tableView.getItems()) {
            for (TableColumn<EvaluationModel, ?> column : tableView.getColumns()) {
                if (!column.getText().equals("Aluno")) {
                    Integer valorCell = (Integer) column.getCellData(student);
                    if (valorCell != null) {
                        total += valorCell;
                    }
                }
            }
        }

        sendButton.setDisable(total > scoreValue);

        pointsInfo.setText(String.format("Pontos destinados: %s (Limite: %s)", total, scoreValue));
    }

    @FXML
    public void goToLoginScreen (ActionEvent event) {
        Utils.setScreen(event, "loginScreen");
    }

    @FXML
    public void goToPastEvaluationsScreen () {
        Utils.setPopup("pastEvaluationsScreen", 400, 600, controller -> {
            if (controller instanceof PastEvaluationsController) {
                ((PastEvaluationsController) controller).passData(teamId);
            }
        });
    }
}
