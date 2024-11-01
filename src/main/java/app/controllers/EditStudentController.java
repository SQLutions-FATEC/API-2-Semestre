package app.controllers;

import app.DAOs.UserDAO;
import app.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.geometry.Side;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditStudentController implements Initializable {
    @FXML
    public TableView<UserModel> tableStudent;
    @FXML
    public TableColumn<UserModel, String> colName;
    @FXML
    public TableColumn<UserModel, String> colRA;
    @FXML
    public TableColumn<UserModel, String> colEmail;
    @FXML
    public TableColumn<UserModel, String> colTeam;
    @FXML
    public ChoiceBox<String> teamList;
    @FXML
    public TextField studentSearch;

    ObservableList<UserModel> studentList = FXCollections.observableArrayList();
    private ContextMenu suggestionsMenu = new ContextMenu();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchStudents();
        configureAutocomplete();
    }

    private void fetchStudents() {
        UserDAO UserDAO = new UserDAO();
        studentList = UserDAO.fetchStudents();
    }

    private void configureAutocomplete() {
        studentSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            updateSuggestions(newValue);
        });

        studentSearch.setOnMouseClicked(event -> {
            if (!suggestionsMenu.isShowing()) {
                updateSuggestions(studentSearch.getText());
                suggestionsMenu.show(studentSearch, Side.BOTTOM, 0, 0);
            }
        });

        studentSearch.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                suggestionsMenu.hide();
            }
        });
    }

    private void updateSuggestions(String text) {
        ObservableList<UserModel> filteredList = studentList.filtered(
                student -> student.getNome().toLowerCase().contains(text.toLowerCase())
        );

        suggestionsMenu.getItems().clear();
        for (UserModel student : filteredList) {
            MenuItem suggestionItem = new MenuItem(student.getNome());
            suggestionItem.setOnAction(event -> {
                studentSearch.setText(student.getNome());
                suggestionsMenu.hide();
            });
            suggestionsMenu.getItems().add(suggestionItem);
        }

        if (!suggestionsMenu.getItems().isEmpty()) {
            suggestionsMenu.show(studentSearch, Side.BOTTOM, 0, 0);
        } else {
            suggestionsMenu.hide();
        }
    }

    public void voltarProfessorScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/professor/professorScreen.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
