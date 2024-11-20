package app.controllers;

import app.DAOs.LoginDAO;
import app.DAOs.SetScoreDAO;
import app.DAOs.SprintDAO;
import app.DAOs.UserDAO;
import app.helpers.Utils;
import app.models.SprintModel;
import app.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    int teamId = 0;

    private boolean checkEvaluationPeriod() {
        SprintDAO sprintDAO = new SprintDAO();
        SprintModel sprint = sprintDAO.selectPastSprint();

        Date sprintEndDate = sprint.getEndDate();

        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sprintEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date setScoreDeadlineDate = calendar.getTime();

        SetScoreDAO setScoreDAO = new SetScoreDAO();
        Date scoreDate = setScoreDAO.selectScoreDateBySprintId(teamId, sprint.getId());

        if (currentDate.before(sprintEndDate) || (scoreDate == null && currentDate.before(setScoreDeadlineDate))) {
            return false;
        }

        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(setScoreDeadlineDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date evaluationPeriodDeadlineDate = calendar2.getTime();

        if (scoreDate == null) {
            return currentDate.before(evaluationPeriodDeadlineDate);
        } else {
            Calendar calendar3 = Calendar.getInstance();
            calendar.setTime(scoreDate);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            evaluationPeriodDeadlineDate = calendar3.getTime();

            return currentDate.after(scoreDate) && currentDate.before(evaluationPeriodDeadlineDate);
        }
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
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
                Map<String, Object> data = new HashMap<>();
                data.put("userEmail", email);
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
