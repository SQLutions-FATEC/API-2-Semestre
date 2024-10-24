package app.controllers;

import app.helpers.ConexaoBanco;
import app.models.EquipeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfessorController implements Initializable {
    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    @FXML
    public TableView<EquipeModel> tableEquipe;
    @FXML
    public TableColumn<EquipeModel, String> colNome;
    @FXML
    public TableColumn<EquipeModel, String> colGithub;
    @FXML
    public Label labelAvisoEquipe;
    @FXML
    public Label labelAvisoDesc;


    private final ObservableList<EquipeModel> equipeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = ConexaoBanco.getConnection();
            if (connection == null) {
                System.out.println("Falha ao estabelecer a conexão com o banco de dados.");
                return;
            }

            String sqlCount = "SELECT COUNT(*) FROM equipe";
            statement = connection.prepareStatement(sqlCount);
            rs = statement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                carregarDadosEquipe();
                labelAvisoEquipe.setText("Lista de Equipes");
                labelAvisoDesc.setText("Segue a lista das equipes cadastradas:");
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    public void carregarDadosEquipe() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConexaoBanco.getConnection();
            if (connection == null) {
                System.out.println("Falha ao estabelecer a conexão com o banco de dados.");
                return;
            }

            String sql = "SELECT nome, link_github FROM equipe";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String github = resultSet.getString("link_github");

                EquipeModel equipe = new EquipeModel(nome, github);
                equipeList.add(equipe);
            }

            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colGithub.setCellValueFactory(new PropertyValueFactory<>("link_github"));

            tableEquipe.setItems(equipeList);

        } catch (SQLException e) {
            System.out.println("Erro ao carregar os dados da equipe: " + e.getMessage());
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

    public void voltarPrincipalScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/student/mainScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public void trocarCSVScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/professor/csvScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Insira um arquivo CSV");
        stage.show();
    }

    public void definirCriteriosCSVScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/professor/criteriaScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public void editarAluno(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/professor/editStudentScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Editar aluno");
        stage.show();
    }
    public void definirPontuacao(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/professor/SetScore.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
