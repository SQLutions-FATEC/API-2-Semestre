package app.controllers;

import app.helpers.DatabaseConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CSVGerador {
    public static void gerarCsv(String caminhoArquivo, int equipeID, int periodoID, int sprintID) throws SQLException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            // Obter a conexão com o banco de dados
            Connection conn = DatabaseConnection.getConnection(true);
            QueryDB CDB = new QueryDB(conn);

            // Escrever o cabeçalho do CSV
            writer.write("Usuario");
            writer.write(CDB.pegarCriterios(periodoID)); // Passa periodoID para pegar critérios
            writer.newLine();

            // Escrever a média geral da equipe com todos os parâmetros
            writer.write(CDB.calcMediaGeralEquipe(periodoID, sprintID, equipeID)); // Passa todos os IDs necessários
            writer.newLine();

            System.out.println("Arquivo CSV gerado com sucesso em: " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo CSV: " + e.getMessage());
        }
    }
}
