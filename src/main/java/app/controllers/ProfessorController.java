package app.controllers;

import app.helpers.DatabaseConnection;
import app.models.EquipeModel;
import app.models.DataModel;
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
    @FXML
    public Label labelAvisoData;
    @FXML
    public Label labelAvisoDescSprint;
    @FXML
    public TableView<DataModel> tableData;
    @FXML
    public TableColumn<DataModel, String> colSprint;


    private final ObservableList<EquipeModel> equipeList = FXCollections.observableArrayList();
    private final ObservableList<DataModel> dataList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = DatabaseConnection.getConnection(true);

            String sqlCountData = "SELECT COUNT(*) periodo";
            statement = connection.prepareStatement(sqlCountData);
            resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0){
                carregarDataSprint();
                labelAvisoData.setText("Lista Sprint");
                labelAvisoDescSprint.setText(" ");
            }
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


        try {
            connection = DatabaseConnection.getConnection(true);

            String sqlCount = "SELECT COUNT(*) FROM equipe";
            statement = connection.prepareStatement(sqlCount);
            resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                carregarDadosEquipe();
                labelAvisoEquipe.setText("Lista de Equipes");
                labelAvisoDesc.setText("Segue a lista das equipes cadastradas:");
            }
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

    public void carregarDadosEquipe() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection(true);

            String sql = "SELECT nome, github FROM equipe";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String github = resultSet.getString("github");

                EquipeModel equipe = new EquipeModel(nome, github);
                equipeList.add(equipe);
            }

            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colGithub.setCellValueFactory(new PropertyValueFactory<>("github"));

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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/loginScreen.fxml")));
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

    public void definirDataSprint(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/professor/setSprintData.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Definir Data Sprint");
        stage.show();
    }

    public void carregarDataSprint() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection(true);

            String sql = "SELECT ano FROM periodo";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String ano = resultSet.getString("Ano");
                String semestre = resultSet.getString("Semestre: ");
                String descricao = resultSet.getString("Semestre: ");
                String data_inicio = resultSet.getString("Semestre: ");
                String data_fim = resultSet.getString("Semestre: ");

                DataModel data = new DataModel(ano, semestre, descricao, data_inicio, data_fim);
                dataList.add(data);
            }

            colSprint.setCellValueFactory(new PropertyValueFactory<>("Ano"));


            tableData.setItems(dataList);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    }
