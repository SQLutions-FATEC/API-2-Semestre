package app.controllers;

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
    private Button buttonEnviarCSV;

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
        BufferedReader leitor = null;
        String line;
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

            leitor = new BufferedReader(new FileReader(file));

            while ((line = leitor.readLine()) != null) {
                String[] linha = line.split(",");

                if (linha.length < 4) {
                    System.out.println("Linha inválida: " + line);
                    continue;
                }

                statement.setString(1, linha[0]);
                statement.setString(2, linha[1]);
                statement.setString(3, linha[2]);
                statement.setString(4, linha[3]);
                statement.setString(5, linha[4]);

                try {
                    statement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Erro ao inserir linha: " + line + " - " + e.getMessage());
                }
            }

            System.out.println("Dados importados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao preparar a declaração SQL: " + e.getMessage());
        } finally {
            try {
                if (leitor != null) {
                    leitor.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (IOException | SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
}
