package app.controllers;

import app.DAOs.*;
import app.helpers.Utils;
import app.models.ScoreModel;
import app.models.SprintModel;
import app.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    int teamId = 0;
    int sprintId = 0;
    String email;

    private boolean checkEvaluationPeriod() {
        SprintDAO sprintDAO = new SprintDAO();
        SprintModel sprint = sprintDAO.selectPastSprint();
        sprintId = sprint.getId();

        Date sprintEndDate = sprint.getEndDate();
        Date setScoreDeadlineDate = Utils.setDate(sprintEndDate, 7);
        Date currentDate = new Date();

        ScoreDAO scoreDAO = new ScoreDAO();
        ScoreModel score = scoreDAO.selectScoreBySprintId(teamId, sprint.getId());
        Date scoreDate = null;

        if (score != null) {
            scoreDate = score.getDate();
        }

        if (currentDate.before(sprintEndDate) || (scoreDate == null && currentDate.before(setScoreDeadlineDate))) {
            return false;
        }

        Date evaluationPeriodDeadlineDate = Utils.setDate(setScoreDeadlineDate, 7);

        if (scoreDate == null) {
            return currentDate.before(evaluationPeriodDeadlineDate);
        } else {
            evaluationPeriodDeadlineDate = Utils.setDate(scoreDate, 7);

            return currentDate.after(scoreDate) && currentDate.before(evaluationPeriodDeadlineDate);
        }
    }

    private boolean checkSprintEvaluation() {
        GradeDAO averageGradeDAO = new GradeDAO();
        int evaluations = averageGradeDAO.selectUserGradeEvaluationBySprint(email, sprintId);

        return evaluations > 0;
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            Utils.setAlert("ERROR", "Erro de login", "Por favor, insira o email e a senha");
            return;
        }

        UserDAO userDAO = new UserDAO();
        UserModel user = userDAO.selectUserByLogin(email, password);

        if (user != null) {
            teamId = user.getEquipeId();
            if (teamId > 0) {
                boolean isEvaluationPeriod = checkEvaluationPeriod();
                if (!isEvaluationPeriod) {
                    Utils.setScreen(event, "outOfEvaluationPeriodScreen");
                    return;
                }
                boolean hasAlreadyEvaluated = checkSprintEvaluation();
                if (hasAlreadyEvaluated) {
                    Utils.setScreen(event, "alreadyEvaluatedScreen");
                    return;
                }
                Map<String, Object> data = new HashMap<>();
                data.put("userEmail", email);
                data.put("teamId", teamId);
                data.put("event", event);
                Utils.setScreen(event, "studentScreen", data);
            } else {
                Utils.setScreen(event, "professorScreen", email);
            }
        } else {
            Utils.setAlert("ERROR", "Erro de login", "Email ou senha não autorizado");
        }
    }

    @FXML
    private void createDatabase() {
        LoginDAO loginDAO = new LoginDAO();
        try {
            loginDAO.executeSQLFromFile("/assets/sql/schema.sql");
            loginDAO.executeSQLFromFile("/assets/sql/default_dump.sql");
            Utils.setAlert("CONFIRMATION", "Preenchimento do banco", "Tabelas criadas com sucesso!");
        } catch (Exception e) {
            Utils.setAlert("ERROR", "Preenchimento do banco", "Erro ao criar as tabelas: " + e.getMessage());
        }

    }

    @FXML
    private void fillDatabase() {
        LoginDAO loginDAO = new LoginDAO();
        try {
            loginDAO.executeSQLFromFile("/assets/sql/dump.sql");
            Utils.setAlert("CONFIRMATION", "Preenchimento do banco", "As tabelas foram populadas com sucesso!");
        } catch (Exception e) {
            Utils.setAlert("ERROR", "Preenchimento do banco", "Erro ao popular as tabelas: " + e.getMessage());
        }
    }
}
