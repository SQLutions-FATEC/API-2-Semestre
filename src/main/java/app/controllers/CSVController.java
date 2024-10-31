package app.controllers;

import app.helpers.DatabaseConnection;
import app.models.UserModel;
import app.models.EquipeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class CSVController extends DatabaseConnection {

    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    @FXML
    public TableView<UserModel> tableView;
    @FXML
    public TableColumn<UserModel, Integer> colRa;
    @FXML
    public TableColumn<UserModel, String> colNome;
    @FXML
    public TableColumn<UserModel, String> colEmail;
    @FXML
    public TableColumn<UserModel, String> colSenha;

    @FXML
    public Label labelNomeEquipe;
    @FXML
    public Label labelGithubEquipe;

    @FXML
    private Button buttonEnviarCSV;

    BufferedReader leitor = null;
    String line;

    @FXML
    public void enviarCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione o arquivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        Stage stage = (Stage) buttonEnviarCSV.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            processarCSV(selectedFile);
        } else {
            System.out.println("Nenhum arquivo selecionado.");
        }
    }

    private void processarCSV(File file) {
        try {
            leitor = new BufferedReader(new FileReader(file));

            ObservableList<UserModel> alunoList = FXCollections.observableArrayList();
            EquipeModel equipe;
            boolean isPrimeiraLinha = true;

            while ((line = leitor.readLine()) != null) {
                String[] linha = line.split(",");

                if (isPrimeiraLinha) {
                    if (linha.length < 2) {
                        System.out.println("Linha da equipe inválida: " + line);
                        return;
                    }
                    equipe = new EquipeModel(linha[0], linha[1]);
                    isPrimeiraLinha = false;
                    labelNomeEquipe.setText(equipe.getNome());
                    labelGithubEquipe.setText(equipe.getGithub());
                    continue;
                }

                if (linha.length != 4) {
                    System.out.println("Linha de aluno inválida: " + line);
                    continue;
                }

                UserModel aluno = new UserModel(
                        Integer.parseInt(linha[0]),
                        linha[1],
                        linha[2],
                        linha[3],
                        0
                );
                alunoList.add(aluno);
            }

            colRa.setCellValueFactory(new PropertyValueFactory<>("ra"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));

            tableView.setItems(alunoList);

            System.out.println("Dados importados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } finally {
            try {
                if (leitor != null) {
                    leitor.close();
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }


    public void confirmarCSV() {
        Connection connection = null;
        PreparedStatement statementAluno = null;
        PreparedStatement statementEquipe = null;

        try {
            connection = DatabaseConnection.getConnection(true);

            statementEquipe = connection.prepareStatement("INSERT INTO equipe (nome, github) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            String nomeEquipe = labelNomeEquipe.getText();
            String linkGithub = labelGithubEquipe.getText();
            statementEquipe.setString(1, nomeEquipe);
            statementEquipe.setString(2, linkGithub);
            int rowsAffected = statementEquipe.executeUpdate();
            int equipeId = 0;

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statementEquipe.getGeneratedKeys();
                if (generatedKeys.next()) {
                    equipeId = generatedKeys.getInt(1);
                }
            }

            String sqlAluno = "INSERT INTO usuario (ra, nome, senha, email, tipo, equipe) VALUES (?, ?, ?, ?, ?, ?)";
            statementAluno = connection.prepareStatement(sqlAluno);

            int typeStudent = 2;

            for (UserModel aluno : tableView.getItems()) {
                statementAluno.setInt(1, aluno.getRa());
                statementAluno.setString(2, aluno.getNome());
                statementAluno.setString(3, aluno.getSenha());
                statementAluno.setString(4, aluno.getEmail());
                statementAluno.setInt(5, typeStudent);
                statementAluno.setInt(6, equipeId);
                try {
                    statementAluno.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Erro ao inserir aluno: " + aluno.getNome() + " - " + e.getMessage());
                }
            }

            System.out.println("Dados confirmados e cadastrados no banco de dados com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao preparar a declaração SQL: " + e.getMessage());
        } finally {
            try {
                if (statementAluno != null) {
                    statementAluno.close();
                }
                if (statementEquipe != null) {
                    statementEquipe.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    public void voltarTelaProfessor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/professorScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("Tela do Professor");
        stage.show();
    }
}
