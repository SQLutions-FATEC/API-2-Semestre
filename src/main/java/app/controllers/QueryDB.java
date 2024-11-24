package app.controllers;

import app.helpers.DatabaseConnection;

import java.sql.*;

public class QueryDB {
    private final Connection conn;

    public QueryDB(Connection conn) {
        this.conn = conn;
    }
    public String calcMediaGeralEquipe(int periodoID, int sprintID, int equipeID) throws SQLException {
        Connection conn = DatabaseConnection.getConnection(true);

        String sql = "SELECT COUNT(*) FROM usuario WHERE equipe = ? AND deleted_at IS NULL";

        String[] aluno;
        int i = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipeID);

            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            aluno = new String[count];
        }

        sql = "SELECT nome FROM usuario where equipe = ? AND deleted_at IS NULL";

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
                    SELECT AVG(n.valor) AS media_nota
                    FROM nota n
                    JOIN usuario u ON n.avaliado = u.id
                    JOIN criterio c ON n.criterio = c.id
                    JOIN equipe e ON u.equipe = e.id
                    JOIN periodo ON periodo.id = ?
                    JOIN sprint ON sprint.id = ? AND n.sprint = sprint.id
                    WHERE e.id = ? AND u.nome = ? AND u.deleted_at IS NULL
                    GROUP BY u.nome, c.nome
                    ORDER BY u.nome, c.nome
                  """;

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, periodoID);
                pstmt.setInt(2, sprintID);
                pstmt.setInt(3, equipeID);
                pstmt.setString(4, String.valueOf(aluno[i]));

                ResultSet rs = pstmt.executeQuery();

                mediaGeral.append(aluno[i]);

                while (rs.next()) {
                    int media = Math.round(rs.getFloat("media_nota"));

                    mediaGeral.append(",").append(media);
                }
                mediaGeral.append("\n");
            }
        }

        conn.close();
        return mediaGeral.toString();
    }
}
