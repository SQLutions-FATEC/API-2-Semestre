package app.controllers;

import app.controllers.Model.AlunoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControllerCSV extends ConexaoBanco {

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

            ObservableList<AlunoModel> observableList = FXCollections.observableArrayList();

            while ((line = leitor.readLine()) != null) {
                String[] linha = line.split(",");

                if (linha.length < 5) {
                    System.out.println("Linha inválida: " + line);
                    continue;
                }

                AlunoModel aluno = new AlunoModel(
                        Integer.parseInt(linha[0]),
                        linha[1],
                        linha[2],
                        linha[3],
                        linha[4]
                );

                observableList.add(aluno);

                try {
                } catch (Exception e) {
                    System.out.println("Erro ao inserir linha: " + line + " - " + e.getMessage());
                }

                colRa.setCellValueFactory(new PropertyValueFactory<>("ra"));
                colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                colSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
                colEquipe.setCellValueFactory(new PropertyValueFactory<>("id_equipe"));
                tableView.setItems(observableList);
            }

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

    public void confirmarCSV(){
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConexaoBanco.getConnection();
            if (connection == null) {
                System.out.println("Falha ao estabelecer a conexão com o banco de dados.");
                return;
            }

            String sql = "INSERT INTO usuario (ra, nome, senha, email, id_equipe) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);

            for (AlunoModel aluno : tableView.getItems()) {
                statement.setInt(1, aluno.getRa());
                statement.setString(2, aluno.getNome());
                statement.setString(3, aluno.getSenha());
                statement.setString(4, aluno.getEmail());
                statement.setString(5, aluno.getId_equipe());

                try {
                    statement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Erro ao inserir aluno: " + aluno.getNome() + " - " + e.getMessage());
                }
            }

            System.out.println("Dados confirmados e cadastrados no banco de dados com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao preparar a declaração SQL: " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    public void voltarTelaProfessor() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/professorScreen.fxml"));  // novo FXML para professor
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Tela do Professor");
        stage.show();
    }
}