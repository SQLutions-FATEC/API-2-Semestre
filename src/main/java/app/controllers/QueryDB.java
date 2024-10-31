package app.controllers;

import app.helpers.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class QueryDB {
    private final Connection conn;

    public QueryDB(Connection conn) {
        this.conn = conn;
    }

    public String pegarCriterios(int periodoID) throws SQLException {
        Connection conn = DatabaseConnection.getConnection(true);

        String sql = """
                SELECT distinct criterio.nome AS criterio
                FROM nota
                JOIN criterio ON nota.criterio = criterio.id
                JOIN periodo ON periodo.id = ?
                WHERE periodo.id = ?
                GROUP BY criterio.nome
            """;

        StringBuilder criterioSQL = new StringBuilder();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(periodoID));
            pstmt.setString(2, String.valueOf(periodoID));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                criterioSQL.append(",");
                criterioSQL.append(rs.getString("criterio"));
            }
        }

        return criterioSQL.toString();
    }

    public String calcMediaGeralEquipe(int periodoID, int sprintID, int equipeID) throws SQLException {
        Connection conn = DatabaseConnection.getConnection(true);

        String sql = "SELECT nome FROM usuario where equipe = ?";

        String[] aluno;
        int i = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipeID);

            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
            }

            aluno = new String[count];
        }

        try (PreparedStatement pstmtEquipe = conn.prepareStatement(sql)) {
            pstmtEquipe.setInt(1, equipeID);

            ResultSet rsEquipe = pstmtEquipe.executeQuery();

            while (rsEquipe.next()) {
                String nome = rsEquipe.getString("nome");

                aluno[i] = nome;
                i++;
            }
        }

        StringBuilder mediaGeral = new StringBuilder();
        for (i = 0; i < aluno.length; i++) {
            sql = """
                    SELECT AVG(nota.valor) AS media_nota
                    FROM nota
                    JOIN usuario ON nota.avaliado = usuario.ra
                    JOIN criterio ON nota.criterio = criterio.id
                    JOIN equipe ON usuario.equipe = equipe.id
                    JOIN periodo ON periodo.id = ?
                    JOIN sprint ON sprint.id = ? AND nota.sprint = sprint.id
                    WHERE equipe.id = ? and usuario.nome = ?
                    GROUP BY usuario.nome, criterio.nome
                    ORDER BY usuario.nome, criterio.nome
                  """;

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, periodoID);
                pstmt.setInt(2, sprintID);
                pstmt.setInt(3, equipeID);
                pstmt.setString(4, String.valueOf(aluno[i]));

                ResultSet rs = pstmt.executeQuery();

                mediaGeral.append(aluno[i]);

                while (rs.next()) {
                    float media = rs.getFloat("media_nota");

                    mediaGeral.append(",").append(media);
                }
                mediaGeral.append("\n");
            }
        }

        conn.close();
        return mediaGeral.toString();
    }
}
