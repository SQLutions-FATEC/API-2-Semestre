package app.controllers;

import app.helpers.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class ConsultationDB {
    private final Connection conn;

    public ConsultationDB(Connection conn) {
        this.conn = conn;
    }

    public String pegarCriterios() throws SQLException {
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
            pstmt.setString(1, String.valueOf(1));
            pstmt.setString(2, String.valueOf(1));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                criterioSQL.append(",");
                criterioSQL.append(rs.getString("criterio"));
            }
        }

        return criterioSQL.toString();
    }

    public Map<String, Map<String, Double>> obterMediaNotasPorEquipe(String semestreId, String sprintId, String equipeId) throws SQLException {
        String sql = """
            SELECT
              usuario.nome AS aluno_nome,
              criterio.nome AS criterio_nome,
              AVG(nota.valor) AS media_nota
            FROM
              nota
            JOIN usuario ON nota.avaliado = usuario.ra
            JOIN criterio ON nota.criterio = criterio.id
            JOIN equipe ON usuario.equipe = equipe.id
            JOIN periodo ON periodo.id = 1
            JOIN sprint ON sprint.id = 2 AND nota.sprint = sprint.id
            WHERE
              equipe.id = 1
            GROUP BY
              usuario.nome, criterio.nome
            ORDER BY
              usuario.nome, criterio.nome
        """;

        Map<String, Map<String, Double>> medias = new HashMap<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, semestreId);
            pstmt.setString(2, sprintId);
            pstmt.setString(3, equipeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String alunoNome = rs.getString("aluno_nome");
                String criterioDescricao = rs.getString("criterio_descricao");
                double mediaNota = rs.getDouble("media_nota");

                medias.computeIfAbsent(alunoNome, k -> new HashMap<>()).put(criterioDescricao, mediaNota);
            }
        }

        return medias;
    }
}
