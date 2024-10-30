package app.controllers;

import app.helpers.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AverageController implements Initializable {

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void inicializarDados(String nomeUsuario) {
    }
}
