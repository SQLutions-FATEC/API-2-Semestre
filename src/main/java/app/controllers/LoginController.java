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
//        ainda falta validar se esta dentro do periodo da sprint. Com o professor tambem
        SprintDAO sprintDAO = new SprintDAO();
        SprintModel sprint = sprintDAO.selectCurrentSprint();

        SetScoreDAO setScoreDAO = new SetScoreDAO();
        Date date = setScoreDAO.selectScoreDateBySprintId(teamId, sprint.getId());

        if (date == null) {
            return false;
        }

        Date startDate = sprint.getStartDate();
        Date endDate = sprint.getEndDate();
        return date.before(endDate) && date.after(startDate);
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
