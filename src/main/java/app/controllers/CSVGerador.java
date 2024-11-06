package app.controllers;

import app.DAOs.SprintDAO;
import app.DAOs.TeamDAO;
import app.helpers.DatabaseConnection;
import app.helpers.Utils;
import app.models.SprintModel;
import app.models.TeamModel;

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
            TeamDAO teamDAO = new TeamDAO();
            SprintDAO sprintDAO = new SprintDAO();

            String nomeEquipe = teamDAO.selectTeams().stream()
                    .filter(team -> team.getId() == equipeID)
                    .map(TeamModel::getName)
                    .findFirst()
                    .orElse("Equipe Desconhecida");

            String descricaoSprint = sprintDAO.selectSprints(periodoID).stream()
                    .filter(sprint -> sprint.getId() == sprintID)
                    .map(SprintModel::getDescription)
                    .findFirst()
                    .orElse("Sprint Desconhecida");

            // Escrever a equipe
            writer.write("Equipe: " + nomeEquipe);
            writer.newLine();

            writer.write("Sprint: " + periodoID);
            writer.newLine();

            // Escrever o cabeçalho do CSV
            writer.write("Usuario");
            writer.write(CDB.pegarCriterios(periodoID)); // Passa periodoID para pegar critérios
            writer.newLine();

            // Escrever a média geral da equipe com todos os parâmetros
            writer.write(CDB.calcMediaGeralEquipe(periodoID, sprintID, equipeID)); // Passa todos os IDs necessários
            writer.newLine();

            System.out.println("Arquivo CSV gerado com sucesso em: " + caminhoArquivo);
            Utils.setAlert("CONFIRMATION", "CSV", "Arquivo CSV gerado com sucesso em" + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo CSV: " + e.getMessage());
        }
    }
}
