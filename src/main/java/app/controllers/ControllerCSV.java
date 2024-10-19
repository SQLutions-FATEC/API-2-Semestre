package app.controllers;

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

public class ControllerCSV extends ConexaoBanco {

    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    @FXML
    public TableView<AlunoModel> tableView;
    @FXML
    public TableColumn<AlunoModel, Integer> colRa;
    @FXML
    public TableColumn<AlunoModel, String> colNome;
    @FXML
    public TableColumn<AlunoModel, String> colEmail;
    @FXML
    public TableColumn<AlunoModel, String> colSenha;
    @FXML
    public TableColumn<AlunoModel, String> colEquipe;

    @FXML
    public Label labelNomeEquipe;
    @FXML
    public Label labelGithubEquipe;

    @FXML
    private Button buttonEnviarCSV;
    @FXML
    private Button buttonConfirmarCSV;

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

            ObservableList<AlunoModel> alunoList = FXCollections.observableArrayList();
            EquipeModel equipe = null;
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
                    labelGithubEquipe.setText(equipe.getLink_github());
                    continue;
                }

                if (linha.length < 5) {
                    System.out.println("Linha de aluno inválida: " + line);
                    continue;
                }

                AlunoModel aluno = new AlunoModel(
                        Integer.parseInt(linha[0]),
                        linha[1],
                        linha[2],
                        linha[3],
                        Integer.parseInt(linha[4])
                );
                alunoList.add(aluno);
            }

            colRa.setCellValueFactory(new PropertyValueFactory<>("ra"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
            colEquipe.setCellValueFactory(new PropertyValueFactory<>("id_equipe"));

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


    public void confirmarCSV() throws SQLException {
        Connection connection = null;
        PreparedStatement statementAluno = null;
        PreparedStatement statementEquipe = null;

        try {
            connection = ConexaoBanco.getConnection();
            if (connection == null) {
                System.out.println("Falha ao estabelecer a conexão com o banco de dados.");
                return;
            }

            statementEquipe = connection.prepareStatement("INSERT INTO equipe (nome, link_github) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
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

            String sqlAluno = "INSERT INTO usuario (ra, nome, senha, email, id_equipe) VALUES (?, ?, ?, ?, ?)";
            statementAluno = connection.prepareStatement(sqlAluno);

            for (AlunoModel aluno : tableView.getItems()) {
                statementAluno.setInt(1, aluno.getRa());
                statementAluno.setString(2, aluno.getNome());
                statementAluno.setString(3, aluno.getSenha());
                statementAluno.setString(4, aluno.getEmail());
                statementAluno.setInt(5, equipeId);
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
