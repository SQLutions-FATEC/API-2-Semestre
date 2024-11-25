package app.controllers;

import app.helpers.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class OutOfSetScorePeriodController {
    @FXML
    public void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }
}
