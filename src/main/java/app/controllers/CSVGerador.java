package app.controllers;

import app.helpers.DatabaseConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CSVGerador {
    public static void gerarCsv(String caminhoArquivo) throws SQLException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            Connection conn = DatabaseConnection.getConnection(true);
            QueryDB CDB = new QueryDB(conn);

            writer.write("Usuario");
            writer.write(CDB.pegarCriterios(1));
            writer.newLine();
            writer.write(CDB.calcMediaGeralEquipe(1,1,1));


            System.out.println("Arquivo CSV gerado com sucesso em: " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo CSV: " + e.getMessage());
        }
    }
}
