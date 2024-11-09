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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVGerador {
    public static void gerarCsv(String caminhoArquivo, int equipeID, int periodoID, int sprintID) throws SQLException {
        try {
            TeamDAO teamDAO = new TeamDAO();
            String nomeEquipe = teamDAO.selectTeams().stream()
                    .filter(team -> team.getId() == equipeID)
                    .map(TeamModel::getName)
                    .findFirst()
                    .orElse("Equipe Desconhecida");

            File file = new File(caminhoArquivo);
            if (file.exists()) {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String novoCaminho = caminhoArquivo.replaceFirst("(\\.csv)$", "_" + nomeEquipe + "_" + timestamp + "$1");
                file = new File(novoCaminho);
                caminhoArquivo = file.getAbsolutePath();
            } else {
                caminhoArquivo = CaminhoDownloads.obterCaminhoDownloads() + "\\" + nomeEquipe + "_relatorio.csv";
            }

            Connection conn = DatabaseConnection.getConnection(true);
            QueryDB CDB = new QueryDB(conn);
            SprintDAO sprintDAO = new SprintDAO();

            String descricaoSprint = sprintDAO.selectSprints(periodoID).stream()
                    .filter(sprint -> sprint.getId() == sprintID)
                    .map(SprintModel::getDescription)
                    .findFirst()
                    .orElse("Sprint Desconhecida");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
                writer.write("Equipe: " + nomeEquipe);
                writer.newLine();

                writer.write("Sprint: " + descricaoSprint);
                writer.newLine();

                writer.write("Usuario");
                writer.write(CDB.pegarCriterios(periodoID));
                writer.newLine();

                writer.write(CDB.calcMediaGeralEquipe(periodoID, sprintID, equipeID));
                writer.newLine();

                System.out.println("Arquivo CSV gerado com sucesso em: " + caminhoArquivo);
                Utils.setAlert("CONFIRMATION", "CSV", "Arquivo CSV gerado com sucesso em: " + caminhoArquivo);
            }
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo CSV: " + e.getMessage());
        }
    }
}